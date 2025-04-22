package org.eclipse.urischeme.internal.registration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class DesktopFileWriter {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$
	private static final String EQUAL_SIGN = "="; //$NON-NLS-1$
	private static final String KEY_MIME_TYPE = "MimeType"; //$NON-NLS-1$
	private static final String KEY_EXEC = "Exec"; //$NON-NLS-1$
	private static final String EXEC_URI_PLACEHOLDER = "%u"; //$NON-NLS-1$
	private Map<String, String> properties;

	public DesktopFileWriter(InputStream inputStream) {
		properties = getProperties(inputStream);
	}

	public void addScheme(String scheme, @SuppressWarnings({ "unused", "javadoc" }) String description) {
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

	public OutputStream getResult() {

		addUriPlaceholderToExecProperty();

		OutputStream outputStream = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);

		for (Entry<String, String> entry : this.properties.entrySet()) {
			writer.write(entry.getKey());
			if (entry.getValue() != null) {
				writer.write(EQUAL_SIGN);
				writer.write(entry.getValue());
			}
			writer.write(LINE_SEPARATOR);
		}
		writer.close();
		return outputStream;
	}

	private void addUriPlaceholderToExecProperty() {
		if (this.properties.containsKey(KEY_EXEC)) {
			String execValue = this.properties.get(KEY_EXEC);
			if (!execValue.contains(EXEC_URI_PLACEHOLDER)) {
				this.properties.put(KEY_EXEC, execValue + " " + EXEC_URI_PLACEHOLDER); //$NON-NLS-1$
			}
		}

	}

	private Map<String, String> getProperties(InputStream inputStream) {
		assertInputStreamNotEmpty(inputStream);

		LinkedHashMap<String, String> props = new LinkedHashMap<>(); // keeps order
		Scanner scanner = new Scanner(inputStream);
		try {
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				if (nextLine.contains(EQUAL_SIGN)) {
					String[] split = nextLine.split(EQUAL_SIGN);
					props.put(split[0], split[1]);
				} else {
					props.put(nextLine, null);
				}
			}
		} finally {
			scanner.close();
			try {
				inputStream.close();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}

		assertDesktopEntryPresent(props);

		return props;
	}

	private void assertDesktopEntryPresent(LinkedHashMap<String, String> props) {
		Iterator<Entry<String, String>> iterator = props.entrySet().iterator();
		if (iterator.hasNext() == false) {
			throw new IllegalStateException("File seems not to be a 'desktop' file"); //$NON-NLS-1$
		}
		String firstLine = iterator.next().getKey();
		if ("[Desktop Entry]".equals(firstLine) == false) { //$NON-NLS-1$
			throw new IllegalStateException("File seems not to be a 'desktop' file"); //$NON-NLS-1$
		}
	}

	private void assertInputStreamNotEmpty(InputStream inputStream) {
		try {
			if (inputStream.available() == 0) {
				throw new IllegalStateException("inputStream is empty"); //$NON-NLS-1$
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private String getHandlerPlusScheme(String scheme) {
		return "x-scheme-handler/" + scheme + ";";//$NON-NLS-1$ //$NON-NLS-2$
	}
}
