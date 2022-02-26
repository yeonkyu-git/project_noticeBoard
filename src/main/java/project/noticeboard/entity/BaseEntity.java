package project.noticeboard.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public class BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;
}
