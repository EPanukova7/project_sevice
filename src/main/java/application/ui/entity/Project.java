package application.ui.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Project {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Getter
    @Setter
    @NotNull
    @Column(unique = true)
    private String name;

    @Getter
    @Setter
    @NotNull
    @Column(unique = true)
    private String code;

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
    private Set<User> users = new HashSet<>();
}
