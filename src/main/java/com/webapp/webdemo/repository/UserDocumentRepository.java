package com.webapp.webdemo.repository;

import com.webapp.webdemo.constants.enums.PermissionName;
import com.webapp.webdemo.entities.UserDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDocumentRepository extends JpaRepository<UserDocument, Long> {
    @Query("SELECT ud FROM UserDocument ud " +
            "JOIN ud.document d " +
            "WHERE (:q IS NULL OR UPPER(d.fileName) LIKE %:q% escape :escapeCharacter " +
                    "OR ud.permissionName IN :permissionNames) " +
                    "AND ud.user.userNo = :userNo")
    Page<UserDocument> getDocuments(@Param("userNo") Long userNo,
                                    @Param("q") String searchedValue,
                                    @Param("permissionNames") List<PermissionName> permissionNames,
                                    Pageable pageable, char escapeCharacter);
    List<UserDocument> getUserDocumentByDocumentDocumentNo(Long documentNo);
}
