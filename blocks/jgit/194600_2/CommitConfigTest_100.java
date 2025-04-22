package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;

import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

public class AbbrevConfigTest extends RepositoryTestCase {

	@Test
	public void testDefault() throws Exception {
		assertEquals(7
	}

	@Test
	public void testAuto() throws Exception {
		assertEquals(7
	}

	@Test
	public void testNo() throws Exception {
		assertEquals(40
	}

	@Test
	public void testValidMin() throws Exception {
		assertEquals(4
	}

	@Test
	public void testValid() throws Exception {
		assertEquals(22
	}

	@Test
	public void testValidMax() throws Exception {
		assertEquals(40
	}

	@Test
	public void testInvalid() {
		assertThrows(InvalidConfigurationException.class
				() -> testCoreAbbrev("foo"));
	}

	@Test
	public void testInvalid2() {
		assertThrows(InvalidConfigurationException.class
				() -> testCoreAbbrev("2k"));
	}

	@Test
	public void testInvalidNegative() {
		assertThrows(InvalidConfigurationException.class
				() -> testCoreAbbrev("-1000"));
	}

	@Test
	public void testInvalidBelowRange() {
		assertThrows(InvalidConfigurationException.class
				() -> testCoreAbbrev("3"));
	}

	@Test
	public void testInvalidBelowRange2() {
		assertThrows(InvalidConfigurationException.class
				() -> testCoreAbbrev("-1"));
	}

	@Test
	public void testInvalidAboveRange() {
		assertThrows(InvalidConfigurationException.class
				() -> testCoreAbbrev("41"));
	}

	@Test
	public void testInvalidAboveRange2() {
		assertThrows(InvalidConfigurationException.class
				() -> testCoreAbbrev("100000"));
	}

	@Test
	public void testToStringNo()
			throws InvalidConfigurationException
		assertEquals("40"
	}

	@Test
	public void testToString()
			throws InvalidConfigurationException
		assertEquals("7"
	}

	@Test
	public void testToString12()
			throws InvalidConfigurationException
		assertEquals("12"
	}

	private int testCoreAbbrev(String value)
			throws InvalidConfigurationException
		return setCoreAbbrev(value).get();
	}

	private AbbrevConfig setCoreAbbrev(String value)
			throws IOException
		FileBasedConfig config = db.getConfig();
		config.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_ABBREV
		config.save();
		return AbbrevConfig.parseFromConfig(db);
	}

}
