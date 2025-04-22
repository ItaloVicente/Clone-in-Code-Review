  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CouchbaseConnectionFactory{");
    sb.append(", bucket='").append(getBucketName()).append('\'');
    sb.append(", nodes=").append(getStoredBaseList());
    sb.append(", order=").append(getStreamingNodeOrder());
    sb.append(", opTimeout=").append(getOperationTimeout());
    sb.append(", opQueue=").append(getOpQueueLen());
    sb.append(", opQueueBlockTime=").append(getOpQueueMaxBlockTime());
    sb.append(", obsPollInt=").append(getObsPollInterval());
    sb.append(", obsPollMax=").append(getObsPollMax());
    sb.append(", obsTimeout=").append(getObsTimeout());
    sb.append(", viewConns=").append(getViewConnsPerNode());
    sb.append(", viewTimeout=").append(getViewTimeout());
    sb.append(", viewWorkers=").append(getViewWorkerSize());
    sb.append(", configCheck=").append(getMaxConfigCheck());
    sb.append(", reconnectInt=").append(getMinReconnectInterval());
    sb.append(", failureMode=").append(getFailureMode());
    sb.append(", hashAlgo=").append(getHashAlg());
    sb.append('}');
    return sb.toString();
  }
