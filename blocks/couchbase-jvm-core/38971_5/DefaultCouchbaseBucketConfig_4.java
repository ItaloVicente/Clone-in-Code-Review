        @Override
        public String toString() {
            return "PartitionInfo{" +
                "numberOfReplicas=" + numberOfReplicas +
                ", partitionHosts=" + partitionHosts +
                ", partitions=" + partitions +
                ", forwardPartitions=" + forwardPartitions +
                '}';
        }
    }

    @Override
    public long rev() {
        return rev;
