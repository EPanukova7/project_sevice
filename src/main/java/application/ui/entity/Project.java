package application.ui.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @NotEmpty(message = "Name is required.")
    private String projectName;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();
}
