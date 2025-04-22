package org.eclipse.urischeme.internal.registration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.urischeme.ISchemeInformation;
import org.eclipse.urischeme.IUriSchemeExtensionReader.Scheme;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUnitRegistrationWindows {

	private static final String PATH_TO_OTHER_APPLICATION_EXE = "/path/to/otherApplication.exe";
	private static final String PATH_TO_ECLIPSE_EXE = "/path/to/Eclipse.exe";
	private static final Scheme OTHER_SCHEME = new Scheme("other", "");
	private static final Scheme ADT_SCHEME = new Scheme("adt", "");
	private static final ISchemeInformation OTHER_SCHEME_INFO = new SchemeInformation("other", "", null);
	private static final ISchemeInformation ADT_SCHEME_INFO = new SchemeInformation("adt", "", null);

	RegistryWriterMock registryWriter;
	private static String originalEclipseLauncher;
	private RegistrationWindows registrationWindows;

	@BeforeClass
	public static void classSetup() {
		originalEclipseLauncher = System.getProperty("eclipse.launcher", "");
	}

	@Before
	public void setUp() throws Exception {
		System.setProperty("eclipse.launcher", PATH_TO_ECLIPSE_EXE);
		registryWriter = new RegistryWriterMock();
		registrationWindows = new RegistrationWindows(registryWriter);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		System.setProperty("eclipse.launcher", originalEclipseLauncher);
	}

	@Test
	public void handlesAdd() throws Exception {
		registrationWindows.handleSchemes(Arrays.asList(OTHER_SCHEME_INFO, ADT_SCHEME_INFO), Collections.emptyList());

		assertEquals("Too many schemes added", 2, registryWriter.addedSchemes.size());
		assertTrue("Scheme not added", registryWriter.addedSchemes.contains(OTHER_SCHEME_INFO.getScheme()));
		assertTrue("Scheme not added", registryWriter.addedSchemes.contains(ADT_SCHEME_INFO.getScheme()));

		assertEquals("Too many schemes removed", 0, registryWriter.removedSchemes.size());
	}

	@Test
	public void handlesAddAndRemoveOfSameScheme() throws Exception {
		registrationWindows.handleSchemes(Arrays.asList(OTHER_SCHEME_INFO), Arrays.asList(OTHER_SCHEME_INFO));

		assertEquals("Too many schemes added", 1, registryWriter.addedSchemes.size());
		assertTrue("Scheme not added", registryWriter.addedSchemes.contains(OTHER_SCHEME_INFO.getScheme()));

		assertEquals("Too many schemes removed", 1, registryWriter.removedSchemes.size());
		assertTrue("Scheme not removed", registryWriter.removedSchemes.contains(OTHER_SCHEME_INFO.getScheme()));
	}

	@Test
	public void handlesAddAndRemoveOfSameSchemes() throws Exception {
		registrationWindows.handleSchemes(
				Arrays.asList(OTHER_SCHEME_INFO, ADT_SCHEME_INFO), Arrays.asList(ADT_SCHEME_INFO, OTHER_SCHEME_INFO));

		assertEquals("Too many schemes added", 2, registryWriter.addedSchemes.size());
		assertTrue("Scheme not added", registryWriter.addedSchemes.contains(OTHER_SCHEME_INFO.getScheme()));
		assertTrue("Scheme not added", registryWriter.addedSchemes.contains(ADT_SCHEME_INFO.getScheme()));

		assertEquals("Too many schemes removed", 2, registryWriter.removedSchemes.size());
		assertTrue("Scheme not removed", registryWriter.removedSchemes.contains(OTHER_SCHEME_INFO.getScheme()));
		assertTrue("Scheme not removed", registryWriter.removedSchemes.contains(ADT_SCHEME_INFO.getScheme()));
	}

	@Test
	public void returnsUnregisteredSchemeInformation() throws Exception {
		List<ISchemeInformation> schemeInformation = registrationWindows
				.getSchemesInformation(Arrays.asList(ADT_SCHEME, OTHER_SCHEME));

		assertEquals(2, schemeInformation.size());
		assertSchemeInformation(schemeInformation.get(0), ADT_SCHEME,"<none>", false);
		assertSchemeInformation(schemeInformation.get(1), OTHER_SCHEME, "<none>", false);
	}

	@Test
	public void returnsRegisteredSchemeInformationForThisEclipse() throws Exception {
		registryWriter.schemeToHandlerPath.put(ADT_SCHEME.getUriScheme(), PATH_TO_ECLIPSE_EXE);

		List<ISchemeInformation> schemeInformation = registrationWindows
				.getSchemesInformation(Arrays.asList(ADT_SCHEME));

		assertEquals(1, schemeInformation.size());
		assertSchemeInformation(schemeInformation.get(0), ADT_SCHEME, PATH_TO_ECLIPSE_EXE, true);
	}

	@Test
	public void returnsRegisteredSchemeInformationForOtherApplication() throws Exception {
		registryWriter.schemeToHandlerPath.put(ADT_SCHEME.getUriScheme(), PATH_TO_OTHER_APPLICATION_EXE);

		List<ISchemeInformation> schemeInformation = registrationWindows
				.getSchemesInformation(Arrays.asList(ADT_SCHEME));

		assertEquals(1, schemeInformation.size());
		assertSchemeInformation(schemeInformation.get(0), ADT_SCHEME, PATH_TO_OTHER_APPLICATION_EXE, false);
	}

	private void assertSchemeInformation(ISchemeInformation schemeInformation, Scheme scheme, String handlerlocation,
			boolean isHandled) {
		assertEquals("Scheme not set correctly", scheme.getUriScheme(), schemeInformation.getScheme());
		assertEquals("Scheme description ot set correctly", scheme.getUriSchemeDescription(),
				schemeInformation.getDescription());
		assertEquals("Handler location not set correctly", handlerlocation,
				schemeInformation.getHandlerInstanceLocation());
		assertEquals("isHandled not set correctly", isHandled, schemeInformation.isHandled());
	}
}
