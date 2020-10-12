package by.harevich.javaspring.controller;
import by.harevich.javaspring.forms.TaskForm;
import by.harevich.javaspring.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;

@Controller
public class TaskController {
    private static ArrayList<Task> tasks = new ArrayList<>();
    static {
        tasks.add(new Task("Go sleep", "Go to bed"));
        tasks.add(new Task("Math addition", "2+2"));
        tasks.add(new Task("Math multiplying", "2*2"));
        tasks.add(new Task("Java Spring", "Tell us about Java Spring"));
    }

    // Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.AddMessage}")
    private String addMessage;

    @Value("${error.RemoveMessage}")
    private String removeMessage;

    @Value("${error.EditMessage}")
    private String editMessage;

    @GetMapping(value = {"/", "/index"})
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        return modelAndView;
    }

    @GetMapping(value = {"/alltasks"})
    public ModelAndView taskList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tasklist");
        model.addAttribute("tasks", tasks);
        return modelAndView;
    }

    @GetMapping(value = {"/addtask"})
    public ModelAndView showAddTaskPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addtask");
        TaskForm taskForm = new TaskForm();
        model.addAttribute("taskform", taskForm);
        return modelAndView;
    }

    @PostMapping(value = {"/addtask"})
    public ModelAndView saveTask(Model model, @ModelAttribute("taskForm") TaskForm taskForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tasklist");
        String title = taskForm.getTask();
        String author = taskForm.getDescription();
        if (title != null && title.length() > 0 //
                && author != null && author.length() > 0) {
            Task newTask = new Task(title, author);
            tasks.add(newTask);
            model.addAttribute("tasks", tasks);
            return modelAndView;
        }
        model.addAttribute("errorMessage", addMessage);
        modelAndView.setViewName("addtask");
        return modelAndView;
    }

    @GetMapping(value = {"/edittask"})
    public ModelAndView showEditTaskPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("edittask");
        TaskForm taskForm = new TaskForm();
        model.addAttribute("taskForm", taskForm);
        return modelAndView;
    }

        @PostMapping(value = {"/edittask"})
    public ModelAndView editTask(Model model, @ModelAttribute("taskForm") TaskForm taskForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tasklist");
            for (Task task : tasks){
                if(task.getTask().equals(taskForm.getTaskToEdit())){
                String newTask = taskForm.getTask();
                String newDesc = taskForm.getDescription();
                if (newTask != null && newTask.length() > 0 && newDesc != null && newDesc.length() > 0) {
                    task.setTask(newTask);
                    task.setDescription(newDesc);
                    model.addAttribute("tasks", tasks);
                    return modelAndView;
                }
            }
        }

        model.addAttribute("errorMessage", editMessage);
        modelAndView.setViewName("edittask");
        return modelAndView;
    }

    @RequestMapping(value = {"/removetask"}, method = RequestMethod.GET)
    public ModelAndView showRemovePersonPage(Model model){
        ModelAndView modelAndView = new ModelAndView("removetask");
        TaskForm taskForm = new TaskForm();
        model.addAttribute("taskForm", taskForm);
        return modelAndView;
    }
    @RequestMapping(value = {"/removetask"}, method = RequestMethod.POST)
    public ModelAndView removePerson(Model model, @ModelAttribute("taskForm") TaskForm taskForm){
        ModelAndView modelAndView = new ModelAndView("tasklist");
        String title = taskForm.getTask();
        if (title != null && title.length() > 0){
            for (Task t : tasks){
                if (t.getTask().equals(title)){
                    tasks.remove(t);
                    model.addAttribute("tasks", tasks);
                    return modelAndView;
                }
            }
        }
        model.addAttribute("errorMessage", removeMessage);
        modelAndView.setViewName("removetask");
        return modelAndView;
    }
}
