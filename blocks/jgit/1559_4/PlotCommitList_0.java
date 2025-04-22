package org.eclipse.jgit.revplot;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalkTestCase;

public class PlotCommitListTest extends RevWalkTestCase {
	@SuppressWarnings("boxing")
	public void testLinar() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.createCommit(c.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		assertCommitListTopo(pcl
				new RevCommit[] { a }
	}

	@SuppressWarnings("boxing")
	public void testMerged() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d = commit(b

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.createCommit(d.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		assertCommitListTopo(pcl
				d
				c
				b
				a
	}

	@SuppressWarnings("boxing")
	public void testSideBranch() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.createCommit(b.getId()));
		pw.markStart(pw.createCommit(c.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		assertCommitListTopo(pcl
				c
				b
				a
	}

	@SuppressWarnings("boxing")
	public void test2SideBranches() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d = commit(a);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.createCommit(b.getId()));
		pw.markStart(pw.createCommit(c.getId()));
		pw.markStart(pw.createCommit(d.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		assertCommitListTopo(pcl
				d
				c
				b
				a
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
		pw.markStart(pw.createCommit(b.getId()));
		pw.markStart(pw.createCommit(c.getId()));
		pw.markStart(pw.createCommit(d.getId()));
		pw.markStart(pw.createCommit(e.getId()));
		pw.markStart(pw.createCommit(g.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		assertCommitListTopo(pcl
				g
				f
				e
				d
				c
				b
				a
	}

	@SuppressWarnings("boxing")
	public void assertCommitListTopo(PlotCommitList<PlotLane> p
			Object... commitAndPos) {
		assertEquals("wrong size of PlotCommitList"
				p.size());
		for (int i = 0; i < p.size(); i++) {
			PlotCommit<PlotLane> pc = p.get(i);
			assertEquals("wrong id for commit #" + i
					((RevCommit) commitAndPos[i * 3]).getId()
			assertEquals("wrong number of parents for commit #" + i
					((RevCommit[]) commitAndPos[i * 3 + 1]).length
					pc.getParentCount());
			for (int j = 0; j < pc.getParentCount(); j++) {
				assertEquals("wrong parent for commit #" + i
						((RevCommit[]) commitAndPos[i * 3 + 1])[j]
						pc.getParent(j));
			}
			assertEquals("wrong lane for commit #" + i
					commitAndPos[i * 3 + 2]
							.getLane().getPosition());
		}
	}
}
