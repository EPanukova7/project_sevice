package application.ui.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Task {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @NotEmpty(message = "Task is required.")
    private String name;

    @Getter
    @Setter
    @NotEmpty(message = "Description is required.")
    private String description;

    @Getter
    @Setter
    @NotEmpty(message = "State is required.")
    private Boolean state;

    @Getter
    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

}
