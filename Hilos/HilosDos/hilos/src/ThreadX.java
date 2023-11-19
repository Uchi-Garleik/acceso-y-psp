public class ThreadX extends Thread{
    private Character letter;
    private static ThreadX currentThread = null;
    private ThreadX nextThread;
    private Boolean finished = false;
    public static Contador contador;

    public ThreadX(Character letter) {
        this.letter = letter;
        if ( contador == null ) {
            contador = new Contador();
        }

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
            if (!finished) {
                synchronized (this){
                    while ( currentThread != this ){
                        wait();
                    }
                    for (int i = contador.getContador(); contador.getContador() < 100; contador.sumarContador()) {
                        System.out.println("Number: " + contador.getContador() + " -- Thread: " + getLetter());
                        if (contador.getContador() >= 99) {
                            this.interrupt();
                        }
                        currentThread = nextThread;
                        synchronized (nextThread){nextThread.notify();}
                        if ( !(currentThread.getLetter().equals('C') && contador.getContador() >= 99) ){
                            this.wait();
                        }
                    }
                }
            }
        } catch(InterruptedException e){
            System.out.println("I am going to sleep!");
        }
    }


}
