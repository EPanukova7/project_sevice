package application.ui.entity;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Getter
    @Setter
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Integer id;

    @Getter
    @Setter
    @NotNull
    private String email;

    @Getter
    @Setter
    @NotNull
    private String password;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Project> ownedProjects = new ArrayList<>();

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<Project> projects = new HashSet<>();

}
