    THIRD(1);

    private int maxAffectedNodes;

    ReplicaMode(int maxAffectedNodes) {
        this.maxAffectedNodes = maxAffectedNodes;
    }

    public int maxAffectedNodes() {
        return maxAffectedNodes;
    }
