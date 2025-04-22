package org.eclipse.jgit.revplot;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalkTestCase;

public class PlotCommitListTest extends RevWalkTestCase {

	class CommitListAssert {
		private PlotCommitList<PlotLane> pcl;
		private PlotCommit<PlotLane> current;
		private int nextIndex = 0;

		CommitListAssert(PlotCommitList<PlotLane> pcl) {
			this.pcl = pcl;
		}

		public CommitListAssert commit(RevCommit id) {
			assertTrue("Unexpected end of list at pos#"+nextIndex
			current = pcl.get(nextIndex++);
			assertEquals("Expected commit not found at pos#" + (nextIndex - 1)
					id.getId()
			return this;
		}

		public CommitListAssert lanePos(int pos) {
			PlotLane lane = current.getLane();
			assertEquals("Position of lane of commit #" + (nextIndex - 1)
					+ " not as expected."
			return this;
		}

		public CommitListAssert parents(RevCommit... parents) {
			assertEquals("Number of parents of commit #" + (nextIndex - 1)
					+ " not as expected."
					current.getParentCount());
			for (int i = 0; i < parents.length; i++)
				assertEquals("Unexpected parent of commit #" + (nextIndex - 1)
						parents[i]
			return this;
		}

		public CommitListAssert noMoreCommits() {
			assertEquals("Unexpected size of list"
			return this;
		}
	}

	@SuppressWarnings("boxing")
	public void testLinear() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(c.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(c).lanePos(0).parents(b);
		test.commit(b).lanePos(0).parents(a);
		test.commit(a).lanePos(0).parents();
		test.noMoreCommits();
	}

	@SuppressWarnings("boxing")
	public void testMerged() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d = commit(b

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(d.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(d).lanePos(0).parents(b
		test.commit(c).lanePos(0).parents(a);
		test.commit(b).lanePos(1).parents(a);
		test.commit(a).lanePos(0).parents();
		test.noMoreCommits();
	}

	@SuppressWarnings("boxing")
	public void testSideBranch() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(b.getId()));
		pw.markStart(pw.lookupCommit(c.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(c).lanePos(0).parents(a);
		test.commit(b).lanePos(1).parents(a);
		test.commit(a).lanePos(0).parents();
		test.noMoreCommits();
	}

	@SuppressWarnings("boxing")
	public void test2SideBranches() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d = commit(a);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(b.getId()));
		pw.markStart(pw.lookupCommit(c.getId()));
		pw.markStart(pw.lookupCommit(d.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(d).lanePos(0).parents(a);
		test.commit(c).lanePos(1).parents(a);
		test.commit(b).lanePos(1).parents(a);
		test.commit(a).lanePos(0).parents();
		test.noMoreCommits();
	}

	@SuppressWarnings("boxing")
	public void testBug300282_1() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d = commit(a);
		final RevCommit e = commit(a);
		final RevCommit f = commit(a);
		final RevCommit g = commit(f);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(b.getId()));
		pw.markStart(pw.lookupCommit(c.getId()));
		pw.markStart(pw.lookupCommit(d.getId()));
		pw.markStart(pw.lookupCommit(e.getId()));
		pw.markStart(pw.lookupCommit(g.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(g).lanePos(0).parents(f);
		test.commit(f).lanePos(0).parents(a);
		test.commit(e).lanePos(1).parents(a);
		test.commit(d).lanePos(1).parents(a);
		test.commit(c).lanePos(1).parents(a);
		test.commit(b).lanePos(1).parents(a);
		test.commit(a).lanePos(0).parents();
		test.noMoreCommits();
	}

	public void testEgitHistory() throws Exception {
		final RevCommit merge_fix = commit();
		final RevCommit add_simple = commit(merge_fix);
		final RevCommit remove_unused = commit(merge_fix);
		final RevCommit merge_remove = commit(add_simple
		final RevCommit resolve_handler = commit(merge_fix);
		final RevCommit clear_repositorycache = commit(merge_remove);
		final RevCommit add_Maven = commit(clear_repositorycache);
		final RevCommit use_remote = commit(clear_repositorycache);
		final RevCommit findToolBar_layout = commit(clear_repositorycache);
		final RevCommit merge_add_Maven = commit(findToolBar_layout
		final RevCommit update_eclipse_iplog = commit(merge_add_Maven);
		final RevCommit changeset_implementation = commit(clear_repositorycache);
		final RevCommit merge_use_remote = commit(update_eclipse_iplog
				use_remote);
		final RevCommit disable_source = commit(merge_use_remote);
		final RevCommit update_eclipse_iplog2 = commit(merge_use_remote);
		final RevCommit merge_disable_source = commit(update_eclipse_iplog2
				disable_source);
		final RevCommit merge_changeset_implementation = commit(
				merge_disable_source
		final RevCommit clone_operation = commit(merge_disable_source
				merge_changeset_implementation);
		final RevCommit update_eclipse = commit(add_Maven);
		final RevCommit merge_resolve_handler = commit(clone_operation
				resolve_handler);
		final RevCommit disable_comment = commit(clone_operation);
		final RevCommit merge_disable_comment = commit(merge_resolve_handler
				disable_comment);
		final RevCommit fix_broken = commit(merge_disable_comment);
		final RevCommit add_a_clear = commit(fix_broken);
		final RevCommit merge_update_eclipse = commit(add_a_clear
				update_eclipse);
		final RevCommit sort_roots = commit(merge_update_eclipse);
		final RevCommit fix_logged_npe = commit(merge_changeset_implementation);
		final RevCommit merge_fixed_logged_npe = commit(sort_roots
				fix_logged_npe);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(merge_fixed_logged_npe.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		CommitListAssert test = new CommitListAssert(pcl);

		test.commit(merge_fixed_logged_npe).parents(sort_roots
				.lanePos(0);
		test.commit(fix_logged_npe).parents(merge_changeset_implementation)
				.lanePos(0);
		test.commit(sort_roots).parents(merge_update_eclipse).lanePos(1);
		test.commit(merge_update_eclipse).parents(add_a_clear
				.lanePos(1);
		test.commit(add_a_clear).parents(fix_broken).lanePos(1);
		test.commit(fix_broken).parents(merge_disable_comment).lanePos(1);
		test.commit(merge_disable_comment)
				.parents(merge_resolve_handler
		test.commit(disable_comment).parents(clone_operation).lanePos(1);
		test.commit(merge_resolve_handler)
				.parents(clone_operation
		test.commit(update_eclipse).parents(add_Maven).lanePos(3);
		test.commit(clone_operation)
				.parents(merge_disable_source
				.lanePos(1);
		test.commit(merge_changeset_implementation)
				.parents(merge_disable_source
				.lanePos(0);
		test.commit(merge_disable_source)
				.parents(update_eclipse_iplog2
		test.commit(update_eclipse_iplog2).parents(merge_use_remote).lanePos(0);
		test.commit(disable_source).parents(merge_use_remote).lanePos(1);
		test.commit(merge_use_remote).parents(update_eclipse_iplog
				.lanePos(0);
		test.commit(changeset_implementation).parents(clear_repositorycache)
				.lanePos(2);
		test.commit(update_eclipse_iplog).parents(merge_add_Maven).lanePos(0);
		test.commit(merge_add_Maven).parents(findToolBar_layout
				.lanePos(0);
		test.commit(findToolBar_layout).parents(clear_repositorycache)
				.lanePos(0);
		test.commit(use_remote).parents(clear_repositorycache).lanePos(1);
		test.commit(add_Maven).parents(clear_repositorycache).lanePos(3);
		test.commit(clear_repositorycache).parents(merge_remove).lanePos(2);
		test.commit(resolve_handler).parents(merge_fix).lanePos(4);
		test.commit(merge_remove).parents(add_simple
		test.commit(remove_unused).parents(merge_fix).lanePos(0);
		test.commit(add_simple).parents(merge_fix).lanePos(1);
		test.commit(merge_fix).parents().lanePos(3);
		test.noMoreCommits();
	}
}
