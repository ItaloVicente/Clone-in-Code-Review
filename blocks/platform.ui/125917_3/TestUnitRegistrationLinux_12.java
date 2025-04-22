package org.eclipse.urischeme.internal.registration;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

		System.setProperty("eclipse.home.location", "file:/home/myuser/Eclipse/");
	}

	@Test
	public void handlesAddOnly() throws IOException {
		fileProvider.lines = getFileLines("");

		registration.handleSchemes(Arrays.asList(ADT_SCHEME), Collections.emptyList());

		assertFilePathIs("~/.local/share/applications/_home_myuser_Eclipse_.desktop");

		assertMimeTypeInFileIs("x-scheme-handler/adt;");

		assertXdgMimeCalledFor("x-scheme-handler/adt");
	}

	@Test
	public void handlesAddAndRemoveAtOnce() throws IOException {
		fileProvider.lines = getFileLines("MimeType=x-scheme-handler/adt;");

		registration.handleSchemes(Arrays.asList(OTHER_SCHEME), Arrays.asList(ADT_SCHEME));

		assertFilePathIs("~/.local/share/applications/_home_myuser_Eclipse_.desktop");

		assertMimeTypeInFileIs("x-scheme-handler/other;");

		assertXdgMimeCalledFor("x-scheme-handler/other");
	}

	@Test
	public void handlesRemoveOnly() throws IOException {
		fileProvider.lines = getFileLines("MimeType=x-scheme-handler/adt;");

		registration.handleSchemes(Collections.emptyList(), Arrays.asList(ADT_SCHEME));

		assertFilePathIs("~/.local/share/applications/_home_myuser_Eclipse_.desktop");

		assertNoMimeTypeInFile();

		assertTrue(processStub.records.isEmpty());
	}

	@Test
	public void createsInitialDesktopFile() throws IOException {
		fileProvider.readException = "desktop file not existing";

		registration.handleSchemes(Arrays.asList(ADT_SCHEME), Collections.emptyList());

		assertEquals("~/.local/share/applications/_home_myuser_Eclipse_.desktop", fileProvider.writePath);

		assertMimeTypeInFileIs("x-scheme-handler/adt;");

		assertXdgMimeCalledFor("x-scheme-handler/adt");
	}

	@Test
	public void callsXdgMimeOnceForAllSchemes() throws IOException {
		fileProvider.lines = getFileLines("");

		registration.handleSchemes(Arrays.asList(ADT_SCHEME, OTHER_SCHEME), Collections.emptyList());

		assertFilePathIs("~/.local/share/applications/_home_myuser_Eclipse_.desktop");

		assertMimeTypeInFileIs("x-scheme-handler/adt;x-scheme-handler/other;");

		assertXdgMimeCalledFor("x-scheme-handler/adt", "x-scheme-handler/other");
	}

	@Test
	public void returnsRegisteredSchemes() throws IOException {
		fileProvider.lines = getFileLines("MimeType=x-scheme-handler/adt;");

		List<Scheme> registeredSchemes = registration.getRegisteredSchemes(Arrays.asList(ADT_SCHEME));

		assertEquals(1, registeredSchemes.size());
		assertEquals("adt", registeredSchemes.get(0).getScheme());

		assertEquals("~/.local/share/applications/_home_myuser_Eclipse_.desktop", fileProvider.readPath);
		assertEquals("not/written", fileProvider.writePath);
	}

	private List<String> getFileLines(String mimeType) {
		return Arrays.asList("[Desktop Entry]", //
				"Exec=/home/myuser/Eclipse/eclipse", //
				"NoDisplay=true", //
				mimeType, //
				"Type=Application");
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

	private void assertXdgMimeCalledFor(String... schemeHandler) {
		assertEquals(1, processStub.records.size());
		assertEquals("xdg-mime", processStub.records.get(0).process);
		String schemeHandlers = Arrays.stream(schemeHandler).collect(Collectors.joining(" "));
		assertArrayEquals(
				new String[] { "default", "_home_myuser_Eclipse_.desktop", schemeHandlers },
				processStub.records.get(0).args);
	}
}
