package org.eclipse.urischeme.internal.registration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestUnitRegistryWriter {
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
	public void addAndRemoveOneScheme() throws WinRegistryException {
		RegistryWriter writer = getRegistryWriter();
		writer.addScheme("schemeToTest", "Scheme for testing");
		assertEquals(launcher, writer.getRegisteredHandlerPath("schemeToTest"));
		writer.removeScheme("schemeToTest");
		assertEquals(null, writer.getRegisteredHandlerPath("schemeToTest"));
	}

	@Test
	public void andAndRemoveTwoSchemes() {
		RegistryWriter writer = getRegistryWriter();
		writer.addScheme("schemeToTest", "URL:Scheme for testing");
		assertEquals(launcher, writer.getRegisteredHandlerPath("schemeToTest"));
		writer.addScheme("other", "URL:Second Scheme for testing");
		assertEquals(launcher, writer.getRegisteredHandlerPath("other"));
		writer.removeScheme("schemeToTest");
		assertEquals(null, writer.getRegisteredHandlerPath("schemeToTest"));
		writer.removeScheme("other");
		assertEquals(null, writer.getRegisteredHandlerPath("other"));
	}

	@Test
	public void setCommandNull() {
		RegistryWriter writer = getRegistryWriter();
		writer.addScheme("schemeToTest", "Scheme for testing");
		assertNull(writer.getRegisteredHandlerPath(null));
	}

	@Test
	public void removeScheme() {
		RegistryWriter writer = getRegistryWriter();
		writer.addScheme("adt", "Scheme for testing");
		writer.removeScheme("adt");
		assertEquals(null, writer.getRegisteredHandlerPath("adt"));
	}

	@Test
	public void removeInvalidScheme() throws WinRegistryException {
		RegistryWriter writer = getRegistryWriter();
		writer.addScheme("InvalidScheme", "Scheme for testing");
		writer.removeScheme("InvalidScheme");
		writer.removeScheme("InvalidScheme");
		String keyScheme = "Software\\Classes\\" + "InvalidScheme";
		assertNull(WinRegistry.getValueForKey(keyScheme, "URL Protocol"));

	}

	@Test
	public void validateUri() {
		String invalidURIWith2Spaces = "abc  ";
		String filledSpaces = "%20";
		String returnedString = RegistryWriter.filePathToURI(invalidURIWith2Spaces).toString();
		int count = returnedString.split(filledSpaces, -1).length - 1;
		assertEquals(2, count);
	}

	@Test
	public void validateUri2() {
		String invalidURI = "file: \\";
		String returnedString = RegistryWriter.filePathToURI(invalidURI).toString();
		Assert.assertTrue(returnedString.contains("%20"));
	}

	public RegistryWriter getRegistryWriter() {
		launcher = this.tempFile.getAbsolutePath();
		String parentPath = "file:\\" + this.tempFile.getParentFile().getAbsolutePath();
		String parentPathLiteralsChanged = parentPath.replaceAll("\\\\", "/");
		homeLocation = parentPathLiteralsChanged + "/";
		return new RegistryWriter(launcher, homeLocation);
	}
}
