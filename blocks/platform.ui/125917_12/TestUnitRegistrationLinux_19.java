package org.eclipse.urischeme.internal.registration;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.urischeme.IOperatingSystemRegistration;
import org.eclipse.urischeme.ISchemeInformation;
import org.eclipse.urischeme.IUriSchemeExtensionReader.Scheme;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.StringContains;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUnitRegistrationLinux {

	private static final String PATH_ADT_ECLIPSE_DESKTOP_FILE = "~/.local/share/applications/_home_myuser_AdtEclipse.desktop";
	private static final String PATH_ECLIPSE_DESKTOP_FILE = "~/.local/share/applications/_home_myuser_Eclipse_.desktop";
	private static final Scheme ADT_SCHEME = new Scheme("adt", "");
	private static final ISchemeInformation OTHER_SCHEME_INFO = new SchemeInformation("other", "", null);
	private static final ISchemeInformation ADT_SCHEME_INFO = new SchemeInformation("adt", "", null);
	private static String originalEclipseHomeLocation;
	private static String originalEclipseLauncher;

	private IOperatingSystemRegistration registration;

	private FileProviderMock fileProvider;
	private ProcessSpy processStub;

	@Before
	public void setup() {
		fileProvider = new FileProviderMock();
		processStub = new ProcessSpy();

		registration = new RegistrationLinux(fileProvider, processStub);

		System.setProperty("eclipse.home.location", "file:/home/myuser/Eclipse/");
		System.setProperty("eclipse.launcher", "Eclipse");
	}

	@BeforeClass
	public static void classSetup() {
		originalEclipseHomeLocation = System.getProperty("eclipse.home.location", "");
		System.setProperty("eclipse.home.location", "file:/home/myuser/Eclipse/");

		originalEclipseLauncher = System.getProperty("eclipse.launcher", "");
		System.setProperty("eclipse.launcher", "Eclipse");
	}

	@AfterClass
	public static void classTearDown() {
		System.setProperty("eclipse.home.location", originalEclipseHomeLocation);
		System.setProperty("eclipse.launcher", originalEclipseLauncher);
	}

	@Test
	public void handlesAddOnly() throws IOException {
		fileProvider.readAnswers.put(PATH_ECLIPSE_DESKTOP_FILE, getFileLines(""));

		registration.handleSchemes(Arrays.asList(ADT_SCHEME_INFO), Collections.emptyList());

		assertFilePathIs(PATH_ECLIPSE_DESKTOP_FILE);

		assertMimeTypeInFileIs("x-scheme-handler/adt;");

		assertXdgMimeCalledFor("x-scheme-handler/adt");
	}

	@Test
	public void handlesAddAndRemoveAtOnce() throws IOException {
		fileProvider.readAnswers.put(PATH_ECLIPSE_DESKTOP_FILE, getFileLines("MimeType=x-scheme-handler/adt;"));

		registration.handleSchemes(Arrays.asList(OTHER_SCHEME_INFO), Arrays.asList(ADT_SCHEME_INFO));

		assertFilePathIs(PATH_ECLIPSE_DESKTOP_FILE);

		assertMimeTypeInFileIs("x-scheme-handler/other;");

		assertXdgMimeCalledFor("x-scheme-handler/other");
	}

	@Test
	public void handlesRemoveOnly() throws IOException {
		fileProvider.readAnswers.put(PATH_ECLIPSE_DESKTOP_FILE, getFileLines("MimeType=x-scheme-handler/adt;"));

		registration.handleSchemes(Collections.emptyList(), Arrays.asList(ADT_SCHEME_INFO));

		assertFilePathIs(PATH_ECLIPSE_DESKTOP_FILE);

		assertNoMimeTypeInFile();

		assertTrue(processStub.records.isEmpty());
	}

	@Test
	public void createsInitialDesktopFile() throws IOException {
		fileProvider.readAnswers.put(PATH_ECLIPSE_DESKTOP_FILE, new IOException("desktop file not existing"));

		registration.handleSchemes(Arrays.asList(ADT_SCHEME_INFO), Collections.emptyList());

		assertEquals(PATH_ECLIPSE_DESKTOP_FILE, fileProvider.writePath);

		assertMimeTypeInFileIs("x-scheme-handler/adt;");

		assertExecInFileIs("/home/myuser/Eclipse/Eclipse %u");

		assertXdgMimeCalledFor("x-scheme-handler/adt");
	}

	@Test
	public void callsXdgMimeOnceForAllSchemes() throws IOException {
		fileProvider.readAnswers.put(PATH_ECLIPSE_DESKTOP_FILE, getFileLines(""));

		registration.handleSchemes(Arrays.asList(ADT_SCHEME_INFO, OTHER_SCHEME_INFO), Collections.emptyList());

		assertFilePathIs(PATH_ECLIPSE_DESKTOP_FILE);

		assertMimeTypeInFileIs("x-scheme-handler/adt;x-scheme-handler/other;");

		assertXdgMimeCalledFor("x-scheme-handler/adt", "x-scheme-handler/other");
	}

	@Test
	public void givesSchemeInfoForHandledScheme() throws IOException {
		fileProvider.readAnswers.put(PATH_ECLIPSE_DESKTOP_FILE, getFileLines("MimeType=x-scheme-handler/adt;"));

		List<ISchemeInformation> registeredSchemes = registration.getSchemesInformation(Arrays.asList(ADT_SCHEME));

		assertEquals(1, registeredSchemes.size());
		assertEquals("adt", registeredSchemes.get(0).getScheme());

		assertEquals(PATH_ECLIPSE_DESKTOP_FILE, fileProvider.recordedReadPaths.get(0));
		assertEquals("not/written", fileProvider.writePath);
	}

	@Test
	public void givesSchemeInfoForSchemeHandledByOtherEclipse() throws IOException {
		fileProvider.readAnswers.put(PATH_ECLIPSE_DESKTOP_FILE, getFileLines("MimeType=x-scheme-handler/other;"));
		fileProvider.readAnswers.put(PATH_ADT_ECLIPSE_DESKTOP_FILE, getFileLines("MimeType=x-scheme-handler/adt;"));

		processStub.result = "_home_myuser_AdtEclipse.desktop"; // this is returned by xdg-mime query default

		List<ISchemeInformation> infos = registration.getSchemesInformation(Arrays.asList(ADT_SCHEME));

		assertEquals(1, infos.size());
		assertEquals("adt", infos.get(0).getScheme());
		assertFalse(infos.get(0).isHandled());
		assertEquals("/home/myuser/Eclipse/eclipse", infos.get(0).getHandlerInstanceLocation());

		assertEquals(PATH_ECLIPSE_DESKTOP_FILE, fileProvider.recordedReadPaths.get(0));
		assertEquals(PATH_ADT_ECLIPSE_DESKTOP_FILE, fileProvider.recordedReadPaths.get(1));
		assertEquals("not/written", fileProvider.writePath);
	}

	@Test
	public void givesSchemeInfoForSchemeNotHandledAnymore() throws IOException {
		fileProvider.readAnswers.put(PATH_ECLIPSE_DESKTOP_FILE, getFileLines("MimeType=x-scheme-handler/other;"));
		fileProvider.readAnswers.put(PATH_ADT_ECLIPSE_DESKTOP_FILE, getFileLines("")); // no schemes

		processStub.result = "_home_myuser_AdtEclipse.desktop"; // this is returned by xdg-mime query default

		List<ISchemeInformation> infos = registration.getSchemesInformation(Arrays.asList(ADT_SCHEME));

		assertEquals(1, infos.size());
		assertEquals("adt", infos.get(0).getScheme());
		assertFalse(infos.get(0).isHandled());
		assertEquals("", infos.get(0).getHandlerInstanceLocation());

		assertEquals(PATH_ECLIPSE_DESKTOP_FILE, fileProvider.recordedReadPaths.get(0));
		assertEquals(PATH_ADT_ECLIPSE_DESKTOP_FILE, fileProvider.recordedReadPaths.get(1));
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
		assertEquals(filePath, fileProvider.recordedReadPaths.get(0));
		assertEquals(filePath, fileProvider.writePath);
	}

	private void assertMimeTypeInFileIs(String mimeType) {
		assertThat(new String(fileProvider.writtenBytes), new StringContains("MimeType=" + mimeType));
	}

	private void assertExecInFileIs(String exec) {
		assertThat(new String(fileProvider.writtenBytes), new StringContains("Exec=" + exec));
	}

	private void assertNoMimeTypeInFile() {
		assertThat(new String(fileProvider.writtenBytes),
				new IsNot<>(new StringContains("MimeType=x-scheme-handler/adt;")));
	}

	private void assertXdgMimeCalledFor(String... schemeHandler) {
		assertEquals(1, processStub.records.size());
		assertEquals("xdg-mime", processStub.records.get(0).process);
		String schemeHandlers = Arrays.stream(schemeHandler).collect(Collectors.joining(" "));
		assertArrayEquals(new String[] { "default", "_home_myuser_Eclipse_.desktop", schemeHandlers },
				processStub.records.get(0).args);
	}
}
