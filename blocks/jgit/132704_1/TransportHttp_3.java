package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.Writer;
import java.net.HttpCookie;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class NetscapeCookieFile {



	public static synchronized Set<HttpCookie> read(Path cookieFile
			Date creationDate)
			throws IOException
		Set<HttpCookie> cookies = new HashSet<>();
		for (String line : Files.readAllLines(cookieFile
			HttpCookie cookie = parseLine(line
			if (cookie != null) {
				cookies.add(cookie);
			}
		}
		return cookies;
	}

	private static HttpCookie parseLine(String line
				&& !line.startsWith(HTTP_ONLY_PREAMBLE))) {
			return null;
		}
		String[] cookieLineParts = line.split(COLUMN_SEPARATOR
		if (cookieLineParts == null) {
			throw new IllegalArgumentException(
		}
		if (cookieLineParts.length < 7) {
			throw new IllegalArgumentException(
					"Each line must consist of 7 tab separated entries
							+ cookieLineParts.length);
		}
		String name = cookieLineParts[5];
		String value = cookieLineParts[6];
		HttpCookie cookie = new HttpCookie(name

		String domain = cookieLineParts[0];
		if (domain.startsWith(HTTP_ONLY_PREAMBLE)) {
			cookie.setHttpOnly(true);
			domain = domain.substring(HTTP_ONLY_PREAMBLE.length());
		}
		cookie.setDomain(domain);
		cookie.setPath(cookieLineParts[2]);
		cookie.setSecure(Boolean.parseBoolean(cookieLineParts[3]));

		long expires = Long.parseLong(cookieLineParts[4]);
		if (creationDate == null) {
			creationDate = new Date();
		}
		long maxAge = (expires - creationDate.getTime()) / 1000;
		if (maxAge < 0) {
			maxAge = -1;
		}
		cookie.setMaxAge(maxAge);
		return cookie;
	}

	public static synchronized void write(Path path
			Collection<HttpCookie> cookies
			Date creationDate)
			throws IOException {
		try (Writer writer = Files.newBufferedWriter(path
				StandardCharsets.US_ASCII)) {
			for (HttpCookie cookie : cookies) {
				writeCookie(writer
			}
		}
	}

	private static void writeCookie(Writer writer
			Date creationDate)
			throws IOException {
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
		if (cookie.getMaxAge() == -1) {
		} else {
			if (creationDate == null) {
				creationDate = new Date();
			}
			expirationDate = String
					.valueOf(creationDate.getTime()
							+ (cookie.getMaxAge() * 1000));
		}
		writer.write(expirationDate);
		writer.write(COLUMN_SEPARATOR);
		writer.write(cookie.getName());
		writer.write(COLUMN_SEPARATOR);
		writer.write(cookie.getValue());
		writer.write(LINE_SEPARATOR);
	}
}
