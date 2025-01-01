package com.security.app.entities

import com.security.app.model.MediaType
import jakarta.persistence.*
import lombok.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@EntityListeners(AuditingEntityListener::class)
@Table
class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var mediaId: String = ""

    @Column(nullable = false)
    var mediaUrl: String = ""

    @Column(nullable = false)
    var mediaType: MediaType = MediaType.IMAGE

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
}