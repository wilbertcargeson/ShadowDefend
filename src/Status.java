public class Status {

    private final String WIN = "Winner!";
    private final String PLACING = "Placing";
    private final String WAVE_IN_PROGRESS = "Wave In Progress";
    private final String WAITING = "Awaiting Start";

    private String status = WAITING;

    /**
     * Sets the status to win
     */
    public void setWin(){ status = WIN;}

    /**
     * Sets the status to placing
     */
    public void setPlacing(){ status = PLACING; }

    /**
     * Sets the status as wave in progress
     */
    public void setProgress(){ status = WAVE_IN_PROGRESS;}

    /**
     * Sets the status as waiting
     */
    public void setWaiting() { status = WAITING;}

    /**
     * Gets the status
     * @return current status
     */
    public String getStatus(){ return status; }
}
