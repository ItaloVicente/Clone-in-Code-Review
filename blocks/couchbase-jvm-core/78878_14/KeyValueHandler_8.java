import java.util.concurrent.TimeUnit;

import static com.couchbase.client.core.endpoint.kv.ErrorMap.ErrorAttribute.AUTH;
import static com.couchbase.client.core.endpoint.kv.ErrorMap.ErrorAttribute.AUTO_RETRY;
import static com.couchbase.client.core.endpoint.kv.ErrorMap.ErrorAttribute.CONN_STATE_INVALIDATED;
import static com.couchbase.client.core.endpoint.kv.ErrorMap.ErrorAttribute.FETCH_CONFIG;
import static com.couchbase.client.core.endpoint.kv.ErrorMap.ErrorAttribute.RETRY_LATER;
import static com.couchbase.client.core.endpoint.kv.ErrorMap.ErrorAttribute.RETRY_NOW;
import static com.couchbase.client.core.endpoint.kv.ErrorMap.ErrorAttribute.SUBDOC;
import static com.couchbase.client.core.endpoint.kv.ErrorMap.ErrorAttribute.TEMP;
import static com.couchbase.client.core.endpoint.kv.ErrorMap.RetryStrategy.CONSTANT;
import static com.couchbase.client.core.endpoint.kv.ErrorMap.RetryStrategy.EXPONENTIAL;
import static com.couchbase.client.core.endpoint.kv.ErrorMap.RetryStrategy.LINEAR;
