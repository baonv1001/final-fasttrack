package com.webapp.webdemo.payload.response;

import com.webapp.webdemo.constants.enums.PermissionName;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse {
    private Long documentNo;
    private String fileName;
    private PermissionName permissionName;
    private List<UserDocumentResponse> userDocumentResponses;
}
