
public class Tour {
    private class Node {
        private Point p;
        private Node next;

        public Node(Point point, Node n) {
            p = point;
            next = n;
        }
    }

    Node head;

    // creates an empty tour
    public Tour() {
        head = null;
    }

    // creates the 4-point tour a->b->c->d->a (for debugging)
    public Tour(Point a, Point b, Point c, Point d) {
        head = new Node(a, null);
        Node m2 = new Node(b, null);
        Node m3 = new Node(c, null);
        Node m4 = new Node(d, null);
        head.next = m2;
        m2.next = m3;
        m3.next = m4;
        m4.next = head;
    }

    // returns the number of points in this tour
    public int size() {
        if (head == null)
            return 0;

        Node last = head;
        int counter = 0;
        while (last.next != head) {
            last = last.next;
            counter++;
        }
        return counter + 1;
    }

    // returns the length of this tour
    public double length() {
        if (head == null)
            return 0.0;

        Node last = head;
        double distance;
        double totalLength = 0;
        while (last.next != head) {
            distance = last.p.distanceTo(last.next.p);
            totalLength = totalLength + distance;
            last = last.next;
        }

        totalLength = totalLength + last.p.distanceTo(head.p);

        return totalLength;
    }

    // returns a string representation of this tour
    public String toString() {
        if (head == null)
            return "";

        Node last = head;
        String result = "";
        while (last.next != head) {
            String minh = last.p.toString() + "\n";
            result = result.concat(minh);
            last = last.next;
        }

        String minh = last.p.toString() + "\n";
        result = result.concat(minh);

        return result;
    }

    // draws this tour to standard drawing
    public void draw() {
        if (head == null)
            return;

        Node last = head;
        while (last.next != head) {
            last.p.drawTo(last.next.p);
            last = last.next;
        }
        last.p.drawTo(head.p);
    }

    // inserts p using the nearest neighbor heuristic
    public void insertNearest(Point p) {
        Node ourPoint = new Node(p, null); // create a Node from the given point
        if (this.head == null) {
            this.head = ourPoint;
            this.head.next = this.head;
        }
        else {
            Node last = this.head.next; // we will start from the node after head
            Node closestNode = this.head; // this will be our closest Node to the given point
            double d1 = p.distanceTo(this.head.p); // smallest distance
            while (last != this.head) {
                double dn = p.distanceTo(last.p); // compute the distance from p to each node
                if (dn < d1) { // if the distance is smaller than the smallest distance
                    d1 = dn; // smallest distance will now be dn
                    closestNode = last; // update the closet node
                }
                last = last.next; // update last
            }

            // last step: insert ourPoint after the closest node
            ourPoint.next = closestNode.next;
            closestNode.next = ourPoint;
        }
    }

    // inserts p using the smallest increase heuristic
    public void insertSmallest(Point p) {
        Node ourPoint = new Node(p, null); // create a Node from the given point
        if (this.head == null) {
            this.head = ourPoint;
            this.head.next = this.head;
        }
        else {
            Node choosenNode = this.head; // this will eventually be our choosen node
            Node last = this.head.next; // we will start from head.next
            // this will eventually be our smallest total length
            double d1 = this.length() - this.head.p.distanceTo(this.head.next.p)
                    + this.head.p.distanceTo(p) + p.distanceTo(this.head.next.p);

            while (last != this.head) {
                double dn = this.length() - last.p.distanceTo(last.next.p)
                        + last.p.distanceTo(p) + p.distanceTo(last.next.p);

                // compare each total length with the smallest total length
                if (dn < d1) {
                    d1 = dn;
                    choosenNode = last; // update oour choosen node
                }

                last = last.next; // update last
            }

            // last step: insert ourPoint after the choosen node
            ourPoint.next = choosenNode.next;
            choosenNode.next = ourPoint;
        }
    }


    // tests this class by directly calling all constructors and instance methods
    public static void main(String[] args) {

        // define 4 points that are the corners of a square
        Point a = new Point(100.0, 100.0);
        Point b = new Point(500.0, 100.0);
        Point c = new Point(500.0, 500.0);
        Point d = new Point(100.0, 500.0);

        // create the tour a -> b -> c -> d -> a
        Tour squareTour = new Tour(a, b, c, d);
        // StdOut.println("total length = " + squareTour.length());

        // print the size to standard output
        int size = squareTour.size();
        StdOut.println("Number of points = " + size);
        StdOut.println("total length = " + squareTour.length());

        StdOut.println(squareTour);

        StdDraw.setXscale(0, 600);
        StdDraw.setYscale(0, 600);
        // a.draw();
        // b.draw();
        // c.draw();
        // d.draw();
        Point test = new Point(300.0, 550.0);
        squareTour.insertNearest(test);
        StdOut.println("Number of points = " + squareTour.size());
        StdOut.println("total length = " + squareTour.length());
        Node last = squareTour.head;
        squareTour.draw();

        /* for (int i = 0; i < 5; i++) {
            last.p.drawTo(last.next.p);
            last = last.next;
        } */

    }
}
