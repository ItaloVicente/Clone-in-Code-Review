
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.util.HttpSupport.HDR_AUTHORIZATION;
import static org.eclipse.jgit.util.HttpSupport.HDR_WWW_AUTHENTICATE;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.eclipse.jgit.util.Base64;

abstract class HttpAuthMethod {
	static final HttpAuthMethod NONE = new None();

	static HttpAuthMethod scanResponse(HttpURLConnection conn) {
		String hdr = conn.getHeaderField(HDR_WWW_AUTHENTICATE);
		if (hdr == null || hdr.length() == 0)
			return NONE;

		int sp = hdr.indexOf(' ');
		if (sp < 0)
			return NONE;

		String type = hdr.substring(0
		if (Basic.NAME.equals(type))
			return new Basic();
		else if (Digest.NAME.equals(type))
			return new Digest(hdr.substring(sp + 1));
		else
			return NONE;
	}

	void authorize(URIish uri) {
		authorize(uri.getUser()
	}

	abstract void authorize(String user

	abstract void configureRequest(HttpURLConnection conn) throws IOException;

	private static class None extends HttpAuthMethod {
		@Override
		void authorize(String user
		}

		@Override
		void configureRequest(HttpURLConnection conn) throws IOException {
		}
	}

	private static class Basic extends HttpAuthMethod {
		static final String NAME = "Basic";

		private String user;

		private String pass;

		@Override
		void authorize(final String username
			this.user = username;
			this.pass = password;
		}

		@Override
		void configureRequest(final HttpURLConnection conn) throws IOException {
			String ident = user + ":" + pass;
			String enc = Base64.encodeBytes(ident.getBytes("UTF-8"));
			conn.setRequestProperty(HDR_AUTHORIZATION
		}
	}

	private static class Digest extends HttpAuthMethod {
		static final String NAME = "Digest";

		private static final Random PRNG = new Random();

		private final Map<String

		private int requestCount;

		private String user;

		private String pass;

		Digest(String hdr) {
			params = parse(hdr);

			final String qop = params.get("qop");
			if ("auth".equals(qop)) {
				final byte[] bin = new byte[8];
				PRNG.nextBytes(bin);
				params.put("cnonce"
			}
		}

		@Override
		void authorize(final String username
			this.user = username;
			this.pass = password;
		}

		@SuppressWarnings("boxing")
		@Override
		void configureRequest(final HttpURLConnection conn) throws IOException {
			final Map<String
			p.put("username"

			final String realm = p.get("realm");
			final String nonce = p.get("nonce");
			final String uri = p.get("uri");
			final String qop = p.get("qop");
			final String method = conn.getRequestMethod();

			final String A1 = user + ":" + realm + ":" + pass;
			final String A2 = method + ":" + uri;

			final String expect;
			if ("auth".equals(qop)) {
				final String c = p.get("cnonce");
				final String nc = String.format("%8.8x"
				p.put("nc"
				expect = KD(H(A1)
						+ H(A2));
			} else {
				expect = KD(H(A1)
			}
			p.put("response"

			StringBuilder v = new StringBuilder();
			for (Map.Entry<String
				if (v.length() > 0) {
					v.append("
				}
				v.append(e.getKey());
				v.append('=');
				v.append('"');
				v.append(e.getValue());
				v.append('"');
			}
			conn.setRequestProperty(HDR_AUTHORIZATION
		}

		private static String H(String data) {
			try {
				MessageDigest md = newMD5();
				md.update(data.getBytes("UTF-8"));
				return LHEX(md.digest());
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("UTF-8 encoding not available"
			}
		}

		private static String KD(String secret
			try {
				MessageDigest md = newMD5();
				md.update(secret.getBytes("UTF-8"));
				md.update((byte) ':');
				md.update(data.getBytes("UTF-8"));
				return LHEX(md.digest());
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("UTF-8 encoding not available"
			}
		}

		private static MessageDigest newMD5() {
			try {
				return MessageDigest.getInstance("MD5");
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
}
