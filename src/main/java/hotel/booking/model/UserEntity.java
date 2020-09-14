package hotel.booking.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class UserEntity {

    private String email_id;
    private String password;
    private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
    private String id;
    private String first_name;
    private String last_name;

    public UserEntity() {
    }

    public UserEntity(String email_id, String password, Collection<GrantedAuthority> grantedAuthoritiesList, String first_name, String last_name) {
        this.email_id = email_id;
        this.password = password;
        this.grantedAuthoritiesList = grantedAuthoritiesList;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public UserEntity(String email_id, String password, Collection<GrantedAuthority> grantedAuthoritiesList, String id, String first_name, String last_name) {
        this.email_id = email_id;
        this.password = password;
        this.grantedAuthoritiesList = grantedAuthoritiesList;
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<GrantedAuthority> getGrantedAuthoritiesList() {
        return grantedAuthoritiesList;
    }

    public void setGrantedAuthoritiesList(Collection<GrantedAuthority> grantedAuthoritiesList) {
        this.grantedAuthoritiesList = grantedAuthoritiesList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


}
