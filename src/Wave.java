import java.util.ArrayList;
import java.util.List;

public class Wave {

    private List<Event> eventList = new ArrayList<>();
    private int eventNumber = 0;

    /**
     * Creates a wave
     */
    public Wave(){}

    /**
     * Runs the wace
     * @return return false if all of the event has been run
     */
    public boolean run(){
        // Return false if all of the event are done
        if ( eventNumber == eventList.size()){
            return false;
        }

        // Go to next event if current event is done
        if ( !eventList.get(eventNumber).run()){
            eventNumber++;
        }

        return true;
    }

    /**
     * Add a new event into the wave
     * @param event The event to be added
     */
    public void add( Event event){
        eventList.add(event);
    }


}
