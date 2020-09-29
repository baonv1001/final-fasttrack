package com.webapp.webdemo.controller;

import com.webapp.webdemo.constants.EntityNameConstants;
import com.webapp.webdemo.constants.enums.PermissionName;
import com.webapp.webdemo.payload.request.UserDocumentRequest;
import com.webapp.webdemo.payload.response.DocumentResponse;
import com.webapp.webdemo.payload.response.PagingResponseModel;
import com.webapp.webdemo.payload.response.UserDocumentResponse;
import com.webapp.webdemo.service.DocumentService;
import com.webapp.webdemo.utils.FileUtils;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/web/documents")
public class DocumentController extends AbstractController{
    private final DocumentService documentService;

    @ApiOperation(value = "Upload Document", notes = "POST method only")
    @ApiResponses(value = {
            @ApiResponse(code = 201, response = DocumentResponse.class, message = "CREATED")
    })
    @PostMapping("upload-file")
    public ResponseEntity<DocumentResponse> uploadFile(@RequestParam("user-no") Long userNo, @RequestParam("file") MultipartFile file) {
        log.info("Starting upload Document API");
        return new ResponseEntity<>(this.documentService.uploadFile(userNo, file), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Download Document", notes = "GET method only")
    @ApiResponses(value = {
            @ApiResponse(code = 201, response = Resource.class, message = "GET")
    })
    @GetMapping(("/download-file/{document-no}"))
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "document-no") Long documentNo, Principal principal){
        log.info("Starting download Document API");
        Resource resource = this.documentService.downloadFile((UsernamePasswordAuthenticationToken) principal, documentNo);
        return FileUtils.responseResourceEntity(resource, resource.getFilename());
    }

    @ApiOperation(value = "Update perrmission for user with document", notes = "GET method only")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = DocumentResponse.class, message = "OK")
    })
    @PutMapping("/update-permission/")
    public ResponseEntity<UserDocumentResponse> updatePermission(@RequestBody @Valid UserDocumentRequest userDocumentRequest){
        return ResponseEntity.ok(this.documentService.updatePermission(userDocumentRequest));
    }

    @ApiOperation(value = "Delete document", notes = "GET method only")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = DocumentResponse.class, message = "OK")
    })
    @DeleteMapping("/delete-doc/{document-no}")
    public ResponseEntity<Boolean> deleteDocument(@PathVariable(value = "document-no") Long documentNo){
        return ResponseEntity.ok(this.documentService.deleteDocument(documentNo));
    }

    @ApiOperation(value = "Get detail document", notes = "GET method only")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = DocumentResponse.class, message = "OK")
    })
    @GetMapping("/{document-no}")
    public ResponseEntity<DocumentResponse> getDetailDocument(@PathVariable(value = "document-no") Long documentNo){
        return ResponseEntity.ok(this.documentService.getDetailDocument(documentNo));
    }

    @ApiOperation(value = "Get document list", notes = "GET method only")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = DocumentResponse.class, message = "OK")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "page"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "page size"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "sort")
    })
    @GetMapping
    public ResponseEntity<PagingResponseModel<DocumentResponse>> getFiles(
            @RequestParam(value = "q", required = false) String searchedValue,
            @RequestParam("user-no") Long userNo,
            @PageableDefault @SortDefault.SortDefaults({
                    @SortDefault(sort = EntityNameConstants.DOCUMENT_NAME, direction = Sort.Direction.DESC),}) Pageable pageable){
        return ResponseEntity.ok(this.documentService.getFiles(searchedValue, userNo, pageable));
    }
}
