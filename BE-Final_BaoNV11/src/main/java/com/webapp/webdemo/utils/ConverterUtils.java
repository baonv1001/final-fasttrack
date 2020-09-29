package com.webapp.webdemo.utils;

import com.webapp.webdemo.entities.UserDocument;
import com.webapp.webdemo.entities.security.User;
import com.webapp.webdemo.payload.response.DocumentResponse;
import com.webapp.webdemo.payload.response.UserDocumentResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConverterUtils {
    public static DocumentResponse convertFromEntityToResponseDTO(UserDocument userDocument){
        return DocumentResponse.builder()
                .fileName(userDocument.getDocument().getFileName())
                .permissionName(userDocument.getPermissionName())
                .build();
    }

    public static UserDocumentResponse convertFromEntityToUserDocResponse(UserDocument userDocument){
        return UserDocumentResponse.builder()
                .userDocumentNo(userDocument.getUserDocumentNo())
                .userNo(userDocument.getUser().getUserNo())
                .permissionName(userDocument.getPermissionName())
                .build();
    }

    public static DocumentResponse convertFromEntitiesToResponseDTO(List<UserDocument> userDocuments){
        DocumentResponse documentResponse = new DocumentResponse();
        if(!CollectionUtils.isEmpty(userDocuments)){
            documentResponse.setFileName(userDocuments.get(0).getDocument().getFileName());
            List<UserDocumentResponse> userDocumentResponses = userDocuments.stream().map(userDocument -> {
                User user = userDocument.getUser();
                return UserDocumentResponse.builder()
                        .userNo(user.getUserNo())
                        .userDocumentNo(userDocument.getUserDocumentNo())
                        .userName(user.getUsername())
                        .permissionName(userDocument.getPermissionName())
                        .build();
            }).collect(Collectors.toList());
            documentResponse.setUserDocumentResponses(userDocumentResponses);
        }
        return documentResponse;
    }
}
