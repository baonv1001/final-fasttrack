package com.webapp.webdemo.payload.request;

import com.webapp.webdemo.constants.enums.PermissionName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDocumentRequest {
    @NotNull
    private Long userDocumentNo;

    @NotNull
    private PermissionName permissionName;
}
