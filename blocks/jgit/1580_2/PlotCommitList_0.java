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
			assertEquals("Expected commit not found at pos#"+id.getName()
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
}
