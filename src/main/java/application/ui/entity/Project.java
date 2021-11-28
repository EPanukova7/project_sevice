package application.ui.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Project {
    @Id
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @NotEmpty(message = "Name is required.")
    private String projectName;
}
