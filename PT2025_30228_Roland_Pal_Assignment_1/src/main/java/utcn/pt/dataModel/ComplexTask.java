package utcn.pt.dataModel;

import java.util.List;

public non-sealed class ComplexTask extends Task {
    private static final long serialVersionUID = 1L;

    private List<Task> tasks;

    public ComplexTask(int idTask, String statusTask, List<Task> tasks) {
        super(idTask, statusTask);
        this.tasks = tasks;
    }

    @Override
    public int estimatedDuration(){
        int duration = 0;
        for(Task t: tasks){
            duration += t.estimatedDuration();
        }
        return duration;
    }

}
