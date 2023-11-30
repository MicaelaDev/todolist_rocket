package br.com.micaelafigueira.todolist.user;

import java.lang.annotation.Inherited;

import lombok.Data;

@Data
@Entity(name = "tb_users")
public class UserModel {

  @Id 
  @GeneratedValue(generator = "UUID")
  public UUID id;
 
  @Column(unique = true)  
  public String username;
  public String name;
  public String password;

  @CreationTimestamp
  public LocalDateTime createdAt;
 
  //getters setters

}
