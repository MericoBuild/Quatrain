/**
 * 
 */
package org.stanzax.quatrain.server;

import java.io.IOException;

/**
 * @author basicthinker
 * 
 */
public class SampleServer extends MrServer {

    public SampleServer(String address, int port, int handlerCount) throws IOException {
        super(address, port, handlerCount);
    }

    /** Remotely called procedure */
    private void functionName() {
        int[] data = { 0, 1, 2, 3, 4, 5, 6 };
        for (int index : data) {
            new Thread(new MrWorker(data[index])).start();
        }

        try {
            // partially return
            preturn("0 arrives without awaiting -1.");
            Thread.sleep(1000000); // simulates calculation or straggler
            preturn("-1 arrives without defering 0.");
        } catch (InterruptedException e) {
            // when not all preturns finish
            e.printStackTrace();
        }
    }

    /** Thread that constructs partial return(s) */
    private class MrWorker implements Runnable {

        public MrWorker(int item) {
            this.item = item;
        }

        @Override
        public void run() {
            // partially return
            preturn(item);
        }

        private int item;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            SampleServer server = new SampleServer("localhost", 3122, 0);
            server.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
