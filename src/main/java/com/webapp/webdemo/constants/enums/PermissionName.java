package com.webapp.webdemo.constants.enums;

import com.webapp.webdemo.constants.CommonConstants;

import java.util.ArrayList;
import java.util.List;

public enum PermissionName {
    READ,
    SHARE,
    OWNER;

    public static List<PermissionName> getPermissionByValue(String value) {
        if(value == null){
            return null;
        }
        String newCode = value.replace(CommonConstants.WHITE_SPACE_CHARACTER, CommonConstants.UNDERSCORE_SYMBOL);
        List<PermissionName> valueDocumentCategoryList = new ArrayList<>();
        for (PermissionName category : PermissionName.values()) {
            if (category.toString().contains(newCode)) {
                valueDocumentCategoryList.add(category);
            }
        }
        return valueDocumentCategoryList;
    }
}
