
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.util.HttpSupport.ENCODING_GZIP;
import static org.eclipse.jgit.util.HttpSupport.ENCODING_X_GZIP;
import static org.eclipse.jgit.util.HttpSupport.HDR_ACCEPT;
import static org.eclipse.jgit.util.HttpSupport.HDR_ACCEPT_ENCODING;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_ENCODING;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_TYPE;
import static org.eclipse.jgit.util.HttpSupport.HDR_LOCATION;
import static org.eclipse.jgit.util.HttpSupport.HDR_PRAGMA;
import static org.eclipse.jgit.util.HttpSupport.HDR_USER_AGENT;
import static org.eclipse.jgit.util.HttpSupport.HDR_WWW_AUTHENTICATE;
import static org.eclipse.jgit.util.HttpSupport.METHOD_GET;
import static org.eclipse.jgit.util.HttpSupport.METHOD_POST;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.SSLHandshakeException;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.NoRemoteRepositoryException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.RefDirectory;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.transport.HttpAuthMethod.Type;
import org.eclipse.jgit.transport.HttpConfig.HttpRedirectMode;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.HttpSupport;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.SystemReader;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.eclipse.jgit.util.io.UnionInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportHttp extends HttpTransport implements WalkTransport
		PackTransport {

	private static final Logger LOG = LoggerFactory
			.getLogger(TransportHttp.class);



	public enum AcceptEncoding {
		UNSPECIFIED

		GZIP
	}

	static final TransportProtocol PROTO_HTTP = new TransportProtocol() {
		private final String[] schemeNames = { "http"

		private final Set<String> schemeSet = Collections
				.unmodifiableSet(new LinkedHashSet<>(Arrays
						.asList(schemeNames)));

		@Override
		public String getName() {
			return JGitText.get().transportProtoHTTP;
		}

		@Override
		public Set<String> getSchemes() {
			return schemeSet;
		}

		@Override
		public Set<URIishField> getRequiredFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.HOST
					URIishField.PATH));
		}

		@Override
		public Set<URIishField> getOptionalFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.USER
					URIishField.PASS
		}

		@Override
		public int getDefaultPort() {
			return 80;
		}

		@Override
		public Transport open(URIish uri
				throws NotSupportedException {
			return new TransportHttp(local
		}

		@Override
		public Transport open(URIish uri) throws NotSupportedException {
			return new TransportHttp(uri);
		}
	};

	static final TransportProtocol PROTO_FTP = new TransportProtocol() {
		@Override
		public String getName() {
			return JGitText.get().transportProtoFTP;
		}

		@Override
		public Set<String> getSchemes() {
		}

		@Override
		public Set<URIishField> getRequiredFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.HOST
					URIishField.PATH));
		}

		@Override
		public Set<URIishField> getOptionalFields() {
			return Collections.unmodifiableSet(EnumSet.of(URIishField.USER
					URIishField.PASS
		}

		@Override
		public int getDefaultPort() {
			return 21;
		}

		@Override
		public Transport open(URIish uri
				throws NotSupportedException {
			return new TransportHttp(local
		}
	};

	private URIish currentUri;

	private URL baseUrl;

	private URL objectsUrl;

	private final HttpConfig http;

	private final ProxySelector proxySelector;

	private boolean useSmartHttp = true;

	private HttpAuthMethod authMethod = HttpAuthMethod.Type.NONE.method(null);

	private Map<String

	private boolean sslVerify;

	private boolean sslFailure = false;

	TransportHttp(Repository local
			throws NotSupportedException {
		super(local
		setURI(uri);
		http = new HttpConfig(local.getConfig()
		proxySelector = ProxySelector.getDefault();
		sslVerify = http.isSslVerify();
	}

	private URL toURL(URIish urish) throws MalformedURLException {
		String uriString = urish.toString();
			uriString += '/';
		}
		return new URL(uriString);
	}

	protected void setURI(URIish uri) throws NotSupportedException {
		try {
			currentUri = uri;
			baseUrl = toURL(uri);
			objectsUrl = new URL(baseUrl
		} catch (MalformedURLException e) {
			throw new NotSupportedException(MessageFormat.format(JGitText.get().invalidURL
		}
	}

	TransportHttp(URIish uri) throws NotSupportedException {
		super(uri);
		setURI(uri);
		http = new HttpConfig(uri);
		proxySelector = ProxySelector.getDefault();
		sslVerify = http.isSslVerify();
	}

	public void setUseSmartHttp(boolean on) {
		useSmartHttp = on;
	}

	private FetchConnection getConnection(HttpConnection c
			String service) throws IOException {
		BaseConnection f;
		if (isSmartHttp(c
			readSmartHeaders(in
			f = new SmartHttpFetchConnection(in);
		} else {
			f = newDumbConnection(in);
		}
		f.setPeerUserAgent(c.getHeaderField(HttpSupport.HDR_SERVER));
		return (FetchConnection) f;
	}

	@Override
	public FetchConnection openFetch() throws TransportException
			NotSupportedException {
		final String service = SVC_UPLOAD_PACK;
		try {
			final HttpConnection c = connect(service);
			try (InputStream in = openInputStream(c)) {
				return getConnection(c
			}
		} catch (NotSupportedException | TransportException err) {
			throw err;
		} catch (IOException err) {
			throw new TransportException(uri
		}
	}

	private WalkFetchConnection newDumbConnection(InputStream in)
			throws IOException
		HttpObjectDB d = new HttpObjectDB(objectsUrl);
		Map<String
		try (BufferedReader br = toBufferedReader(in)) {
			refs = d.readAdvertisedImpl(br);
		}

		if (!refs.containsKey(HEAD)) {
			HttpConnection conn = httpOpen(
					METHOD_GET
					new URL(baseUrl
					AcceptEncoding.GZIP);
			int status = HttpSupport.response(conn);
			switch (status) {
			case HttpConnection.HTTP_OK: {
				try (BufferedReader br = toBufferedReader(
						openInputStream(conn))) {
					String line = br.readLine();
					if (line != null && line.startsWith(RefDirectory.SYMREF)) {
						String target = line.substring(RefDirectory.SYMREF.length());
						Ref r = refs.get(target);
						if (r == null)
							r = new ObjectIdRef.Unpeeled(Ref.Storage.NEW
						r = new SymbolicRef(HEAD
						refs.put(r.getName()
					} else if (line != null && ObjectId.isId(line)) {
						Ref r = new ObjectIdRef.Unpeeled(Ref.Storage.NETWORK
								HEAD
						refs.put(r.getName()
					}
				}
				break;
			}

			case HttpConnection.HTTP_NOT_FOUND:
				break;

			default:
				throw new TransportException(uri
						JGitText.get().cannotReadHEAD
						conn.getResponseMessage()));
			}
		}

		WalkFetchConnection wfc = new WalkFetchConnection(this
		wfc.available(refs);
		return wfc;
	}

	private BufferedReader toBufferedReader(InputStream in) {
		return new BufferedReader(new InputStreamReader(in
	}

	@Override
	public PushConnection openPush() throws NotSupportedException
			TransportException {
		final String service = SVC_RECEIVE_PACK;
		try {
			final HttpConnection c = connect(service);
			try (InputStream in = openInputStream(c)) {
				if (isSmartHttp(c
					return smartPush(service
				} else if (!useSmartHttp) {
					final String msg = JGitText.get().smartHTTPPushDisabled;
					throw new NotSupportedException(msg);

				} else {
					final String msg = JGitText.get().remoteDoesNotSupportSmartHTTPPush;
					throw new NotSupportedException(msg);
				}
			}
		} catch (NotSupportedException | TransportException err) {
			throw err;
		} catch (IOException err) {
			throw new TransportException(uri
		}
	}

	private PushConnection smartPush(String service
			InputStream in) throws IOException
		readSmartHeaders(in
		SmartHttpPushConnection p = new SmartHttpPushConnection(in);
		p.setPeerUserAgent(c.getHeaderField(HttpSupport.HDR_SERVER));
		return p;
	}

	@Override
	public void close() {
	}

	public void setAdditionalHeaders(Map<String
		this.headers = headers;
	}

	private NoRemoteRepositoryException createNotFoundException(URIish u
			URL url
		String text;
		if (msg != null && !msg.isEmpty()) {
			text = MessageFormat.format(JGitText.get().uriNotFoundWithMessage
					url
		} else {
			text = MessageFormat.format(JGitText.get().uriNotFound
		}
		return new NoRemoteRepositoryException(u
	}

	private HttpConnection connect(String service)
			throws TransportException
		URL u = getServiceURL(service);
		int authAttempts = 1;
		int redirects = 0;
		Collection<Type> ignoreTypes = null;
		for (;;) {
			try {
				final HttpConnection conn = httpOpen(METHOD_GET
				if (useSmartHttp) {
					conn.setRequestProperty(HDR_ACCEPT
				} else {
					conn.setRequestProperty(HDR_ACCEPT
				}
				final int status = HttpSupport.response(conn);
				switch (status) {
				case HttpConnection.HTTP_OK:
					if (authMethod.getType() == HttpAuthMethod.Type.NONE
							&& conn.getHeaderField(HDR_WWW_AUTHENTICATE) != null)
						authMethod = HttpAuthMethod.scanResponse(conn
					return conn;

				case HttpConnection.HTTP_NOT_FOUND:
					throw createNotFoundException(uri
							conn.getResponseMessage());

				case HttpConnection.HTTP_UNAUTHORIZED:
					authMethod = HttpAuthMethod.scanResponse(conn
					if (authMethod.getType() == HttpAuthMethod.Type.NONE)
						throw new TransportException(uri
								JGitText.get().authenticationNotSupported
					CredentialsProvider credentialsProvider = getCredentialsProvider();
					if (credentialsProvider == null)
						throw new TransportException(uri
								JGitText.get().noCredentialsProvider);
					if (authAttempts > 1)
						credentialsProvider.reset(currentUri);
					if (3 < authAttempts
							|| !authMethod.authorize(currentUri
									credentialsProvider)) {
						throw new TransportException(uri
								JGitText.get().notAuthorized);
					}
					authAttempts++;
					continue;

				case HttpConnection.HTTP_FORBIDDEN:
					throw new TransportException(uri
							JGitText.get().serviceNotPermitted
							service));

				case HttpConnection.HTTP_MOVED_PERM:
				case HttpConnection.HTTP_MOVED_TEMP:
				case HttpConnection.HTTP_SEE_OTHER:
				case HttpConnection.HTTP_11_MOVED_TEMP:
					if (http.getFollowRedirects() == HttpRedirectMode.FALSE) {
						throw new TransportException(uri
								MessageFormat.format(
										JGitText.get().redirectsOff
										Integer.valueOf(status)));
					}
					URIish newUri = redirect(conn.getHeaderField(HDR_LOCATION)
							Constants.INFO_REFS
					setURI(newUri);
					u = getServiceURL(service);
					authAttempts = 1;
					break;
				default:
					throw new TransportException(uri
				}
			} catch (NotSupportedException | TransportException e) {
				throw e;
			} catch (SSLHandshakeException e) {
				handleSslFailure(e);
			} catch (IOException e) {
				if (authMethod.getType() != HttpAuthMethod.Type.NONE) {
					if (ignoreTypes == null) {
						ignoreTypes = new HashSet<>();
					}

					ignoreTypes.add(authMethod.getType());

					authMethod = HttpAuthMethod.Type.NONE.method(null);
					authAttempts = 1;

					continue;
				}

				throw new TransportException(uri
			}
		}
	}

	private static class CredentialItems {
		CredentialItem.InformationalMessage message;

		CredentialItem.YesNoType now;

		CredentialItem.YesNoType forRepo;

		CredentialItem.YesNoType always;

		public CredentialItem[] items() {
			if (forRepo == null) {
				return new CredentialItem[] { message
			} else {
				return new CredentialItem[] { message
			}
		}
	}

	private void handleSslFailure(Throwable e) throws TransportException {
		if (sslFailure || !trustInsecureSslConnection(e.getCause())) {
			throw new TransportException(uri
					MessageFormat.format(
							JGitText.get().sslFailureExceptionMessage
							currentUri.setPass(null))
					e);
		}
		sslFailure = true;
	}

	private boolean trustInsecureSslConnection(Throwable cause) {
		if (cause instanceof CertificateException
				|| cause instanceof CertPathBuilderException
				|| cause instanceof CertPathValidatorException) {
			CredentialsProvider provider = getCredentialsProvider();
			if (provider != null) {
				CredentialItems trust = constructSslTrustItems(cause);
				CredentialItem[] items = trust.items();
				if (provider.supports(items)) {
					boolean answered = provider.get(uri
					if (answered) {
						boolean trustNow = trust.now.getValue();
						boolean trustLocal = trust.forRepo != null
								&& trust.forRepo.getValue();
						boolean trustAlways = trust.always.getValue();
						if (trustNow || trustLocal || trustAlways) {
							sslVerify = false;
							if (trustAlways) {
								updateSslVerifyUser(false);
							} else if (trustLocal) {
								updateSslVerify(local.getConfig()
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private CredentialItems constructSslTrustItems(Throwable cause) {
		CredentialItems items = new CredentialItems();
		String info = MessageFormat.format(JGitText.get().sslFailureInfo
				currentUri.setPass(null));
		String sslMessage = cause.getLocalizedMessage();
		if (sslMessage == null) {
			sslMessage = cause.toString();
		}
		sslMessage = MessageFormat.format(JGitText.get().sslFailureCause
				sslMessage);
		items.message = new CredentialItem.InformationalMessage(info + '\n'
				+ sslMessage + '\n'
				+ JGitText.get().sslFailureTrustExplanation);
		items.now = new CredentialItem.YesNoType(JGitText.get().sslTrustNow);
		if (local != null) {
			items.forRepo = new CredentialItem.YesNoType(
					MessageFormat.format(JGitText.get().sslTrustForRepo
					local.getDirectory()));
		}
		items.always = new CredentialItem.YesNoType(
				JGitText.get().sslTrustAlways);
		return items;
	}

	private void updateSslVerify(StoredConfig config
		int port = uri.getPort();
		if (port > 0) {
		}
		config.setBoolean(HttpConfig.HTTP
				HttpConfig.SSL_VERIFY_KEY
		try {
			config.save();
		} catch (IOException e) {
			LOG.error(JGitText.get().sslVerifyCannotSave
		}
	}

	private void updateSslVerifyUser(boolean value) {
		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null
		try {
			userConfig.load();
			updateSslVerify(userConfig
		} catch (IOException | ConfigInvalidException e) {
			LOG.error(MessageFormat.format(JGitText.get().userConfigFileInvalid
					userConfig.getFile().getAbsolutePath()
		}
	}

	private URIish redirect(String location
			throws TransportException {
		if (location == null || location.isEmpty()) {
			throw new TransportException(uri
					MessageFormat.format(JGitText.get().redirectLocationMissing
							baseUrl));
		}
		if (redirects >= http.getMaxRedirects()) {
			throw new TransportException(uri
					MessageFormat.format(JGitText.get().redirectLimitExceeded
							Integer.valueOf(http.getMaxRedirects())
							location));
		}
		try {
			if (!isValidRedirect(baseUrl
				throw new TransportException(uri
						MessageFormat.format(JGitText.get().redirectBlocked
								baseUrl
			}
			location = location.substring(0
			URIish result = new URIish(location);
			if (LOG.isInfoEnabled()) {
				LOG.info(MessageFormat.format(JGitText.get().redirectHttp
						uri.setPass(null)
						Integer.valueOf(redirects)
			}
			return result;
		} catch (URISyntaxException e) {
			throw new TransportException(uri
					MessageFormat.format(JGitText.get().invalidRedirectLocation
							baseUrl
					e);
		}
	}

	private boolean isValidRedirect(URL current
		String oldProtocol = current.getProtocol().toLowerCase(Locale.ROOT);
		if (schemeEnd < 0) {
			return false;
		}
		String newProtocol = next.substring(0
				.toLowerCase(Locale.ROOT);
		if (!oldProtocol.equals(newProtocol)) {
				return false;
			}
		}
		if (!next.contains(checkFor)) {
			return false;
		}
		return true;
	}

	private URL getServiceURL(String service)
			throws NotSupportedException {
		try {
			final StringBuilder b = new StringBuilder();
			b.append(baseUrl);

			if (b.charAt(b.length() - 1) != '/') {
				b.append('/');
			}
			b.append(Constants.INFO_REFS);

			if (useSmartHttp) {
				b.append(service);
			}

			return new URL(b.toString());
		} catch (MalformedURLException e) {
			throw new NotSupportedException(MessageFormat.format(JGitText.get().invalidURL
		}
	}

	protected HttpConnection httpOpen(String method
			AcceptEncoding acceptEncoding) throws IOException {
		if (method == null || u == null || acceptEncoding == null) {
			throw new NullPointerException();
		}

		final Proxy proxy = HttpSupport.proxyFor(proxySelector
		HttpConnection conn = connectionFactory.create(u

			HttpSupport.disableSslVerify(conn);
		}

		conn.setInstanceFollowRedirects(false);

		conn.setRequestMethod(method);
		conn.setUseCaches(false);
		if (acceptEncoding == AcceptEncoding.GZIP) {
			conn.setRequestProperty(HDR_ACCEPT_ENCODING
		}
		conn.setRequestProperty(HDR_PRAGMA
		if (UserAgent.get() != null) {
			conn.setRequestProperty(HDR_USER_AGENT
		}
		int timeOut = getTimeout();
		if (timeOut != -1) {
			int effTimeOut = timeOut * 1000;
			conn.setConnectTimeout(effTimeOut);
			conn.setReadTimeout(effTimeOut);
		}
		if (this.headers != null && !this.headers.isEmpty()) {
			for (Map.Entry<String
				conn.setRequestProperty(entry.getKey()
		}
		authMethod.configureRequest(conn);
		return conn;
	}

	final InputStream openInputStream(HttpConnection conn)
			throws IOException {
		InputStream input = conn.getInputStream();
		if (isGzipContent(conn))
			input = new GZIPInputStream(input);
		return input;
	}

	IOException wrongContentType(String expType
		final String why = MessageFormat.format(JGitText.get().expectedReceivedContentType
		return new TransportException(uri
	}

	private boolean isSmartHttp(HttpConnection c
		final String actType = c.getContentType();
		return expType.equals(actType);
	}

	private boolean isGzipContent(HttpConnection c) {
		return ENCODING_GZIP.equals(c.getHeaderField(HDR_CONTENT_ENCODING))
				|| ENCODING_X_GZIP.equals(c.getHeaderField(HDR_CONTENT_ENCODING));
	}

	private void readSmartHeaders(InputStream in
			throws IOException {
		final byte[] magic = new byte[5];
		IO.readFully(in
		if (magic[4] != '#') {
			throw new TransportException(uri
					JGitText.get().expectedPktLineWithService
		}

		final PacketLineIn pckIn = new PacketLineIn(new UnionInputStream(
				new ByteArrayInputStream(magic)
		final String act = pckIn.readString();
		if (!exp.equals(act)) {
			throw new TransportException(uri
					JGitText.get().expectedGot
		}

		while (pckIn.readString() != PacketLineIn.END) {
		}
	}

	class HttpObjectDB extends WalkRemoteObjectDatabase {
		private final URL httpObjectsUrl;

		HttpObjectDB(URL b) {
			httpObjectsUrl = b;
		}

		@Override
		URIish getURI() {
			return new URIish(httpObjectsUrl);
		}

		@Override
		Collection<WalkRemoteObjectDatabase> getAlternates() throws IOException {
			try {
				return readAlternates(INFO_HTTP_ALTERNATES);
			} catch (FileNotFoundException err) {
			}

			try {
				return readAlternates(INFO_ALTERNATES);
			} catch (FileNotFoundException err) {
			}

			return null;
		}

		@Override
		WalkRemoteObjectDatabase openAlternate(String location)
				throws IOException {
			return new HttpObjectDB(new URL(httpObjectsUrl
		}

		@Override
		BufferedReader openReader(String path) throws IOException {
			InputStream is = open(path
			return new BufferedReader(new InputStreamReader(is
		}

		@Override
		Collection<String> getPackNames() throws IOException {
			final Collection<String> packs = new ArrayList<>();
			try (BufferedReader br = openReader(INFO_PACKS)) {
				for (;;) {
					final String s = br.readLine();
					if (s == null || s.length() == 0)
						break;
						throw invalidAdvertisement(s);
					packs.add(s.substring(2));
				}
				return packs;
			} catch (FileNotFoundException err) {
				return packs;
			}
		}

		@Override
		FileStream open(String path) throws IOException {
			return open(path
		}

		FileStream open(String path
				throws IOException {
			final URL base = httpObjectsUrl;
			final URL u = new URL(base
			final HttpConnection c = httpOpen(METHOD_GET
			switch (HttpSupport.response(c)) {
			case HttpConnection.HTTP_OK:
				final InputStream in = openInputStream(c);
				if (!isGzipContent(c)) {
					final int len = c.getContentLength();
					return new FileStream(in
				}
				return new FileStream(in);
			case HttpConnection.HTTP_NOT_FOUND:
				throw new FileNotFoundException(u.toString());
			default:
						+ c.getResponseMessage());
			}
		}

		Map<String
				throws IOException
			final TreeMap<String
			for (;;) {
				String line = br.readLine();
				if (line == null)
					break;

				final int tab = line.indexOf('\t');
				if (tab < 0)
					throw invalidAdvertisement(line);

				String name;
				final ObjectId id;

				name = line.substring(tab + 1);
				id = ObjectId.fromString(line.substring(0
					name = name.substring(0
					final Ref prior = avail.get(name);
					if (prior == null)
						throw outOfOrderAdvertisement(name);

					if (prior.getPeeledObjectId() != null)

					avail.put(name
							Ref.Storage.NETWORK
							prior.getObjectId()
				} else {
					Ref prior = avail.put(name
							Ref.Storage.NETWORK
					if (prior != null)
						throw duplicateAdvertisement(name);
				}
			}
			return avail;
		}

		private PackProtocolException outOfOrderAdvertisement(String n) {
			return new PackProtocolException(MessageFormat.format(JGitText.get().advertisementOfCameBefore
		}

		private PackProtocolException invalidAdvertisement(String n) {
			return new PackProtocolException(MessageFormat.format(JGitText.get().invalidAdvertisementOf
		}

		private PackProtocolException duplicateAdvertisement(String n) {
			return new PackProtocolException(MessageFormat.format(JGitText.get().duplicateAdvertisementsOf
		}

		@Override
		void close() {
		}
	}

	class SmartHttpFetchConnection extends BasePackFetchConnection {
		private MultiRequestService svc;

		SmartHttpFetchConnection(InputStream advertisement)
				throws TransportException {
			super(TransportHttp.this);
			statelessRPC = true;

			init(advertisement
			outNeedsEnd = false;
			readAdvertisedRefs();
		}

		@Override
		protected void doFetch(final ProgressMonitor monitor
				final Collection<Ref> want
				final OutputStream outputStream) throws TransportException {
			try {
				svc = new MultiRequestService(SVC_UPLOAD_PACK);
				init(svc.getInputStream()
				super.doFetch(monitor
			} finally {
				svc = null;
			}
		}

		@Override
		protected void onReceivePack() {
			svc.finalRequest = true;
		}
	}

	class SmartHttpPushConnection extends BasePackPushConnection {
		SmartHttpPushConnection(InputStream advertisement)
				throws TransportException {
			super(TransportHttp.this);
			statelessRPC = true;

			init(advertisement
			outNeedsEnd = false;
			readAdvertisedRefs();
		}

		@Override
		protected void doPush(final ProgressMonitor monitor
				final Map<String
				OutputStream outputStream) throws TransportException {
			final Service svc = new MultiRequestService(SVC_RECEIVE_PACK);
			init(svc.getInputStream()
			super.doPush(monitor
		}
	}

	abstract class Service {
		protected final String serviceName;

		protected final String requestType;

		protected final String responseType;

		protected HttpConnection conn;

		protected HttpOutputStream out;

		protected final HttpExecuteStream execute;

		final UnionInputStream in;

		Service(String serviceName) {
			this.serviceName = serviceName;

			this.out = new HttpOutputStream();
			this.execute = new HttpExecuteStream();
			this.in = new UnionInputStream(execute);
		}

		void openStream() throws IOException {
			conn = httpOpen(METHOD_POST
					AcceptEncoding.GZIP);
			conn.setInstanceFollowRedirects(false);
			conn.setDoOutput(true);
			conn.setRequestProperty(HDR_CONTENT_TYPE
			conn.setRequestProperty(HDR_ACCEPT
		}

		void sendRequest() throws IOException {
			TemporaryBuffer buf = new TemporaryBuffer.Heap(
					http.getPostBuffer());
			try (GZIPOutputStream gzip = new GZIPOutputStream(buf)) {
				out.writeTo(gzip
				if (out.length() < buf.length())
					buf = out;
			} catch (IOException err) {
				buf = out;
			}

			HttpAuthMethod authenticator = null;
			Collection<Type> ignoreTypes = EnumSet.noneOf(Type.class);
			int authAttempts = 1;
			int redirects = 0;
			for (;;) {
				try {
					openStream();
					if (buf != out) {
						conn.setRequestProperty(HDR_CONTENT_ENCODING
								ENCODING_GZIP);
					}
					conn.setFixedLengthStreamingMode((int) buf.length());
					try (OutputStream httpOut = conn.getOutputStream()) {
						buf.writeTo(httpOut
					}

					final int status = HttpSupport.response(conn);
					switch (status) {
					case HttpConnection.HTTP_OK:
						return;

					case HttpConnection.HTTP_NOT_FOUND:
						throw createNotFoundException(uri
								conn.getResponseMessage());

					case HttpConnection.HTTP_FORBIDDEN:
						throw new TransportException(uri
								MessageFormat.format(
										JGitText.get().serviceNotPermitted
										baseUrl

					case HttpConnection.HTTP_MOVED_PERM:
					case HttpConnection.HTTP_MOVED_TEMP:
					case HttpConnection.HTTP_11_MOVED_TEMP:
						if (http.getFollowRedirects() != HttpRedirectMode.TRUE) {
							return;
						}
						currentUri = redirect(conn.getHeaderField(HDR_LOCATION)
								'/' + serviceName
						try {
							baseUrl = toURL(currentUri);
						} catch (MalformedURLException e) {
							throw new TransportException(uri
									MessageFormat.format(
											JGitText.get().invalidRedirectLocation
											baseUrl
									e);
						}
						continue;

					case HttpConnection.HTTP_UNAUTHORIZED:
						HttpAuthMethod nextMethod = HttpAuthMethod
								.scanResponse(conn
						switch (nextMethod.getType()) {
						case NONE:
							throw new TransportException(uri
									MessageFormat.format(
											JGitText.get().authenticationNotSupported
											conn.getURL()));
						case NEGOTIATE:
							ignoreTypes.add(HttpAuthMethod.Type.NEGOTIATE);
							if (authenticator != null) {
								ignoreTypes.add(authenticator.getType());
							}
							authAttempts = 1;
							break;
						default:
							ignoreTypes.add(HttpAuthMethod.Type.NEGOTIATE);
							if (authenticator == null || authenticator
									.getType() != nextMethod.getType()) {
								if (authenticator != null) {
									ignoreTypes.add(authenticator.getType());
								}
								authAttempts = 1;
							}
							break;
						}
						authMethod = nextMethod;
						authenticator = nextMethod;
						CredentialsProvider credentialsProvider = getCredentialsProvider();
						if (credentialsProvider == null) {
							throw new TransportException(uri
									JGitText.get().noCredentialsProvider);
						}
						if (authAttempts > 1) {
							credentialsProvider.reset(currentUri);
						}
						if (3 < authAttempts || !authMethod
								.authorize(currentUri
							throw new TransportException(uri
									JGitText.get().notAuthorized);
						}
						authAttempts++;
						continue;

					default:
						return;
					}
				} catch (SSLHandshakeException e) {
					handleSslFailure(e);
				} catch (IOException e) {
					if (authenticator == null || authMethod
							.getType() != HttpAuthMethod.Type.NONE) {
						if (authMethod.getType() != HttpAuthMethod.Type.NONE) {
							ignoreTypes.add(authMethod.getType());
						}
						authMethod = HttpAuthMethod.Type.NONE.method(null);
						authenticator = authMethod;
						authAttempts = 1;
						continue;
					}
					throw e;
				}
			}
		}

		void openResponse() throws IOException {
			final int status = HttpSupport.response(conn);
			if (status != HttpConnection.HTTP_OK) {
				throw new TransportException(uri
						+ conn.getResponseMessage());
			}

			final String contentType = conn.getContentType();
			if (!responseType.equals(contentType)) {
				conn.getInputStream().close();
				throw wrongContentType(responseType
			}
		}

		HttpOutputStream getOutputStream() {
			return out;
		}

		InputStream getInputStream() {
			return in;
		}

		abstract void execute() throws IOException;

		class HttpExecuteStream extends InputStream {
			@Override
			public int read() throws IOException {
				execute();
				return -1;
			}

			@Override
			public int read(byte[] b
				execute();
				return -1;
			}

			@Override
			public long skip(long n) throws IOException {
				execute();
				return 0;
			}
		}

		class HttpOutputStream extends TemporaryBuffer {
			HttpOutputStream() {
				super(http.getPostBuffer());
			}

			@Override
			protected OutputStream overflow() throws IOException {
				openStream();
				conn.setChunkedStreamingMode(0);
				return conn.getOutputStream();
			}
		}
	}

	class MultiRequestService extends Service {
		boolean finalRequest;

		MultiRequestService(String serviceName) {
			super(serviceName);
		}

		@Override
		void execute() throws IOException {
			out.close();

			if (conn == null) {
				if (out.length() == 0) {
					if (finalRequest)
						return;
					throw new TransportException(uri
							JGitText.get().startingReadStageWithoutWrittenRequestDataPendingIsNotSupported);
				}

				sendRequest();
			}

			out.reset();

			openResponse();

			in.add(openInputStream(conn));
			if (!finalRequest)
				in.add(execute);
			conn = null;
		}
	}

	class LongPollService extends Service {
		LongPollService(String serviceName) {
			super(serviceName);
		}

		@Override
		void execute() throws IOException {
			out.close();
			if (conn == null)
				sendRequest();
			openResponse();
			in.add(openInputStream(conn));
		}
	}
}
