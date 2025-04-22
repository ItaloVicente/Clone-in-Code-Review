package org.eclipse.egit.core;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.eclipse.jgit.lib.Repository;

public class NetUtil {

	private static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}
	} };

	private static HostnameVerifier trustAllHostNames = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	public static void setSslVerification(Repository repo,
			HttpURLConnection conn) throws IOException {
		if ("https".equals(conn.getURL().getProtocol())) { //$NON-NLS-1$
			HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
			if (!repo.getConfig().getBoolean("http", "sslVerify", true)) { //$NON-NLS-1$ //$NON-NLS-2$
				try {
					SSLContext ctx = SSLContext.getInstance("TLS"); //$NON-NLS-1$
					ctx.init(null, trustAllCerts, null);
					httpsConn.setSSLSocketFactory(ctx.getSocketFactory());
					httpsConn.setHostnameVerifier(trustAllHostNames);
				} catch (KeyManagementException e) {
					throw new IOException(e.getMessage());
				} catch (NoSuchAlgorithmException e) {
					throw new IOException(e.getMessage());
				}
			}
		}
	}
}
