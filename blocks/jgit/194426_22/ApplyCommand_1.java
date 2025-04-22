package org.eclipse.jgit.patch;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import org.eclipse.jgit.api.errors.PatchApplyException;
import org.eclipse.jgit.api.errors.PatchFormatException;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.patch.PatchApplier.ApplyPatchResult;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandFactory;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.IO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Suite;

@RunWith(Suite.class)
		PatchApplierTest.Text.class
		PatchApplierTest.Binary.class
})
public class PatchApplierTest {

	public abstract static class Base extends RepositoryTestCase {

		protected String name;
		protected RawText expectedText;
		protected RevTree baseTip;

		protected void init(boolean inCore
				final boolean postExists) throws Exception {
			this.name = name;
			if (postExists) {
				expectedText = new RawText(readFile(name + "_PostImage"));
			}
			if (inCore) {
				initInCore(preExists);
			} else {
				initWithWorkTree(preExists);
			}
		}

		protected void initWithWorkTree(final boolean preExists)
				throws Exception {
			try (Git git = new Git(db)) {
				if (preExists) {
					RawText originText = new RawText(readFile(name + "_PreImage"));
					write(new File(db.getDirectory().getParent()
							originText.getString(0

					git.add().addFilepattern(name).call();
					git.commit().setMessage("PreImage").call();
				}
			}
		}

		protected void initInCore(final boolean preExists) throws Exception {
			try (Git git = new Git(db)) {
				if (preExists) {
					RawText originText = new RawText(readFile(name + "_PreImage"));
					writeTrashFile(name
					git.add().addFilepattern(name).call();
				}
				RevCommit base = git.commit().setMessage("PreImage").call();
				baseTip = base.getTree();
				if (preExists) {
					git.rm().addFilepattern(name).call();
				}
			}
		}

		protected ApplyPatchResult applyPatch(boolean inCore)
				throws PatchApplyException
			if (inCore) {
				try (ObjectInserter oi = db.newObjectInserter()) {
					return new PatchApplier(db
							getTestResource(name + ".patch"));
				}
			}
			return new PatchApplier(db).applyPatch(getTestResource(name + ".patch"));
		}

		protected static byte[] readFile(String patchFile) throws IOException {
			final InputStream in = getTestResource(patchFile);
			if (in == null) {
				fail("No " + patchFile + " test vector");
			}
			try {
				final byte[] buf = new byte[1024];
				final ByteArrayOutputStream temp = new ByteArrayOutputStream();
				int n;
				while ((n = in.read(buf)) > 0) {
					temp.write(buf
				}
				return temp.toByteArray();
			} finally {
				in.close();
			}
		}

		protected static InputStream getTestResource(String patchFile) {
			return PatchApplierTest.class.getClassLoader()
					.getResourceAsStream("org/eclipse/jgit/diff/" + patchFile);
		}
	}

	@RunWith(Parameterized.class)
	public static class Text extends Base {

		@Parameter(0)
		public boolean inCore;

		@Parameters(name = "inCore={0}")
		public static Collection<Boolean> getUseAnnotatedTagsValues() {
			return Arrays.asList(new Boolean[]{Boolean.TRUE
		}

		private void init(final String name
				final boolean postExists) throws Exception {
			super.init(inCore
		}

		private ApplyPatchResult applyPatch()
				throws PatchApplyException
			return super.applyPatch(inCore);
		}

		private void verifyChange(ApplyPatchResult result
			assertEquals(1
			if (inCore) {
				verifyInCoreChange(result
			} else {
				verifyWorkTreeChange(result
			}
		}

		private void verifyWorkTreeChange(ApplyPatchResult result
			assertEquals(new File(db.getWorkTree()
					result.appliedFiles.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		private void verifyInCoreChange(ApplyPatchResult result
			checkInputStream(new ByteArrayInputStream(readBlob(result.appliedTree
					expectedText.getString(0
		}

		private RawText readBlob(ObjectId treeish
			try (TestRepository<?> tr = new TestRepository<>(db);
					RevWalk rw = tr.getRevWalk()) {
				db.incrementOpen();
				RevTree tree = rw.parseTree(treeish);
				RevObject obj = tr.get(tree
				if (obj == null) {
					return null;
				}
				return new RawText(
						rw.getObjectReader().open(obj
			}
		}

		@Test
		public void testCrLf() throws Exception {
			try {
				db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_AUTOCRLF
				init("crlf"

				ApplyPatchResult result = applyPatch();

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

				ApplyPatchResult result = applyPatch();

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

				ApplyPatchResult result = applyPatch();

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

				ApplyPatchResult result = applyPatch();

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

				ApplyPatchResult result = applyPatch();

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

				ApplyPatchResult result = applyPatch();

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

				ApplyPatchResult result = applyPatch();

				verifyChange(result
			} finally {
				config.unset(ConfigConstants.CONFIG_FILTER_SECTION
						"clean");
				config.unset(ConfigConstants.CONFIG_FILTER_SECTION
						"smudge");
			}
		}
	}

	public static class Binary extends Base {

		private void init(final String name
				final boolean postExists) throws Exception {
			super.init(false
		}

		private void init(final String name) throws Exception {
			init(name
		}

		private ApplyPatchResult applyPatch()
				throws PatchApplyException
			return super.applyPatch(false);
		}

		private void checkBinary(String name
				throws Exception {
			checkBinary(name
		}

		private void checkBinary(String name
				int numberOfFiles) throws Exception {
			try (Git git = new Git(db)) {
				byte[] post = IO
						.readWholeStream(getTestResource(name + "_PostImage")
						.array();
				File f = new File(db.getWorkTree()
				if (hasPreImage) {
					byte[] pre = IO
							.readWholeStream(getTestResource(name + "_PreImage")
							.array();
					Files.write(f.toPath()
					git.add().addFilepattern(name).call();
					git.commit().setMessage("PreImage").call();
				}
				ApplyPatchResult result = new PatchApplier(db).applyPatch(getTestResource(name + ".patch"));
				assertEquals(numberOfFiles
				assertEquals(f
				assertArrayEquals(post
			}
		}

		@Test
		public void testModifyM2() throws Exception {
			init("M2"

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			if (FS.DETECTED.supportsExecute()) {
				assertTrue(FS.DETECTED.canExecute(result.appliedFiles.get(0)));
			}
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testModifyM3() throws Exception {
			init("M3"

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			if (FS.DETECTED.supportsExecute()) {
				assertFalse(
						FS.DETECTED.canExecute(result.appliedFiles.get(0)));
			}
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testModifyX() throws Exception {
			init("X");

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testModifyY() throws Exception {
			init("Y");

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testModifyZ() throws Exception {
			init("Z");

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testModifyNL1() throws Exception {
			init("NL1");

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					.appliedFiles.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testNonASCII() throws Exception {
			init("NonASCII");

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.appliedFiles.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testNonASCII2() throws Exception {
			init("NonASCII2");

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.appliedFiles.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testNonASCIIAdd() throws Exception {
			init("NonASCIIAdd");

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.appliedFiles.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testNonASCIIAdd2() throws Exception {
			init("NonASCIIAdd2"

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.appliedFiles.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testNonASCIIDel() throws Exception {
			init("NonASCIIDel"

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.appliedFiles.get(0));
			assertFalse(new File(db.getWorkTree()
		}

		@Test
		public void testRenameNoHunks() throws Exception {
			init("RenameNoHunks"

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testRenameWithHunks() throws Exception {
			init("RenameWithHunks"

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testCopyWithHunks() throws Exception {
			init("CopyWithHunks"

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testShiftUp() throws Exception {
			init("ShiftUp");

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.appliedFiles.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testShiftUp2() throws Exception {
			init("ShiftUp2");

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.appliedFiles.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testShiftDown() throws Exception {
			init("ShiftDown");

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.appliedFiles.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}

		@Test
		public void testShiftDown2() throws Exception {
			init("ShiftDown2");

			ApplyPatchResult result = applyPatch();

			assertEquals(1
			assertEquals(new File(db.getWorkTree()
					result.appliedFiles.get(0));
			checkFile(new File(db.getWorkTree()
					expectedText.getString(0
		}
	}
}
