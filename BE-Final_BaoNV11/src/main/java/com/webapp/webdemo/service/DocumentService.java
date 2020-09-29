/*******************************************************************************
 * Copyright (c) 2020 iXchange Pte. Ltd. All rights reserved.
 *
 *  This software is the confidential and proprietary information of iXchange Pte
 *  Ltd ("Confidential Information"). You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the license
 *   agreement you entered into with iXchange.
 ******************************************************************************/

package com.webapp.webdemo.service;

import com.webapp.webdemo.payload.request.UserDocumentRequest;
import com.webapp.webdemo.payload.response.DocumentResponse;
import com.webapp.webdemo.payload.response.PagingResponseModel;
import com.webapp.webdemo.payload.response.UserDocumentResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    DocumentResponse uploadFile(Long userNo, MultipartFile files);
    PagingResponseModel<DocumentResponse> getFiles(String searchedValue, Long userNo, Pageable pageable);
    DocumentResponse getDetailDocument(Long documentNo);
    UserDocumentResponse updatePermission(UserDocumentRequest userDocumentRequest);
    Resource downloadFile(UsernamePasswordAuthenticationToken principal, Long documentNo);
    boolean deleteDocument(Long documentNo);
}
