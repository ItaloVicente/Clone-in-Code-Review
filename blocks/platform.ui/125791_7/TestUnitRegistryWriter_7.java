package org.eclipse.urischeme.internal.registration;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.urischeme.IRegistryWriter;
import org.eclipse.urischeme.ISchemeInformation;
import org.eclipse.urischeme.IUriSchemeExtensionReader.Scheme;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUnitRegistrationWindows {

	IRegistryWriter registryWriter;
	private static String originalEclipseHomeLocation;
	private static String originalEclipseLauncher;

	@Before
	public void setUp() throws Exception {
		System.setProperty("eclipse.home.location", "file:/C:/Users/Test/");
		System.setProperty("eclipse.launcher", "C:\\Users\\Test\\eclipse.exe");
		registryWriter = new RegistryWriterMock();
	}

	@After
	public void tearDown() throws Exception {
		System.setProperty("eclipse.home.location", originalEclipseHomeLocation);
		System.setProperty("eclipse.launcher", originalEclipseLauncher);
	}

	@BeforeClass
	public static void classSetup() {
		originalEclipseHomeLocation = System.getProperty("eclipse.home.location", "");
		System.setProperty("eclipse.home.location", "file:/C:/Users/Test/");
		originalEclipseLauncher = System.getProperty("eclipse.launcher", "");
		System.setProperty("eclipse.launcher", "C:\\Users\\Test\\eclipse.exe");
	}

	@AfterClass
	public static void classTearDown() {
		System.setProperty("eclipse.home.location", originalEclipseHomeLocation);
		System.setProperty("eclipse.launcher", originalEclipseLauncher);
	}

	@Test
	public void testHandleSchemesForOneScheme() {
		RegistrationWindows registrationWindows = new RegistrationWindows(registryWriter);
		SchemeInformation schemeInformation = new SchemeInformation("schemeToTest", "ThisSchemeisJustForTesting",
				System.getProperty("eclipse.launcher"));
		registrationWindows.handleSchemes(Arrays.asList(schemeInformation), Arrays.asList(schemeInformation));
		assertEquals(null, registryWriter.getRegisteredHandlerPath("schemeToTest"));
	}

	@Test
	public void testHandleSchemesForMultipleSchemes() {
		RegistrationWindows registrationWindows = new RegistrationWindows(registryWriter);
		SchemeInformation schemeToTestInformation = new SchemeInformation("schemeToTest", "SchemsToTest is for testing",
				System.getProperty("eclipse.launcher"));
		SchemeInformation adtSchemeInformation = new SchemeInformation("adtScheme", "AdtScheme is for testing",
				System.getProperty("eclipse.launcher"));
		SchemeInformation otherSchemeInformation = new SchemeInformation("otherScheme", "OtherScheme is for testing",
				System.getProperty("eclipse.launcher"));
		registrationWindows.handleSchemes(
				Arrays.asList(schemeToTestInformation, adtSchemeInformation, otherSchemeInformation),
				Arrays.asList(adtSchemeInformation, schemeToTestInformation, otherSchemeInformation));
		assertEquals(null, registryWriter.getRegisteredHandlerPath("schemeToTest"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidScheme() {
		RegistrationWindows registrationWindows = new RegistrationWindows(registryWriter);
		SchemeInformation schemeToTestInformation = new SchemeInformation("$$$*##", "Invalid Scheme",
				System.getProperty("eclipse.launcher"));
		registrationWindows.handleSchemes(Arrays.asList(schemeToTestInformation), Collections.emptyList());
	}

	@Test
	public void testRegisteredSchemes() {
		RegistrationWindows registrationWindows = new RegistrationWindows(registryWriter);
		Scheme schemeToTest = new Scheme("schemeToTest", "SchemsToTest is for testing");
		Scheme adtScheme = new Scheme("adtScheme", "AdtScheme is for testing");
		List<ISchemeInformation> schemeInformation = new ArrayList<ISchemeInformation>();
		SchemeInformation schemeToTestInformation = new SchemeInformation("schemeToTest", "SchemsToTest is for testing",
				System.getProperty("eclipse.launcher"));
		SchemeInformation adtSchemeInformation = new SchemeInformation("adtScheme", "AdtScheme is for testing",
				System.getProperty("eclipse.launcher"));
		registrationWindows.handleSchemes(Arrays.asList(schemeToTestInformation, adtSchemeInformation),
				Collections.emptyList());
		schemeInformation = registrationWindows.getSchemesInformation(Arrays.asList(schemeToTest, adtScheme));
		assertEquals(2, schemeInformation.size());
		assertEquals("schemeToTest", schemeInformation.get(0).getScheme());
		assertEquals("SchemsToTest is for testing", schemeInformation.get(0).getDescription());
		assertEquals(System.getProperty("eclipse.launcher"), schemeInformation.get(0).getHandlerInstanceLocation());
		assertEquals("adtScheme", schemeInformation.get(1).getScheme());
		assertEquals("AdtScheme is for testing", schemeInformation.get(1).getDescription());
		assertEquals(System.getProperty("eclipse.launcher"), schemeInformation.get(1).getHandlerInstanceLocation());
		registrationWindows.handleSchemes(Collections.emptyList(),
				Arrays.asList(schemeToTestInformation, adtSchemeInformation));

	}

	@Test
	public void testUnregisteredSchemes() {
		RegistrationWindows registrationWindows = new RegistrationWindows(registryWriter);
		Scheme schemeToTest = new Scheme("unRegisteredScheme", "This scheme is not registered");
		Scheme adtScheme = new Scheme("schemeUnregistered", "This scheme is not registered");
		List<ISchemeInformation> schemeInformation = new ArrayList<ISchemeInformation>();
		registrationWindows.handleSchemes(Collections.emptyList(), Collections.emptyList());
		schemeInformation = registrationWindows.getSchemesInformation(Arrays.asList(schemeToTest, adtScheme));
		assertEquals(2, schemeInformation.size());
		assertEquals("<none>", schemeInformation.get(0).getHandlerInstanceLocation());
		assertEquals("<none>", schemeInformation.get(1).getHandlerInstanceLocation());
	}

	@Test
	public void testRegisteredAndUnRegisteredSchemes() {
		RegistrationWindows registrationWindows = new RegistrationWindows(registryWriter);
		Scheme schemeToTest = new Scheme("schemeToTest", "SchemsToTest is for testing");
		Scheme adtScheme = new Scheme("adtScheme", "AdtScheme is for testing");
		Scheme otherScheme = new Scheme("otherScheme", "OtherScheme is not being added to registry");
		List<ISchemeInformation> schemeInformation = new ArrayList<ISchemeInformation>();
		SchemeInformation schemeToTestInformation = new SchemeInformation("schemeToTest", "SchemsToTest is for testing",
				System.getProperty("eclipse.launcher"));
		SchemeInformation adtSchemeInformation = new SchemeInformation("adtScheme", "AdtScheme is for testing",
				System.getProperty("eclipse.launcher"));
		registrationWindows.handleSchemes(Arrays.asList(schemeToTestInformation, adtSchemeInformation),
				Collections.emptyList());
		schemeInformation = registrationWindows
				.getSchemesInformation(Arrays.asList(schemeToTest, adtScheme, otherScheme));
		assertEquals(3, schemeInformation.size());
		assertEquals("schemeToTest", schemeInformation.get(0).getScheme());
		assertEquals("SchemsToTest is for testing", schemeInformation.get(0).getDescription());
		assertEquals(System.getProperty("eclipse.launcher"), schemeInformation.get(0).getHandlerInstanceLocation());
		assertEquals("adtScheme", schemeInformation.get(1).getScheme());
		assertEquals("AdtScheme is for testing", schemeInformation.get(1).getDescription());
		assertEquals(System.getProperty("eclipse.launcher"), schemeInformation.get(1).getHandlerInstanceLocation());
		assertEquals("otherScheme", schemeInformation.get(2).getScheme());
		assertEquals("OtherScheme is not being added to registry", schemeInformation.get(2).getDescription());
		assertEquals("<none>", schemeInformation.get(2).getHandlerInstanceLocation());
		registrationWindows.handleSchemes(Collections.emptyList(),
				Arrays.asList(schemeToTestInformation, adtSchemeInformation));

	}
}
