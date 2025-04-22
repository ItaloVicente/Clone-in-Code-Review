
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestOutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.util.Base64;
import org.eclipse.jgit.util.HttpSupport;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class AmazonS3 {
	private static final Set<String> SIGNED_HEADERS;




	static {
		SIGNED_HEADERS = new HashSet<>();
	}

	private static boolean isSignedHeader(String name) {
		final String nameLC = StringUtils.toLowerCase(name);
	}

	private static String toCleanString(List<String> list) {
		final StringBuilder s = new StringBuilder();
		for (String v : list) {
			if (s.length() > 0)
				s.append('
			s.append(v.replaceAll("\n"
		}
		return s.toString();
	}

	private static String remove(Map<String
		final String r = m.remove(k);
	}

	private static String httpNow() {
		final SimpleDateFormat fmt;
		fmt = new SimpleDateFormat("EEE
		fmt.setTimeZone(TimeZone.getTimeZone(tz));
	}

	private static MessageDigest newMD5() {
		try {
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(JGitText.get().JRELacksMD5Implementation
		}
	}

	private final String publicKey;

	private final SecretKeySpec privateKey;

	private final ProxySelector proxySelector;

	private final String acl;

	final int maxAttempts;

	private final WalkEncryption encryption;

	private final File tmpDir;

	private final String domain;

	interface Keys {
	}

	public AmazonS3(final Properties props) {
		domain = props.getProperty(Keys.DOMAIN

		publicKey = props.getProperty(Keys.ACCESS_KEY);
		if (publicKey == null)
			throw new IllegalArgumentException(JGitText.get().missingAccesskey);

		final String secret = props.getProperty(Keys.SECRET_KEY);
		if (secret == null)
			throw new IllegalArgumentException(JGitText.get().missingSecretkey);
		privateKey = new SecretKeySpec(Constants.encodeASCII(secret)

		final String pacl = props.getProperty(Keys.ACL
		if (StringUtils.equalsIgnoreCase("PRIVATE"
		else if (StringUtils.equalsIgnoreCase("PUBLIC"
		else if (StringUtils.equalsIgnoreCase("PUBLIC-READ"
		else if (StringUtils.equalsIgnoreCase("PUBLIC_READ"
		else

		try {
			encryption = WalkEncryption.instance(props);
		} catch (GeneralSecurityException e) {
			throw new IllegalArgumentException(JGitText.get().invalidEncryption
		}

		maxAttempts = Integer
				.parseInt(props.getProperty(Keys.HTTP_RETRY
		proxySelector = ProxySelector.getDefault();

		String tmp = props.getProperty(Keys.TMP_DIR);
		tmpDir = tmp != null && tmp.length() > 0 ? new File(tmp) : null;
	}

	public URLConnection get(String bucket
			throws IOException {
		for (int curAttempt = 0; curAttempt < maxAttempts; curAttempt++) {
			final HttpURLConnection c = open("GET"
			authorize(c);
			switch (HttpSupport.response(c)) {
			case HttpURLConnection.HTTP_OK:
				encryption.validate(c
				return c;
			case HttpURLConnection.HTTP_NOT_FOUND:
				throw new FileNotFoundException(key);
			case HttpURLConnection.HTTP_INTERNAL_ERROR:
				continue;
			default:
				throw error(JGitText.get().s3ActionReading
			}
		}
		throw maxAttempts(JGitText.get().s3ActionReading
	}

	public InputStream decrypt(URLConnection u) throws IOException {
		return encryption.decrypt(u.getInputStream());
	}

	public List<String> list(String bucket
			throws IOException {
		final ListParser lp = new ListParser(bucket
		do {
			lp.list();
		} while (lp.truncated);
		return lp.entries;
	}

	public void delete(String bucket
			throws IOException {
		for (int curAttempt = 0; curAttempt < maxAttempts; curAttempt++) {
			final HttpURLConnection c = open("DELETE"
			authorize(c);
			switch (HttpSupport.response(c)) {
			case HttpURLConnection.HTTP_NO_CONTENT:
				return;
			case HttpURLConnection.HTTP_INTERNAL_ERROR:
				continue;
			default:
				throw error(JGitText.get().s3ActionDeletion
			}
		}
		throw maxAttempts(JGitText.get().s3ActionDeletion
	}

	public void put(String bucket
			throws IOException {
		if (encryption != WalkEncryption.NONE) {
			try (OutputStream os = beginPut(bucket
				os.write(data);
			}
			return;
		}

		final String md5str = Base64.encodeBytes(newMD5().digest(data));
		final String lenstr = String.valueOf(data.length);
		for (int curAttempt = 0; curAttempt < maxAttempts; curAttempt++) {
			final HttpURLConnection c = open("PUT"
			c.setRequestProperty("Content-Length"
			c.setRequestProperty("Content-MD5"
			c.setRequestProperty(X_AMZ_ACL
			authorize(c);
			c.setDoOutput(true);
			c.setFixedLengthStreamingMode(data.length);
			try (OutputStream os = c.getOutputStream()) {
				os.write(data);
			}

			switch (HttpSupport.response(c)) {
			case HttpURLConnection.HTTP_OK:
				return;
			case HttpURLConnection.HTTP_INTERNAL_ERROR:
				continue;
			default:
				throw error(JGitText.get().s3ActionWriting
			}
		}
		throw maxAttempts(JGitText.get().s3ActionWriting
	}

	public OutputStream beginPut(final String bucket
			final ProgressMonitor monitor
			throws IOException {
		final MessageDigest md5 = newMD5();
		final TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(tmpDir) {
			@Override
			public void close() throws IOException {
				super.close();
				try {
					putImpl(bucket
							monitorTask);
				} finally {
					destroy();
				}
			}
		};
		return encryption.encrypt(new DigestOutputStream(buffer
	}

	void putImpl(final String bucket
			final byte[] csum
			ProgressMonitor monitor
		if (monitor == null)
			monitor = NullProgressMonitor.INSTANCE;
		if (monitorTask == null)
			monitorTask = MessageFormat.format(JGitText.get().progressMonUploading

		final String md5str = Base64.encodeBytes(csum);
		final long len = buf.length();
		for (int curAttempt = 0; curAttempt < maxAttempts; curAttempt++) {
			final HttpURLConnection c = open("PUT"
			c.setFixedLengthStreamingMode(len);
			c.setRequestProperty("Content-MD5"
			c.setRequestProperty(X_AMZ_ACL
			encryption.request(c
			authorize(c);
			c.setDoOutput(true);
			monitor.beginTask(monitorTask
			try (OutputStream os = c.getOutputStream()) {
				buf.writeTo(os
			} finally {
				monitor.endTask();
			}

			switch (HttpSupport.response(c)) {
			case HttpURLConnection.HTTP_OK:
				return;
			case HttpURLConnection.HTTP_INTERNAL_ERROR:
				continue;
			default:
				throw error(JGitText.get().s3ActionWriting
			}
		}
		throw maxAttempts(JGitText.get().s3ActionWriting
	}

	IOException error(final String action
			final HttpURLConnection c) throws IOException {
		final IOException err = new IOException(MessageFormat.format(
				JGitText.get().amazonS3ActionFailed
				Integer.valueOf(HttpSupport.response(c))
				c.getResponseMessage()));
		if (c.getErrorStream() == null) {
			return err;
		}

		try (InputStream errorStream = c.getErrorStream()) {
			final ByteArrayOutputStream b = new ByteArrayOutputStream();
			byte[] buf = new byte[2048];
			for (;;) {
				final int n = errorStream.read(buf);
				if (n < 0) {
					break;
				}
				if (n > 0) {
					b.write(buf
				}
			}
			buf = b.toByteArray();
			if (buf.length > 0) {
				err.initCause(new IOException("\n" + new String(buf
			}
		}
		return err;
	}

	IOException maxAttempts(String action
		return new IOException(MessageFormat.format(
				JGitText.get().amazonS3ActionFailedGivingUp
				Integer.valueOf(maxAttempts)));
	}

	private HttpURLConnection open(final String method
			final String key) throws IOException {
		final Map<String
		return open(method
	}

	HttpURLConnection open(final String method
			final String key
			throws IOException {
		final StringBuilder urlstr = new StringBuilder();
		urlstr.append(bucket);
		urlstr.append('.');
		urlstr.append(domain);
		urlstr.append('/');
		if (key.length() > 0)
			HttpSupport.encode(urlstr
		if (!args.isEmpty()) {
			final Iterator<Map.Entry<String

			urlstr.append('?');
			i = args.entrySet().iterator();
			while (i.hasNext()) {
				final Map.Entry<String
				urlstr.append(e.getKey());
				urlstr.append('=');
				HttpSupport.encode(urlstr
				if (i.hasNext())
					urlstr.append('&');
			}
		}

		final URL url = new URL(urlstr.toString());
		final Proxy proxy = HttpSupport.proxyFor(proxySelector
		final HttpURLConnection c;

		c = (HttpURLConnection) url.openConnection(proxy);
		c.setRequestMethod(method);
		c.setRequestProperty("User-Agent"
		c.setRequestProperty("Date"
		return c;
	}

	void authorize(HttpURLConnection c) throws IOException {
		final Map<String
		final SortedMap<String
		for (Map.Entry<String
			final String hdr = entry.getKey();
			if (isSignedHeader(hdr))
				sigHdr.put(StringUtils.toLowerCase(hdr)
		}

		final StringBuilder s = new StringBuilder();
		s.append(c.getRequestMethod());
		s.append('\n');

		s.append(remove(sigHdr
		s.append('\n');

		s.append(remove(sigHdr
		s.append('\n');

		s.append(remove(sigHdr
		s.append('\n');

		for (Map.Entry<String
			s.append(e.getKey());
			s.append(':');
			s.append(e.getValue());
			s.append('\n');
		}

		final String host = c.getURL().getHost();
		s.append('/');
		s.append(host.substring(0
		s.append(c.getURL().getPath());

		final String sec;
		try {
			final Mac m = Mac.getInstance(HMAC);
			m.init(privateKey);
			sec = Base64.encodeBytes(m.doFinal(s.toString().getBytes(UTF_8)));
		} catch (NoSuchAlgorithmException e) {
			throw new IOException(MessageFormat.format(JGitText.get().noHMACsupport
		} catch (InvalidKeyException e) {
			throw new IOException(MessageFormat.format(JGitText.get().invalidKey
		}
		c.setRequestProperty("Authorization"
	}

	static Properties properties(File authFile)
			throws FileNotFoundException
		final Properties p = new Properties();
		try (FileInputStream in = new FileInputStream(authFile)) {
			p.load(in);
		}
		return p;
	}

	private final class ListParser extends DefaultHandler {
		final List<String> entries = new ArrayList<>();

		private final String bucket;

		private final String prefix;

		boolean truncated;

		private StringBuilder data;

		ListParser(String bn
			bucket = bn;
			prefix = p;
		}

		void list() throws IOException {
			final Map<String
			if (prefix.length() > 0)
				args.put("prefix"
			if (!entries.isEmpty())
				args.put("marker"

			for (int curAttempt = 0; curAttempt < maxAttempts; curAttempt++) {
				final HttpURLConnection c = open("GET"
				authorize(c);
				switch (HttpSupport.response(c)) {
				case HttpURLConnection.HTTP_OK:
					truncated = false;
					data = null;

					final XMLReader xr;
					try {
						xr = XMLReaderFactory.createXMLReader();
					} catch (SAXException e) {
						throw new IOException(JGitText.get().noXMLParserAvailable);
					}
					xr.setContentHandler(this);
					try (InputStream in = c.getInputStream()) {
						xr.parse(new InputSource(in));
					} catch (SAXException parsingError) {
						throw new IOException(
								MessageFormat.format(
										JGitText.get().errorListing
								parsingError);
					}
					return;

				case HttpURLConnection.HTTP_INTERNAL_ERROR:
					continue;

				default:
					throw AmazonS3.this.error("Listing"
				}
			}
			throw maxAttempts("Listing"
		}

		@Override
		public void startElement(final String uri
				final String qName
				throws SAXException {
				data = new StringBuilder();
		}

		@Override
		public void ignorableWhitespace(final char[] ch
				final int n) throws SAXException {
			if (data != null)
				data.append(ch
		}

		@Override
		public void characters(char[] ch
				throws SAXException {
			if (data != null)
				data.append(ch
		}

		@Override
		public void endElement(final String uri
				final String qName) throws SAXException {
				entries.add(data.toString().substring(prefix.length()));
				truncated = StringUtils.equalsIgnoreCase("true"
			data = null;
		}
	}
}
