package application.ui.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    @NotNull
    private String description;

    @Getter
    @Setter
    @NotNull
    @Column(columnDefinition = "boolean default false")
    private boolean state;

    @Getter
    @Setter
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
    private List<Comment> comments = new ArrayList<>();


}
