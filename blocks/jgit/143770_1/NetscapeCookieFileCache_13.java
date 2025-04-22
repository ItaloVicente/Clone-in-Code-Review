package org.eclipse.jgit.internal.transport.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.net.HttpCookie;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.FileSnapshot;
import org.eclipse.jgit.internal.storage.file.LockFile;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NetscapeCookieFile {




	private static final int LOCK_ACQUIRE_MAX_RETRY_COUNT = 4;

	private static final int LOCK_ACQUIRE_RETRY_SLEEP = 500;

	private final Path path;

	private FileSnapshot snapshot;

	private byte[] hash;

	final Date creationDate;

	private Set<HttpCookie> cookies = null;

	private static final Logger LOG = LoggerFactory
			.getLogger(NetscapeCookieFile.class);

	public NetscapeCookieFile(Path path) {
		this(path
	}

	NetscapeCookieFile(Path path
		this.path = path;
		this.snapshot = FileSnapshot.DIRTY;
		this.creationDate = creationDate;
	}

	public Path getPath() {
		return path;
	}

	public Set<HttpCookie> getCookies(boolean refresh) {
		if (cookies == null || refresh) {
			try {
				byte[] in = getFileContentIfModified();
				Set<HttpCookie> newCookies = parseCookieFile(in
				if (cookies != null) {
					cookies = mergeCookies(newCookies
				} else {
					cookies = newCookies;
				}
				return cookies;
			} catch (IOException | IllegalArgumentException e) {
				LOG.warn(
						MessageFormat.format(
								JGitText.get().couldNotReadCookieFile
						e);
				if (cookies == null) {
					cookies = new LinkedHashSet<>();
				}
			}
		}
		return cookies;

	}

	private static Set<HttpCookie> parseCookieFile(@NonNull byte[] input
			@NonNull Date creationDate)
			throws IOException

		String decoded = RawParseUtils.decode(StandardCharsets.US_ASCII

		Set<HttpCookie> cookies = new LinkedHashSet<>();
		try (BufferedReader reader = new BufferedReader(
				new StringReader(decoded))) {
			String line;
			while ((line = reader.readLine()) != null) {
				HttpCookie cookie = parseLine(line
				if (cookie != null) {
					cookies.add(cookie);
				}
			}
		}
		return cookies;
	}

	private static HttpCookie parseLine(@NonNull String line
			@NonNull Date creationDate) {
				&& !line.startsWith(HTTP_ONLY_PREAMBLE))) {
			return null;
		}
		String[] cookieLineParts = line.split(COLUMN_SEPARATOR
		if (cookieLineParts == null) {
			throw new IllegalArgumentException(MessageFormat
					.format(JGitText.get().couldNotFindTabInLine
		}
		if (cookieLineParts.length < 7) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().couldNotFindSixTabsInLine
					Integer.valueOf(cookieLineParts.length)
		}
		String name = cookieLineParts[5];
		String value = cookieLineParts[6];
		HttpCookie cookie = new HttpCookie(name

		String domain = cookieLineParts[0];
		if (domain.startsWith(HTTP_ONLY_PREAMBLE)) {
			cookie.setHttpOnly(true);
			domain = domain.substring(HTTP_ONLY_PREAMBLE.length());
		}
			domain = domain.substring(1);
		}
		cookie.setDomain(domain);
		cookie.setPath(cookieLineParts[2]);
		cookie.setSecure(Boolean.parseBoolean(cookieLineParts[3]));

		long expires = Long.parseLong(cookieLineParts[4]);
		long maxAge = (expires - creationDate.getTime()) / 1000;
		if (maxAge <= 0) {
		}
		cookie.setMaxAge(maxAge);
		return cookie;
	}

	public void write(URL url)
			throws IllegalArgumentException
		try {
			byte[] cookieFileContent = getFileContentIfModified();
			if (cookieFileContent != null) {
				LOG.debug(
						"Reading the underlying cookie file '{}' as it has been modified since the last access"
						path);
				Set<HttpCookie> cookiesFromFile = NetscapeCookieFile
						.parseCookieFile(cookieFileContent
				this.cookies = mergeCookies(cookiesFromFile
			}
		} catch (FileNotFoundException e) {
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try (Writer writer = new OutputStreamWriter(output
				StandardCharsets.US_ASCII)) {
			write(writer
		}
		LockFile lockFile = new LockFile(path.toFile());
		for (int retryCount = 0; retryCount < LOCK_ACQUIRE_MAX_RETRY_COUNT; retryCount++) {
			if (lockFile.lock()) {
				try {
					lockFile.setNeedSnapshot(true);
					lockFile.write(output.toByteArray());
					if (!lockFile.commit()) {
						throw new IOException(MessageFormat.format(
								JGitText.get().cannotCommitWriteTo
					}
				} finally {
					lockFile.unlock();
				}
				return;
			}
			Thread.sleep(LOCK_ACQUIRE_RETRY_SLEEP);
		}
		throw new IOException(
				MessageFormat.format(JGitText.get().cannotLock

	}

	private byte[] getFileContentIfModified() throws IOException {
		final int maxStaleRetries = 5;
		int retries = 0;
		File file = getPath().toFile();
		if (!file.exists()) {
			LOG.warn(MessageFormat.format(JGitText.get().missingCookieFile
					file.getAbsolutePath()));
			return new byte[0];
		}
		while (true) {
			final FileSnapshot oldSnapshot = snapshot;
			final FileSnapshot newSnapshot = FileSnapshot.save(file);
			try {
				final byte[] in = IO.readFully(file);
				byte[] newHash = hash(in);
				if (Arrays.equals(hash
					if (oldSnapshot.equals(newSnapshot)) {
						oldSnapshot.setClean(newSnapshot);
					} else {
						snapshot = newSnapshot;
					}
				} else {
					snapshot = newSnapshot;
					hash = newHash;
				}
				return in;
			} catch (FileNotFoundException e) {
				throw e;
			} catch (IOException e) {
				if (FileUtils.isStaleFileHandle(e)
						&& retries < maxStaleRetries) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(MessageFormat.format(
								JGitText.get().configHandleIsStale
								Integer.valueOf(retries))
					}
					retries++;
					continue;
				}
				throw new IOException(MessageFormat
						.format(JGitText.get().cannotReadFile
			}
		}

	}

	private byte[] hash(final byte[] in) {
		return Constants.newMessageDigest().digest(in);
	}

	static void write(@NonNull Writer writer
			@NonNull Collection<HttpCookie> cookies
			@NonNull Date creationDate) throws IOException {
		for (HttpCookie cookie : cookies) {
			writeCookie(writer
		}
	}

	private static void writeCookie(@NonNull Writer writer
			@NonNull HttpCookie cookie
			@NonNull Date creationDate) throws IOException {
		if (cookie.getMaxAge() <= 0) {
		}
		if (cookie.isHttpOnly()) {
			domain = HTTP_ONLY_PREAMBLE;
		}
		if (cookie.getDomain() != null) {
			domain += cookie.getDomain();
		} else {
			domain += url.getHost();
		}
		writer.write(domain);
		writer.write(COLUMN_SEPARATOR);
		writer.write(COLUMN_SEPARATOR);
		String path = cookie.getPath();
		if (path == null) {
			path = url.getPath();
		}
		writer.write(path);
		writer.write(COLUMN_SEPARATOR);
		writer.write(Boolean.toString(cookie.getSecure()).toUpperCase());
		writer.write(COLUMN_SEPARATOR);
		final String expirationDate;
		expirationDate = String
				.valueOf(creationDate.getTime() + (cookie.getMaxAge() * 1000));
		writer.write(expirationDate);
		writer.write(COLUMN_SEPARATOR);
		writer.write(cookie.getName());
		writer.write(COLUMN_SEPARATOR);
		writer.write(cookie.getValue());
		writer.write(LINE_SEPARATOR);
	}

	static Set<HttpCookie> mergeCookies(Set<HttpCookie> cookies1
			@Nullable Set<HttpCookie> cookies2) {
		Set<HttpCookie> mergedCookies = new LinkedHashSet<>(cookies1);
		if (cookies2 != null) {
			mergedCookies.addAll(cookies2);
		}
		return mergedCookies;
	}
}
