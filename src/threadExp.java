import java.util.Scanner;

import static java.lang.Thread.sleep;

public class threadExp implements Runnable {

    public static void main(String[] args) {
//        Thread t = new Thread(new threadExp());
//        t.start();
        try {
            threadExp tE = new threadExp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void nonBlockingFunc() throws InterruptedException {
        System.out.println(">>>>>>> This is a non-blocking portion of the code");
        for (int i = 0; i < 50; i++) {
            System.out.println("abc");
            sleep(400);
        }
        System.out.println(">>>>>>> Done with non-blocking portion of the code");
    }

    public void blockingFunc() {
        Scanner sc = new Scanner(System.in);
        System.out.println(">>>>>>> This is a blocking portion of the code");
        System.out.print("Enter a number: ");
        int n = sc.nextInt();
        System.out.println("You entered: " + n);
    }

    public threadExp() throws InterruptedException {
        Thread t = new Thread(this);
        t.start();
        nonBlockingFunc();

    }


    @Override
    public void run() {
        blockingFunc();
    }
}
