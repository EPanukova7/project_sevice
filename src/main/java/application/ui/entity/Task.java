package application.ui.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@Entity
public class Task {

    @Getter
    @Setter
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Integer id;

    @Getter
    @Setter
    @Size(min=4, message = "{Size.Task.TaskName}")
    @NotEmpty(message = "Task is required.")
    private String taskName;

    @Getter
    @Setter
    @Size(min=1, message = "{Size.Task.TaskDescription}")
    @NotEmpty(message = "Description is required.")
    @Valid()
    private String taskDescription;

    @Getter
    @Setter
    @NotEmpty(message = "State is required.")
    private Boolean taskState;

    @Getter
    @Setter
    @NotEmpty
    private Integer projectId;

}
