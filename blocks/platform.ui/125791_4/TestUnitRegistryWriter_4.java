package org.eclipse.urischeme.internal.registration;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.urischeme.ISchemeInformation;
import org.eclipse.urischeme.IUriSchemeExtensionReader.Scheme;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestRegistrationWindows {
	private File tempFile;
	public static String launcher;
	public static String homeLocation;

	@Before
	public void setUp() throws Exception {
		File folder = new File(System.getProperty("java.io.tmpdir"), "path_without_blanks");
		folder.mkdirs();
		this.tempFile = File.createTempFile("launcher", ".exe", folder);
	}

	@After
	public void tearDown() throws Exception {
		if (this.tempFile != null && this.tempFile.exists()) {
			this.tempFile.delete();
		}
		this.tempFile = null;
	}

	@Test
	public void testHandleSchemesForOneScheme() {
		RegistryWriter registryWriter = getRegistryWriter();
		RegistrationWindows registrationWindows = new RegistrationWindows(registryWriter);
		SchemeInformation schemeInformation = new SchemeInformation("schemeToTest", "ThisSchemeisJustForTesting",
				launcher);
		registrationWindows.handleSchemes(Arrays.asList(schemeInformation), Arrays.asList(schemeInformation));
		assertEquals(null, registryWriter.getRegisteredHandlerPath("schemeToTest"));
	}

	@Test
	public void testHandleSchemesForMultipleSchemes() {
		RegistryWriter registryWriter = getRegistryWriter();
		RegistrationWindows registrationWindows = new RegistrationWindows(registryWriter);
		SchemeInformation schemeToTestInformation = new SchemeInformation("schemeToTest", "SchemsToTest is for testing",
				launcher);
		SchemeInformation adtSchemeInformation = new SchemeInformation("adtScheme", "AdtScheme is for testing",
				launcher);
		SchemeInformation otherSchemeInformation = new SchemeInformation("otherScheme", "OtherScheme is for testing",
				launcher);
		registrationWindows.handleSchemes(
				Arrays.asList(schemeToTestInformation, adtSchemeInformation, otherSchemeInformation),
				Arrays.asList(adtSchemeInformation, schemeToTestInformation, otherSchemeInformation));
		assertEquals(null, registryWriter.getRegisteredHandlerPath("schemeToTest"));
	}

	@Test
	public void testGetRegisteredSchemes() {
		RegistryWriter registryWriter = getRegistryWriter();
		RegistrationWindows registrationWindows = new RegistrationWindows(registryWriter);

		Scheme schemeToTest = new Scheme("schemeToTest", "SchemsToTest is for testing");
		Scheme adtScheme = new Scheme("adtScheme", "AdtScheme is for testing");
		Scheme otherScheme = new Scheme("otherScheme", "OtherScheme is for testing");

		SchemeInformation schemeToTestInformation = new SchemeInformation("schemeToTest", "SchemsToTest is for testing",
				launcher);
		SchemeInformation adtSchemeInformation = new SchemeInformation("adtScheme", "AdtScheme is for testing",
				launcher);
		registrationWindows.handleSchemes(
				Arrays.asList(schemeToTestInformation, adtSchemeInformation),
				Collections.emptyList());
		List<ISchemeInformation> schemeInformation = new ArrayList<ISchemeInformation>();
		schemeInformation = registrationWindows
				.getSchemesInformation(
						Arrays.asList(schemeToTest, adtScheme, otherScheme));
		assertEquals(3, schemeInformation.size());
		assertEquals("<none>", schemeInformation.get(2).getHandlerInstanceLocation());
		assertEquals(launcher, schemeInformation.get(0).getHandlerInstanceLocation());
		registrationWindows.handleSchemes(Collections.emptyList(),
				Arrays.asList(schemeToTestInformation, adtSchemeInformation));


	}
	public RegistryWriter getRegistryWriter() {
		launcher = this.tempFile.getAbsolutePath();
		String parentPath = "file:\\" + this.tempFile.getParentFile().getAbsolutePath();
		String parentPathLiteralsChanged = parentPath.replaceAll("\\\\", "/");
		homeLocation = parentPathLiteralsChanged + "/";
		return new RegistryWriter(launcher, homeLocation);
	}

}
