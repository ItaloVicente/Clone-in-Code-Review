
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.eclipse.jgit.junit.TestRepository.CommitBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.Merger;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.junit.Test;

public class GcBranchPrunedTest extends GcTestCase {

	@Test
	public void branch_historyNotPruned() throws Exception {
		RevCommit tip = commitChain(10);
		tr.branch("b").update(tip);
		gc.setExpireAgeMillis(0);
		fsTick();
		gc.prune(Collections.<ObjectId> emptySet());
		do {
			assertTrue(repo.getObjectDatabase().has(tip));
			tr.parseBody(tip);
			RevTree t = tip.getTree();
			assertTrue(repo.getObjectDatabase().has(t));
			assertTrue(repo.getObjectDatabase().has(tr.get(t
			tip = tip.getParentCount() > 0 ? tip.getParent(0) : null;
		} while (tip != null);
	}

	@Test
	public void deleteBranch_historyPruned() throws Exception {
		RevCommit tip = commitChain(10);
		tr.branch("b").update(tip);
		RefUpdate update = repo.updateRef("refs/heads/b");
		update.setForceUpdate(true);
		update.delete();
		gc.setExpireAgeMillis(0);
		fsTick();
		gc.prune(Collections.<ObjectId> emptySet());
		assertTrue(gc.getStatistics().numberOfLooseObjects == 0);
	}

	@Test
	public void deleteMergedBranch_historyNotPruned() throws Exception {
		RevCommit parent = tr.commit().create();
		RevCommit b1Tip = tr.branch("b1").commit().parent(parent).add("x"
				.create();
		RevCommit b2Tip = tr.branch("b2").commit().parent(parent).add("y"
				.create();

		Merger merger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(repo);
		merger.merge(b1Tip
		CommitBuilder cb = tr.commit();
		cb.parent(b1Tip).parent(b2Tip);
		cb.setTopLevelTree(merger.getResultTreeId());
		RevCommit mergeCommit = cb.create();
		RefUpdate u = repo.updateRef("refs/heads/b1");
		u.setNewObjectId(mergeCommit);
		u.update();

		RefUpdate update = repo.updateRef("refs/heads/b2");
		update.setForceUpdate(true);
		update.delete();

		gc.setExpireAgeMillis(0);
		fsTick();
		gc.prune(Collections.<ObjectId> emptySet());
		assertTrue(repo.getObjectDatabase().has(b2Tip));
	}
}
