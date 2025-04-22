package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class MergeMessageFormatterTest extends SampleDataRepositoryTestCase {

	private MergeMessageFormatter formatter;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		RefUpdate createRemoteRefA = db
				.updateRef("refs/remotes/origin/remote-a");
		createRemoteRefA.setNewObjectId(db.resolve("refs/heads/a"));
		createRemoteRefA.update();

		RefUpdate createRemoteRefB = db
				.updateRef("refs/remotes/origin/remote-b");
		createRemoteRefB.setNewObjectId(db.resolve("refs/heads/b"));
		createRemoteRefB.update();

		formatter = new MergeMessageFormatter();
	}

	@Test
	public void testOneBranch() throws IOException {
		Ref a = db.exactRef("refs/heads/a");
		Ref master = db.exactRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(a)
		assertEquals("Merge branch 'a'"
	}

	@Test
	public void testTwoBranches() throws IOException {
		Ref a = db.exactRef("refs/heads/a");
		Ref b = db.exactRef("refs/heads/b");
		Ref master = db.exactRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(a
		assertEquals("Merge branches 'a' and 'b'"
	}

	@Test
	public void testThreeBranches() throws IOException {
		Ref c = db.exactRef("refs/heads/c");
		Ref b = db.exactRef("refs/heads/b");
		Ref a = db.exactRef("refs/heads/a");
		Ref master = db.exactRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(c
		assertEquals("Merge branches 'c'
	}

	@Test
	public void testRemoteBranch() throws Exception {
		Ref remoteA = db.exactRef("refs/remotes/origin/remote-a");
		Ref master = db.exactRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(remoteA)
		assertEquals("Merge remote-tracking branch 'origin/remote-a'"
	}

	@Test
	public void testMixed() throws IOException {
		Ref c = db.exactRef("refs/heads/c");
		Ref remoteA = db.exactRef("refs/remotes/origin/remote-a");
		Ref master = db.exactRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(c
		assertEquals("Merge branch 'c'
				message);
	}

	@Test
	public void testTag() throws IOException {
		Ref tagA = db.exactRef("refs/tags/A");
		Ref master = db.exactRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(tagA)
		assertEquals("Merge tag 'A'"
	}

	@Test
	public void testCommit() throws IOException {
		ObjectId objectId = ObjectId
				.fromString("6db9c2ebf75590eef973081736730a9ea169a0c4");
		Ref commit = new ObjectIdRef.Unpeeled(Storage.LOOSE
				objectId.getName()
		Ref master = db.exactRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(commit)
		assertEquals("Merge commit '6db9c2ebf75590eef973081736730a9ea169a0c4'"
				message);
	}

	@Test
	public void testPullWithUri() throws IOException {
		ObjectId objectId = ObjectId
				.fromString("6db9c2ebf75590eef973081736730a9ea169a0c4");
		Ref remoteBranch = new ObjectIdRef.Unpeeled(Storage.LOOSE
				objectId);
		Ref master = db.exactRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(remoteBranch)
				message);
	}

	@Test
	public void testIntoOtherThanMaster() throws IOException {
		Ref a = db.exactRef("refs/heads/a");
		Ref b = db.exactRef("refs/heads/b");
		String message = formatter.format(Arrays.asList(a)
		assertEquals("Merge branch 'a' into b"
	}

	@Test
	public void testIntoHeadOtherThanMaster() throws IOException {
		Ref a = db.exactRef("refs/heads/a");
		Ref b = db.exactRef("refs/heads/b");
		SymbolicRef head = new SymbolicRef("HEAD"
		String message = formatter.format(Arrays.asList(a)
		assertEquals("Merge branch 'a' into b"
	}

	@Test
	public void testIntoSymbolicRefHeadPointingToMaster() throws IOException {
		Ref a = db.exactRef("refs/heads/a");
		Ref master = db.exactRef("refs/heads/master");
		SymbolicRef head = new SymbolicRef("HEAD"
		String message = formatter.format(Arrays.asList(a)
		assertEquals("Merge branch 'a'"
	}

	@Test
	public void testFormatWithConflictsNoFooter() {
		String originalMessage = "Header Line\n\nCommit body\n";
		String message = formatter.formatWithConflicts(originalMessage
				Arrays.asList(new String[] { "path1" }));
		assertEquals("Header Line\n\nCommit body\n\nConflicts:\n\tpath1\n"
				message);
	}

	@Test
	public void testFormatWithConflictsNoFooterNoLineBreak() {
		String originalMessage = "Header Line\n\nCommit body";
		String message = formatter.formatWithConflicts(originalMessage
				Arrays.asList(new String[] { "path1" }));
		assertEquals("Header Line\n\nCommit body\n\nConflicts:\n\tpath1\n"
				message);
	}

	@Test
	public void testFormatWithConflictsWithFooters() {
		String originalMessage = "Header Line\n\nCommit body\n\nChangeId:"
				+ " I123456789123456789123456789123456789\nBug:1234567\n";
		String message = formatter.formatWithConflicts(originalMessage
				Arrays.asList(new String[] { "path1" }));
		assertEquals(
				"Header Line\n\nCommit body\n\nConflicts:\n\tpath1\n\n"
						+ "ChangeId: I123456789123456789123456789123456789\nBug:1234567\n"
				message);
	}

	@Test
	public void testFormatWithConflictsWithFooterlikeLineInBody() {
		String originalMessage = "Header Line\n\nCommit body\nBug:1234567\nMore Body\n\nChangeId:"
				+ " I123456789123456789123456789123456789\nBug:1234567\n";
		String message = formatter.formatWithConflicts(originalMessage
				Arrays.asList(new String[] { "path1" }));
		assertEquals(
				"Header Line\n\nCommit body\nBug:1234567\nMore Body\n\nConflicts:\n\tpath1\n\n"
						+ "ChangeId: I123456789123456789123456789123456789\nBug:1234567\n"
				message);
	}
}
