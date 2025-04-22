package org.eclipse.jgit.transport.http;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpCookie;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.FileSnapshot;
import org.eclipse.jgit.internal.storage.file.LockFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NetscapeCookieFile {




	private static final Integer LOCK_ACQUIRE_MAX_RETRY_COUNT_DEFAULT = Integer
			.valueOf(4);

	private static final int LOCK_ACQUIRE_MAX_RETRY_COUNT = Integer
			.getInteger("NetscapeCookieFile.LockAcquireMaxRetryCount"
					LOCK_ACQUIRE_MAX_RETRY_COUNT_DEFAULT)
			.intValue();

	private static final Integer LOCK_ACQUIRE_RETRY_SLEEP_DEFAULT = Integer
			.valueOf(500);

	private static final int LOCK_ACQUIRE_RETRY_SLEEP = Integer
			.getInteger("NetscapeCookieFile.LockAcquireRetrySleep"
					LOCK_ACQUIRE_RETRY_SLEEP_DEFAULT)
			.intValue();

	private final Path file;

	private FileSnapshot snapshot;

	final Date creationDate;

	private Set<HttpCookie> cookies = null;

	private static final Logger LOG = LoggerFactory
			.getLogger(NetscapeCookieFile.class);

	public NetscapeCookieFile(Path file) {
		this.file = file;
		this.snapshot = FileSnapshot.save(file.toFile());
		creationDate = new Date();
	}

	public Path getFile() {
		return file;
	}

	public @Nullable Set<HttpCookie> getCookies(boolean refresh) {
		if (cookies == null
				|| (refresh && snapshot.isModified(file.toFile()))) {
			try {
				LockFile lockFile = new LockFile(file.toFile());
				for (int retryCount = 0; retryCount < LOCK_ACQUIRE_MAX_RETRY_COUNT; retryCount++) {
					if (lockFile.lock()) {
						try {
							snapshot = FileSnapshot.save(file.toFile());
							Set<HttpCookie> newCookies = read(file
							if (cookies != null) {
								cookies = mergeCookies(newCookies
							} else {
								cookies = newCookies;
							}
							return cookies;
						} finally {
							lockFile.unlock();
						}
					}
					Thread.sleep(LOCK_ACQUIRE_RETRY_SLEEP);
				}
				throw new IOException(MessageFormat
						.format(JGitText.get().cannotLock
			} catch (IOException | IllegalArgumentException
					| InterruptedException e) {
				LOG.warn(
						MessageFormat.format(
								JGitText.get().couldNotReadCookieFile
						e);
			}
		}
		return cookies;
	}

	static Set<HttpCookie> read(@NonNull Path cookieFile
			@NonNull Date creationDate)
			throws IOException
		Set<HttpCookie> cookies = new LinkedHashSet<>();
		for (String line : Files.readAllLines(cookieFile
				StandardCharsets.US_ASCII)) {
			HttpCookie cookie = parseLine(line
			if (cookie != null) {
				cookies.add(cookie);
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
		LockFile lockFile = new LockFile(file.toFile());
		for (int retryCount = 0; retryCount < LOCK_ACQUIRE_MAX_RETRY_COUNT; retryCount++) {
			if (lockFile.lock()) {
				try {
					if (snapshot.isModified(file.toFile())) {
						LOG.debug(
								"Reading the underlying cookie file '{}' as it has been modified since the last access"
								file);
						Set<HttpCookie> cookiesFromFile = NetscapeCookieFile
								.read(file
						this.cookies = mergeCookies(cookiesFromFile
					}
					try (OutputStream output = new BufferedOutputStream(
							lockFile.getOutputStream());
							Writer writer = new OutputStreamWriter(output
									StandardCharsets.US_ASCII)) {
						write(writer
					}
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
