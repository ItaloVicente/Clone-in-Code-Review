import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.HttpTransport;
import org.eclipse.jgit.transport.RemoteSession;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.HttpSupport;
import org.eclipse.jgit.util.io.MessageWriter;
import org.eclipse.jgit.util.io.StreamCopyThread;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
