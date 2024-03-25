// public class DockGate implements Runnable {
//     private String threadName;
//     private Thread thread;
//     private boolean occupied;

//     public DockGate (String threadName) {
//         this.threadName = threadName;
//     }
//     // public void start () {
//     //     System.out.println(threadName + " \t: started");
//     //     if (thread == null) {
//     //         thread = new Thread(this, "");
//     //         thread.start();
//     //     }
//     // }

//     // public void run() {
//     //     System.out.println(threadName + " running");
//     // }

//     public synchronized boolean acquire () {
//         if (!occupied) {
//             occupied = true;
//             return true;
//         }
//         return false;
//     }

//     public synchronized void release () {
//         occupied = false;
//     }

//     public String getName () {
//         return this.threadName;
//     }
// }

public class DockGate {
    private int id;
    private boolean occupied;

    public DockGate(int id) {
        this.id = id;
        this.occupied = false;
    }

    public synchronized boolean acquire() {
        if (!occupied) {
            occupied = true;
            return true;
        }
        return false;
    }

    public synchronized void release() {
        occupied = false;
    }

    public int getId() {
        return id;
    }
}