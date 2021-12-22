package application.ui.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task_statuses")
public class TaskStatus {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @Column(unique = true)
    private String name;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private Set<Task> tasks = new HashSet<>();
}
