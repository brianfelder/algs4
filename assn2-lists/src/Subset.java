/**
 * Created by bfelder on 2/7/15.
 */
public class Subset {
    public static void main(String[] args) {
        int subsetSize = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String currentString = StdIn.readString();
            // System.out.println("You said: " + currentString);
            rq.enqueue(currentString);
        }
        // System.out.println("Got Strings!");
        int stringCount = 0;
        for (String randomString : rq) {
            stringCount++;
            System.out.println(randomString);
            if (stringCount >= subsetSize)
                break;
        }
    }
}
