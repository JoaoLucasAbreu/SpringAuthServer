package auth.server.Entity;

import org.springframework.boot.context.properties.bind.DefaultValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "tab_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int userId;

  private String username;

  private String password;

  private Integer totalLogins = 0;

  private Integer totalFails = 0;

  private boolean isBlocked;

  public int getId() {
    return userId;
  }
  public void setId(int userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getTotalLogins() {
    return totalLogins;
  }
  public void setTotalLogins(Integer totalLogins) {
    this.totalLogins = totalLogins;
  }

  public Integer getTotalFails() {
    return totalFails;
  }
  public void setTotalFails(Integer totalFails) {
    this.totalFails = totalFails;
  }

  public boolean getIsBlocked() {
    return isBlocked;
  }
  public void setIsBlocked(boolean isBlocked) {
    this.isBlocked = isBlocked;
  }
}