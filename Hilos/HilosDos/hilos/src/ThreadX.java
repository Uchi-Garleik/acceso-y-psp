public class ThreadX extends Thread{
    private Character letter;
    private static ThreadX currentThread = null;
    private ThreadX nextThread;

    public ThreadX(Character letter) {
        this.letter = letter;
    }

    public Character getLetter() {
        return letter;
    }

    public void setNextThread(ThreadX nextThread) {
        this.nextThread = nextThread;
    }

    public static ThreadX getCurrentThread() {
        return currentThread;
    }

    public static void setCurrentThread(ThreadX currentThread) {
        ThreadX.currentThread = currentThread;
    }

    @Override
    public void run() {
        try{
            synchronized (this){
                while ( currentThread != this ){
                    wait();
                }
                for (int i = 0; i < 10; i++) {
                    System.out.println("Number: " + i + " -- Thread: " + getLetter());
                    currentThread = nextThread;
                    synchronized (nextThread){nextThread.notify();}
                    if ( !(currentThread.getLetter().equals('C') && i >= 9) ){
                        this.wait();
                    }
                }
            }
        } catch(InterruptedException e){
            System.out.println("Exception!");
        }
    }


}
