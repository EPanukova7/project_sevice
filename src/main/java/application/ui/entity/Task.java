package application.ui.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Task {

    @Getter
    @Setter
    @GeneratedValue
    @Id
    private Integer id;

    @Getter
    @Setter
    @NotEmpty(message = "Task is required.")
    private String title;

    @Getter
    @Setter
    @NotEmpty(message = "Description is required.")
    private String description;

    @Getter
    @Setter
    @NotEmpty(message = "State is required.")
    private Boolean state;

    @Getter
    @Setter
    @NotEmpty
    private Integer projectId;

}
