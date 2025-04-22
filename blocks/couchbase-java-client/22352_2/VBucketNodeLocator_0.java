
    if(serverNumber == -1) {
      throw new RuntimeException("The key "+ k +" pointed to vbucket "+ vbucket
        + ", for which no server is responsible in the cluster map. This "
        + "can be an indication that either no replica is defined for a "
        + "failed server or more nodes have been failed over than replicas "
        + "defined.");
    }

