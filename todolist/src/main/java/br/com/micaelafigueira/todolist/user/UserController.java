package br.com.micaelafigueira.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Modificador
 * Public
 * Private
 * Protected
 */
@RestController
@RequestMapping("/users")
public class UserController{

    @Autowired
    private IUserRepository userRepository;


    public IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if(user != null){
            System.out.println("Usuario já existe");
            //mensagem de erro
            //Status Code
            return ResponseEntity.status(400).body("Usuário já existe");
        }

        var passwordHashred = BCrypt.withDefaults()
                .hashToString(cost:12,userModel.getPassword.toCharArray());

        userModel.setPassword(passwordHashred);        


        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
    
}
