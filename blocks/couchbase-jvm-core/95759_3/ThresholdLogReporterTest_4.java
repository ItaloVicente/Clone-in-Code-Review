
package com.couchbase.client.core.tracing;

public interface ZombieResponseReporter {

    void Report(OperationContext context);

}
