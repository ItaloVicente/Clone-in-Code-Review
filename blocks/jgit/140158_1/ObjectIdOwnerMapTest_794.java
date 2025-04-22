
package org.eclipse.jgit.lib;

import static java.lang.Integer.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.junit.JGitTestUtil.concat;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.OBJ_BAD;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.OBJ_TAG;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;
import static org.eclipse.jgit.lib.Constants.encode;
import static org.eclipse.jgit.lib.Constants.encodeASCII;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.DUPLICATE_ENTRIES;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.EMPTY_NAME;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.FULL_PATHNAME;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.HAS_DOT;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.HAS_DOTDOT;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.HAS_DOTGIT;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.NULL_SHA1;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.TREE_NOT_SORTED;
import static org.eclipse.jgit.lib.ObjectChecker.ErrorType.ZERO_PADDED_FILEMODE;
import static org.eclipse.jgit.util.RawParseUtils.decode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.text.MessageFormat;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ObjectCheckerTest {
	private static final ObjectChecker SECRET_KEY_CHECKER = new ObjectChecker() {
		@Override
		public void checkBlob(byte[] raw) throws CorruptObjectException {
			String in = decode(raw);
			if (in.contains("secret_key")) {
				throw new CorruptObjectException("don't add a secret key");
			}
		}
	};

	private static final ObjectChecker SECRET_KEY_BLOB_CHECKER = new ObjectChecker() {
		@Override
		public BlobObjectChecker newBlobObjectChecker() {
			return new BlobObjectChecker() {
				private boolean containSecretKey;

				@Override
				public void update(byte[] in
					String str = decode(in
					if (str.contains("secret_key")) {
						containSecretKey = true;
					}
				}

				@Override
				public void endBlob(AnyObjectId id)
						throws CorruptObjectException {
					if (containSecretKey) {
						throw new CorruptObjectException(
								"don't add a secret key");
					}
				}
			};
		}
	};

	private ObjectChecker checker;

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		checker = new ObjectChecker();
	}

	@Test
	public void testInvalidType() {
		String msg = MessageFormat.format(
				JGitText.get().corruptObjectInvalidType2
				valueOf(OBJ_BAD));
		assertCorrupt(msg
	}

	@Test
	public void testCheckBlob() throws CorruptObjectException {
		checker.checkBlob(new byte[0]);
		checker.checkBlob(new byte[1]);

		checker.check(OBJ_BLOB
		checker.check(OBJ_BLOB
	}

	@Test
	public void testCheckBlobNotCorrupt() throws CorruptObjectException {
		SECRET_KEY_CHECKER.check(OBJ_BLOB
	}

	@Test
	public void testCheckBlobCorrupt() throws CorruptObjectException {
		thrown.expect(CorruptObjectException.class);
		SECRET_KEY_CHECKER.check(OBJ_BLOB
	}

	@Test
	public void testCheckBlobWithBlobObjectCheckerNotCorrupt()
			throws CorruptObjectException {
		SECRET_KEY_BLOB_CHECKER.check(OBJ_BLOB
				encodeASCII("key = \"public_key\""));
	}

	@Test
	public void testCheckBlobWithBlobObjectCheckerCorrupt()
			throws CorruptObjectException {
		thrown.expect(CorruptObjectException.class);
		SECRET_KEY_BLOB_CHECKER.check(OBJ_BLOB
				encodeASCII("key = \"secret_key\""));
	}

	@Test
	public void testValidCommitNoParent() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author A. U. Thor <author@localhost> 1 +0000\n");
		b.append("committer A. U. Thor <author@localhost> 1 +0000\n");

		byte[] data = encodeASCII(b.toString());
		checker.checkCommit(data);
		checker.check(OBJ_COMMIT
	}

	@Test
	public void testValidCommitBlankAuthor() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author <> 0 +0000\n");
		b.append("committer <> 0 +0000\n");

		byte[] data = encodeASCII(b.toString());
		checker.checkCommit(data);
		checker.check(OBJ_COMMIT
	}

	@Test
	public void testCommitCorruptAuthor() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree be9bfa841874ccc9f2ef7c48d0c76226f89b7189\n");
		b.append("author b <b@c> <b@c> 0 +0000\n");
		b.append("committer <> 0 +0000\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("bad date"
		checker.setAllowInvalidPersonIdent(true);
		checker.checkCommit(data);

		checker.setAllowInvalidPersonIdent(false);
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testCommitCorruptCommitter() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree be9bfa841874ccc9f2ef7c48d0c76226f89b7189\n");
		b.append("author <> 0 +0000\n");
		b.append("committer b <b@c> <b@c> 0 +0000\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("bad date"
		checker.setAllowInvalidPersonIdent(true);
		checker.checkCommit(data);

		checker.setAllowInvalidPersonIdent(false);
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testValidCommit1Parent() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("parent ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author A. U. Thor <author@localhost> 1 +0000\n");
		b.append("committer A. U. Thor <author@localhost> 1 +0000\n");

		byte[] data = encodeASCII(b.toString());
		checker.checkCommit(data);
		checker.check(OBJ_COMMIT
	}

	@Test
	public void testValidCommit2Parent() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("parent ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("parent ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author A. U. Thor <author@localhost> 1 +0000\n");
		b.append("committer A. U. Thor <author@localhost> 1 +0000\n");

		byte[] data = encodeASCII(b.toString());
		checker.checkCommit(data);
		checker.check(OBJ_COMMIT
	}

	@Test
	public void testValidCommit128Parent() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		for (int i = 0; i < 128; i++) {
			b.append("parent ");
			b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
			b.append('\n');
		}

		b.append("author A. U. Thor <author@localhost> 1 +0000\n");
		b.append("committer A. U. Thor <author@localhost> 1 +0000\n");

		byte[] data = encodeASCII(b.toString());
		checker.checkCommit(data);
		checker.check(OBJ_COMMIT
	}

	@Test
	public void testValidCommitNormalTime() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		String when = "1222757360 -0730";

		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');

		b.append("author A. U. Thor <author@localhost> " + when + "\n");
		b.append("committer A. U. Thor <author@localhost> " + when + "\n");

		byte[] data = encodeASCII(b.toString());
		checker.checkCommit(data);
		checker.check(OBJ_COMMIT
	}

	@Test
	public void testInvalidCommitNoTree1() {
		StringBuilder b = new StringBuilder();
		b.append("parent ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		assertCorrupt("no tree header"
	}

	@Test
	public void testInvalidCommitNoTree2() {
		StringBuilder b = new StringBuilder();
		b.append("trie ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		assertCorrupt("no tree header"
	}

	@Test
	public void testInvalidCommitNoTree3() {
		StringBuilder b = new StringBuilder();
		b.append("tree");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		assertCorrupt("no tree header"
	}

	@Test
	public void testInvalidCommitNoTree4() {
		StringBuilder b = new StringBuilder();
		b.append("tree\t");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		assertCorrupt("no tree header"
	}

	@Test
	public void testInvalidCommitInvalidTree1() {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("zzzzfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		assertCorrupt("invalid tree"
	}

	@Test
	public void testInvalidCommitInvalidTree2() {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append("z\n");
		assertCorrupt("invalid tree"
	}

	@Test
	public void testInvalidCommitInvalidTree3() {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9b");
		b.append("\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid tree"
	}

	@Test
	public void testInvalidCommitInvalidTree4() {
		StringBuilder b = new StringBuilder();
		b.append("tree  ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		assertCorrupt("invalid tree"
	}

	@Test
	public void testInvalidCommitInvalidParent1() {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("parent ");
		b.append("\n");
		assertCorrupt("invalid parent"
	}

	@Test
	public void testInvalidCommitInvalidParent2() {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("parent ");
		b.append("zzzzfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append("\n");
		assertCorrupt("invalid parent"
	}

	@Test
	public void testInvalidCommitInvalidParent3() {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("parent  ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append("\n");
		assertCorrupt("invalid parent"
	}

	@Test
	public void testInvalidCommitInvalidParent4() {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("parent  ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append("z\n");
		assertCorrupt("invalid parent"
	}

	@Test
	public void testInvalidCommitInvalidParent5() {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("parent\t");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append("\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("no author"
	}

	@Test
	public void testInvalidCommitNoAuthor() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("committer A. U. Thor <author@localhost> 1 +0000\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("no author"
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testInvalidCommitNoCommitter1() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("author A. U. Thor <author@localhost> 1 +0000\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("no committer"
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testInvalidCommitNoCommitter2() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("author A. U. Thor <author@localhost> 1 +0000\n");
		b.append("\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("no committer"
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testInvalidCommitInvalidAuthor1()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("author A. U. Thor <foo 1 +0000\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("bad email"
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testInvalidCommitInvalidAuthor2()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("author A. U. Thor foo> 1 +0000\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("missing email"
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testInvalidCommitInvalidAuthor3()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("author 1 +0000\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("missing email"
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testInvalidCommitInvalidAuthor4()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("author a <b> +0000\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("bad date"
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testInvalidCommitInvalidAuthor5()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("author a <b>\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("bad date"
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testInvalidCommitInvalidAuthor6()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("author a <b> z");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("bad date"
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testInvalidCommitInvalidAuthor7()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("author a <b> 1 z");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("bad time zone"
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testInvalidCommitInvalidCommitter()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("author a <b> 1 +0000\n");
		b.append("committer a <");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("bad email"
		assertSkipListAccepts(OBJ_COMMIT
	}

	@Test
	public void testValidTag() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("type commit\n");
		b.append("tag test-tag\n");
		b.append("tagger A. U. Thor <author@localhost> 1 +0000\n");

		byte[] data = encodeASCII(b.toString());
		checker.checkTag(data);
		checker.check(OBJ_TAG
	}

	@Test
	public void testInvalidTagNoObject1() {
		assertCorrupt("no object header"
	}

	@Test
	public void testInvalidTagNoObject2() {
		StringBuilder b = new StringBuilder();
		b.append("object\t");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		assertCorrupt("no object header"
	}

	@Test
	public void testInvalidTagNoObject3() {
		StringBuilder b = new StringBuilder();
		b.append("obejct ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		assertCorrupt("no object header"
	}

	@Test
	public void testInvalidTagNoObject4() {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("zz9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		assertCorrupt("invalid object"
	}

	@Test
	public void testInvalidTagNoObject5() {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append(" \n");
		assertCorrupt("invalid object"
	}

	@Test
	public void testInvalidTagNoObject6() {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9");
		assertCorrupt("invalid object"
	}

	@Test
	public void testInvalidTagNoType1() {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		assertCorrupt("no type header"
	}

	@Test
	public void testInvalidTagNoType2() {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("type\tcommit\n");
		assertCorrupt("no type header"
	}

	@Test
	public void testInvalidTagNoType3() {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("tpye commit\n");
		assertCorrupt("no type header"
	}

	@Test
	public void testInvalidTagNoType4() {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("type commit");
		assertCorrupt("no tag header"
	}

	@Test
	public void testInvalidTagNoTagHeader1() {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("type commit\n");
		assertCorrupt("no tag header"
	}

	@Test
	public void testInvalidTagNoTagHeader2() {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("type commit\n");
		b.append("tag\tfoo\n");
		assertCorrupt("no tag header"
	}

	@Test
	public void testInvalidTagNoTagHeader3() {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("type commit\n");
		b.append("tga foo\n");
		assertCorrupt("no tag header"
	}

	@Test
	public void testValidTagHasNoTaggerHeader() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("type commit\n");
		b.append("tag foo\n");
		checker.checkTag(encodeASCII(b.toString()));
	}

	@Test
	public void testInvalidTagInvalidTaggerHeader1()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("type commit\n");
		b.append("tag foo\n");
		b.append("tagger \n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("missing email"
		checker.setAllowInvalidPersonIdent(true);
		checker.checkTag(data);

		checker.setAllowInvalidPersonIdent(false);
		assertSkipListAccepts(OBJ_TAG
	}

	@Test
	public void testInvalidTagInvalidTaggerHeader3()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		b.append("object ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("type commit\n");
		b.append("tag foo\n");
		b.append("tagger a < 1 +000\n");

		byte[] data = encodeASCII(b.toString());
		assertCorrupt("bad email"
		assertSkipListAccepts(OBJ_TAG
	}

	@Test
	public void testValidEmptyTree() throws CorruptObjectException {
		checker.checkTree(new byte[0]);
		checker.check(OBJ_TREE
	}

	@Test
	public void testValidTree1() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTree2() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTree3() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTree4() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTree5() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTree6() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTreeWithGitmodules() throws CorruptObjectException {
		ObjectId treeId = ObjectId
				.fromString("0123012301230123012301230123012301230123");
		StringBuilder b = new StringBuilder();
		ObjectId blobId = entry(b

		byte[] data = encodeASCII(b.toString());
		checker.checkTree(treeId
		assertEquals(1
		assertEquals(treeId
		assertEquals(blobId
	}

	@Test
	public void testNTFSGitmodules() throws CorruptObjectException {
		for (String gitmodules : new String[] {
			".GITMODULES"
			".gitmodules"
			".Gitmodules"
			".gitmoduleS"
			"gitmod~1"
			"GITMOD~1"
			"gitmod~4"
			"GI7EBA~1"
			"gi7eba~9"
			"GI7EB~10"
			"GI7E~123"
			"~1000000"
			"~9999999"
		}) {
			checker.setSafeForWindows(true);
			ObjectId treeId = ObjectId
					.fromString("0123012301230123012301230123012301230123");
			StringBuilder b = new StringBuilder();
			ObjectId blobId = entry(b

			byte[] data = encodeASCII(b.toString());
			checker.checkTree(treeId
			assertEquals(1
			assertEquals(treeId
			assertEquals(blobId
		}
	}

	@Test
	public void testNotGitmodules() throws CorruptObjectException {
		for (String notGitmodules : new String[] {
			".gitmodu"
			".gitmodules oh never mind"
		}) {
			checker.setSafeForWindows(true);
			ObjectId treeId = ObjectId
					.fromString("0123012301230123012301230123012301230123");
			StringBuilder b = new StringBuilder();
			entry(b

			byte[] data = encodeASCII(b.toString());
			checker.checkTree(treeId
			assertEquals(0
		}
	}


	@Test
	public void testValidTreeWithGitmodulesUppercase()
			throws CorruptObjectException {
		ObjectId treeId = ObjectId
				.fromString("0123012301230123012301230123012301230123");
		StringBuilder b = new StringBuilder();
		ObjectId blobId = entry(b

		byte[] data = encodeASCII(b.toString());
		checker.setSafeForWindows(true);
		checker.checkTree(treeId
		assertEquals(1
		assertEquals(treeId
		assertEquals(blobId
	}

	@Test
	public void testTreeWithInvalidGitmodules() throws CorruptObjectException {
		ObjectId treeId = ObjectId
				.fromString("0123012301230123012301230123012301230123");
		StringBuilder b = new StringBuilder();
		entry(b

		byte[] data = encodeASCII(b.toString());
		checker.checkTree(treeId
		checker.setSafeForWindows(true);
		assertEquals(0
	}

	@Test
	public void testNullSha1InTreeEntry() throws CorruptObjectException {
		byte[] data = concat(
				encodeASCII("100644 A")
				new byte[OBJECT_ID_LENGTH]);
		assertCorrupt("entry points to null SHA-1"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(NULL_SHA1
		checker.checkTree(data);
	}

	@Test
	public void testValidPosixTree() throws CorruptObjectException {
		checkOneName("a<b>c:d|e");
		checkOneName("test ");
		checkOneName("test.");
		checkOneName("NUL");
	}

	@Test
	public void testValidTreeSorting1() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTreeSorting2() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTreeSorting3() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTreeSorting4() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTreeSorting5() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTreeSorting6() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTreeSorting7() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testValidTreeSorting8() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testAcceptTreeModeWithZero() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		checker.setAllowLeadingZeroFileMode(true);
		checker.checkTree(data);

		checker.setAllowLeadingZeroFileMode(false);
		assertSkipListAccepts(OBJ_TREE

		checker.setIgnore(ZERO_PADDED_FILEMODE
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeModeStartsWithZero1() {
		StringBuilder b = new StringBuilder();
		entry(b
		assertCorrupt("mode starts with '0'"
	}

	@Test
	public void testInvalidTreeModeStartsWithZero2() {
		StringBuilder b = new StringBuilder();
		entry(b
		assertCorrupt("mode starts with '0'"
	}

	@Test
	public void testInvalidTreeModeStartsWithZero3() {
		StringBuilder b = new StringBuilder();
		entry(b
		assertCorrupt("mode starts with '0'"
	}

	@Test
	public void testInvalidTreeModeNotOctal1() {
		StringBuilder b = new StringBuilder();
		entry(b
		assertCorrupt("invalid mode character"
	}

	@Test
	public void testInvalidTreeModeNotOctal2() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid mode character"
		assertSkipListRejects("invalid mode character"
	}

	@Test
	public void testInvalidTreeModeNotSupportedMode1() {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid mode 1"
		assertSkipListRejects("invalid mode 1"
	}

	@Test
	public void testInvalidTreeModeNotSupportedMode2() {
		StringBuilder b = new StringBuilder();
		entry(b
		assertCorrupt("invalid mode " + 0170000
	}

	@Test
	public void testInvalidTreeModeMissingName() {
		StringBuilder b = new StringBuilder();
		b.append("100644");
		assertCorrupt("truncated in mode"
	}

	@Test
	public void testInvalidTreeNameContainsSlash()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("name contains '/'"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(FULL_PATHNAME
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsEmpty() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("zero length name"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(EMPTY_NAME
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDot() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid name '.'"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOT
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotDot() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid name '..'"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTDOT
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsGit() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid name '.git'"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTGIT
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsMixedCaseGit()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid name '.GiT'"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTGIT
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsMacHFSGit() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encode(b.toString());

		checker.checkTree(data);

		checker.setSafeForMacOS(true);
		assertCorrupt(
				"invalid name '.gi\u200Ct' contains ignorable Unicode characters"
				OBJ_TREE
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTGIT
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsMacHFSGit2()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encode(b.toString());

		checker.checkTree(data);

		checker.setSafeForMacOS(true);
		assertCorrupt(
				"invalid name '\u206B.git' contains ignorable Unicode characters"
				OBJ_TREE
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTGIT
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsMacHFSGit3()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encode(b.toString());

		checker.checkTree(data);

		checker.setSafeForMacOS(true);
		assertCorrupt(
				"invalid name '.git\uFEFF' contains ignorable Unicode characters"
				OBJ_TREE
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTGIT
		checker.checkTree(data);
	}



	@Test
	public void testInvalidTreeNameIsMacHFSGitCorruptUTF8AtEnd()
			throws CorruptObjectException {
		byte[] data = concat(encode("100644 .git")
				new byte[] { (byte) 0xef });
		StringBuilder b = new StringBuilder();
		entry(b
		data = concat(data

		checker.checkTree(data);

		checker.setSafeForMacOS(true);
		assertCorrupt(
				"invalid name contains byte sequence '0xef' which is not a valid UTF-8 character"
				OBJ_TREE
		assertSkipListAccepts(OBJ_TREE
	}

	@Test
	public void testInvalidTreeNameIsMacHFSGitCorruptUTF8AtEnd2()
			throws CorruptObjectException {
		byte[] data = concat(encode("100644 .git")
				new byte[] {
				(byte) 0xe2
		StringBuilder b = new StringBuilder();
		entry(b
		data = concat(data

		checker.checkTree(data);

		checker.setSafeForMacOS(true);
		assertCorrupt(
				"invalid name contains byte sequence '0xe2ab' which is not a valid UTF-8 character"
				OBJ_TREE
		assertSkipListAccepts(OBJ_TREE
	}

	@Test
	public void testInvalidTreeNameIsNotMacHFSGit()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encode(b.toString());
		checker.setSafeForMacOS(true);
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsNotMacHFSGit2()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encode(b.toString());
		checker.setSafeForMacOS(true);
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsNotMacHFSGitOtherPlatform()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encode(b.toString());
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotGitDot() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid name '.git.'"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTGIT
		checker.checkTree(data);
	}

	@Test
	public void testValidTreeNameIsDotGitDotDot()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	@Test
	public void testInvalidTreeNameIsDotGitSpace()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid name '.git '"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTGIT
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotGitSomething()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotGitSomethingSpaceSomething()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotGitSomethingDot()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotGitSomethingDotDot()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotGitDotSpace()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid name '.git. '"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTGIT
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsDotGitSpaceDot()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid name '.git . '"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTGIT
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsGITTilde1() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid name 'GIT~1'"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTGIT
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeNameIsGiTTilde1() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("invalid name 'GiT~1'"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(HAS_DOTGIT
		checker.checkTree(data);
	}

	@Test
	public void testValidTreeNameIsGitTilde11() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		byte[] data = encodeASCII(b.toString());
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeTruncatedInName() {
		StringBuilder b = new StringBuilder();
		b.append("100644 b");
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("truncated in name"
		assertSkipListRejects("truncated in name"
	}

	@Test
	public void testInvalidTreeTruncatedInObjectId() {
		StringBuilder b = new StringBuilder();
		b.append("100644 b\0\1\2");
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("truncated in object id"
		assertSkipListRejects("truncated in object id"
	}

	@Test
	public void testInvalidTreeBadSorting1() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = encodeASCII(b.toString());

		assertCorrupt("incorrectly sorted"

		ObjectId id = idFor(OBJ_TREE
		try {
			checker.check(id
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException e) {
			assertSame(TREE_NOT_SORTED
			assertEquals("treeNotSorted: object " + id.name()
					+ ": incorrectly sorted"
		}

		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(TREE_NOT_SORTED
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeBadSorting2() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("incorrectly sorted"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(TREE_NOT_SORTED
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeBadSorting3() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("incorrectly sorted"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(TREE_NOT_SORTED
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeDuplicateNames1_File()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("duplicate entry names"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(DUPLICATE_ENTRIES
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeDuplicateNames1_Tree()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("duplicate entry names"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(DUPLICATE_ENTRIES
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeDuplicateNames2() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("duplicate entry names"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(DUPLICATE_ENTRIES
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeDuplicateNames3() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("duplicate entry names"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(DUPLICATE_ENTRIES
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeDuplicateNames4() throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		entry(b
		entry(b
		entry(b
		entry(b
		byte[] data = encodeASCII(b.toString());
		assertCorrupt("duplicate entry names"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(DUPLICATE_ENTRIES
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeDuplicateNames5()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = b.toString().getBytes(UTF_8);
		checker.setSafeForWindows(true);
		assertCorrupt("duplicate entry names"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(DUPLICATE_ENTRIES
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeDuplicateNames6()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = b.toString().getBytes(UTF_8);
		checker.setSafeForMacOS(true);
		assertCorrupt("duplicate entry names"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(DUPLICATE_ENTRIES
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeDuplicateNames7()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		entry(b
		byte[] data = b.toString().getBytes(UTF_8);
		checker.setSafeForMacOS(true);
		assertCorrupt("duplicate entry names"
		assertSkipListAccepts(OBJ_TREE
		checker.setIgnore(DUPLICATE_ENTRIES
		checker.checkTree(data);
	}

	@Test
	public void testInvalidTreeDuplicateNames8()
			throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		checker.setSafeForMacOS(true);
		checker.checkTree(b.toString().getBytes(UTF_8));
	}

	@Test
	public void testRejectNulInPathSegment() {
		try {
			checker.checkPathSegment(encodeASCII("a\u0000b")
			fail("incorrectly accepted NUL in middle of name");
		} catch (CorruptObjectException e) {
			assertEquals("name contains byte 0x00"
		}
	}

	@Test
	public void testRejectSpaceAtEndOnWindows() {
		checker.setSafeForWindows(true);
		try {
			checkOneName("test ");
			fail("incorrectly accepted space at end");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name ends with ' '"
		}
	}

	@Test
	public void testBug477090() throws CorruptObjectException {
		checker.setSafeForMacOS(true);
		final byte[] bytes = {
				(byte) 0xe2
				0x2e
		checker.checkPathSegment(bytes
	}

	@Test
	public void testRejectDotAtEndOnWindows() {
		checker.setSafeForWindows(true);
		try {
			checkOneName("test.");
			fail("incorrectly accepted dot at end");
		} catch (CorruptObjectException e) {
			assertEquals("invalid name ends with '.'"
		}
	}

	@Test
	public void testRejectDevicesOnWindows() {
		checker.setSafeForWindows(true);

		String[] bad = { "CON"
				"COM4"
				"LPT3"
		for (String b : bad) {
			try {
				checkOneName(b);
				fail("incorrectly accepted " + b);
			} catch (CorruptObjectException e) {
				assertEquals("invalid name '" + b + "'"
			}
			try {
				checkOneName(b + ".txt");
				fail("incorrectly accepted " + b + ".txt");
			} catch (CorruptObjectException e) {
				assertEquals("invalid name '" + b + "'"
			}
		}
	}

	@Test
	public void testRejectInvalidWindowsCharacters() {
		checker.setSafeForWindows(true);
		rejectName('<');
		rejectName('>');
		rejectName(':');
		rejectName('"');
		rejectName('\\');
		rejectName('|');
		rejectName('?');
		rejectName('*');

		for (int i = 1; i <= 31; i++)
			rejectName((byte) i);
	}

	private void rejectName(char c) {
		try {
			checkOneName("te" + c + "st");
			fail("incorrectly accepted with " + c);
		} catch (CorruptObjectException e) {

			assertEquals("char '" + c + "' not allowed in Windows filename"
		}
	}

	private void rejectName(byte c) {
		String h = Integer.toHexString(c);
		try {
			checkOneName("te" + ((char) c) + "st");
			fail("incorrectly accepted with 0x" + h);
		} catch (CorruptObjectException e) {
			assertEquals("byte 0x" + h + " not allowed in Windows filename"
		}
	}


	@Test
	public void testRejectInvalidCharacter() {
		try {
			checkOneName("te/st");
			fail("incorrectly accepted with /");
		} catch (CorruptObjectException e) {

			assertEquals("name contains '/'"
		}
	}

	private void checkOneName(String name) throws CorruptObjectException {
		StringBuilder b = new StringBuilder();
		entry(b
		checker.checkTree(encodeASCII(b.toString()));
	}

	private static ObjectId entry(StringBuilder b
		byte[] id = new byte[OBJECT_ID_LENGTH];

		b.append(modeName);
		b.append('\0');
		for (int i = 0; i < OBJECT_ID_LENGTH; i++) {
			b.append((char) i);
			id[i] = (byte) i;
		}

		return ObjectId.fromRaw(id);
	}

	private void assertCorrupt(String msg
		assertCorrupt(msg
	}

	private void assertCorrupt(String msg
		try {
			checker.check(type
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException e) {
			assertEquals(msg
		}
	}

	private void assertSkipListAccepts(int type
			throws CorruptObjectException {
		ObjectId id = idFor(type
		checker.setSkipList(set(id));
		checker.check(id
		checker.setSkipList(null);
	}

	private void assertSkipListRejects(String msg
		ObjectId id = idFor(type
		checker.setSkipList(set(id));
		try {
			checker.check(id
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException e) {
			assertEquals(msg
		}
		checker.setSkipList(null);
	}

	private static ObjectIdSet set(ObjectId... ids) {
		return new ObjectIdSet() {
			@Override
			public boolean contains(AnyObjectId objectId) {
				for (ObjectId id : ids) {
					if (id.equals(objectId)) {
						return true;
					}
				}
				return false;
			}
		};
	}

	@SuppressWarnings("resource")
	private static ObjectId idFor(int type
		return new ObjectInserter.Formatter().idFor(type
	}
}
