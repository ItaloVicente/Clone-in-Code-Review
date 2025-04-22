import java.security.GeneralSecurityException;
import java.text.MessageFormat;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.transport.http.DelegatingSSLSocketFactory;
import org.eclipse.jgit.util.HttpSupport;
