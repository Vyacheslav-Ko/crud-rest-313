package rest.crud.app.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "firstname")
    @Size(min = 2, message = "2 symbols minimum required")
    private String firstname;

    @Column (name = "lastname")
    @Size(min = 2, message = "2 symbols minimum required")
    private String lastname;

    @Column (name = "age")
    @Max(value = 127, message = "value must be between 0 and 127")
    private int age;

    @Column (name = "email")//, unique = true
    @Email (message = "E-mail is not valid")
    @NotBlank (message = "E-mail can not be empty")
    private String username;

    @Size(min = 6, message = "6 symbols minimum required")
    @Column (name = "password")
    private String password;

    @Column (name = "flagrole")
    @NotBlank (message = "At least one is needed")
    private int flagRole = 1;

    @ManyToMany(fetch = FetchType.EAGER, cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})//cascade = CascadeType.ALL
    @JoinTable(
            name = "user_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(@Size(min = 2, message = "2 symbols minimum required") String firstname
            , @Size(min = 2, message = "2 symbols minimum required") String lastname
            , @Max(value = 127, message = "value must be between 0 and 127") int age
            , @Email(message = "E-mail is not valid") @NotBlank(message = "Email can not be empty") String username
            , @Size(min = 6, message = "6 symbols minimum required") String password)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    public User(@Size(min = 2, message = "2 symbols minimum required") String firstname
            , @Size(min = 2, message = "2 symbols minimum required") String lastname
            , @Max(value = 127, message = "value must be between 0 and 127") int age
            , @Email(message = "E-mail is not valid") @NotBlank(message = "Email can not be empty") String username
            , @Size(min = 6, message = "6 symbols minimum required") String password
            , int flagRole)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.username = username;
        this.password = password;
        this.flagRole = flagRole;
    }

    public int getFlagRole() {
        return flagRole;
    }

    public void setFlagRole(int flagRole) {
        this.flagRole = flagRole;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }
    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String rolesInString() {
        String ris = "";
        for (Role role : getRoles()) {
            ris = ris + role.getName().substring(5) + " ";
        }
        return ris;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", email='" + username + '\'' +
                '}';
    }
}
