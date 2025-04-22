package org.eclipse.jgit.merge;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.SampleDataRepositoryTestCase;
import org.eclipse.jgit.lib.Ref.Storage;

public class MergeMessageFormatterTest extends SampleDataRepositoryTestCase {

	private MergeMessageFormatter formatter;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		RefUpdate refUpdate = db.updateRef("refs/remotes/origin/remote-a");
		refUpdate.setNewObjectId(db.resolve("refs/heads/a"));
		refUpdate.update();
		refUpdate = db.updateRef("refs/remotes/origin/remote-b");
		refUpdate.setNewObjectId(db.resolve("refs/heads/b"));
		refUpdate.update();

		formatter = new MergeMessageFormatter();
	}

	public void testOneBranch() throws IOException {
		Ref a = db.getRef("refs/heads/a");
		Ref master = db.getRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(a)
		assertEquals("Merge branch 'a'"
	}

	public void testTwoBranches() throws IOException {
		Ref a = db.getRef("refs/heads/a");
		Ref b = db.getRef("refs/heads/b");
		Ref master = db.getRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(a
		assertEquals("Merge branches 'a' and 'b'"
	}

	public void testThreeBranches() throws IOException {
		Ref c = db.getRef("refs/heads/c");
		Ref b = db.getRef("refs/heads/b");
		Ref a = db.getRef("refs/heads/a");
		Ref master = db.getRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(c
		assertEquals("Merge branches 'c'
	}

	public void testRemoteBranch() throws Exception {
		Ref remoteA = db.getRef("refs/remotes/origin/remote-a");
		Ref master = db.getRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(remoteA)
		assertEquals("Merge remote branch 'origin/remote-a'"
	}

	public void testMixed() throws IOException {
		Ref c = db.getRef("refs/heads/c");
		Ref remoteA = db.getRef("refs/remotes/origin/remote-a");
		Ref master = db.getRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(c
		assertEquals("Merge branch 'c'
				message);
	}

	public void testTag() throws IOException {
		Ref tagA = db.getRef("refs/tags/A");
		Ref master = db.getRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(tagA)
		assertEquals("Merge tag 'A'"
	}

	public void testCommit() throws IOException {
		ObjectId objectId = ObjectId
				.fromString("6db9c2ebf75590eef973081736730a9ea169a0c4");
		Ref commit = new ObjectIdRef.Unpeeled(Storage.LOOSE
				objectId.getName()
		Ref master = db.getRef("refs/heads/master");
		String message = formatter.format(Arrays.asList(commit)
		assertEquals("Merge commit '6db9c2ebf75590eef973081736730a9ea169a0c4'"
				message);
	}

	public void testIntoOtherThanMaster() throws IOException {
		Ref a = db.getRef("refs/heads/a");
		Ref b = db.getRef("refs/heads/b");
		String message = formatter.format(Arrays.asList(a)
		assertEquals("Merge branch 'a' into b"
	}
}
