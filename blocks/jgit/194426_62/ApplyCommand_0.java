
package org.eclipse.jgit.patch;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.PatchApplyException;
import org.eclipse.jgit.api.errors.PatchFormatException;
import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandFactory;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.patch.PatchApplier.Result;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.IO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
 		PatchApplierTest.WithWorktree. class
		PatchApplierTest.InCore.class
})
public class PatchApplierTest {

	public abstract static class Base extends RepositoryTestCase {

		protected String name;

		protected byte[] preImage;
		protected byte[] postImage;

		protected String expectedText;
		protected RevTree baseTip;
		public boolean inCore;

		Base(boolean inCore) {
			this.inCore = inCore;
		}

		protected void init(final String name
				final boolean postExists) throws Exception {
			this.name = name;
			if (postExists) {
				postImage = IO
						.readWholeStream(getTestResource(name + "_PostImage")
						.array();
				expectedText = new String(postImage
			}

			File f = new File(db.getWorkTree()
			if (preExists) {
				preImage = IO
						.readWholeStream(getTestResource(name + "_PreImage")
						.array();
				try (Git git = new Git(db)) {
					Files.write(f.toPath()
					git.add().addFilepattern(name).call();
				}
			}
			try (Git git = new Git(db)) {
				RevCommit base = git.commit().setMessage("PreImage").call();
				baseTip = base.getTree();
			}
		}

		void init(final String name) throws Exception {
			init(name
		}

		protected Result applyPatch()
				throws PatchApplyException
			InputStream patchStream = getTestResource(name + ".patch");
			if (inCore) {
				try (ObjectInserter oi = db.newObjectInserter()) {
					return new PatchApplier(db
				}
			}
			return new PatchApplier(db).applyPatch(patchStream);
		}

		protected static InputStream getTestResource(String patchFile) {
			return PatchApplierTest.class.getClassLoader()
					.getResourceAsStream("org/eclipse/jgit/diff/" + patchFile);
		}
		void verifyChange(Result result
			verifyChange(result
		}

		protected void verifyContent(Result result
			if (inCore) {
				byte[] output = readBlob(result.getTreeId()
				if (!exists)
					assertNull(output);
				else {
					assertNotNull(output);
					assertEquals(new String(output
				}
			} else {
				File f = new File(db.getWorkTree()
				if (!exists)
					assertFalse(f.exists());
				else
					checkFile(f
			}
		}

		void verifyChange(Result result
			assertEquals(1
			verifyContent(result
		}

		protected byte[] readBlob(ObjectId treeish
			try (TestRepository<?> tr = new TestRepository<>(db);
					RevWalk rw = tr.getRevWalk()) {
				db.incrementOpen();
				RevTree tree = rw.parseTree(treeish);
				try (TreeWalk tw = TreeWalk.forPath(db
					if (tw == null) {
						return null;
					}
					return tw.getObjectReader().open(tw.getObjectId(0)
				}
			}
		}

		protected void checkBinary(Result result
			assertEquals(numberOfFiles
			if (inCore) {
				assertArrayEquals(postImage
			} else {
				File f = new File(db.getWorkTree()
				assertArrayEquals(postImage
			}
		}


		@Test
		public void testBinaryDelta() throws Exception {
			init("delta");
			checkBinary(applyPatch()
		}

		@Test
		public void testBinaryLiteral() throws Exception {
			init("literal");
			checkBinary(applyPatch()
		}

		@Test
		public void testBinaryLiteralAdd() throws Exception {
			init("literal_add"
			checkBinary(applyPatch()
		}

		@Test
		public void testModifyM2() throws Exception {
			init("M2"

			Result result = applyPatch();

			if (!inCore && FS.DETECTED.supportsExecute()) {
				assertEquals(1
				File f = new File(db.getWorkTree()
				assertTrue(FS.DETECTED.canExecute(f));
			}

			verifyChange(result
		}

		@Test
		public void testModifyM3() throws Exception {
			init("M3"

			Result result = applyPatch();

			verifyChange(result
			if (!inCore && FS.DETECTED.supportsExecute()) {
				File f = new File(db.getWorkTree()
				assertFalse(FS.DETECTED.canExecute(f));
			}
		}

		@Test
		public void testModifyX() throws Exception {
			init("X");

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testModifyY() throws Exception {
			init("Y");

			Result result = applyPatch();

			verifyChange(result
		}

		@Test
		public void testModifyZ() throws Exception {
			init("Z");

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNonASCII() throws Exception {
			init("NonASCII");

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNonASCII2() throws Exception {
			init("NonASCII2");

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNonASCIIAdd() throws Exception {
			init("NonASCIIAdd");

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNonASCIIAdd2() throws Exception {
			init("NonASCIIAdd2"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testNonASCIIDel() throws Exception {
			init("NonASCIIDel"

			Result result = applyPatch();
			verifyChange(result
			assertEquals("NonASCIIDel"
		}

		@Test
		public void testRenameNoHunks() throws Exception {
			init("RenameNoHunks"

			Result result = applyPatch();

			assertEquals(2
			assertTrue(result.getPaths().contains("RenameNoHunks"));
			assertTrue(result.getPaths().contains("nested/subdir/Renamed"));

			verifyContent(result
		}

		@Test
		public void testRenameWithHunks() throws Exception {
			init("RenameWithHunks"

			Result result = applyPatch();
			assertEquals(2
			assertTrue(result.getPaths().contains("RenameWithHunks"));
			assertTrue(result.getPaths().contains("nested/subdir/Renamed"));

			verifyContent(result
		}

		@Test
		public void testCopyWithHunks() throws Exception {
			init("CopyWithHunks"

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testShiftUp() throws Exception {
			init("ShiftUp");

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testShiftUp2() throws Exception {
			init("ShiftUp2");

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testShiftDown() throws Exception {
			init("ShiftDown");

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testShiftDown2() throws Exception {
			init("ShiftDown2");

			Result result = applyPatch();
			verifyChange(result
		}
	}

	public static class InCore extends Base {

		public InCore() {
			super(true);
		}
	}

	public static class WithWorktree extends Base {
		public WithWorktree() { super(false); }

		@Test
		public void testModifyNL1() throws Exception {
			init("NL1");

			Result result = applyPatch();
			verifyChange(result
		}

		@Test
		public void testCrLf() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF
				init("crlf"

				Result result = applyPatch();

				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testCrLfOff() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF
				init("crlf"

				Result result = applyPatch();

				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testCrLfEmptyCommitted() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF
				init("crlf3"

				Result result = applyPatch();

				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testCrLfNewFile() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF
				init("crlf4"

				Result result = applyPatch();

				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testPatchWithCrLf() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF
				init("crlf2"

				Result result = applyPatch();

				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		@Test
		public void testPatchWithCrLf2() throws Exception {
			String name = "crlf2";
			try (Git git = new Git(db)) {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF
				init(name
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF

				Result result = applyPatch();

				verifyChange(result
			} finally {
				db.getConfig().unset(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF);
			}
		}

		private static class ReplaceFilter extends FilterCommand {

			private final char toReplace;

			private final char replacement;

			ReplaceFilter(InputStream in
					char replacement) {
				super(in
				this.toReplace = toReplace;
				this.replacement = replacement;
			}

			@Override
			public int run() throws IOException {
				int b = in.read();
				if (b < 0) {
					in.close();
					out.close();
					return -1;
				}
				if ((b & 0xFF) == toReplace) {
					b = replacement;
				}
				out.write(b);
				return 1;
			}
		}

		@Test
		public void testFiltering() throws Exception {
			FilterCommandFactory clean = (repo
			FilterCommandFactory smudge = (repo
			Config config = db.getConfig();
			try (Git git = new Git(db)) {
				config.setString(ConfigConstants.CONFIG_FILTER_SECTION
						"clean"
				config.setString(ConfigConstants.CONFIG_FILTER_SECTION
						"smudge"
				write(new File(db.getWorkTree()
						"smudgetest filter=a2e");
				git.add().addFilepattern(".gitattributes").call();
				git.commit().setMessage("Attributes").call();
				init("smudgetest"

				Result result = applyPatch();

				verifyChange(result
			} finally {
				config.unset(ConfigConstants.CONFIG_FILTER_SECTION
						"clean");
				config.unset(ConfigConstants.CONFIG_FILTER_SECTION
						"smudge");
			}
		}
	}
}
