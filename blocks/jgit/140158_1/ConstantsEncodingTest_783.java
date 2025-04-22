
package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.eclipse.jgit.util.FileUtils.pathToString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.eclipse.jgit.api.MergeCommand.FastForwardMode;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.junit.MockSystemReader;
import org.eclipse.jgit.merge.MergeConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

@SuppressWarnings("boxing")
public class ConfigTest {
	private static final char WS = '\u2002';




	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Rule
	public TemporaryFolder tmp = new TemporaryFolder();

	@After
	public void tearDown() {
		SystemReader.setInstance(null);
	}

	@Test
	public void test001_ReadBareKey() throws ConfigInvalidException {
		final Config c = parse("[foo]\nbar\n");
		assertTrue(c.getBoolean("foo"
		assertEquals(""
	}

	@Test
	public void test002_ReadWithSubsection() throws ConfigInvalidException {
		final Config c = parse("[foo \"zip\"]\nbar\n[foo \"zap\"]\nbar=false\nn=3\n");
		assertTrue(c.getBoolean("foo"
		assertEquals(""
		assertFalse(c.getBoolean("foo"
		assertEquals("false"
		assertEquals(3
		assertEquals(4
	}

	@Test
	public void test003_PutRemote() {
		final Config c = new Config();
		c.setString("sec"
		c.setString("sec"
		final String expText = "[sec \"ext\"]\n\tname = value\n\tname2 = value2\n";
		assertEquals(expText
	}

	@Test
	public void test004_PutGetSimple() {
		Config c = new Config();
		c.setString("my"
		assertEquals("false"
		assertEquals("[my]\n\tsomename = false\n"
	}

	@Test
	public void test005_PutGetStringList() {
		Config c = new Config();
		final LinkedList<String> values = new LinkedList<>();
		values.add("value1");
		values.add("value2");
		c.setStringList("my"

		final Object[] expArr = values.toArray();
		final String[] actArr = c.getStringList("my"
		assertArrayEquals(expArr

		final String expText = "[my]\n\tsomename = value1\n\tsomename = value2\n";
		assertEquals(expText
	}

	@Test
	public void test006_readCaseInsensitive() throws ConfigInvalidException {
		final Config c = parse("[Foo]\nBar\n");
		assertTrue(c.getBoolean("foo"
		assertEquals(""
	}

	@Test
	public void test007_readUserConfig() {
		final MockSystemReader mockSystemReader = new MockSystemReader();
		SystemReader.setInstance(mockSystemReader);
		final String hostname = mockSystemReader.getHostname();
		final Config userGitConfig = mockSystemReader.openUserConfig(null
				FS.DETECTED);
		final Config localConfig = new Config(userGitConfig);
		mockSystemReader.clearProperties();

		String authorName;
		String authorEmail;

		authorName = localConfig.get(UserConfig.KEY).getAuthorName();
		authorEmail = localConfig.get(UserConfig.KEY).getAuthorEmail();
		assertEquals(Constants.UNKNOWN_USER_DEFAULT
		assertEquals(Constants.UNKNOWN_USER_DEFAULT + "@" + hostname
		assertTrue(localConfig.get(UserConfig.KEY).isAuthorNameImplicit());
		assertTrue(localConfig.get(UserConfig.KEY).isAuthorEmailImplicit());

		mockSystemReader.setProperty(Constants.OS_USER_NAME_KEY
		localConfig.uncache(UserConfig.KEY);
		authorName = localConfig.get(UserConfig.KEY).getAuthorName();
		assertEquals("os user name"
		assertTrue(localConfig.get(UserConfig.KEY).isAuthorNameImplicit());

		if (hostname != null && hostname.length() != 0) {
			authorEmail = localConfig.get(UserConfig.KEY).getAuthorEmail();
			assertEquals("os user name@" + hostname
		}
		assertTrue(localConfig.get(UserConfig.KEY).isAuthorEmailImplicit());

		mockSystemReader.setProperty(Constants.GIT_AUTHOR_NAME_KEY
		mockSystemReader.setProperty(Constants.GIT_AUTHOR_EMAIL_KEY
		localConfig.uncache(UserConfig.KEY);
		authorName = localConfig.get(UserConfig.KEY).getAuthorName();
		authorEmail = localConfig.get(UserConfig.KEY).getAuthorEmail();
		assertEquals("git author name"
		assertEquals("author@email"
		assertFalse(localConfig.get(UserConfig.KEY).isAuthorNameImplicit());
		assertFalse(localConfig.get(UserConfig.KEY).isAuthorEmailImplicit());

		mockSystemReader.clearProperties();
		userGitConfig.setString("user"
		userGitConfig.setString("user"
		authorName = localConfig.get(UserConfig.KEY).getAuthorName();
		authorEmail = localConfig.get(UserConfig.KEY).getAuthorEmail();
		assertEquals("global username"
		assertEquals("author@globalemail"
		assertFalse(localConfig.get(UserConfig.KEY).isAuthorNameImplicit());
		assertFalse(localConfig.get(UserConfig.KEY).isAuthorEmailImplicit());

		localConfig.setString("user"
		localConfig.setString("user"
		authorName = localConfig.get(UserConfig.KEY).getAuthorName();
		authorEmail = localConfig.get(UserConfig.KEY).getAuthorEmail();
		assertEquals("local username"
		assertEquals("author@localemail"
		assertFalse(localConfig.get(UserConfig.KEY).isAuthorNameImplicit());
		assertFalse(localConfig.get(UserConfig.KEY).isAuthorEmailImplicit());

		authorName = localConfig.get(UserConfig.KEY).getCommitterName();
		authorEmail = localConfig.get(UserConfig.KEY).getCommitterEmail();
		assertEquals("local username"
		assertEquals("author@localemail"
		assertFalse(localConfig.get(UserConfig.KEY).isCommitterNameImplicit());
		assertFalse(localConfig.get(UserConfig.KEY).isCommitterEmailImplicit());

		mockSystemReader.setProperty(Constants.GIT_AUTHOR_NAME_KEY
				"git author name");
		mockSystemReader.setProperty(Constants.GIT_AUTHOR_EMAIL_KEY
				"author@email");
		localConfig.setString("user"
		localConfig.setString("user"
		authorName = localConfig.get(UserConfig.KEY).getAuthorName();
		authorEmail = localConfig.get(UserConfig.KEY).getAuthorEmail();
		assertEquals("git author name"
		assertEquals("author@email"
		assertFalse(localConfig.get(UserConfig.KEY).isAuthorNameImplicit());
		assertFalse(localConfig.get(UserConfig.KEY).isAuthorEmailImplicit());
	}

	@Test
	public void testReadUserConfigWithInvalidCharactersStripped() {
		final MockSystemReader mockSystemReader = new MockSystemReader();
		final Config localConfig = new Config(mockSystemReader.openUserConfig(
				null

		localConfig.setString("user"
		localConfig.setString("user"

		UserConfig userConfig = localConfig.get(UserConfig.KEY);
		assertEquals("foobar"
		assertEquals("bazqux@example.com"
	}

	@Test
	public void testReadBoolean_TrueFalse1() throws ConfigInvalidException {
		final Config c = parse("[s]\na = true\nb = false\n");
		assertEquals("true"
		assertEquals("false"

		assertTrue(c.getBoolean("s"
		assertFalse(c.getBoolean("s"
	}

	@Test
	public void testReadBoolean_TrueFalse2() throws ConfigInvalidException {
		final Config c = parse("[s]\na = TrUe\nb = fAlSe\n");
		assertEquals("TrUe"
		assertEquals("fAlSe"

		assertTrue(c.getBoolean("s"
		assertFalse(c.getBoolean("s"
	}

	@Test
	public void testReadBoolean_YesNo1() throws ConfigInvalidException {
		final Config c = parse("[s]\na = yes\nb = no\n");
		assertEquals("yes"
		assertEquals("no"

		assertTrue(c.getBoolean("s"
		assertFalse(c.getBoolean("s"
	}

	@Test
	public void testReadBoolean_YesNo2() throws ConfigInvalidException {
		final Config c = parse("[s]\na = yEs\nb = NO\n");
		assertEquals("yEs"
		assertEquals("NO"

		assertTrue(c.getBoolean("s"
		assertFalse(c.getBoolean("s"
	}

	@Test
	public void testReadBoolean_OnOff1() throws ConfigInvalidException {
		final Config c = parse("[s]\na = on\nb = off\n");
		assertEquals("on"
		assertEquals("off"

		assertTrue(c.getBoolean("s"
		assertFalse(c.getBoolean("s"
	}

	@Test
	public void testReadBoolean_OnOff2() throws ConfigInvalidException {
		final Config c = parse("[s]\na = ON\nb = OFF\n");
		assertEquals("ON"
		assertEquals("OFF"

		assertTrue(c.getBoolean("s"
		assertFalse(c.getBoolean("s"
	}

	static enum TestEnum {
		ONE_TWO;
	}

	@Test
	public void testGetEnum() throws ConfigInvalidException {
		Config c = parse("[s]\na = ON\nb = input\nc = true\nd = off\n");
		assertSame(CoreConfig.AutoCRLF.TRUE
				CoreConfig.AutoCRLF.FALSE));

		assertSame(CoreConfig.AutoCRLF.INPUT
				CoreConfig.AutoCRLF.FALSE));

		assertSame(CoreConfig.AutoCRLF.TRUE
				CoreConfig.AutoCRLF.FALSE));

		assertSame(CoreConfig.AutoCRLF.FALSE
				CoreConfig.AutoCRLF.TRUE));

		c = new Config();
		assertSame(CoreConfig.AutoCRLF.FALSE
				CoreConfig.AutoCRLF.FALSE));

		c = parse("[s \"b\"]\n\tc = one two\n");
		assertSame(TestEnum.ONE_TWO

		c = parse("[s \"b\"]\n\tc = one-two\n");
		assertSame(TestEnum.ONE_TWO
	}

	@Test
	public void testGetInvalidEnum() throws ConfigInvalidException {
		Config c = parse("[a]\n\tb = invalid\n");
		try {
			c.getEnum("a"
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid value: a.b=invalid"
		}

		c = parse("[a \"b\"]\n\tc = invalid\n");
		try {
			c.getEnum("a"
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid value: a.b.c=invalid"
		}
	}

	@Test
	public void testSetEnum() {
		final Config c = new Config();
		c.setEnum("s"
		assertEquals("[s \"b\"]\n\tc = one two\n"
	}

	@Test
	public void testGetFastForwardMergeoptions() throws ConfigInvalidException {
		assertSame(FastForwardMode.FF
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS
		MergeConfig mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF
		c = parse("[branch \"side\"]\n\tmergeoptions = --ff-only\n");
		assertSame(FastForwardMode.FF_ONLY
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS
				FastForwardMode.FF_ONLY));
		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF_ONLY
		c = parse("[branch \"side\"]\n\tmergeoptions = --ff\n");
		assertSame(FastForwardMode.FF
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS
		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF
		c = parse("[branch \"side\"]\n\tmergeoptions = --no-ff\n");
		assertSame(FastForwardMode.NO_FF
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS
		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.NO_FF
	}

	@Test
	public void testSetFastForwardMergeoptions() {
		final Config c = new Config();
		c.setEnum("branch"
		assertEquals("[branch \"side\"]\n\tmergeoptions = --ff\n"
		c.setEnum("branch"
		assertEquals("[branch \"side\"]\n\tmergeoptions = --ff-only\n"
				c.toText());
		c.setEnum("branch"
		assertEquals("[branch \"side\"]\n\tmergeoptions = --no-ff\n"
				c.toText());
	}

	@Test
	public void testGetFastForwardMerge() throws ConfigInvalidException {
		assertSame(FastForwardMode.Merge.TRUE
				ConfigConstants.CONFIG_KEY_MERGE
				ConfigConstants.CONFIG_KEY_FF
		MergeConfig mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF
		c = parse("[merge]\n\tff = only\n");
		assertSame(FastForwardMode.Merge.ONLY
				ConfigConstants.CONFIG_KEY_MERGE
				ConfigConstants.CONFIG_KEY_FF
		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF_ONLY
		c = parse("[merge]\n\tff = true\n");
		assertSame(FastForwardMode.Merge.TRUE
				ConfigConstants.CONFIG_KEY_MERGE
				ConfigConstants.CONFIG_KEY_FF
		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF
		c = parse("[merge]\n\tff = false\n");
		assertSame(FastForwardMode.Merge.FALSE
				ConfigConstants.CONFIG_KEY_MERGE
				ConfigConstants.CONFIG_KEY_FF
		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.NO_FF
	}

	@Test
	public void testCombinedMergeOptions() throws ConfigInvalidException {
		MergeConfig mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF
		assertTrue(mergeConfig.isCommit());
		assertFalse(mergeConfig.isSquash());
		c = parse("[merge]\n\tff = false\n"
				+ "[branch \"side\"]\n\tmergeoptions = --ff-only\n");
		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF_ONLY
		assertTrue(mergeConfig.isCommit());
		assertFalse(mergeConfig.isSquash());
		c = parse("[merge]\n\tff = only\n"
				+ "[branch \"side\"]\n\tmergeoptions = --squash\n");
		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF_ONLY
		assertTrue(mergeConfig.isCommit());
		assertTrue(mergeConfig.isSquash());
		c = parse("[merge]\n\tff = false\n"
				+ "[branch \"side\"]\n\tmergeoptions = --ff-only --no-commit\n");
		mergeConfig = c.get(MergeConfig.getParser("side"));
		assertSame(FastForwardMode.FF_ONLY
		assertFalse(mergeConfig.isCommit());
		assertFalse(mergeConfig.isSquash());
	}

	@Test
	public void testSetFastForwardMerge() {
		final Config c = new Config();
		c.setEnum("merge"
				FastForwardMode.Merge.valueOf(FastForwardMode.FF));
		assertEquals("[merge]\n\tff = true\n"
		c.setEnum("merge"
				FastForwardMode.Merge.valueOf(FastForwardMode.FF_ONLY));
		assertEquals("[merge]\n\tff = only\n"
		c.setEnum("merge"
				FastForwardMode.Merge.valueOf(FastForwardMode.NO_FF));
		assertEquals("[merge]\n\tff = false\n"
	}

	@Test
	public void testReadLong() throws ConfigInvalidException {
		assertReadLong(1L);
		assertReadLong(-1L);
		assertReadLong(Long.MIN_VALUE);
		assertReadLong(Long.MAX_VALUE);
		assertReadLong(4L * 1024 * 1024 * 1024
		assertReadLong(3L * 1024 * 1024
		assertReadLong(8L * 1024

		try {
			assertReadLong(-1
			fail("incorrectly accepted 1.5g");
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid integer value: s.a=1.5g"
		}
	}

	@Test
	public void testBooleanWithNoValue() throws ConfigInvalidException {
		Config c = parse("[my]\n\tempty\n");
		assertEquals(""
		assertEquals(1
		assertEquals(""
		assertTrue(c.getBoolean("my"
		assertEquals("[my]\n\tempty\n"
	}

	@Test
	public void testUnsetBranchSection() throws ConfigInvalidException {
				+ "[branch \"keep\"]\n"
				+ "  merge = master.branch.to.keep.in.the.file\n"
				+ "\n"
				+ "[branch \"remove\"]\n"
				+ "  merge = this.will.get.deleted\n"
				+ "  remote = origin-for-some-long-gone-place\n"
				+ "\n"
				+ "[core-section-not-to-remove-in-test]\n"
				+ "  packedGitLimit = 14\n");
		c.unsetSection("branch"
		c.unsetSection("branch"
				+ "[branch \"keep\"]\n"
				+ "  merge = master.branch.to.keep.in.the.file\n"
				+ "\n"
				+ "[core-section-not-to-remove-in-test]\n"
				+ "  packedGitLimit = 14\n"
	}

	@Test
	public void testUnsetSingleSection() throws ConfigInvalidException {
				+ "[branch \"keep\"]\n"
				+ "  merge = master.branch.to.keep.in.the.file\n"
				+ "\n"
				+ "[single]\n"
				+ "  merge = this.will.get.deleted\n"
				+ "  remote = origin-for-some-long-gone-place\n"
				+ "\n"
				+ "[core-section-not-to-remove-in-test]\n"
				+ "  packedGitLimit = 14\n");
		c.unsetSection("single"
				+ "[branch \"keep\"]\n"
				+ "  merge = master.branch.to.keep.in.the.file\n"
				+ "\n"
				+ "[core-section-not-to-remove-in-test]\n"
				+ "  packedGitLimit = 14\n"
	}

	@Test
	public void test008_readSectionNames() throws ConfigInvalidException {
		final Config c = parse("[a]\n [B]\n");
		Set<String> sections = c.getSections();
		assertTrue("Sections should contain \"a\""
		assertTrue("Sections should contain \"b\""
	}

	@Test
	public void test009_readNamesInSection() throws ConfigInvalidException {
		String configString = "[core]\n" + "repositoryFormatVersion = 0\n"
				+ "filemode = false\n" + "logAllRefUpdates = true\n";
		final Config c = parse(configString);
		Set<String> names = c.getNames("core");
		assertEquals("Core section size"
		assertTrue("Core section should contain \"filemode\""
				.contains("filemode"));

		assertTrue("Core section should contain \"repositoryFormatVersion\""
				names.contains("repositoryFormatVersion"));

		assertTrue("Core section should contain \"repositoryformatversion\""
				names.contains("repositoryformatversion"));

		Iterator<String> itr = names.iterator();
		assertEquals("filemode"
		assertEquals("logAllRefUpdates"
		assertEquals("repositoryFormatVersion"
		assertFalse(itr.hasNext());
	}

	@Test
	public void test_ReadNamesInSectionRecursive()
			throws ConfigInvalidException {
		String baseConfigString = "[core]\n" + "logAllRefUpdates = true\n";
		String configString = "[core]\n" + "repositoryFormatVersion = 0\n"
				+ "filemode = false\n";
		final Config c = parse(configString
		Set<String> names = c.getNames("core"
		assertEquals("Core section size"
		assertTrue("Core section should contain \"filemode\""
				names.contains("filemode"));
		assertTrue("Core section should contain \"repositoryFormatVersion\""
				names.contains("repositoryFormatVersion"));
		assertTrue("Core section should contain \"logAllRefUpdates\""
				names.contains("logAllRefUpdates"));
		assertTrue("Core section should contain \"logallrefupdates\""
				names.contains("logallrefupdates"));

		Iterator<String> itr = names.iterator();
		assertEquals("filemode"
		assertEquals("repositoryFormatVersion"
		assertEquals("logAllRefUpdates"
		assertFalse(itr.hasNext());
	}

	@Test
	public void test010_readNamesInSubSection() throws ConfigInvalidException {
				+ "b=1\n";
		final Config c = parse(configString);
		Set<String> names = c.getNames("a"
		assertEquals("Subsection size"
		assertTrue("Subsection should contain \"x\""
		assertTrue("Subsection should contain \"y\""
		assertTrue("Subsection should contain \"z\""
		names = c.getNames("a"
		assertEquals("Subsection size"
		assertTrue("Subsection should contain \"a\""
		assertTrue("Subsection should contain \"b\""
	}

	@Test
	public void readNamesInSubSectionRecursive() throws ConfigInvalidException {
				+ "B=1\n";
		final Config c = parse(configString
		Set<String> names = c.getNames("a"
		assertEquals("Subsection size"
		assertTrue("Subsection should contain \"x\""
		assertTrue("Subsection should contain \"y\""
		assertTrue("Subsection should contain \"z\""
		names = c.getNames("a"
		assertEquals("Subsection size"
		assertTrue("Subsection should contain \"A\""
		assertTrue("Subsection should contain \"a\""
		assertTrue("Subsection should contain \"B\""
	}


	@Test
	public void testNoFinalNewline() throws ConfigInvalidException {
		Config c = parse("[a]\n"
				+ "x = 0\n"
				+ "y = 1");
		assertEquals("0"
		assertEquals("1"
	}

	@Test
	public void testExplicitlySetEmptyString() throws Exception {
		Config c = new Config();
		c.setString("a"
		c.setString("a"

		assertEquals("0"
		assertEquals(0

		assertEquals(""
		assertArrayEquals(new String[]{""}
		assertEquals(1

		assertNull(c.getString("a"
		assertArrayEquals(new String[]{}
	}

	@Test
	public void testParsedEmptyString() throws Exception {
		Config c = parse("[a]\n"
				+ "x = 0\n"
				+ "y =\n");

		assertEquals("0"
		assertEquals(0

		assertNull(c.getString("a"
		assertArrayEquals(new String[]{null}
		assertEquals(1

		assertNull(c.getString("a"
		assertArrayEquals(new String[]{}
	}

	@Test
	public void testSetStringListWithEmptyValue() throws Exception {
		Config c = new Config();
		c.setStringList("a"
		assertArrayEquals(new String[]{""}
	}

	@Test
	public void testEmptyValueAtEof() throws Exception {
		String text = "[a]\nx =";
		Config c = parse(text);
		assertNull(c.getString("a"
		assertArrayEquals(new String[]{null}
				c.getStringList("a"
		c = parse(text + "\n");
		assertNull(c.getString("a"
		assertArrayEquals(new String[]{null}
				c.getStringList("a"
	}

	@Test
	public void testReadMultipleValuesForName() throws ConfigInvalidException {
		Config c = parse("[foo]\nbar=false\nbar=true\n");
		assertTrue(c.getBoolean("foo"
	}

	@Test
	public void testIncludeInvalidName() throws ConfigInvalidException {
		expectedEx.expect(ConfigInvalidException.class);
		expectedEx.expectMessage(JGitText.get().invalidLineInConfigFile);
		parse("[include]\nbar\n");
	}

	@Test
	public void testIncludeNoValue() throws ConfigInvalidException {
		expectedEx.expect(ConfigInvalidException.class);
		expectedEx.expectMessage(JGitText.get().invalidLineInConfigFile);
		parse("[include]\npath\n");
	}

	@Test
	public void testIncludeEmptyValue() throws ConfigInvalidException {
		expectedEx.expect(ConfigInvalidException.class);
		expectedEx.expectMessage(JGitText.get().invalidLineInConfigFile);
		parse("[include]\npath=\n");
	}

	@Test
	public void testIncludeValuePathNotFound() throws ConfigInvalidException {
		String notFound = "/not/found";
		Config parsed = parse("[include]\npath=" + notFound + "\n");
		assertEquals(1
		assertEquals(notFound
	}

	@Test
	public void testIncludeValuePathWithTilde() throws ConfigInvalidException {
		String notSupported = "~/someFile";
		Config parsed = parse("[include]\npath=" + notSupported + "\n");
		assertEquals(1
		assertEquals(notSupported
	}

	@Test
	public void testIncludeValuePathRelative() throws ConfigInvalidException {
		String notSupported = "someRelativeFile";
		Config parsed = parse("[include]\npath=" + notSupported + "\n");
		assertEquals(1
		assertEquals(notSupported
	}

	@Test
	public void testIncludeTooManyRecursions() throws IOException {
		File config = tmp.newFile("config");
		String include = "[include]\npath=" + pathToString(config) + "\n";
		Files.write(config.toPath()
		try {
			loadConfig(config);
			fail();
		} catch (ConfigInvalidException cie) {
			for (Throwable t = cie; t != null; t = t.getCause()) {
				if (t.getMessage()
						.equals(JGitText.get().tooManyIncludeRecursions)) {
					return;
				}
			}
			fail("Expected to find expected exception message: "
					+ JGitText.get().tooManyIncludeRecursions);
		}
	}

	@Test
	public void testIncludeIsNoop() throws IOException
		File config = tmp.newFile("config");

		String fooBar = "[foo]\nbar=true\n";
		Files.write(config.toPath()

		Config parsed = parse("[include]\npath=" + pathToString(config) + "\n");
		assertFalse(parsed.getBoolean("foo"
	}

	@Test
	public void testIncludeCaseInsensitiveSection()
			throws IOException
		File included = tmp.newFile("included");
		String content = "[foo]\nbar=true\n";
		Files.write(included.toPath()

		File config = tmp.newFile("config");
		content = "[Include]\npath=" + pathToString(included) + "\n";
		Files.write(config.toPath()

		FileBasedConfig fbConfig = loadConfig(config);
		assertTrue(fbConfig.getBoolean("foo"
	}

	@Test
	public void testIncludeCaseInsensitiveKey()
			throws IOException
		File included = tmp.newFile("included");
		String content = "[foo]\nbar=true\n";
		Files.write(included.toPath()

		File config = tmp.newFile("config");
		content = "[include]\nPath=" + pathToString(included) + "\n";
		Files.write(config.toPath()

		FileBasedConfig fbConfig = loadConfig(config);
		assertTrue(fbConfig.getBoolean("foo"
	}

	@Test
	public void testIncludeExceptionContainsLine() {
		try {
			parse("[include]\npath=\n");
			fail("Expected ConfigInvalidException");
		} catch (ConfigInvalidException e) {
			assertTrue(
					"Expected to find the problem line in the exception message"
					e.getMessage().contains("include.path"));
		}
	}

	@Test
	public void testIncludeExceptionContainsFile() throws IOException {
		File included = tmp.newFile("included");
		String includedPath = pathToString(included);
		String content = "[include]\npath=\n";
		Files.write(included.toPath()

		File config = tmp.newFile("config");
		String include = "[include]\npath=" + includedPath + "\n";
		Files.write(config.toPath()
		try {
			loadConfig(config);
			fail("Expected ConfigInvalidException");
		} catch (ConfigInvalidException e) {
			for (Throwable t = e; t != null; t = t.getCause()) {
				if (t.getMessage().contains(includedPath)) {
					return;
				}
			}
			fail("Expected to find the path in the exception message: "
					+ includedPath);
		}
	}

	@Test
	public void testIncludeSetValueMustNotTouchIncludedLines1()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = createAllTypesSampleContent("Alice Parker"
				21
				+ pathToString(includedFile);
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsIncluded(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsIncluded(config
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeSetValueMustNotTouchIncludedLines2()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = "[include]\npath=" + pathToString(includedFile) + "\n"
				+ createAllTypesSampleContent("Alice Parker"
						CoreConfig.AutoCRLF.FALSE
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsConfig(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsNew(config
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeSetValueOnFileWithJustContainsInclude()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = "[include]\npath=" + pathToString(includedFile);
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsIncluded(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsNew(config
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeSetValueOnFileWithJustEmptySection1()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = "[user]\n[include]\npath="
				+ pathToString(includedFile);
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsIncluded(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsNewWithName(config
					REFS_BACKUP);
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeSetValueOnFileWithJustEmptySection2()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = "[include]\npath=" + pathToString(includedFile)
				+ "\n[user]";
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsIncluded(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsNew(config
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeSetValueOnFileWithJustExistingSection1()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = "[user]\nemail=alice@home\n[include]\npath="
				+ pathToString(includedFile);
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsIncluded(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsNewWithName(config
					REFS_BACKUP);
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeSetValueOnFileWithJustExistingSection2()
			throws IOException
		File includedFile = createAllTypesIncludedContent();

		File configFile = tmp.newFile("config");
		String content = "[include]\npath=" + pathToString(includedFile)
				+ "\n[user]\nemail=alice@home\n";
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);
		assertValuesAsIncluded(fbConfig
		assertSections(fbConfig

		setAllValuesNew(fbConfig);
		assertValuesAsIsSaveLoad(fbConfig
			assertValuesAsNew(config
			assertSections(fbConfig
		});
	}

	@Test
	public void testIncludeUnsetSectionMustNotTouchIncludedLines()
			throws IOException
		File includedFile = tmp.newFile("included");
		RefSpec includedRefSpec = new RefSpec(REFS_UPSTREAM);
		String includedContent = "[remote \"origin\"]\n" + "fetch="
				+ includedRefSpec;
		Files.write(includedFile.toPath()

		File configFile = tmp.newFile("config");
		RefSpec refSpec = new RefSpec(REFS_ORIGIN);
		String content = "[include]\npath=" + pathToString(includedFile) + "\n"
				+ "[remote \"origin\"]\n" + "fetch=" + refSpec;
		Files.write(configFile.toPath()

		FileBasedConfig fbConfig = loadConfig(configFile);

		Consumer<FileBasedConfig> assertion = config -> {
			assertEquals(Arrays.asList(includedRefSpec
					config.getRefSpecs("remote"
		};
		assertion.accept(fbConfig);

		fbConfig.unsetSection("remote"
		assertValuesAsIsSaveLoad(fbConfig
			assertEquals(Collections.singletonList(includedRefSpec)
					config.getRefSpecs("remote"
		});
	}

	private File createAllTypesIncludedContent() throws IOException {
		File includedFile = tmp.newFile("included");
		String includedContent = createAllTypesSampleContent("Alice Muller"
				true
		Files.write(includedFile.toPath()
		return includedFile;
	}

	private static void assertValuesAsIsSaveLoad(FileBasedConfig fbConfig
			Consumer<FileBasedConfig> assertion)
			throws IOException
		assertion.accept(fbConfig);

		fbConfig.save();
		assertion.accept(fbConfig);

		fbConfig = loadConfig(fbConfig.getFile());
		assertion.accept(fbConfig);
	}

	private static void setAllValuesNew(Config config) {
		config.setString("user"
		config.setBoolean("core"
		config.setInt("core"
		config.setLong("core"
		config.setLong("core"
		config.setEnum("core"
		config.setString("remote"
	}

	private static void assertValuesAsIncluded(Config config
		assertAllTypesSampleContent("Alice Muller"
				CoreConfig.AutoCRLF.TRUE
	}

	private static void assertValuesAsConfig(Config config
		assertAllTypesSampleContent("Alice Parker"
				CoreConfig.AutoCRLF.FALSE
	}

	private static void assertValuesAsNew(Config config
		assertValuesAsNewWithName(config
	}

	private static void assertValuesAsNewWithName(Config config
			String... refs) {
		assertAllTypesSampleContent(name
				CoreConfig.AutoCRLF.FALSE
	}

	private static void assertSections(Config config
		assertEquals(Arrays.asList(sections)
				new ArrayList<>(config.getSections()));
	}

	private static String createAllTypesSampleContent(String name
			boolean fileMode
			long repositoryCacheExpireAfter
			String fetchRefSpec) {
		final StringBuilder builder = new StringBuilder();
		builder.append("[user]\n");
		builder.append("name=");
		builder.append(name);
		builder.append("\n");

		builder.append("[core]\n");
		builder.append("fileMode=");
		builder.append(fileMode);
		builder.append("\n");

		builder.append("deltaBaseCacheLimit=");
		builder.append(deltaBaseCacheLimit);
		builder.append("\n");

		builder.append("packedGitLimit=");
		builder.append(packedGitLimit);
		builder.append("\n");

		builder.append("repositoryCacheExpireAfter=");
		builder.append(repositoryCacheExpireAfter);
		builder.append("\n");

		builder.append("autocrlf=");
		builder.append(autoCRLF.name());
		builder.append("\n");

		builder.append("[remote \"origin\"]\n");
		builder.append("fetch=");
		builder.append(fetchRefSpec);
		builder.append("\n");
		return builder.toString();
	}

	private static void assertAllTypesSampleContent(String name
			boolean fileMode
			long repositoryCacheExpireAfter
			Config config
		assertEquals(name
		assertEquals(fileMode
				config.getBoolean("core"
		assertEquals(deltaBaseCacheLimit
				config.getInt("core"
		assertEquals(packedGitLimit
				config.getLong("core"
		assertEquals(repositoryCacheExpireAfter
				null
		assertEquals(autoCRLF
				CoreConfig.AutoCRLF.INPUT));
		final List<RefSpec> refspecs = new ArrayList<>();
		for (String fetchRefSpec : fetchRefSpecs) {
			refspecs.add(new RefSpec(fetchRefSpec));
		}

		assertEquals(refspecs
	}

	private static void assertReadLong(long exp) throws ConfigInvalidException {
		assertReadLong(exp
	}

	private static void assertReadLong(long exp
			throws ConfigInvalidException {
		final Config c = parse("[s]\na = " + act + "\n");
		assertEquals(exp
	}

	private static Config parse(String content)
			throws ConfigInvalidException {
		return parse(content
	}

	private static Config parse(String content
			throws ConfigInvalidException {
		final Config c = new Config(baseConfig);
		c.fromText(content);
		return c;
	}

	@Test
	public void testTimeUnit() throws ConfigInvalidException {
		assertEquals(0
		assertEquals(2
		assertEquals(200

		assertEquals(0
		assertEquals(2
		assertEquals(231
		assertEquals(1
		assertEquals(300

		assertEquals(2
		assertEquals(2
		assertEquals(1
		assertEquals(10

		assertEquals(5
		assertEquals(5
		assertEquals(1
		assertEquals(48

		assertEquals(5
		assertEquals(5
		assertEquals(1
		assertEquals(48
		assertEquals(48

		assertEquals(4
		assertEquals(1
		assertEquals(14

		assertEquals(7
		assertEquals(7
		assertEquals(14
		assertEquals(14

		assertEquals(30
		assertEquals(30
		assertEquals(60
		assertEquals(60

		assertEquals(365
		assertEquals(365
		assertEquals(365 * 2
	}

	private long parseTime(String value
			throws ConfigInvalidException {
		Config c = parse("[a]\na=" + value + "\n");
		return c.getTimeUnit("a"
	}

	@Test
	public void testTimeUnitDefaultValue() throws ConfigInvalidException {
		assertEquals(20
				MILLISECONDS));
		assertEquals(20
				MILLISECONDS));

		assertEquals(20
				MILLISECONDS));
	}

	@Test
	public void testTimeUnitInvalid() throws ConfigInvalidException {
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx
				.expectMessage("Invalid time unit value: a.a=1 monttthhh");
		parseTime("1 monttthhh"
	}

	@Test
	public void testTimeUnitInvalidWithSection() throws ConfigInvalidException {
		Config c = parse("[a \"b\"]\na=1 monttthhh\n");
		expectedEx.expect(IllegalArgumentException.class);
		expectedEx.expectMessage("Invalid time unit value: a.b.a=1 monttthhh");
		c.getTimeUnit("a"
	}

	@Test
	public void testTimeUnitNegative() throws ConfigInvalidException {
		expectedEx.expect(IllegalArgumentException.class);
		parseTime("-1"
	}

	@Test
	public void testEscapeSpacesOnly() throws ConfigInvalidException {
		assertEquals(""

		assertValueRoundTrip(" "
		assertValueRoundTrip("  "
	}

	@Test
	public void testEscapeLeadingSpace() throws ConfigInvalidException {
		assertValueRoundTrip("x"
		assertValueRoundTrip(" x"
		assertValueRoundTrip("  x"
	}

	@Test
	public void testEscapeTrailingSpace() throws ConfigInvalidException {
		assertValueRoundTrip("x"
		assertValueRoundTrip("x  "
		assertValueRoundTrip("x "
	}

	@Test
	public void testEscapeLeadingAndTrailingSpace()
			throws ConfigInvalidException {
		assertValueRoundTrip(" x "
		assertValueRoundTrip("  x "
		assertValueRoundTrip(" x  "
		assertValueRoundTrip("  x  "
	}

	@Test
	public void testNoEscapeInternalSpaces() throws ConfigInvalidException {
		assertValueRoundTrip("x y");
		assertValueRoundTrip("x  y");
		assertValueRoundTrip("x  y");
		assertValueRoundTrip("x  y   z");
		assertValueRoundTrip("x " + WS + " y");
	}

	@Test
	public void testNoEscapeSpecialCharacters() throws ConfigInvalidException {
		assertValueRoundTrip("x\\y"
		assertValueRoundTrip("x\"y"
		assertValueRoundTrip("x\ny"
		assertValueRoundTrip("x\ty"
		assertValueRoundTrip("x\by"
	}

	@Test
	public void testParseLiteralBackspace() throws ConfigInvalidException {
		assertEquals("x\by"
	}

	@Test
	public void testEscapeCommentCharacters() throws ConfigInvalidException {
		assertValueRoundTrip("x#y"
		assertValueRoundTrip("x;y"
	}

	@Test
	public void testEscapeValueInvalidCharacters() {
		assertIllegalArgumentException(() -> Config.escapeSubsection("x\0y"));
	}

	@Test
	public void testEscapeSubsectionInvalidCharacters() {
		assertIllegalArgumentException(() -> Config.escapeSubsection("x\ny"));
		assertIllegalArgumentException(() -> Config.escapeSubsection("x\0y"));
	}

	@Test
	public void testParseMultipleQuotedRegions() throws ConfigInvalidException {
		assertEquals("b a z; \n"
	}

	@Test
	public void testParseComments() throws ConfigInvalidException {
		assertEquals("baz"
		assertEquals("baz"
		assertEquals("baz"
		assertEquals("baz"

		assertEquals("baz"
		assertEquals("baz"
		assertEquals("baz"
		assertEquals("baz"

		assertEquals("baz "
		assertEquals("baz "
		assertEquals("baz "
		assertEquals("baz "
	}

	@Test
	public void testEscapeSubsection() throws ConfigInvalidException {
		assertSubsectionRoundTrip(""
		assertSubsectionRoundTrip("x"
		assertSubsectionRoundTrip(" x"
		assertSubsectionRoundTrip("x "
		assertSubsectionRoundTrip(" x "
		assertSubsectionRoundTrip("x y"
		assertSubsectionRoundTrip("x  y"
		assertSubsectionRoundTrip("x\\y"
		assertSubsectionRoundTrip("x\"y"

		assertSubsectionRoundTrip("x\by"
		assertSubsectionRoundTrip("x\ty"
	}

	@Test
	public void testParseInvalidValues() {
		assertInvalidValue(JGitText.get().newlineInQuotesNotAllowed
		assertInvalidValue(JGitText.get().endOfFileInEscape
		assertInvalidValue(
				MessageFormat.format(JGitText.get().badEscape
	}

	@Test
	public void testParseInvalidSubsections() {
		assertInvalidSubsection(
				JGitText.get().newlineInQuotesNotAllowed
	}

	@Test
	public void testDropBackslashFromInvalidEscapeSequenceInSubsectionName()
			throws ConfigInvalidException {
		assertEquals("x0"
		assertEquals("xq"
		assertEquals("xb"
		assertEquals("xn"
		assertEquals("xt"
	}

	private static void assertValueRoundTrip(String value)
			throws ConfigInvalidException {
		assertValueRoundTrip(value
	}

	private static void assertValueRoundTrip(String value
			throws ConfigInvalidException {
		String escaped = Config.escapeValue(value);
		assertEquals("escape failed;"
		assertEquals("parse failed;"
	}

	private static String parseEscapedValue(String escapedValue)
			throws ConfigInvalidException {
		String text = "[foo]\nbar=" + escapedValue;
		Config c = parse(text);
		return c.getString("foo"
	}

	private static void assertInvalidValue(String expectedMessage
			String escapedValue) {
		try {
			parseEscapedValue(escapedValue);
			fail("expected ConfigInvalidException");
		} catch (ConfigInvalidException e) {
			assertEquals(expectedMessage
		}
	}

	private static void assertSubsectionRoundTrip(String subsection
			String expectedEscaped) throws ConfigInvalidException {
		String escaped = Config.escapeSubsection(subsection);
		assertEquals("escape failed;"
		assertEquals("parse failed;"
	}

	private static String parseEscapedSubsection(String escapedSubsection)
			throws ConfigInvalidException {
		String text = "[foo " + escapedSubsection + "]\nbar = value";
		Config c = parse(text);
		Set<String> subsections = c.getSubsections("foo");
		assertEquals("only one section"
		return subsections.iterator().next();
	}

	private static void assertIllegalArgumentException(Runnable r) {
		try {
			r.run();
			fail("expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	private static void assertInvalidSubsection(String expectedMessage
			String escapedSubsection) {
		try {
			parseEscapedSubsection(escapedSubsection);
			fail("expected ConfigInvalidException");
		} catch (ConfigInvalidException e) {
			assertEquals(expectedMessage
		}
	}

	private static FileBasedConfig loadConfig(File file)
			throws IOException
		final FileBasedConfig config = new FileBasedConfig(null
				FS.DETECTED);
		config.load();
		return config;
	}
}
