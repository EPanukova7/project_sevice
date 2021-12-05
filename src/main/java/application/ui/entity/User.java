package application.ui.entity;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Getter
    @Setter
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Id
    private Integer id;

    @Getter
    @Setter
    @NotEmpty(message = "Email is required.")
    private String email;

    @Getter
    @Setter
    @NotEmpty(message = "Password is required.")
    private String hashedPassword;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Project> ownedProjects = new ArrayList<>();

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<Project> projects = new ArrayList<>();

}
