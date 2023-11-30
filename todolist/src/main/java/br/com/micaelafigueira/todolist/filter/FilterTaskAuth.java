package br.com.micaelafigueira.todolist.filter;

import java.io.IOException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.micaelafigueira.todolist.user.IUserRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{


    @Autowired
    private IUserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var servletPath = request.getServeletPath();
        System.out.println("PATH"+serveletPath);
        if (servletPath.equals("/tasks/")){

                         // TODO Auto-generated method stub
                
                //Pegar a autenticação (suario e senha)
            var authorization = request.getHeader("Authorization");
               
            var authEncoded = authorization.substring("Basic".length()).trim();

            final byte[] authDecode = Base64.getDecoder().decode(authEncoded);
 
            var authString = new String(authDecode);
            System.out.println("Authorization");
            System.out.println(authString);

               //{"micaelamachado", "12345"}

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];
            System.out.println("Authorization");
            System.out.println(username);
            System.out.println(password);




            System.out.println("Authorization");
            System.out.println(authorization); 

            authorization.substring("Basic".length()).trim();
                //Validar Usuario

              var user = this.userRepository.findByUsername(username);
              if (user == null){
                  response.sendError(401,"Usuario sem autorização");

                  } else {

                   
                //Validar Senha 
                  var passwordVerify =  BCrypt.verifyer().verifyer(password.toCharArray(), user.getPassword());
                  if(passwordVerify.verified){
                     request.setAttribute("idUser",user.getId());
                     filterChain.doFilter(request,response);

                     } else {
                     response.sendError(401);
                  }
                //Validar

                

        
    }

  
        
    }

                }
   

    
}
