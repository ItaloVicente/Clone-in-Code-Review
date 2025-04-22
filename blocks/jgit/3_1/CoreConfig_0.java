
package org.eclipse.jgit.lib;

import java.io.IOException;

public class ReflogConfigTest extends RepositoryTestCase {
	public void testlogAllRefUpdates() throws Exception {
		long commitTime = 1154236443000L;
		int tz = -4 * 60;
		int nrOfReflogs;

		assertTrue(db.getConfig().getCore().isLogAllRefUpdates());
		nrOfReflogs = db.getReflogReader(Constants.HEAD).getReverseEntries()
				.size();
		db.getConfig().setBoolean("core"

		final Tree t = new Tree(db);
		addFileToTree(t
		commit(t
				new PersonIdent(jcommitter
		commitTime += 100;
		assertTrue(
				"Reflog for HEAD should contain one entry"
				db.getReflogReader(Constants.HEAD).getReverseEntries().size() == nrOfReflogs);

		db.getConfig().setBoolean("core"
		assertTrue(db.getConfig().getCore().isLogAllRefUpdates());

		addFileToTree(t
		commit(t
				new PersonIdent(jcommitter
		commitTime += 100;
		assertTrue(
				"Reflog for HEAD should contain one additional entry"
				db.getReflogReader(Constants.HEAD).getReverseEntries().size() == nrOfReflogs + 1);

		db.getConfig().setBoolean("core"
		assertFalse(db.getConfig().getCore().isLogAllRefUpdates());

		addFileToTree(t
				"and this is other other data in me\n");
		commit(t
				new PersonIdent(jcommitter
		assertTrue(
				"Reflog for HEAD should contain two additional entries"
				db.getReflogReader(Constants.HEAD).getReverseEntries().size() == nrOfReflogs + 2);
	}

	private void addFileToTree(final Tree t
			throws IOException {
		FileTreeEntry f = t.addFile(filename);
		writeTrashFile(f.getName()
		t.accept(new WriteTree(trash
	}

	private void commit(final Tree t
			PersonIdent committer) throws IOException {
		final Commit commit = new Commit(db);
		commit.setAuthor(author);
		commit.setCommitter(committer);
		commit.setMessage(commitMsg);
		commit.setTree(t);
		ObjectWriter writer = new ObjectWriter(db);
		commit.setCommitId(writer.writeCommit(commit));

		int nl = commitMsg.indexOf('\n');
		final RefUpdate ru = db.updateRef(Constants.HEAD);
		ru.setNewObjectId(commit.getCommitId());
		ru.setRefLogMessage("commit : "
				+ ((nl == -1) ? commitMsg : commitMsg.substring(0
		ru.forceUpdate();
	}
}
