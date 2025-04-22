package org.eclipse.jgit.internal.transport.sshd;

import static java.text.MessageFormat.format;
import static org.apache.sshd.client.config.hosts.HostPatternsHolder.NON_STANDARD_PORT_PATTERN_ENCLOSURE_END_DELIM;
import static org.apache.sshd.client.config.hosts.HostPatternsHolder.NON_STANDARD_PORT_PATTERN_ENCLOSURE_START_DELIM;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.sshd.client.config.hosts.HostPatternValue;
import org.apache.sshd.client.config.hosts.HostPatternsHolder;
import org.apache.sshd.client.config.hosts.KnownHostEntry;
import org.apache.sshd.client.config.hosts.KnownHostHashValue;
import org.apache.sshd.common.config.keys.AuthorizedKeyEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnownHostEntryReader {

	private static final Logger LOG = LoggerFactory
			.getLogger(KnownHostEntryReader.class);

	private KnownHostEntryReader() {
	}

	public static List<KnownHostEntry> readFromFile(Path path)
			throws IOException {
		List<KnownHostEntry> result = new LinkedList<>();
		try (BufferedReader r = Files.newBufferedReader(path
				StandardCharsets.UTF_8)) {
			r.lines().forEachOrdered(l -> {
				if (l == null) {
					return;
				}
				String line = clean(l);
				if (line.isEmpty()) {
					return;
				}
				try {
					KnownHostEntry entry = parseHostEntry(line);
					if (entry != null) {
						result.add(entry);
					} else {
						LOG.warn(format(SshdText.get().knownHostsInvalidLine
								path
					}
				} catch (RuntimeException e) {
					LOG.warn(format(SshdText.get().knownHostsInvalidLine
							line)
				}
			});
		}
		return result;
	}

	private static String clean(String line) {
		int i = line.indexOf('#');
		return i < 0 ? line.trim() : line.substring(0
	}

	private static KnownHostEntry parseHostEntry(String line) {
		KnownHostEntry entry = new KnownHostEntry();
		entry.setConfigLine(line);
		String tmp = line;
		int i = 0;
		if (tmp.charAt(0) == KnownHostEntry.MARKER_INDICATOR) {
			i = tmp.indexOf(' '
			if (i < 0) {
				return null;
			}
			entry.setMarker(tmp.substring(1
			tmp = tmp.substring(i + 1).trim();
		}
		i = tmp.indexOf(' ');
		if (i < 0) {
			return null;
		}
		if (tmp.charAt(0) == KnownHostHashValue.HASHED_HOST_DELIMITER) {
			KnownHostHashValue hash = KnownHostHashValue
					.parse(tmp.substring(0
			if (hash == null) {
				return null;
			}
			entry.setHashedEntry(hash);
			entry.setPatterns(null);
		} else {
			Collection<HostPatternValue> patterns = parsePatterns(
					tmp.substring(0
			if (patterns == null || patterns.isEmpty()) {
				return null;
			}
			entry.setHashedEntry(null);
			entry.setPatterns(patterns);
		}
		tmp = tmp.substring(i + 1).trim();
		AuthorizedKeyEntry key = AuthorizedKeyEntry
				.parseAuthorizedKeyEntry(tmp);
		if (key == null) {
			return null;
		}
		entry.setKeyEntry(key);
		return entry;
	}

	private static Collection<HostPatternValue> parsePatterns(String text) {
		if (text.isEmpty()) {
			return null;
		}
		List<String> items = Arrays.stream(text.split("
					return NON_STANDARD_PORT_PATTERN_ENCLOSURE_START_DELIM
							+ item.substring(0
							+ NON_STANDARD_PORT_PATTERN_ENCLOSURE_END_DELIM
							+ item.substring(firstColon);
				}).collect(Collectors.toList());
		return items.isEmpty() ? null : HostPatternsHolder.parsePatterns(items);
	}
}
