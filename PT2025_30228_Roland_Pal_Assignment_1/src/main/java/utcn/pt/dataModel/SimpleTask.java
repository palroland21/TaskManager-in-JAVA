package utcn.pt.dataModel;

public non-sealed class SimpleTask extends Task {
    private static final long serialVersionUID = 1L;

    private int startHour;
    private int endHour;

    public SimpleTask(int idTask, String statusTask, int startHour, int endHour) {
        super(idTask, statusTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public int estimatedDuration(){
        return endHour - startHour;
    }
}
