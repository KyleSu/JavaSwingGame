import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Snake {
    private LinkedList<Square> body = new LinkedList<Square>();
    private final Iterator<Square> iterator = body.iterator();

    public Snake(int court_width, int court_height) { //Create new snake, first segment at upper left
        Square firstSegment = new Square(court_width, court_height);
        body.add(firstSegment);
    }

    public void addSegment() { //Add new segment to snake
        Square newMember = copy(body.getFirst());
        body.addFirst(newMember); //Inserts new segment at beginning

        body.getFirst().pos_x = body.getFirst().pos_x - 5;

    }

    public void update() {
        int count = 0;     

        for (Square s: body){
            if (!s.equals(body.getFirst())) {
                body.get(count).set(s);
                count++;
            }
        }

    }

    public GameObj getLast() {
        return body.getLast();
    }

    public LinkedList<Square> getBody() {
        return body;
    }

    public Square copy(Square original) {
        Square s = new Square(500, 500);
        s.set(original);
        return s;
    }

}
