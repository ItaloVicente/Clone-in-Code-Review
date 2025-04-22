
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.Config;
import org.junit.Before;
import org.junit.Test;

public class RemoteConfigTest {
	private Config config;

	@Before
	public void setUp() throws Exception {
		config = new Config();
	}

	private void readConfig(String dat) throws ConfigInvalidException {
		config = new Config();
		config.fromText(dat);
	}

	private void checkConfig(String exp) {
		assertEquals(exp
	}

	@Test
	public void testSimple() throws Exception {
		readConfig("[remote \"spearce\"]\n"

		final RemoteConfig rc = new RemoteConfig(config
		final List<URIish> allURIs = rc.getURIs();
		RefSpec spec;

		assertEquals("spearce"
		assertNotNull(allURIs);
		assertNotNull(rc.getFetchRefSpecs());
		assertNotNull(rc.getPushRefSpecs());
		assertNotNull(rc.getTagOpt());
		assertEquals(0
		assertSame(TagOpt.AUTO_FOLLOW

		assertEquals(1
				.toString());

		assertEquals(1
		spec = rc.getFetchRefSpecs().get(0);
		assertTrue(spec.isForceUpdate());
		assertTrue(spec.isWildcard());

		assertEquals(0
	}

	@Test
	public void testSimpleNoTags() throws Exception {
		readConfig("[remote \"spearce\"]\n"
				+ "tagopt = --no-tags\n");
		final RemoteConfig rc = new RemoteConfig(config
		assertSame(TagOpt.NO_TAGS
	}

	@Test
	public void testSimpleAlwaysTags() throws Exception {
		readConfig("[remote \"spearce\"]\n"
				+ "tagopt = --tags\n");
		final RemoteConfig rc = new RemoteConfig(config
		assertSame(TagOpt.FETCH_TAGS
	}

	@Test
	public void testMirror() throws Exception {
		readConfig("[remote \"spearce\"]\n"

		final RemoteConfig rc = new RemoteConfig(config
		final List<URIish> allURIs = rc.getURIs();
		RefSpec spec;

		assertEquals("spearce"
		assertNotNull(allURIs);
		assertNotNull(rc.getFetchRefSpecs());
		assertNotNull(rc.getPushRefSpecs());

		assertEquals(1
				.toString());

		assertEquals(2

		spec = rc.getFetchRefSpecs().get(0);
		assertTrue(spec.isForceUpdate());
		assertTrue(spec.isWildcard());

		spec = rc.getFetchRefSpecs().get(1);
		assertFalse(spec.isForceUpdate());
		assertTrue(spec.isWildcard());

		assertEquals(0
	}

	@Test
	public void testBackup() throws Exception {
		readConfig("[remote \"backup\"]\n"
				+ "url = user@repo.or.cz:/srv/git/egit.git\n"

		final RemoteConfig rc = new RemoteConfig(config
		final List<URIish> allURIs = rc.getURIs();
		RefSpec spec;

		assertEquals("backup"
		assertNotNull(allURIs);
		assertNotNull(rc.getFetchRefSpecs());
		assertNotNull(rc.getPushRefSpecs());

		assertEquals(2
				.toString());
		assertEquals("user@repo.or.cz:/srv/git/egit.git"
				.toString());

		assertEquals(0

		assertEquals(2
		spec = rc.getPushRefSpecs().get(0);
		assertTrue(spec.isForceUpdate());
		assertTrue(spec.isWildcard());

		spec = rc.getPushRefSpecs().get(1);
		assertFalse(spec.isForceUpdate());
		assertTrue(spec.isWildcard());
	}

	@Test
	public void testUploadPack() throws Exception {
		readConfig("[remote \"example\"]\n"
				+ "url = user@example.com:egit.git\n"
				+ "uploadpack = /path/to/git/git-upload-pack\n"
				+ "receivepack = /path/to/git/git-receive-pack\n");

		final RemoteConfig rc = new RemoteConfig(config
		final List<URIish> allURIs = rc.getURIs();
		RefSpec spec;

		assertEquals("example"
		assertNotNull(allURIs);
		assertNotNull(rc.getFetchRefSpecs());
		assertNotNull(rc.getPushRefSpecs());

		assertEquals(1
		assertEquals("user@example.com:egit.git"

		assertEquals(1
		spec = rc.getFetchRefSpecs().get(0);
		assertTrue(spec.isForceUpdate());
		assertTrue(spec.isWildcard());

		assertEquals(0

		assertEquals("/path/to/git/git-upload-pack"
		assertEquals("/path/to/git/git-receive-pack"
	}

	@Test
	public void testUnknown() throws Exception {
		readConfig("");

		final RemoteConfig rc = new RemoteConfig(config
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals("git-upload-pack"
		assertEquals("git-receive-pack"
	}

	@Test
	public void testAddURI() throws Exception {
		readConfig("");

		final URIish uri = new URIish("/some/dir");
		final RemoteConfig rc = new RemoteConfig(config
		assertEquals(0

		assertTrue(rc.addURI(uri));
		assertEquals(1
		assertSame(uri

		assertFalse(rc.addURI(new URIish(uri.toString())));
		assertEquals(1
	}

	@Test
	public void testRemoveFirstURI() throws Exception {
		readConfig("");

		final URIish a = new URIish("/some/dir");
		final URIish b = new URIish("/another/dir");
		final URIish c = new URIish("/more/dirs");
		final RemoteConfig rc = new RemoteConfig(config
		assertTrue(rc.addURI(a));
		assertTrue(rc.addURI(b));
		assertTrue(rc.addURI(c));

		assertEquals(3
		assertSame(a
		assertSame(b
		assertSame(c

		assertTrue(rc.removeURI(a));
		assertEquals(2
		assertSame(b
		assertSame(c
	}

	@Test
	public void testRemoveMiddleURI() throws Exception {
		readConfig("");

		final URIish a = new URIish("/some/dir");
		final URIish b = new URIish("/another/dir");
		final URIish c = new URIish("/more/dirs");
		final RemoteConfig rc = new RemoteConfig(config
		assertTrue(rc.addURI(a));
		assertTrue(rc.addURI(b));
		assertTrue(rc.addURI(c));

		assertEquals(3
		assertSame(a
		assertSame(b
		assertSame(c

		assertTrue(rc.removeURI(b));
		assertEquals(2
		assertSame(a
		assertSame(c
	}

	@Test
	public void testRemoveLastURI() throws Exception {
		readConfig("");

		final URIish a = new URIish("/some/dir");
		final URIish b = new URIish("/another/dir");
		final URIish c = new URIish("/more/dirs");
		final RemoteConfig rc = new RemoteConfig(config
		assertTrue(rc.addURI(a));
		assertTrue(rc.addURI(b));
		assertTrue(rc.addURI(c));

		assertEquals(3
		assertSame(a
		assertSame(b
		assertSame(c

		assertTrue(rc.removeURI(c));
		assertEquals(2
		assertSame(a
		assertSame(b
	}

	@Test
	public void testRemoveOnlyURI() throws Exception {
		readConfig("");

		final URIish a = new URIish("/some/dir");
		final RemoteConfig rc = new RemoteConfig(config
		assertTrue(rc.addURI(a));

		assertEquals(1
		assertSame(a

		assertTrue(rc.removeURI(a));
		assertEquals(0
	}

	@Test
	public void testCreateOrigin() throws Exception {
		final RemoteConfig rc = new RemoteConfig(config
		rc.addURI(new URIish("/some/dir"));
		rc.update(config);
		checkConfig("[remote \"origin\"]\n" + "\turl = /some/dir\n"
	}

	@Test
	public void testSaveAddURI() throws Exception {
		readConfig("[remote \"spearce\"]\n"

		final RemoteConfig rc = new RemoteConfig(config
		rc.addURI(new URIish("/some/dir"));
		assertEquals(2
		rc.update(config);
		checkConfig("[remote \"spearce\"]\n"
				+ "\turl = /some/dir\n"
	}

	@Test
	public void testSaveRemoveLastURI() throws Exception {
		readConfig("[remote \"spearce\"]\n"
				+ "url = /some/dir\n"

		final RemoteConfig rc = new RemoteConfig(config
		assertEquals(2
		rc.removeURI(new URIish("/some/dir"));
		assertEquals(1
		rc.update(config);
		checkConfig("[remote \"spearce\"]\n"
	}

	@Test
	public void testSaveRemoveFirstURI() throws Exception {
		readConfig("[remote \"spearce\"]\n"
				+ "url = /some/dir\n"

		final RemoteConfig rc = new RemoteConfig(config
		assertEquals(2
		assertEquals(1
		rc.update(config);
		checkConfig("[remote \"spearce\"]\n" + "\turl = /some/dir\n"
	}

	@Test
	public void testSaveNoTags() throws Exception {
		final RemoteConfig rc = new RemoteConfig(config
		rc.addURI(new URIish("/some/dir"));
		rc.setTagOpt(TagOpt.NO_TAGS);
		rc.update(config);
		checkConfig("[remote \"origin\"]\n" + "\turl = /some/dir\n"
				+ "\ttagopt = --no-tags\n");
	}

	@Test
	public void testSaveAllTags() throws Exception {
		final RemoteConfig rc = new RemoteConfig(config
		rc.addURI(new URIish("/some/dir"));
		rc.setTagOpt(TagOpt.FETCH_TAGS);
		rc.update(config);
		checkConfig("[remote \"origin\"]\n" + "\turl = /some/dir\n"
				+ "\ttagopt = --tags\n");
	}

	@Test
	public void testSimpleTimeout() throws Exception {
		readConfig("[remote \"spearce\"]\n"
				+ "timeout = 12\n");
		final RemoteConfig rc = new RemoteConfig(config
		assertEquals(12
	}

	@Test
	public void testSaveTimeout() throws Exception {
		final RemoteConfig rc = new RemoteConfig(config
		rc.addURI(new URIish("/some/dir"));
		rc.setTimeout(60);
		rc.update(config);
		checkConfig("[remote \"origin\"]\n" + "\turl = /some/dir\n"
				+ "\ttimeout = 60\n");
	}

	@Test
	public void noInsteadOf() throws Exception {
		config.setString("remote"
		config.setString("url"
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getURIs().isEmpty());
		assertEquals("short:project.git"
	}

	@Test
	public void singleInsteadOf() throws Exception {
		config.setString("remote"
		config.setString("url"
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getURIs().isEmpty());
				.toASCIIString());
	}

	@Test
	public void multipleInsteadOf() throws Exception {
		config.setString("remote"
		config.setStringList("url"
				Arrays.asList("pre"
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getURIs().isEmpty());
				.toASCIIString());
	}

	@Test
	public void noPushInsteadOf() throws Exception {
		config.setString("remote"
		config.setString("url"
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getPushURIs().isEmpty());
		assertEquals("short:project.git"
				.toASCIIString());
	}

	@Test
	public void pushInsteadOfNotAppliedToPushUri() throws Exception {
		config.setString("remote"
		config.setString("url"
				"short:");
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getPushURIs().isEmpty());
		assertEquals("short:project.git"
				rc.getPushURIs().get(0).toASCIIString());
	}

	@Test
	public void pushInsteadOfAppliedToUri() throws Exception {
		config.setString("remote"
		config.setString("url"
				"short:");
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getPushURIs().isEmpty());
				rc.getPushURIs().get(0).toASCIIString());
	}

	@Test
	public void multiplePushInsteadOf() throws Exception {
		config.setString("remote"
		config.setStringList("url"
				Arrays.asList("pre"
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getPushURIs().isEmpty());
				.get(0).toASCIIString());
	}

	@Test
	public void pushInsteadOfNoPushUrl() throws Exception {
		config.setString("remote"
		config.setStringList("url"
				"pushInsteadOf"
		RemoteConfig rc = new RemoteConfig(config
		assertFalse(rc.getPushURIs().isEmpty());
				rc.getPushURIs().get(0).toASCIIString());
	}
}
