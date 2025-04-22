/*******************************************************************************
 * Copyright (c) 2018 SAP SE and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     SAP SE - initial version
 *******************************************************************************/
package org.eclipse.urischeme.internal.registration;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Used to change the MimeType property of a Linux .desktop file. Adds handler
 * entries for uri schemes like "x-scheme-handler/myScheme;". Can also remove
 * schemes.
 */
public class DesktopFileWriter {

	private Map<String, String> properties;

	/**
	 * Creates an instance of the DekstopFileWriter. Throws an
	 * {@link IllegalStateException} if the given lines is not a .desktop file. E.g.
	 * no "[Desktop Entry]" at the beginning
	 *
	 * @param lines The lines of the .desktop file (e.g. read with java.nio.Files
	 *
	 * @throws IllegalStateException if lines cannot be understood as .desktop file
	 */
	public DesktopFileWriter(List<String> lines) {
		properties = getProperties(lines);
	}

	/**
	 * Takes the given schemes and checks whether they are registered. Returns a
	 * list with these schemes that are registered.
	 *
	 * @param schemes The schemes that should be checked for registrations.
	 * @return the registered schemes.
	 */
	public List<String> getRegisteredSchemes(Collection<String> schemes) {
		String mimeType = properties.get(KEY_MIME_TYPE);
		if (mimeType == null || mimeType.isEmpty()) {
			return Collections.emptyList();
		}
		Predicate<String> matchingSchemes = scheme -> {
			Util.assertUriSchemeIsLegal(scheme);
			String handlerPlusScheme = getHandlerPlusScheme(scheme);
			return mimeType.contains(handlerPlusScheme);
		};

		return schemes.stream().filter(matchingSchemes).collect(toList());
	}

	/**
	 * Adds an entry "x-scheme-handler/givenScheme;" to the MimeType property of the
	 * .desktop file. Creates the MimeType property if not yet existing. Otherwise
	 * adds to the entry separated by ";".
	 *
	 * @param scheme The uri scheme which should be handled by the application
	 *               mentioned in the .desktop file.
	 *
	 * @throws IllegalArgumentException if the given scheme contains illegal
	 *                                  characters
	 *
	 * @see #removeScheme(String)
	 *
	 *      Resource Identifier (URI): Generic Syntax</a>
	 *
	 */
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

	/**
	 * Removes the corresponding handler ("x-scheme-handler/givenScheme;") for the
	 * given scheme from the MimeType property of the .desktop file. Removes the
	 * MimeType property completely if it is empty after removal.
	 *
	 * @param scheme The uri scheme which should not be handled anymore by the
	 *               application mentioned in the .desktop file.
	 *
	 * @throws IllegalArgumentException if the given scheme contains illegal
	 *                                  characters
	 *
	 * @see #addScheme(String)
	 *
	 * @see <a href=
	 *
	 */
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

	/**
	 * Returns a byte array with all the properties and the changed MimeType
	 * property.
	 *
	 * @return the new file content as byte[]
	 */
	public byte[] getResult() {

		addUriPlaceholderToExecProperty();

		Function<Entry<String, String>, String> toList = new Function<Map.Entry<String, String>, String>() {

			@Override
			public String apply(Entry<String, String> e) {
				if (e.getValue() == null) {
					return e.getKey();
				}
				return String.join(EQUAL_SIGN, e.getKey(), e.getValue());
			}
		};
				.collect(joining(LINE_SEPARATOR));

		return result.getBytes();
	}
	private void addUriPlaceholderToExecProperty() {
		if (this.properties.containsKey(KEY_EXEC)) {
			String execValue = this.properties.get(KEY_EXEC);
			if (!execValue.contains(EXEC_URI_PLACEHOLDER)) {
				this.properties.put(KEY_EXEC, execValue + " " + EXEC_URI_PLACEHOLDER); //$NON-NLS-1$
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

	private void assertDesktopEntryPresent(LinkedHashMap<String, String> props) {
		Iterator<Entry<String, String>> iterator = props.entrySet().iterator();
		if (iterator.hasNext() == false) {
		}
		String firstLine = iterator.next().getKey();
		}
	}

	private void assertLinesNotEmpty(List<?> lines) {
		if (lines.isEmpty()) {
		}
	}

	private String getHandlerPlusScheme(String scheme) {
	}
}
