
package org.eclipse.jgit.lfs.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

public class LfsPointerFilterTest {

	private static final int SIZE = 12345;

	private static final String OID = "4d7a214614ab2935c943f9e0ff69d22eadbb8f32b1258daaa5e2ca24d17e2393";

	private static final String[] NOT_VALID_LFS_FILES = { ""
			"package org.eclipse.jgit;"
					+ "oid sha256:4d7a214614ab2935c943f9e0ff69d22eadbb8f32b1258daaa5e2ca24d17e2393\n"
					+ "oid sha256:4d7a214614ab2935c943f9e0ff69d22eadbb8f32b1258daaa5e2ca24d17e2393\n" };

	private static final String[] LFS_VERSION_DOMAINS = {
			"hawser"
	};

	private static final String[] VALID_LFS_FILES = {
					+ "oid sha256:" + OID + "\n"
					+ "size " + SIZE + "\n"
					+ "custom key with value\n"
					+ "oid sha256:" + OID + "\n"
					+ "size " + SIZE + "\n"
					+ "oid sha256:" + OID + "\n"
					+ "r.key key with .\n"
					+ "size " + SIZE + "\n"
					+ "oid sha256:" + OID + "\n"
					+ "size " + SIZE + "\n"
					+ "valid-name another valid key\n" };

	@Test
	public void testRegularFilesInRepositoryRoot() throws Exception {
		for (String file : NOT_VALID_LFS_FILES) {
			assertLfs("file.bin"
		}
	}

	@Test
	public void testNestedRegularFiles() throws Exception {
		for (String file : NOT_VALID_LFS_FILES) {
			assertLfs("a/file.bin"
		}
	}

	@Test
	public void testValidPointersInRepositoryRoot() throws Exception {
		for (String domain : LFS_VERSION_DOMAINS) {
			for (String file : VALID_LFS_FILES) {
				assertLfs("file.bin"
						.withRecursive(true).shouldBe(true)
					.check();
			}
		}
	}

	@Test
	public void testValidNestedPointers() throws Exception {
		for (String file : VALID_LFS_FILES) {
			assertLfs("a/file.bin"
					.check();
		}
	}

	@Test
	public void testValidNestedPointersWithoutRecurence() throws Exception {
		for (String file : VALID_LFS_FILES) {
			assertLfs("file.bin"
					.check();
			assertLfs("a/file.bin"
					.check();
		}
	}

	private static LfsTreeWalk assertLfs(String path
		return new LfsTreeWalk(path
	}

	private static class LfsTreeWalk {
		private final String path;

		private final String content;

		private boolean state;

		private boolean recursive;

		private TestRepository<InMemoryRepository> tr;

		LfsTreeWalk(String path
			this.path = path;
			this.content = content;
		}

		LfsTreeWalk withRecursive(boolean shouldBeRecursive) {
			this.recursive = shouldBeRecursive;
			return this;
		}

		LfsTreeWalk shouldBe(boolean shouldBeValid) {
			this.state = shouldBeValid;
			return this;
		}

		void check() throws Exception {
			tr = new TestRepository<>(new InMemoryRepository(
					new DfsRepositoryDescription("test")));
			RevCommit commit = tr.branch("master").commit().add(path
					.message("iniial commit").create();
			RevTree tree = parseCommit(commit);
			LfsPointerFilter filter = new LfsPointerFilter();
			try (TreeWalk treeWalk = new TreeWalk(tr.getRepository())) {
				treeWalk.addTree(tree);
				treeWalk.setRecursive(recursive);
				treeWalk.setFilter(filter);

				if (state) {
					assertTrue(treeWalk.next());
					assertEquals(path
					assertNotNull(filter.getPointer());
					assertEquals(SIZE
					assertEquals(OID
				} else {
					assertFalse(treeWalk.next());
					assertNull(filter.getPointer());
				}
			}
		}

		private RevTree parseCommit(RevCommit commit) throws Exception {
			try (ObjectWalk ow = new ObjectWalk(tr.getRepository())) {
				return ow.parseCommit(commit).getTree();
			}
		}
	}
}
