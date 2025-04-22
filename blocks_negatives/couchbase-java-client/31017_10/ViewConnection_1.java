import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.nio.protocol.AsyncNHttpClientHandler;
import org.apache.http.nio.util.DirectByteBufferAllocator;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
