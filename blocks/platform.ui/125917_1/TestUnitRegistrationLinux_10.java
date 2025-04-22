package org.eclipse.urischeme.internal.registration;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.urischeme.IOperatingSystemRegistration;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Test;

public class TestUnitRegistrationLinux {

	private static final Scheme OTHER_SCHEME = new Scheme("other", "");
	private static final Scheme ADT_SCHEME = new Scheme("adt", "");
	private IOperatingSystemRegistration registration;
	private FileProviderMock fileProvider;
	private ProcessStub processStub;

	@Before
	public void setup() {
		fileProvider = new FileProviderMock();
		processStub = new ProcessStub();

		registration = new RegistrationLinux(fileProvider, processStub);
	}

	@Test
	public void handlesAddOnly() throws IOException {
		fileProvider.lines = getFileLines("");

		registration.handleSchemes(Arrays.asList(ADT_SCHEME), Collections.emptyList());

		assertFilePathIs("~/.local/share/applications/eclipse.desktop");

		assertMimeTypeInFileIs("x-scheme-handler/adt;");

		assertXdgMimeCalledFor("x-scheme-handler/adt");
	}

	@Test
	public void handlesAddAndRemoveAtOnce() throws IOException {
		fileProvider.lines = getFileLines("MimeType=x-scheme-handler/adt;");

		registration.handleSchemes(Arrays.asList(OTHER_SCHEME), Arrays.asList(ADT_SCHEME));

		assertFilePathIs("~/.local/share/applications/eclipse.desktop");

		assertMimeTypeInFileIs("x-scheme-handler/other;");

		assertXdgMimeCalledFor("x-scheme-handler/other");
	}

	@Test
	public void handlesRemoveOnly() throws IOException {
		fileProvider.lines = getFileLines("MimeType=x-scheme-handler/adt;");

		registration.handleSchemes(Collections.emptyList(), Arrays.asList(ADT_SCHEME));

		assertFilePathIs("~/.local/share/applications/eclipse.desktop");

		assertNoMimeTypeInFile();

		assertTrue(processStub.records.isEmpty());
	}

	private List<String> getFileLines(String mimeType) {
		return Arrays.asList("[Desktop Entry]", //
				"Encoding=UTF-8", //
				"Name=Eclipse 4.4.1", //
				"Comment=Eclipse Luna", //
				"Exec=/usr/bin/eclipse", //
				"Icon=/opt/eclipse/icon.xpm", //
				"Categories=Application;Development;Java;IDE", //
				"Version=1.0", //
				mimeType, //
				"Type=Application", //
				"Terminal=0");
	}

	private void assertFilePathIs(String filePath) {
		assertEquals(filePath, fileProvider.readPath);
		assertEquals(filePath, fileProvider.writePath);
	}

	private void assertMimeTypeInFileIs(String mimeType) {
		assertThat(new String(fileProvider.writtenBytes), new StringContains("MimeType=" + mimeType));
	}

	private void assertNoMimeTypeInFile() {
		assertThat(new String(fileProvider.writtenBytes),
				new IsNot<>(new StringContains("MimeType=x-scheme-handler/adt;")));
	}

	private void assertXdgMimeCalledFor(String schemeHandler) {
		assertEquals("xdg-mime", processStub.records.get(0).process);
		assertArrayEquals(
				new String[] { "default", "~/.local/share/applications/eclipse.desktop", schemeHandler },
				processStub.records.get(0).args);
	}
}
