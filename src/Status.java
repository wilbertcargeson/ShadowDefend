public class Status {

    private final String WIN = "Winner!";
    private final String PLACING = "Placing";
    private final String WAVE_IN_PROGRESS = "Wave In Progress";
    private final String WAITING = "Awaiting Start";

    private String status = WAITING;

    public void setWin(){ status = WIN;}
    public void setPlacing(){ status = PLACING; }
    public void setProgress(){ status = WAVE_IN_PROGRESS;}
    public void setWaiting() { status = WAITING;}
    public String getStatus(){ return status; }
}
