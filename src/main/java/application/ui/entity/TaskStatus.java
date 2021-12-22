package application.ui.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TaskStatus {
    @Id
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    private Set<Task> tasks = new HashSet<>();
}
