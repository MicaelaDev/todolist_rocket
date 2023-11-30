package br.com.micaelafigueira.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.micaelafigueira.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private iTaskRepository taskRepository;

    @PostMapping("/")
    public void create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt() || currentDate.isAfter(taskModel.getEndAt))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início/termino deve ser maior do que a data atual");
        }
        if (taskModel.getStartAt()isAfter(taskModel.getEndAt)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início/termino deve ser maior do que a data atual");
        
        var task = this.taskRepository.save(taskModel);
        return new ResponseEntity.status(HttpStatus.OK).body(task);
    }
    @GetMapping("/")
    public Object list (HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }
    @PostMapping("/{id}")
    public ResponseEntity uptade(@RequestBody TaskModel taskModel,HttpServletRequest request,@PathVariable UUID id ){
      
        var task = this.taskRepository.findById(id).orElse(null);
        
        if (task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tarefa não encontrada");
        }

        var idUser = request.getAttribute("idUser");
      
        if (!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário não tem permissão para alterar essa tarefa")
        }
      
      
        Utils.copyNonNullProperties(taskModel, task);
      
        var task = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }
    
}
