import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class User {
  private String username;
  private String password;
  private int id;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public int getId() {
    return this.id;
  }

  public List<Post> getPosts() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM posts WHERE parentid = :id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Post.class);
    }
  }

  // public static User login(String username, String password) {
  //   if(user == null) {
  //     throw new
  //   }
  //   try(Connection  con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM users WHERE username = :username, password = :password";
  //     User user =  con.createQuery(sql)
  //       .addParameter("username", username)
  //       .addParameter("password", password)
  //       .executeAndFetchFirst(User.class);
  //     return user;
  //   }
  // }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users (username, password) VALUES (:username, :password)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("username", this.username)
        .addParameter("password", this.password)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<User> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users";
      return con.createQuery(sql).executeAndFetch(User.class);
    }
  }

  public static User find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(User.class);
    }
  }

  public void updateUsername(String username) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET username = :username WHERE id = :id";
      con.createQuery(sql)
        .addParameter("username", username)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updatePassword(String password) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET password = :password WHERE id = :id";
      con.createQuery(sql)
        .addParameter("password", password)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM users WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherUser) {
    if (!(otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) otherUser;
      return this.getUsername().equals(newUser.getUsername()) && this.getPassword().equals(newUser.getPassword()) && this.getId() == newUser.getId();
    }
  }
}
