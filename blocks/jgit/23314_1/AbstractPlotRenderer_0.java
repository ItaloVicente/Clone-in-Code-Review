package org.eclipse.jgit.revplot;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand.FastForwardMode;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.junit.Before;
import org.junit.Test;

public class AbstractPlotRendererTest extends RepositoryTestCase {

	private Git git;
	private TestPlotRenderer plotRenderer;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		plotRenderer = new TestPlotRenderer();
	}

	@Test
	public void testDrawTextAlignment() throws Exception {
		git.commit().setMessage("initial commit").call();
		git.branchCreate().setName("topic").call();
		git.checkout().setName("topic").call();
		git.commit().setMessage("commit 1 on topic").call();
		git.commit().setMessage("commit 2 on topic").call();
		git.checkout().setName("master").call();
		git.commit().setMessage("commit on master").call();
		MergeResult mergeCall = merge(db.resolve("topic"));
		ObjectId start = mergeCall.getNewHead();
		PlotCommitList<PlotLane> commitList = createCommitList(start);

		for (int i = 0; i < commitList.size(); i++) {
			plotRenderer.paintCommit(commitList.get(i)
		}

		List<Integer> indentations = plotRenderer.indentations;
		assertEquals(indentations.get(2)
	}

	private PlotCommitList<PlotLane> createCommitList(ObjectId start)
			throws IOException {
		TestPlotWalk walk = new TestPlotWalk(db);
		walk.markStart(walk.parseCommit(start));
		PlotCommitList<PlotLane> commitList = new PlotCommitList<PlotLane>();
		commitList.source(walk);
		commitList.fillTo(1000);
		return commitList;
	}

	private MergeResult merge(ObjectId includeId) throws GitAPIException {
		return git.merge().setFastForward(FastForwardMode.NO_FF)
				.include(includeId).call();
	}

	private static class TestPlotWalk extends PlotWalk {
		public TestPlotWalk(Repository repo) {
			super(repo);
		}
	}

	private static class TestPlotRenderer extends
			AbstractPlotRenderer<PlotLane

		List<Integer> indentations = new LinkedList<Integer>();

		@Override
		protected int drawLabel(int x
			return 0;
		}

		@Override
		protected Object laneColor(PlotLane myLane) {
			return null;
		}

		@Override
		protected void drawLine(Object color
				int width) {
		}

		@Override
		protected void drawCommitDot(int x
		}

		@Override
		protected void drawBoundaryDot(int x
		}

		@Override
		protected void drawText(String msg
			indentations.add(Integer.valueOf(x));
		}
	}

}
