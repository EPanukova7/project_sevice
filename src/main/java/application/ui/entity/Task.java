package application.ui.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id", referencedColumnName = "id", nullable = true)
    private User executor;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
    private List<Comment> comments = new ArrayList<>();

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private TaskStatus status;

    public void addComment(Comment comment){
        this.comments.add(comment);
    }
}
