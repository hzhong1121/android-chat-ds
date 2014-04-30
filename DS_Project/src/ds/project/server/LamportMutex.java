package ds.project.server;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

public class LamportMutex{
	ArrayList<Integer> servers;
	Integer rs;
	DirectClock v;
	Set<Integer> curAcks;
	ArrayList<PriorityQueue<MutexMsg>> pendingMsgs;
	int[][] msgSequence;
    int[] q;
    int myId;
    int numServers;
    
    // Constants
    final static int WAIT_CONSTANT = 100;
    final static int INFINITY = -1;
    
    /**
     * Helper class that enables us to use a PriorityQueue to handle messages in FIFO order
     * 
     */
    private class MutexMsg implements Comparable {    	
    	private int sequenceNum;
		private int src;
		private int timeStamp;
		private String tag;

		public MutexMsg(int sequenceNum, int src, int timeStamp, String tag) {
    		this.sequenceNum = sequenceNum;
    		this.src = src;
    		this.timeStamp = timeStamp;
    		this.tag = tag;
    	}

		public int compareTo(Object o) {
			MutexMsg m = (MutexMsg) o;
			if (this.sequenceNum < m.sequenceNum) {
				return -1;
			} else if (this.sequenceNum > m.sequenceNum) {
				return 1;
			}
			return 0;
		}    	
    }
    
    public LamportMutex(ArrayList<Integer> serversList, int id) {
    	this.servers = serversList;
    	this.numServers = serversList.size();
    	this.myId = id;
    	this.rs = serversList.get(id);    	
    	this.curAcks = new HashSet<Integer>();
        this.v = new DirectClock(numServers, id);
        this.q = new int[numServers];
        this.msgSequence = new int[numServers][numServers];
        this.pendingMsgs = new ArrayList<PriorityQueue<MutexMsg>>();
        
        for (int j = 0; j < numServers; j++) {
        	pendingMsgs.add(j, new PriorityQueue<MutexMsg>());
            q[j] = INFINITY;
        }
    }
    
    /**
     * Sends out a broadcast to request the critical section
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public synchronized void requestCS() throws IOException, InterruptedException {
        v.tick();
        q[myId] = v.getValue(myId);
        System.out.println("s" + this.myId + " is requesting the critical section with timestamp " + q[myId]);
        broadcastMsg("request", q[myId]);

        while (!okayCS()) {
        	long start = System.currentTimeMillis();
			wait(WAIT_CONSTANT);
			long end = System.currentTimeMillis();

			// Check to see if process has waited too long 
			if ((end - start) >= WAIT_CONSTANT) {
				if (altOkayCS()) break; // Perform alternate cs check
			}
        }
        curAcks.clear();
        System.out.println("s" + this.myId + " entering critical section!");
    }

    /**
     * Releases critical section and broadcasts message saying so.
     * 
     * @throws IOException
     */
	public synchronized void releaseCS() throws IOException {
        q[myId] = INFINITY;
        System.out.println("s" + this.myId + " has left the critical section");
        broadcastMsg("release", v.getValue(myId));
    }

	/**
	 * Determines if process can enter critical section based on what servers are currently active
	 * 
	 * @return true if process can enter critical section
	 */
	boolean altOkayCS() {
        for (int j = 0 ; j < numServers; j++){
        	if (j == myId) continue;
            if (isGreater(q[myId], myId, q[j], j) && curAcks.contains(j)) {
            	return false;	
            }
            if (isGreater(q[myId], myId, v.getValue(j), j) && curAcks.contains(j)) {
                return false;
            }
        }
        return true;
    }

	/**
	 * Determines if a process can enter the critical section based on state of queue and direct clock.
	 * 
	 * @return true if process can enter critical section
	 */
	boolean okayCS() {
        for (int j = 0 ; j < numServers; j++){
            if (isGreater(q[myId], myId, q[j], j))
                return false;
            if (isGreater(q[myId], myId, v.getValue(j), j))
                return false;
        }
        return true;
    }

	/**
	 * Determines if p1's timestamp is greater than p2's
	 * 
	 * @param entry1 p1's timestamp
	 * @param pid1 p1's id
	 * @param entry2 p2's timestamp
	 * @param pid2 p2's id
	 * @return true if p1 > than p2
	 */
	boolean isGreater(int entry1, int pid1, int entry2, int pid2) {
        if (entry2 == INFINITY) return false;
        return ((entry1 > entry2) || ((entry1 == entry2) && (pid1 > pid2)));
    }

	/**
	 * Broadcasts message to all known servers
	 * 
	 * @param tag Message being sent
	 * @param timeStamp Timestamp of source server
	 */
    private void broadcastMsg(String tag, int timeStamp) {
		for(int i = 0; i < numServers; i++){
			try{
				if (i != myId) {					
					sendMsg(myId, i, tag, timeStamp, ++msgSequence[myId][i], null);
				}
			} catch(BindException e){
        		System.out.println("socket already opened");
        		System.exit(1);
			} catch(IOException e) {
				System.out.println("io exception broadcasting message");
				System.exit(1);
			}
		}
	}
    
    /**
     * Sends a special message to a server that holds mutex information.
     * 
     * @param srcId Id of the source server
     * @param destId Id of the destination server
     * @param tag Message being sent
     * @param timeStamp Timestamp of source server
     * @param sequenceNum The appropriate UDP message sequence number
     * @throws IOException
     */
	public synchronized void sendMsg(int srcId, int destId, String tag, int timeStamp, int sequenceNum, DatagramPacket dp) throws IOException {
		
	}

	/**
	 * Checks if the received message is expected. 
	 * 
	 * @param src
	 * @param sequence
	 * @return
	 */
	private boolean expectedMsg(int src, int sequence) {
		return (this.msgSequence[src][myId] + 1) == sequence;
	}
    
	/**
	 * Handles messages for LamportMutex in FIFO fashion based using a UDP message ordering scheme.
	 * 
	 * @param timeStamp Timestamp provided from source server
	 * @param src Id of server that sent message
	 * @param msg Message that was sent
	 * @param sequenceNum The UDP order number determined by the sender of the packet
	 * @throws IOException
	 */
    public synchronized void handleMsg(DatagramPacket dp) throws IOException {    	
		StringTokenizer st = new StringTokenizer(new String(dp.getData()));
		String msg = st.nextToken();
		int timeStamp = Integer.parseInt(st.nextToken().trim());
		int src = Integer.parseInt(st.nextToken().trim());
		int sequenceNum = Integer.parseInt(st.nextToken().trim());

    	PriorityQueue<MutexMsg> pq = pendingMsgs.get(src);
    	
    	// Queue up a new message from src
    	pq.add(new MutexMsg(sequenceNum, src, timeStamp, msg));
    	MutexMsg m = pq.peek();
        
    	// Handle all possible messages from source in FIFO fashion
    	while(!pq.isEmpty() && expectedMsg(m.src, m.sequenceNum)) {
    		this.msgSequence[src][myId]++;
    		v.receiveAction(m.src, m.timeStamp);
    		
    		// Handle message
            if (m.tag.equals("ack")) {
            	curAcks.add(src);
            } else if (m.tag.equals("request")) {
                q[src] = timeStamp;        
                sendMsg(myId, src, "ack", v.getValue(myId), ++msgSequence[myId][src], dp);                
            } else if (m.tag.equals("release")) {
                q[src] = INFINITY;
            }
            pq.poll();
            notify();
    	}
    }
}