package auth.server.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity(name = "tab_history")
public class History {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int historyId;

  private String username;

  private Date date;

  public int getId() {
    return historyId;
  }
  public void setId(int historyId) {
    this.historyId = historyId;
  }

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }

  public History() {}
  public History(String username, Date date) {
    this.username = username;
    this.date = date;
  }
}
