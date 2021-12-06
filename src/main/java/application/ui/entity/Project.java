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
    @Column(unique = true)
    private String name;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private User owner;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "project_user",
        joinColumns = { @JoinColumn(name = "project_id", nullable = false) },
        inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false) }
    )
    private List<User> users = new ArrayList<>();
}
