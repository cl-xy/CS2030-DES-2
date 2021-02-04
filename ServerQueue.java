package cs2030.simulator;

class ServerQueue {
    private final int serverId;
    private final int currQueueLength;

    public ServerQueue(int serverId, int currQueueLength) {
        this.serverId = serverId;
        this.currQueueLength = currQueueLength;
    }

    public int getServerId() {
        return this.serverId;
    }

    public int getQueueLength() {
        return this.currQueueLength;
    }
}
