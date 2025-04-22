import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.query.dsl.path.DefaultFromPath;
import com.couchbase.client.java.query.dsl.path.DefaultGroupByPath;
import com.couchbase.client.java.query.dsl.path.DefaultHintPath;
import com.couchbase.client.java.query.dsl.path.DefaultLetPath;
import com.couchbase.client.java.query.dsl.path.DefaultLimitPath;
import com.couchbase.client.java.query.dsl.path.DefaultOffsetPath;
import com.couchbase.client.java.query.dsl.path.DefaultOrderByPath;
import com.couchbase.client.java.query.dsl.path.DefaultSelectPath;
import com.couchbase.client.java.query.dsl.path.DefaultWherePath;
import com.couchbase.client.java.query.dsl.path.Path;
import com.couchbase.client.java.query.dsl.path.SelectResultPath;
import com.couchbase.client.java.query.dsl.path.index.IndexReference;
import com.couchbase.client.java.query.dsl.path.index.IndexType;
import org.junit.Test;

