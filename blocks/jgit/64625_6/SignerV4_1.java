package org.eclipse.jgit.lfs.server.s3;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.eclipse.jgit.lfs.server.s3.SignerV4.UNSIGNED_PAYLOAD;
import static org.eclipse.jgit.lfs.server.s3.SignerV4.X_AMZ_CONTENT_SHA256;
import static org.eclipse.jgit.lfs.server.s3.SignerV4.X_AMZ_EXPIRES;
import static org.eclipse.jgit.lfs.server.s3.SignerV4.X_AMZ_STORAGE_CLASS;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_LENGTH;
import static org.eclipse.jgit.util.HttpSupport.METHOD_GET;
import static org.eclipse.jgit.util.HttpSupport.METHOD_HEAD;
import static org.eclipse.jgit.util.HttpSupport.METHOD_PUT;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.server.LargeFileRepository;
import org.eclipse.jgit.lfs.server.Response;
import org.eclipse.jgit.lfs.server.Response.Action;
import org.eclipse.jgit.lfs.server.internal.LfsServerText;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.transport.http.apache.HttpClientConnectionFactory;
import org.eclipse.jgit.util.HttpSupport;

public class S3Repository implements LargeFileRepository {

	private S3Config s3Config;

	public S3Repository(S3Config config) {
		this.s3Config = config;
	}

	@Override
	public Response.Action getDownloadAction(AnyLongObjectId oid) {
		URL endpointUrl = getObjectUrl(oid);
		Map<String
		queryParams.put(X_AMZ_EXPIRES
				Integer.toString(s3Config.getExpirationSeconds()));
		Map<String
		String authorizationQueryParameters = SignerV4.createAuthorizationQuery(
				s3Config
				UNSIGNED_PAYLOAD);

		Response.Action a = new Response.Action();
		return a;
	}

	@Override
	public Response.Action getUploadAction(AnyLongObjectId oid
		cacheObjectMetaData(oid
		URL objectUrl = getObjectUrl(oid);
		Map<String
		headers.put(X_AMZ_CONTENT_SHA256
		headers.put(HDR_CONTENT_LENGTH
		headers.put(X_AMZ_STORAGE_CLASS
		headers.put(HttpSupport.HDR_CONTENT_TYPE
		headers = SignerV4.createHeaderAuthorization(s3Config
				METHOD_PUT

		Response.Action a = new Response.Action();
		a.href = objectUrl.toString();
		a.header = new HashMap<>();
		a.header.putAll(headers);
		return a;
	}

	@Override
	public Action getVerifyAction(AnyLongObjectId id) {
	}

	@Override
	public long getSize(AnyLongObjectId oid) throws IOException {
		URL endpointUrl = getObjectUrl(oid);
		Map<String
		queryParams.put(X_AMZ_EXPIRES
				Integer.toString(s3Config.getExpirationSeconds()));
		Map<String

		String authorizationQueryParameters = SignerV4.createAuthorizationQuery(
				s3Config
				UNSIGNED_PAYLOAD);
				+ authorizationQueryParameters;

		Proxy proxy = HttpSupport.proxyFor(ProxySelector.getDefault()
				endpointUrl);
		HttpClientConnectionFactory f = new HttpClientConnectionFactory();
		HttpConnection conn = f.create(new URL(href)
		if (s3Config.isDisableSslVerify()) {
			HttpSupport.disableSslVerify(conn);
		}
		conn.setRequestMethod(METHOD_HEAD);
		conn.connect();
		int status = conn.getResponseCode();
		if (status == SC_OK) {
			String contentLengthHeader = conn
					.getHeaderField(HDR_CONTENT_LENGTH);
			if (contentLengthHeader != null) {
				return Integer.parseInt(contentLengthHeader);
			}
		}
		return -1;
	}

	protected void cacheObjectMetaData(AnyLongObjectId oid
	}

	private URL getObjectUrl(AnyLongObjectId oid) {
		try {
					s3Config.getRegion()
					getPath(oid)));
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(MessageFormat.format(
					LfsServerText.get().unparsableEndpoint
		}
	}

	private String getPath(AnyLongObjectId oid) {
		return oid.getName();
	}
}
