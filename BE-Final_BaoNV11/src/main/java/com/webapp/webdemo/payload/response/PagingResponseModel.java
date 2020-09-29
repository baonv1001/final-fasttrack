/*******************************************************************************
 * Copyright (c) 2020 iXchange Pte. Ltd. All rights reserved.
 *
 *  This software is the confidential and proprietary information of iXchange Pte
 *  Ltd ("Confidential Information"). You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the license
 *   agreement you entered into with iXchange.
 ******************************************************************************/

package com.webapp.webdemo.payload.response;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

/**
 * @author DungTP
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagingResponseModel<T> {
    private long totalElements;
    private int pageSize;
    private int pageNumber;
    private List<T> rows;

    public PagingResponseModel(Page<T> page) {
        if (page == null) {
            page = new PageImpl<>(Collections.emptyList());
        }
        this.totalElements = page.getTotalElements();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.rows = page.getContent();
    }
}
