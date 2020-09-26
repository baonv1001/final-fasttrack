package com.webapp.webdemo.service.impl;

import com.webapp.webdemo.config.FileStorageProperties;
import com.webapp.webdemo.constants.EntityNameConstants;
import com.webapp.webdemo.constants.enums.PermissionName;
import com.webapp.webdemo.constants.enums.RoleName;
import com.webapp.webdemo.entities.Document;
import com.webapp.webdemo.entities.UserDocument;
import com.webapp.webdemo.entities.security.User;
import com.webapp.webdemo.exception.AppException;
import com.webapp.webdemo.exception.ResourceNotFoundException;
import com.webapp.webdemo.payload.request.UserDocumentRequest;
import com.webapp.webdemo.payload.response.DocumentResponse;
import com.webapp.webdemo.payload.response.PagingResponseModel;
import com.webapp.webdemo.payload.response.UserDocumentResponse;
import com.webapp.webdemo.repository.DocumentRepository;
import com.webapp.webdemo.repository.UserDocumentRepository;
import com.webapp.webdemo.repository.UserRepository;
import com.webapp.webdemo.security.dto.UserPrincipal;
import com.webapp.webdemo.service.DocumentService;
import com.webapp.webdemo.utils.ConverterUtils;
import com.webapp.webdemo.utils.FileUtils;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
@Service
public class DocumentServiceImpl implements DocumentService {
    // Helper
    private final FileStorageProperties fileStorageProperties;
    private Path fileStorageLocation;

    // Repository
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final UserDocumentRepository userDocumentRepository;

    @Override
    public DocumentResponse uploadFile(Long userNo, MultipartFile files) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userNo));

        fileStorageLocation = fileStorageProperties.getUploadPath();
        Path targetLocation = FileUtils.storeFile(fileStorageLocation, files);
        Document document = new Document();
        document.setFileName(targetLocation.getFileName().toString());
        document.setUrl(targetLocation.toAbsolutePath().normalize().toString());

        Document newDocument = documentRepository.save(document);

        UserDocument userDocument = new UserDocument();
        userDocument.setUser(user);
        userDocument.setDocument(document);

        userDocumentRepository.save(userDocument);

        return DocumentResponse.builder()
                .documentNo(newDocument.getDocumentNo())
                .fileName(newDocument.getFileName())
                .build();
    }

    @Override
    public Resource downloadFile(UsernamePasswordAuthenticationToken principal, Long documentNo) {
        Optional<Document> documentOptional = documentRepository.findById(documentNo);
        if(documentOptional.isPresent()){
            Document document = documentOptional.get();
            List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) principal.getAuthorities();
            for(GrantedAuthority grantedAuthority : grantedAuthorities){
                String authority = grantedAuthority.getAuthority();
                if(RoleName.ROLE_ADMIN.name().equals(authority)){
                    return getResource(document);
                } else if (RoleName.ROLE_USER.name().equals(authority)){
                    UserPrincipal userPrincipal = (UserPrincipal) principal.getPrincipal();
                    if(document.getCreatedBy().equals(userPrincipal.getUsername())){
                        return getResource(document);
                    }
                }
            }
            throw new AppException("Not enough permissions to download resource !");
        }
        throw new ResourceNotFoundException(EntityNameConstants.DOCUMENT, EntityNameConstants.DOCUMENT_NO, documentNo);
    }

    @Override
    public DocumentResponse getDetailDocument(Long documentNo) {
        List<UserDocument> userDocuments = userDocumentRepository.getUserDocumentByDocumentDocumentNo(documentNo);
        return ConverterUtils.convertFromEntitiesToResponseDTO(userDocuments);
    }

    @Override
    public boolean deleteDocument(Long documentNo) {
        documentRepository.deleteById(documentNo);
        return true;
    }

    @Override
    public PagingResponseModel<DocumentResponse> getFiles(String searchedValue, Long userNo, Pageable pageable) {
        List<PermissionName> permissions = PermissionName.getPermissionByValue(searchedValue);
        Page<UserDocument> documents = userDocumentRepository.getDocuments(userNo, searchedValue, permissions, pageable, EscapeCharacter.DEFAULT.getEscapeCharacter());

        return new PagingResponseModel<>(documents.map(ConverterUtils::convertFromEntityToResponseDTO));
    }

    @Override
    public UserDocumentResponse updatePermission(UserDocumentRequest userDocumentRequest) {
        Long userDocumentNo = userDocumentRequest.getUserDocumentNo();
        UserDocument userDocument = this.userDocumentRepository.findById(userDocumentNo)
                .orElseThrow(() -> new AppException("User Document not found with userDocumentNo: " + userDocumentNo));
        userDocument.setPermissionName(userDocumentRequest.getPermissionName());

        return ConverterUtils.convertFromEntityToUserDocResponse(this.userDocumentRepository.save(userDocument));
    }

    private Resource getResource(Document document){
        return FileUtils.getResource(document.getUrl(), document.getFileName());
    }
}
