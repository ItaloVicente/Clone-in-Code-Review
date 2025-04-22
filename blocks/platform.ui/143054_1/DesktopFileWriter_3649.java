package org.eclipse.urischeme.internal.registration;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

public class DesktopFileWriter {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$
	private static final String EQUAL_SIGN = "="; //$NON-NLS-1$
	private static final String KEY_MIME_TYPE = "MimeType"; //$NON-NLS-1$
	private static final String KEY_EXEC = "Exec"; //$NON-NLS-1$
	private static final String EXEC_URI_PLACEHOLDER = " %u"; //$NON-NLS-1$
	private Map<String, String> properties;

	public DesktopFileWriter(List<String> lines) {
		properties = getProperties(lines);
	}

	public boolean isRegistered(String scheme) {
		Util.assertUriSchemeIsLegal(scheme);
		String mimeType = properties.get(KEY_MIME_TYPE);
		if (mimeType == null || mimeType.isEmpty()) {
			return false;
		}
		return mimeType.contains(getHandlerPlusScheme(scheme));
	}

	public void addScheme(String scheme) {
		Util.assertUriSchemeIsLegal(scheme);

		String handlerPlusScheme = getHandlerPlusScheme(scheme);

		if (properties.containsKey(KEY_MIME_TYPE)) {
			String mimeType = properties.get(KEY_MIME_TYPE);
			if (!mimeType.contains(handlerPlusScheme)) {
				mimeType += handlerPlusScheme;
				properties.put(KEY_MIME_TYPE, mimeType);
			}
		} else {
			properties.put(KEY_MIME_TYPE, handlerPlusScheme);
		}
	}

	public void removeScheme(String scheme) {
		Util.assertUriSchemeIsLegal(scheme);

		if (properties.containsKey(KEY_MIME_TYPE)) {

			String handlerPlusScheme = getHandlerPlusScheme(scheme);

			String mimeType = properties.get(KEY_MIME_TYPE);
			mimeType = mimeType.replace(handlerPlusScheme, ""); //$NON-NLS-1$
			if (mimeType.isEmpty()) {
				properties.remove(KEY_MIME_TYPE);
			} else {
				properties.put(KEY_MIME_TYPE, mimeType);
			}
		}
	}

	public byte[] getResult() {

		addUriPlaceholderToExecProperty();

		Function<Entry<String, String>, String> toList = (Entry<String, String> e) -> {
			if (e.getValue() == null) {
				return e.getKey();
			}
			return String.join(EQUAL_SIGN, e.getKey(), e.getValue());
		};
		String result = this.properties.entrySet().stream() //
				.map(toList) //
				.collect(joining(LINE_SEPARATOR));

		return result.getBytes();
	}

	public static List<String> getMinimalDesktopFileContent(String eclipseExecutableLocation, String productName) {
		String executable = escapeSpaces(eclipseExecutableLocation);
		return Arrays.asList(//
				"[Desktop Entry]", //$NON-NLS-1$
				"Name=" + productName, //$NON-NLS-1$
				"Exec=" + executable, //$NON-NLS-1$
				"NoDisplay=true", //$NON-NLS-1$
				"Type=Application" //$NON-NLS-1$
		);
	}

	private static String escapeSpaces(String path) {
		return path.replace(" ", "\\ "); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static String unescapeSpaces(String path) {
		return path.replace("\\ ", " "); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private void addUriPlaceholderToExecProperty() {
		if (this.properties.containsKey(KEY_EXEC)) {
			String execValue = this.properties.get(KEY_EXEC);
			if (!execValue.contains(EXEC_URI_PLACEHOLDER)) {
				this.properties.put(KEY_EXEC, execValue + EXEC_URI_PLACEHOLDER);
			}
		}

	}

	private Map<String, String> getProperties(List<String> lines) {
		assertLinesNotEmpty(lines);

		LinkedHashMap<String, String> props = new LinkedHashMap<>(); // keeps order
		for (String line : lines) {
			if (line.contains(EQUAL_SIGN)) {
				String[] split = line.split(EQUAL_SIGN);
				props.put(split[0], split[1]);
			} else {
				props.put(line, null);
			}
		}

		assertDesktopEntryPresent(props);

		return props;
	}

	private void assertDesktopEntryPresent(Map<String, String> props) {
		Iterator<Entry<String, String>> iterator = props.entrySet().iterator();
		String firstLine = iterator.next().getKey();
		if ("[Desktop Entry]".equals(firstLine) == false) { //$NON-NLS-1$
			throw new IllegalStateException("File seems not to be a 'desktop' file"); //$NON-NLS-1$
		}
	}

	private void assertLinesNotEmpty(List<?> lines) {
		if (lines.isEmpty()) {
			throw new IllegalStateException("inputStream is empty"); //$NON-NLS-1$
		}
	}

	private String getHandlerPlusScheme(String scheme) {
		return "x-scheme-handler/" + scheme + ";";//$NON-NLS-1$ //$NON-NLS-2$
	}

	public String getExecutableLocation() {
		String executableLocation = properties.get(KEY_EXEC);
		executableLocation = executableLocation.replace(EXEC_URI_PLACEHOLDER, ""); //$NON-NLS-1$ // cut uri placeholder
		return unescapeSpaces(executableLocation);
	}
}
