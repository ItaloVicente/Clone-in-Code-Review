        return nodes().get(nodeIndex);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class PartitionInfo {

        private final int numberOfReplicas;
        private final List<String> partitionHosts;
        private final List<Partition> partitions;
        private final List<Partition> forwardPartitions;

        PartitionInfo(
            @JsonProperty("numReplicas") int numberOfReplicas,
            @JsonProperty("serverList") List<String> partitionHosts,
            @JsonProperty("vBucketMap") List<List<Short>> partitions,
            @JsonProperty("vBucketMapForward") List<List<Short>> forwardPartitions) {
            this.numberOfReplicas = numberOfReplicas;
            trimPort(partitionHosts);
            this.partitionHosts = partitionHosts;
            this.partitions = fromPartitionList(partitions);
            this.forwardPartitions = fromPartitionList(forwardPartitions);
        }

        public int numberOfReplicas() {
            return numberOfReplicas;
        }

        public List<String> partitionHosts() {
            return partitionHosts;
        }

        public List<Partition> partitions() {
            return partitions;
        }

        public List<Partition> forwardPartitions() {
            return forwardPartitions;
        }

        private static void trimPort(List<String> input) {
            for (int i = 0; i < input.size(); i++) {
                String[] parts =  input.get(i).split(":");
                input.set(i, parts[0]);
            }
        }

        private static List<Partition> fromPartitionList(List<List<Short>> input) {
            List<Partition> partitions = new ArrayList<Partition>();
            if (input == null) {
                return partitions;
            }

            for (List<Short> partition : input) {
                short master = partition.remove(0);
                short[] replicas = new short[partition.size()];
                int i = 0;
                for (short replica : partition) {
                    replicas[i++] = replica;
                }
                partitions.add(new DefaultPartition(master, replicas));
            }
            return partitions;
        }

        @Override
        public String toString() {
            return "PartitionInfo{"
                + "numberOfReplicas=" + numberOfReplicas
                + ", partitionHosts=" + partitionHosts
                + ", partitions=" + partitions
                + ", forwardPartitions=" + forwardPartitions
                + '}';
        }
