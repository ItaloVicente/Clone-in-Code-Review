
        int nodeId;
        if (request instanceof ReplicaGetRequest) {
            nodeId = partition.replica(((ReplicaGetRequest) request).replica()-1);
        } else if(request instanceof ObserveRequest && ((ObserveRequest) request).replica() > 0){
            nodeId = partition.replica(((ObserveRequest) request).replica()-1);
        } else {
            nodeId = partition.master();
        }
