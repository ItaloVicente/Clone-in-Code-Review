
package com.couchbase.client.core.node.locate;

import com.couchbase.client.core.config.BucketConfig;
import com.couchbase.client.core.config.ClusterConfig;
import com.couchbase.client.core.config.CouchbaseBucketConfig;
import com.couchbase.client.core.config.NodeInfo;
import com.couchbase.client.core.config.Partition;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.dcp.DCPRequest;
import com.couchbase.client.core.node.Node;

import java.util.Set;

public class DCPLocator implements Locator {
    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(DCPLocator.class);


    @Override
    public Node[] locate(final CouchbaseRequest request, final Set<Node> nodes, final ClusterConfig cluster) {
        BucketConfig bucket = cluster.bucketConfig(request.bucket());
        if (!(bucket instanceof CouchbaseBucketConfig && request instanceof DCPRequest)) {
            throw new IllegalStateException("Unsupported Bucket Type: for request " + request);
        }
        CouchbaseBucketConfig config = (CouchbaseBucketConfig) bucket;
        DCPRequest dcpRequest = (DCPRequest) request;

        Partition partition = config.partitions().get(dcpRequest.partition());
        int nodeId = partition.master();
        if (nodeId == -2) {
            return null;
        }
        if (nodeId == -1) {
            return new Node[]{};
        }

        NodeInfo nodeInfo = config.partitionHosts().get(nodeId);

        if (config.partitionHosts().size() != nodes.size()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Node list and configuration's partition hosts sizes : {} <> {}, rescheduling",
                        nodes.size(), config.partitionHosts().size());
            }
            return new Node[]{};
        }

        for (Node node : nodes) {
            if (node.hostname().equals(nodeInfo.hostname())) {
                return new Node[]{node};
            }
        }

        throw new IllegalStateException("Node not found for request: " + request);
    }
}
