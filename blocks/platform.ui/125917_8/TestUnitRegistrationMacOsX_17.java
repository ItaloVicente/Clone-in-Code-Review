package org.eclipse.urischeme.internal.registration;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.urischeme.IOperatingSystemRegistration;
import org.eclipse.urischeme.ISchemeInformation;
import org.eclipse.urischeme.IUriSchemeExtensionReader.Scheme;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.StringContains;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUnitRegistrationMacOsX {

	private static final String PLIST_PATH = "/Users/myuser/Applications/Eclipse.app/Contents/Info.plist";
	private static final Scheme ADT_SCHEME = new Scheme("adt", "");
	private static final ISchemeInformation OTHER_SCHEME_INFO = new SchemeInformation("other", "", null);
	private static final ISchemeInformation ADT_SCHEME_INFO = new SchemeInformation("adt", "", null);

	private IOperatingSystemRegistration registration;
	private FileProviderMock fileProvider;
	private ProcessStub processStub;
	private static String originalEclipseHomeLocation;

	@Before
	public void setup() {
		fileProvider = new FileProviderMock();
		fileProvider.writer = new StringWriter();

		processStub = new ProcessStub();

		registration = new RegistrationMacOsX(fileProvider, processStub);
	}

	@BeforeClass
	public static void classSetup() {
		originalEclipseHomeLocation = System.getProperty("eclipse.home.location", "");

		System.setProperty("eclipse.home.location", "file:/Users/myuser/Applications/Eclipse.app/Contents/Eclipse/");
	}

	@AfterClass
	public static void classTearDown() {
		System.setProperty("eclipse.home.location", originalEclipseHomeLocation);
	}

	@Test
	public void handlesAddOnly() throws IOException {
		fileProvider.readAnswers.put(PLIST_PATH, getPlistFileReader());

		registration.handleSchemes(Arrays.asList(ADT_SCHEME_INFO), Collections.emptyList());

		assertFilePathIs(PLIST_PATH);

		assertSchemeInFile("adt");

		assertLsRegisterCallWithOptionAtIndex("-u", 0);
		assertLsRegisterCallWithOptionAtIndex("-r", 1);
	}

	@Test
	public void handlesAddAndRemoveAtOnce() throws IOException {
		fileProvider.readAnswers.put(PLIST_PATH, getPlistFileReaderWithAdtScheme());

		registration.handleSchemes(Arrays.asList(OTHER_SCHEME_INFO), Arrays.asList(ADT_SCHEME_INFO));

		assertFilePathIs(PLIST_PATH);

		assertSchemeInFile("other");
		assertSchemeNotInFile("adt");

		assertLsRegisterCallWithOptionAtIndex("-u", 0);
		assertLsRegisterCallWithOptionAtIndex("-r", 1);
	}

	@Test
	public void handlesRemoveOnly() throws IOException {
		fileProvider.readAnswers.put(PLIST_PATH, getPlistFileReaderWithAdtScheme());

		registration.handleSchemes(Collections.emptyList(), Arrays.asList(ADT_SCHEME_INFO));

		assertFilePathIs(PLIST_PATH);

		assertSchemeNotInFile("adt");

		assertLsRegisterCallWithOptionAtIndex("-u", 0);
		assertLsRegisterCallWithOptionAtIndex("-r", 1);
	}

	@Test
	public void returnsRegisteredSchemes() throws IOException {
		fileProvider.readAnswers.put(PLIST_PATH, getPlistFileReaderWithAdtScheme());

		List<ISchemeInformation> info = registration.getSchemesInformation(Arrays.asList(ADT_SCHEME));

		assertEquals(1, info.size());
		assertEquals("adt", info.get(0).getScheme());
		assertTrue(info.get(0).isHandled());

		assertEquals(PLIST_PATH, fileProvider.recordedReadPaths.get(0));
		assertEquals("not/written", fileProvider.writePath);
	}

	private void assertFilePathIs(String filePath) {
		assertEquals(filePath, fileProvider.recordedReadPaths.get(0));
		assertEquals(filePath, fileProvider.writePath);
	}

	private void assertSchemeInFile(String scheme) {
		assertThat(fileProvider.writer.toString(), new StringContains("<string>" + scheme + "</string>"));

	}

	private void assertSchemeNotInFile(String scheme) {
		assertThat(fileProvider.writer.toString(), new IsNot<>(new StringContains("<string>" + scheme + "</string>")));

	}

	private void assertLsRegisterCallWithOptionAtIndex(String option, int index) {
		String expectedProcess = "/System/Library/Frameworks/CoreServices.framework/Frameworks/LaunchServices.framework/Support/lsregister";
		String[] expectedArguments = new String[] { option, "/Users/myuser/Applications/Eclipse.app" };

		assertEquals(expectedProcess, processStub.records.get(index).process);
		assertArrayEquals(expectedArguments, processStub.records.get(index).args);
	}

	private Reader getPlistFileReader() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + //
				"<plist version=\"1.0\">\n" + //
				"<dict>\n" + //
				"	<key>CFBundleExecutable</key>\n" + //
				"		<string>eclipse</string>\n" + //
				"</dict>\n" + //
				"</plist>\n";

		return new StringReader(xml);
	}

	private Reader getPlistFileReaderWithAdtScheme() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + //
				"<plist version=\"1.0\">\n" + //
				"<dict>\n" + //
				"	<key>CFBundleExecutable</key>\n" + //
				"		<string>eclipse</string>\n" + //
				"	<key>CFBundleURLTypes</key>\n" + //
				"		<array>\n" + //
				"			<dict>\n" + //
				"				<key>CFBundleURLName</key>\n" + //
				"					<string>AdtScheme</string>\n" + //
				"				<key>CFBundleURLSchemes</key>\n" + //
				"					<array>\n" + //
				"						<string>adt</string>\n" + //
				"					</array>\n" + //
				"			</dict>\n" + //
				"		</array>\n" + //
				"</dict>\n" + //
				"</plist>\n";

		return new StringReader(xml);
	}
}
