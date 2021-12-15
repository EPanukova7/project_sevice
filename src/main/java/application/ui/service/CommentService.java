package application.ui.service;

import application.ui.entity.Comment;
import application.ui.entity.Project;
import application.ui.entity.Task;
import application.ui.entity.User;
import application.ui.repository.CommentRepository;
import application.ui.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private static CommentRepository commentRepository;

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository){
        CommentService.commentRepository = commentRepository;
    }
    public static Comment create(Project project, Task task, User user, Comment comment){
        task.setProject(project);
        comment.setOwner(user);
        comment.setTask(task);
        return commentRepository.save(comment);
    }

    public static Comment getById(Integer id){
        return commentRepository.findById(id).orElse(null);
    }

    public static Iterable<Comment> getAll(){
        return commentRepository.findAll();
    }


    public static Iterable<Comment> getAllByUserId(Integer userId){
        User user = UserService.getById(userId);
        return user.getComments();
    }

    public static Iterable<Comment> getAllByTaskId(Integer taskId){
        Task task = TaskService.getById(taskId);
        return task.getComments();
    }
    public static void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
