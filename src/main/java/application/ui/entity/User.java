package application.ui.entity;

import jdk.jfr.SettingDefinition;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @Getter
    @Setter
    private Integer Id;

    @Getter
    @Setter
    private String email;

    // hash
    @Getter
    @Setter
    private String password;

    // manytomany

}
