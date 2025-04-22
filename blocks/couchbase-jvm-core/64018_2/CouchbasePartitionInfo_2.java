        if (forwardPartitions != null && !forwardPartitions.isEmpty()) {
            this.forwardPartitions = fromPartitionList(forwardPartitions);
            this.tainted = true;
        } else {
            this.forwardPartitions = null;
            this.tainted = false;
        }
    }

    public boolean hasFastForwardMap() {
        return forwardPartitions != null;
