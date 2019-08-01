package vn.vissoft.employee.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "author")
@SequenceGenerator(name="seq")
public class author implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String authority;

    public author(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public author() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
