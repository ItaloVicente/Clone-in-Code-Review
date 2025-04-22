package com.couchbase.client.core.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CouchbasePartitionInfo {

    private final int numberOfReplicas;
    private final String[] partitionHosts;
    private final List<Partition> partitions;
    private final boolean tainted;

    CouchbasePartitionInfo(
        @JsonProperty("numReplicas") int numberOfReplicas,
        @JsonProperty("serverList") List<String> partitionHosts,
        @JsonProperty("vBucketMap") List<List<Short>> partitions,
        @JsonProperty("vBucketMapForward") List<List<Short>> forwardPartitions) {
        this.numberOfReplicas = numberOfReplicas;
        trimPort(partitionHosts);
        this.partitionHosts = partitionHosts.toArray(new String[partitionHosts.size()]);
        this.partitions = fromPartitionList(partitions);
        this.tainted = forwardPartitions != null && !forwardPartitions.isEmpty();
    }

    public int numberOfReplicas() {
        return numberOfReplicas;
    }

    public String[] partitionHosts() {
        return partitionHosts;
    }

    public List<Partition> partitions() {
        return partitions;
    }

    public boolean tainted() {
        return tainted;
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
            + ", partitionHosts=" + Arrays.toString(partitionHosts)
            + ", partitions=" + partitions
            + ", tainted=" + tainted
            + '}';
    }
}
