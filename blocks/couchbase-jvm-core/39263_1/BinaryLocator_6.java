
        Partition partition = config.partitions().get(partitionId);
        int nodeId = request instanceof ReplicaGetRequest
            ? partition.replica(((ReplicaGetRequest) request).replica()-1) : partition.master();

        if (nodeId == -2) {
            request.observable().onError(new ReplicaNotConfiguredException("Replica number "
                + ((ReplicaGetRequest) request).replica() + " not configured for bucket " + config.name()));
            return null;
        }
        if (nodeId == -1) {
