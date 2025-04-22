import java.util.concurrent.TimeUnit;

import static com.couchbase.client.core.endpoint.util.ByteBufJsonHelper.findNextChar;
import static com.couchbase.client.core.endpoint.util.ByteBufJsonHelper.findNextCharNotPrefixedBy;
import static com.couchbase.client.core.endpoint.util.ByteBufJsonHelper.findSectionClosingPosition;
import static com.couchbase.client.core.endpoint.util.ByteBufJsonHelper.findSplitPosition;
