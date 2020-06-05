import java.util.ArrayList;
import java.util.List;

public class Wave {

    private List<Event> eventList = new ArrayList<>();
    private int eventNumber = 0;
    public Wave(){}

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

    public void add( Event event){
        eventList.add(event);
    }


}
