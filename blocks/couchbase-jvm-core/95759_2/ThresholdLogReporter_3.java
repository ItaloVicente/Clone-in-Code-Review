
package com.couchbase.client.core.tracing;

import java.util.HashMap;

public class OperationContext {

    public String serviceType;

    private String operationId;
    private String connectionId;
    private String bucketName;
    private String localEndpoint;
    private String remoteEndpoint;
    private Long timeoutMicros;
    private Long serverDuration;

    public OperationContext(String serviceType)
    {
        this.serviceType = serviceType;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setLocalEndpoint(String localEndpoint) {
        this.localEndpoint = localEndpoint;
    }

    public void setRemoteEndpoint(String remoteEndpoint) {
        this.remoteEndpoint = remoteEndpoint;
    }

    public void setTimeoutMicros(Long timeoutMicros) {
        this.timeoutMicros = timeoutMicros;
    }

    public void setServerDuration(Long serverDuration) {
        this.serverDuration = serverDuration;
    }

    public HashMap<String, Object> ToHashMap()
    {
        HashMap<String, Object> entry = new HashMap<String, Object>();
        entry.put("s", serviceType);

        if (operationId != null && !operationId.isEmpty()) {
            entry.put("i", operationId);
        }
        if (connectionId != null && !connectionId.isEmpty()) {
            entry.put("c", connectionId);
        }
        if (bucketName != null && !bucketName.isEmpty()) {
            entry.put("b", bucketName);
        }
        if (localEndpoint != null && !localEndpoint.isEmpty()) {
            entry.put("l", localEndpoint);
        }
        if (remoteEndpoint != null && !remoteEndpoint.isEmpty()) {
            entry.put("r", remoteEndpoint);
        }
        if (timeoutMicros > 0) {
            entry.put("t", timeoutMicros);
        }
        if (serverDuration > 0) {
            entry.put("d", serverDuration);
        }

        return entry;
    }

    public static OperationContext CreateKvContext(String operationId) {
        OperationContext context = new OperationContext("kv");
        context.setOperationId(operationId);

        return context;
    }
}
