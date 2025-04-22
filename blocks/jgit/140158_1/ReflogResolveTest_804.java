
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

public class ReflogConfigTest extends RepositoryTestCase {
	@Test
	public void testlogAllRefUpdates() throws Exception {
		long commitTime = 1154236443000L;
		int tz = -4 * 60;

		assertEquals(0
		final FileBasedConfig cfg = db.getConfig();
		cfg.setBoolean("core"
		cfg.save();

		commit("A Commit\n"
		commitTime += 60 * 1000;
		assertTrue("Reflog for HEAD still contain no entry"

		cfg.setBoolean("core"
		cfg.save();
		assertTrue(cfg.get(CoreConfig.KEY).isLogAllRefUpdates());

		commit("A Commit\n"
		commitTime += 60 * 1000;
		assertTrue(
				"Reflog for HEAD should contain one entry"
				db.getReflogReader(Constants.HEAD).getReverseEntries().size() == 1);

		cfg.setBoolean("core"
		cfg.save();
		assertFalse(cfg.get(CoreConfig.KEY).isLogAllRefUpdates());

		commit("A Commit\n"
		assertTrue(
				"Reflog for HEAD should contain two entries"
				db.getReflogReader(Constants.HEAD).getReverseEntries().size() == 2);
	}

	private void commit(String commitMsg
			throws IOException {
		final CommitBuilder commit = new CommitBuilder();
		commit.setAuthor(new PersonIdent(author
		commit.setCommitter(new PersonIdent(committer
		commit.setMessage(commitMsg);
		ObjectId id;
		try (ObjectInserter inserter = db.newObjectInserter()) {
			commit.setTreeId(inserter.insert(new TreeFormatter()));
			id = inserter.insert(commit);
			inserter.flush();
		}

		int nl = commitMsg.indexOf('\n');
		final RefUpdate ru = db.updateRef(Constants.HEAD);
		ru.setNewObjectId(id);
		ru.setRefLogMessage("commit : "
				+ ((nl == -1) ? commitMsg : commitMsg.substring(0
		ru.forceUpdate();
	}
}
