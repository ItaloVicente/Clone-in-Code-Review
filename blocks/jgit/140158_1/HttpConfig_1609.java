
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.util.HttpSupport.HDR_AUTHORIZATION;
import static org.eclipse.jgit.util.HttpSupport.HDR_WWW_AUTHENTICATE;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.Base64;
import org.eclipse.jgit.util.GSSManagerFactory;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

abstract class HttpAuthMethod {
	public enum Type {
		NONE {
			@Override
			public HttpAuthMethod method(String hdr) {
				return None.INSTANCE;
			}

			@Override
			public String getSchemeName() {
			}
		}
		BASIC {
			@Override
			public HttpAuthMethod method(String hdr) {
				return new Basic();
			}

			@Override
			public String getSchemeName() {
			}
		}
		DIGEST {
			@Override
			public HttpAuthMethod method(String hdr) {
				return new Digest(hdr);
			}

			@Override
			public String getSchemeName() {
			}
		}
		NEGOTIATE {
			@Override
			public HttpAuthMethod method(String hdr) {
				return new Negotiate(hdr);
			}

			@Override
			public String getSchemeName() {
			}
		};
		public abstract HttpAuthMethod method(String hdr);

		public abstract String getSchemeName();
	}


	static HttpAuthMethod scanResponse(final HttpConnection conn
			Collection<Type> ignoreTypes) {
		final Map<String
		HttpAuthMethod authentication = Type.NONE.method(EMPTY_STRING);

		for (Entry<String
			if (HDR_WWW_AUTHENTICATE.equalsIgnoreCase(entry.getKey())) {
				if (entry.getValue() != null) {
					for (String value : entry.getValue()) {
						if (value != null && value.length() != 0) {
							final String[] valuePart = value.split(
									SCHEMA_NAME_SEPARATOR

							try {
								Type methodType = Type.valueOf(
										valuePart[0].toUpperCase(Locale.ROOT));

								if ((ignoreTypes != null)
										&& (ignoreTypes.contains(methodType))) {
									continue;
								}

								if (authentication.getType().compareTo(methodType) >= 0) {
									continue;
								}

								final String param;
								if (valuePart.length == 1)
									param = EMPTY_STRING;
								else
									param = valuePart[1];

								authentication = methodType
										.method(param);
							} catch (IllegalArgumentException e) {
							}
						}
					}
				}
				break;
			}
		}

		return authentication;
	}

	protected final Type type;

	protected HttpAuthMethod(Type type) {
		this.type = type;
	}

	boolean authorize(URIish uri
		String username;
		String password;

		if (credentialsProvider != null) {
			CredentialItem.Username u = new CredentialItem.Username();
			CredentialItem.Password p = new CredentialItem.Password();

			if (credentialsProvider.supports(u
					&& credentialsProvider.get(uri
				username = u.getValue();
				char[] v = p.getValue();
				password = (v == null) ? null : new String(p.getValue());
				p.clear();
			} else
				return false;
		} else {
			username = uri.getUser();
			password = uri.getPass();
		}
		if (username != null) {
			authorize(username
			return true;
		}
		return false;
	}

	abstract void authorize(String user

	abstract void configureRequest(HttpConnection conn) throws IOException;

	public Type getType() {
		return type;
	}

	private static class None extends HttpAuthMethod {
		static final None INSTANCE = new None();
		public None() {
			super(Type.NONE);
		}

		@Override
		void authorize(String user
		}

		@Override
		void configureRequest(HttpConnection conn) throws IOException {
		}
	}

	private static class Basic extends HttpAuthMethod {
		private String user;

		private String pass;

		public Basic() {
			super(Type.BASIC);
		}

		@Override
		void authorize(String username
			this.user = username;
			this.pass = password;
		}

		@Override
		void configureRequest(HttpConnection conn) throws IOException {
			String enc = Base64.encodeBytes(ident.getBytes(UTF_8));
			conn.setRequestProperty(HDR_AUTHORIZATION
		}
	}

	private static class Digest extends HttpAuthMethod {
		private static final SecureRandom PRNG = new SecureRandom();

		private final Map<String

		private int requestCount;

		private String user;

		private String pass;

		Digest(String hdr) {
			super(Type.DIGEST);
			params = parse(hdr);

				final byte[] bin = new byte[8];
				PRNG.nextBytes(bin);
				params.put("cnonce"
			}
		}

		@Override
		void authorize(String username
			this.user = username;
			this.pass = password;
		}

		@SuppressWarnings("boxing")
		@Override
		void configureRequest(HttpConnection conn) throws IOException {
			final Map<String

			final String uri = uri(conn.getURL());
			final String method = conn.getRequestMethod();


			r.put("username"
			r.put("realm"
			r.put("nonce"
			r.put("uri"

			final String response
				nc = String.format("%08x"
				response = KD(H(A1)
						+ H(A2));
			} else {
				nc = null;
				response = KD(H(A1)
			}
			r.put("response"
				r.put("algorithm"
			if (cnonce != null && qop != null)
				r.put("cnonce"
				r.put("opaque"
			if (qop != null)
				r.put("qop"
			if (nc != null)
				r.put("nc"

			StringBuilder v = new StringBuilder();
			for (Map.Entry<String
				if (v.length() > 0)
					v.append("
				v.append(e.getKey());
				v.append('=');
				v.append('"');
				v.append(e.getValue());
				v.append('"');
			}
			conn.setRequestProperty(HDR_AUTHORIZATION
		}

		private static String uri(URL u) {
			StringBuilder r = new StringBuilder();
			r.append(u.getProtocol());
			r.append(u.getHost());
			if (0 < u.getPort()) {
				} else if (u.getPort() == 443
				} else {
					r.append(':').append(u.getPort());
				}
			}
			r.append(u.getPath());
			if (u.getQuery() != null)
				r.append('?').append(u.getQuery());
			return r.toString();
		}

		private static String H(String data) {
			MessageDigest md = newMD5();
			md.update(data.getBytes(UTF_8));
			return LHEX(md.digest());
		}

		private static String KD(String secret
			MessageDigest md = newMD5();
			md.update(secret.getBytes(UTF_8));
			md.update((byte) ':');
			md.update(data.getBytes(UTF_8));
			return LHEX(md.digest());
		}

		private static MessageDigest newMD5() {
			try {
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("No MD5 available"
			}
		}

		private static final char[] LHEX = { '0'
				'7'
				'a'

		private static String LHEX(byte[] bin) {
			StringBuilder r = new StringBuilder(bin.length * 2);
			for (int i = 0; i < bin.length; i++) {
				byte b = bin[i];
				r.append(LHEX[(b >>> 4) & 0x0f]);
				r.append(LHEX[b & 0x0f]);
			}
			return r.toString();
		}

		private static Map<String
			Map<String
			int next = 0;
			while (next < auth.length()) {
				if (next < auth.length() && auth.charAt(next) == '
					next++;
				}
				while (next < auth.length()
						&& Character.isWhitespace(auth.charAt(next))) {
					next++;
				}

				int eq = auth.indexOf('='
				if (eq < 0 || eq + 1 == auth.length()) {
					return Collections.emptyMap();
				}

				final String name = auth.substring(next
				final String value;
				if (auth.charAt(eq + 1) == '"') {
					int dq = auth.indexOf('"'
					if (dq < 0) {
						return Collections.emptyMap();
					}
					value = auth.substring(eq + 2
					next = dq + 1;

				} else {
					int space = auth.indexOf(' '
					int comma = auth.indexOf('
					if (space < 0)
						space = auth.length();
					if (comma < 0)
						comma = auth.length();

					final int e = Math.min(space
					value = auth.substring(eq + 1
					next = e + 1;
				}
				p.put(name
			}
			return p;
		}
	}

	private static class Negotiate extends HttpAuthMethod {
		private static final GSSManagerFactory GSS_MANAGER_FACTORY = GSSManagerFactory
				.detect();

		private static final Oid OID;
		static {
			try {
			} catch (GSSException e) {
				throw new Error("Cannot create NEGOTIATE oid."
			}
		}

		private final byte[] prevToken;

		public Negotiate(String hdr) {
			super(Type.NEGOTIATE);
			prevToken = Base64.decode(hdr);
		}

		@Override
		void authorize(String user
		}

		@Override
		void configureRequest(HttpConnection conn) throws IOException {
			GSSManager gssManager = GSS_MANAGER_FACTORY.newInstance(conn
					.getURL());
			String host = conn.getURL().getHost();
			try {
				GSSName gssName = gssManager.createName(peerName
						GSSName.NT_HOSTBASED_SERVICE);
				GSSContext context = gssManager.createContext(gssName
						null
				context.requestCredDeleg(true);

				byte[] token = context.initSecContext(prevToken
						prevToken.length);

				conn.setRequestProperty(HDR_AUTHORIZATION
			} catch (GSSException e) {
				throw new IOException(e);
			}
		}
	}
}
