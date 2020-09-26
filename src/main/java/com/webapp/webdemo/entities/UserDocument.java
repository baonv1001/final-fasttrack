package com.webapp.webdemo.entities;

import com.webapp.webdemo.constants.enums.PermissionName;
import com.webapp.webdemo.entities.audit.Audit;
import com.webapp.webdemo.entities.security.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "USER_DOCUMENT")
public class UserDocument extends Audit {
    @Id
    @SequenceGenerator(sequenceName = "USER_DOCUMENT_NO_SEQ", name = "SEQ_USER_DOCUMENT_NO_GEN", allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_USER_DOCUMENT_NO_GEN"
    )
    @Column(name = "USER_DOCUMENT_NO")
    private Long userDocumentNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_NO")
    private Document document;

    @Column(name = "PERMISSION_NAME")
    @Enumerated(EnumType.STRING)
    private PermissionName permissionName = PermissionName.OWNER;
}
