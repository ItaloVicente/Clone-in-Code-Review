
package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.junit.Test;

public class RefTest extends SampleDataRepositoryTestCase {

	private void writeSymref(String src
		RefUpdate u = db.updateRef(src);
		switch (u.link(dst)) {
		case NEW:
		case FORCED:
		case NO_CHANGE:
			break;
		default:
			fail("link " + src + " to " + dst);
		}
	}

	private void writeNewRef(String name
		RefUpdate updateRef = db.updateRef(name);
		updateRef.setNewObjectId(value);
		assertEquals(RefUpdate.Result.NEW
	}

	@Test
	public void testRemoteNames() throws Exception {
		FileBasedConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_REMOTE_SECTION
				"origin"
		config.setBoolean(ConfigConstants.CONFIG_REMOTE_SECTION
				"ab/c"
		config.save();
		assertEquals("[ab/c
				new TreeSet<>(db.getRemoteNames()).toString());

		assertEquals("master"
				db.shortenRemoteBranchName("refs/remotes/origin/master"));
		assertEquals("origin"

		assertEquals("masta/r"
				db.shortenRemoteBranchName("refs/remotes/origin/masta/r"));
		assertEquals("origin"

		assertEquals("xmaster"
				db.shortenRemoteBranchName("refs/remotes/ab/c/xmaster"));
		assertEquals("ab/c"

		assertEquals("xmasta/r"
				db.shortenRemoteBranchName("refs/remotes/ab/c/xmasta/r"));
		assertEquals("ab/c"

		assertNull(db.getRemoteName("refs/remotes/nosuchremote/x"));
		assertNull(db.shortenRemoteBranchName("refs/remotes/nosuchremote/x"));

		assertNull(db.getRemoteName("refs/remotes/abranch"));
		assertNull(db.shortenRemoteBranchName("refs/remotes/abranch"));

		assertNull(db.getRemoteName("refs/heads/abranch"));
		assertNull(db.shortenRemoteBranchName("refs/heads/abranch"));
	}

	@Test
	public void testReadAllIncludingSymrefs() throws Exception {
		ObjectId masterId = db.resolve("refs/heads/master");
		RefUpdate updateRef = db.updateRef("refs/remotes/origin/master");
		updateRef.setNewObjectId(masterId);
		updateRef.setForceUpdate(true);
		updateRef.update();
		writeSymref("refs/remotes/origin/HEAD"
					"refs/remotes/origin/master");

		ObjectId r = db.resolve("refs/remotes/origin/HEAD");
		assertEquals(masterId

		List<Ref> allRefs = db.getRefDatabase().getRefs();
		Optional<Ref> refHEAD = allRefs.stream()
				.filter(ref -> ref.getName().equals("refs/remotes/origin/HEAD"))
				.findAny();
		assertTrue(refHEAD.isPresent());
		assertEquals(masterId
		assertFalse(refHEAD.get().isPeeled());
		assertNull(refHEAD.get().getPeeledObjectId());

		Optional<Ref> refmaster = allRefs.stream().filter(
				ref -> ref.getName().equals("refs/remotes/origin/master"))
				.findAny();
		assertTrue(refmaster.isPresent());
		assertEquals(masterId
		assertFalse(refmaster.get().isPeeled());
		assertNull(refmaster.get().getPeeledObjectId());
	}

	@Test
	public void testReadSymRefToPacked() throws IOException {
		writeSymref("HEAD"
		Ref ref = db.exactRef("HEAD");
		assertEquals(Ref.Storage.LOOSE
		assertTrue("is symref"
		ref = ref.getTarget();
		assertEquals("refs/heads/b"
		assertEquals(Ref.Storage.PACKED
	}

	@Test
	public void testReadSymRefToLoosePacked() throws IOException {
		ObjectId pid = db.resolve("refs/heads/master^");
		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(pid);
		updateRef.setForceUpdate(true);
		Result update = updateRef.update();
		assertEquals(Result.FORCED

		writeSymref("HEAD"
		Ref ref = db.exactRef("HEAD");
		assertEquals(Ref.Storage.LOOSE
		ref = ref.getTarget();
		assertEquals("refs/heads/master"
		assertEquals(Ref.Storage.LOOSE
	}

	@Test
	public void testReadLooseRef() throws IOException {
		RefUpdate updateRef = db.updateRef("ref/heads/new");
		updateRef.setNewObjectId(db.resolve("refs/heads/master"));
		Result update = updateRef.update();
		assertEquals(Result.NEW
		Ref ref = db.exactRef("ref/heads/new");
		assertEquals(Storage.LOOSE
	}

	@Test
	public void testGetShortRef() throws IOException {
		Ref ref = db.exactRef("refs/heads/master");
		assertEquals("refs/heads/master"
		assertEquals(db.resolve("refs/heads/master")
	}

	@Test
	public void testGetShortExactRef() throws IOException {
		assertNull(db.getRefDatabase().exactRef("master"));

		Ref ref = db.getRefDatabase().exactRef("HEAD");
		assertEquals("HEAD"
		assertEquals("refs/heads/master"
		assertEquals(db.resolve("refs/heads/master")
	}

	@Test
	public void testRefsUnderRefs() throws IOException {
		ObjectId masterId = db.resolve("refs/heads/master");
		writeNewRef("refs/heads/refs/foo/bar"

		assertNull(db.getRefDatabase().exactRef("refs/foo/bar"));

		Ref ref = db.findRef("refs/foo/bar");
		assertEquals("refs/heads/refs/foo/bar"
		assertEquals(db.resolve("refs/heads/master")
	}

	@Test
	public void testAmbiguousRefsUnderRefs() throws IOException {
		ObjectId masterId = db.resolve("refs/heads/master");
		writeNewRef("refs/foo/bar"
		writeNewRef("refs/heads/refs/foo/bar"

		Ref exactRef = db.getRefDatabase().exactRef("refs/foo/bar");
		assertEquals("refs/foo/bar"
		assertEquals(masterId

		Ref ref = db.findRef("refs/foo/bar");
		assertEquals("refs/foo/bar"
		assertEquals(masterId
	}

	@Test
	public void testReadLoosePackedRef() throws IOException
			InterruptedException {
		Ref ref = db.exactRef("refs/heads/master");
		assertEquals(Storage.PACKED
		try (FileOutputStream os = new FileOutputStream(
				new File(db.getDirectory()
			os.write(ref.getObjectId().name().getBytes(UTF_8));
			os.write('\n');
		}

		ref = db.exactRef("refs/heads/master");
		assertEquals(Storage.LOOSE
	}

	@Test
	public void testReadSimplePackedRefSameRepo() throws IOException {
		Ref ref = db.exactRef("refs/heads/master");
		ObjectId pid = db.resolve("refs/heads/master^");
		assertEquals(Storage.PACKED
		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(pid);
		updateRef.setForceUpdate(true);
		Result update = updateRef.update();
		assertEquals(Result.FORCED

		ref = db.exactRef("refs/heads/master");
		assertEquals(Storage.LOOSE
	}

	@Test
	public void testResolvedNamesBranch() throws IOException {
		Ref ref = db.findRef("a");
		assertEquals("refs/heads/a"
	}

	@Test
	public void testResolvedSymRef() throws IOException {
		Ref ref = db.exactRef(Constants.HEAD);
		assertEquals(Constants.HEAD
		assertTrue("is symbolic ref"
		assertSame(Ref.Storage.LOOSE

		Ref dst = ref.getTarget();
		assertNotNull("has target"
		assertEquals("refs/heads/master"

		assertSame(dst.getObjectId()
		assertSame(dst.getPeeledObjectId()
		assertEquals(dst.isPeeled()
	}

	private static void checkContainsRef(List<Ref> haystack
		for (Ref ref : haystack) {
			if (ref.getName().equals(needle.getName()) &&
					ref.getObjectId().equals(needle.getObjectId())) {
				return;
			}
		}
		fail("list " + haystack + " does not contain ref " + needle);
	}

	@Test
	public void testGetRefsByPrefix() throws IOException {
		List<Ref> refs = db.getRefDatabase().getRefsByPrefix("refs/heads/g");
		assertEquals(2
		checkContainsRef(refs
		checkContainsRef(refs

		refs = db.getRefDatabase().getRefsByPrefix("refs/heads/prefix/");
		assertEquals(1
		checkContainsRef(refs
	}

	@Test
	public void testGetRefsByPrefixes() throws IOException {
		List<Ref> refs = db.getRefDatabase().getRefsByPrefix();
		assertEquals(0

		refs = db.getRefDatabase().getRefsByPrefix("refs/heads/p"
				"refs/tags/A");
		assertEquals(3
		checkContainsRef(refs
		checkContainsRef(refs
		checkContainsRef(refs
	}
}
