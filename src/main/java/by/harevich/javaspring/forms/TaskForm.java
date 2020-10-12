package by.harevich.javaspring.forms;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskForm {
    private String task;
    private String description;
    private String taskToEdit;

    public String getTaskToEdit() {
        return taskToEdit;
    }

    public void setTaskToEdit(String taskToEdit) {
        this.taskToEdit = taskToEdit;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
