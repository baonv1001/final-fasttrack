package com.webapp.webdemo.payload.response;

import com.webapp.webdemo.constants.enums.PermissionName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class UserDocumentResponse {
    private Long userDocumentNo;
    private Long userNo;
    private String userName;
    private PermissionName permissionName;
}
