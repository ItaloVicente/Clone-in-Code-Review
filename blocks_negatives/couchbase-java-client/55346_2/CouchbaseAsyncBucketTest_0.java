import static com.couchbase.client.core.endpoint.ResponseStatusConverter.BINARY_SUCCESS;
import static com.couchbase.client.java.query.Select.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

