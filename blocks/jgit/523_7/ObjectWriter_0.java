package org.eclipse.jgit.util;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.eclipse.jgit.junit.MockSystemReader;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;

public class ChangeIdUtilTest extends TestCase {

	private final String SOB1 = "Signed-off-by: J Author <ja@example.com>\n";

	private final String SOB2 = "Signed-off-by: J Committer <jc@example.com>\n";

	final PersonIdent p = new PersonIdent(
			"A U Thor <author@example.com> 1142878501 -0500");

	final PersonIdent q = new PersonIdent(
			"W Riter <writer@example.com> 1142878502 -0500");

	ObjectId treeId = ObjectId
			.fromString("f51de923607cd51cf872b928a6b523ba823f7f35");

	ObjectId treeId1 = ObjectId
			.fromString("4b825dc642cb6eb9a060e54bf8d69288fbee4904");

	final ObjectId treeId2 = ObjectId
			.fromString("617601c79811cbbae338512798318b4e5b70c9ac");

	ObjectId parentId = ObjectId
			.fromString("91fea719aaf9447feb9580477eb3dd08b62b5eca");

	ObjectId parentId1 = null;

	final ObjectId parentId2 = ObjectId
			.fromString("485c91e0600b165c301c278bfbae3e492413980c");

	MockSystemReader mockSystemReader = new MockSystemReader();

	final long when = mockSystemReader.getCurrentTime();

	final int tz = new MockSystemReader().getTimezone(when);

	PersonIdent author = new PersonIdent("J. Author"
	{
		author = new PersonIdent(author
	}

	PersonIdent committer = new PersonIdent("J. Committer"
	{
		committer = new PersonIdent(committer
	}

	public void testClean() {
		assertEquals("hej"
		assertEquals("hej\n\nsan"
		assertEquals("hej\nsan"
		assertEquals("hej\nsan"
		assertEquals("hej\nsan"
		assertEquals("hej\nsan"
				.clean("#no\nhej\nsan\nSigned-off-by: me \n#men"));
	}

	public void testId() throws IOException {
		String msg = "A\nMessage\n";
		ObjectId id = ChangeIdUtil.computeChangeId(treeId
		assertEquals("73f3751208ac92cbb76f9a26ac4a0d9d472e381b"
				.toString(id));
	}

	public void testHasChangeid() throws Exception {
		assertEquals(
				"has changeid\n\nBug: 33\nmore text\nSigned-off-by: me@you.too\nChange-Id: I0123456789012345678901234567890123456789\nAnd then some\n"
				call("has changeid\n\nBug: 33\nmore text\nSigned-off-by: me@you.too\nChange-Id: I0123456789012345678901234567890123456789\nAnd then some\n"));
	}

	public void testOneliner() throws Exception {
		assertEquals(
				"oneliner\n\nChange-Id: I3a98091ce4470de88d52ae317fcd297e2339f063\n"
				call("oneliner\n"));
	}

	public void testOnelinerFollowedByBlank() throws Exception {
		assertEquals(
				"oneliner followed by blank\n\nChange-Id: I3a12c21ef342a18498f95c62efbc186cd782b743\n"
				call("oneliner followed by blank\n"));
	}

	public void testATwoLines() throws Exception {
		assertEquals(
				"a two lines\nwith text withour break after subject line\n\nChange-Id: I549a0fed3d69b7876c54b4f5a35637135fd43fac\n"
				call("a two lines\nwith text withour break after subject line\n"));
	}

	public void testRegularCommit() throws Exception {
		assertEquals(
				"regular commit\n\nwith header and body\n\nChange-Id: I62d8749d3c3a888c11e3fadc3924220a19389766\n"
				call("regular commit\n\nwith header and body\n"));
	}

	public void testRegularCommitWithSob_ButNoBody() throws Exception {
		assertEquals(
				"regular commit with sob
				call("regular commit with sob
	}

	public void testACommitWithBug_SubButNoBody() throws Exception {
		assertEquals(
				"a commit with bug
				call("a commit with bug
	}

	public void testACommitWithSubject_NoBodySobAndBug() throws Exception {
		assertEquals(
				"a commit with subject
				call("a commit with subject
	}

	public void testACommitWithSubjectBug_NonFooterLineAndSob()
			throws Exception {
		assertEquals(
				"a commit with subject bug
				call("a commit with subject bug
	}

	public void testACommitWithSubject_NonFooterAndBugAndSob() throws Exception {
		assertEquals(
				"a commit with subject
				call("a commit with subject
	}

	public void testACommitWithSubjectBodyBugBrackersAndSob() throws Exception {
		assertEquals(
				"a commit with subject body
				call("a commit with subject body
	}

	public void testACommitWithSubjectBodyBugLineWithASpaceAndSob()
			throws Exception {
		assertEquals(
				"a commit with subject body
				call("a commit with subject body
	}

	public void testACommitWithSubjectBodyBugEmptyLineAndSob() throws Exception {
		assertEquals(
				"a commit with subject body
				call("a commit with subject body
	}

	public void testEmptyMessages() throws Exception {
		hookDoesNotModify("");
		hookDoesNotModify(" ");
		hookDoesNotModify("\n");
		hookDoesNotModify("\n\n");
		hookDoesNotModify("  \n  ");

		hookDoesNotModify("#");
		hookDoesNotModify("#\n");
		hookDoesNotModify("# on branch master\n# Untracked files:\n");
		hookDoesNotModify("\n# on branch master\n# Untracked files:\n");
		hookDoesNotModify("\n\n# on branch master\n# Untracked files:\n");

		hookDoesNotModify("\n# on branch master\ndiff --git a/src b/src\n"
				+ "new file mode 100644\nindex 0000000..c78b7f0\n");
	}

	public void testChangeIdAlreadySet() throws Exception {
				"Change-Id: Iaeac9b4149291060228ef0154db2985a31111335\n");
				"Change-Id: I388bdaf52ed05b55e62a22d0a20d2c1ae0d33e7e\n");
				"Change-Id: Id3bc5359d768a6400450283e12bdfb6cd135ea4b\n");
				"Change-Id: I1b55098b5a2cce0b3f3da783dda50d5f79f873fa\n");
				"Change-Id: I4f4e2e1e8568ddc1509baecb8c1270a1fb4b6da7\n");
	}

	public void testTimeAltersId() throws Exception {
				"Change-Id: I7fc3876fee63c766a2063df97fbe04a2dddd8d7c\n"
				call("a\n"));

		tick();
				"Change-Id: I3251906b99dda598a58a6346d8126237ee1ea800\n"
				call("a\n"));

		tick();
				"Change-Id: I69adf9208d828f41a3d7e41afbca63aff37c0c5c\n"
				call("a\n"));
	}

	protected void tick() {
		final long delta = TimeUnit.MILLISECONDS.convert(5 * 60
				TimeUnit.SECONDS);
		final long now = author.getWhen().getTime() + delta;

		author = new PersonIdent(author
		committer = new PersonIdent(committer
	}

	public void testFirstParentAltersId() throws Exception {
				"Change-Id: I7fc3876fee63c766a2063df97fbe04a2dddd8d7c\n"
				call("a\n"));

		parentId1 = parentId2;
				"Change-Id: I51e86482bde7f92028541aaf724d3a3f996e7ea2\n"
				call("a\n"));
	}

	public void testDirCacheAltersId() throws Exception {
				"Change-Id: I7fc3876fee63c766a2063df97fbe04a2dddd8d7c\n"
				call("a\n"));

		treeId1 = treeId2;
				"Change-Id: If56597ea9759f23b070677ea6f064c60c38da631\n"
				call("a\n"));
	}

	public void testSingleLineMessages() throws Exception {
				"Change-Id: I7fc3876fee63c766a2063df97fbe04a2dddd8d7c\n"
				call("a\n"));

				"Change-Id: I0f13d0e6c739ca3ae399a05a93792e80feb97f37\n"
				call("fix: this thing\n"));
				"Change-Id: I1a1a0c751e4273d532e4046a501a612b9b8a775e\n"
				call("fix-a-widget: this thing\n"));

				"Change-Id: If816d944c57d3893b60cf10c65931fead1290d97\n"
				call("FIX: this thing\n"));
				"Change-Id: I3e18d00cbda2ba1f73aeb63ed8c7d57d7fd16c76\n"
				call("Fix-A-Widget: this thing\n"));
	}

	public void testMultiLineMessagesWithoutFooter() throws Exception {
				"Change-Id: Id0b4f42d3d6fc1569595c9b97cb665e738486f5d\n"
				call("a\n" + "\n" + "b\n"));

				"Change-Id: I7d237b20058a0f46cc3f5fabc4a0476877289d75\n"
				call("a\n" + "\n" + "b\nc\nd\ne\n"));

				"Change-Id: I382e662f47bf164d6878b7fe61637873ab7fa4e8\n"
				call("a\n" + "\n" + "b\nc\nd\ne\n" + "\n" + "f\ng\nh\n"));
	}

	public void testSingleLineMessagesWithSignedOffBy() throws Exception {
				SOB1
				call("a\n" + "\n" + SOB1));

				SOB2
				call("a\n" + "\n" + SOB1 + SOB2));
	}

	public void testMultiLineMessagesWithSignedOffBy() throws Exception {
				SOB1
				call("a\n" + "\n" + "b\nc\nd\ne\n" + "\n" + "f\ng\nh\n" + "\n"
						+ SOB1));

				SOB2
						SOB2));

				SOB2
						SOB2));
	}

	public void testNoteInMiddle() throws Exception {
				"Change-Id: I988a127969a6ee5e58db546aab74fc46e66847f8\n"
						"does not fix it.\n"));
	}

	public void testKernelStyleFooter() throws Exception {
				SOB2
						SOB2));
	}

	public void testChangeIdAfterBugOrIssue() throws Exception {
				SOB1
						SOB1));

				SOB1
						SOB1));
	}

	public void notestCommitDashV() throws Exception {
				SOB2
						"index 0000000..c78b7f0\n"));
	}

	public void testWithEndingURL() throws Exception {
				"Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\n"
				"Change-Id: I62b9039e2fc0dce274af55e8f99312a8a80a805d\n"
				"Change-Id: I71b05dc1f6b9a5540a53a693e64d58b65a8910e8\n"
				"Change-Id: Id34e942baa68d790633737d815ddf11bac9183e5\n"
	}

	private void hookDoesNotModify(final String in) throws Exception {
		assertEquals(in
	}

	private String call(final String body) throws Exception {
		ObjectId computeChangeId = ChangeIdUtil.computeChangeId(treeId1
				parentId1
		if (computeChangeId == null)
			return body;
		return ChangeIdUtil.insertId(body
	}

}
