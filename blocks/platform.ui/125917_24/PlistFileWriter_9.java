package org.eclipse.urischeme.internal.registration;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LsregisterParser {

	private static final String ANY_LINES = "(?:.*\\n)*"; //$NON-NLS-1$
	private static final String ENTRY_SEPERATOR = "-{80}\n"; //$NON-NLS-1$
	private String lsregisterDump;

	public LsregisterParser(String lsregisterDump) {
		this.lsregisterDump = lsregisterDump;
	}

	public String getAppFor(String scheme) {
		String[] split = lsregisterDump.split(ENTRY_SEPERATOR);

		String pathRegex = "^" + ANY_LINES + "\\spath:\\s*(.*)\\n" + ANY_LINES + "\\s*bindings:.*" + scheme + ":"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Pattern pattern = Pattern.compile(pathRegex, Pattern.MULTILINE);

		Optional<String> pathOptional = Stream.of(split).parallel().//
				filter(s -> s.startsWith("BundleClass")). //$NON-NLS-1$
				filter(s -> s.contains(scheme + ":")). //$NON-NLS-1$
				map(s -> pattern.matcher(s)).//
				filter(m -> m.find()).//
				map(m -> m.group(1)).findFirst();

		return pathOptional.orElse(""); //$NON-NLS-1$

	}
}
