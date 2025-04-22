
package org.eclipse.jgit.internal.storage.pack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.internal.storage.file.GcTestCase;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndexBuilder;
import org.eclipse.jgit.internal.storage.pack.PackWriterBitmapPreparer.BitmapCommit;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.junit.TestRepository.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.junit.Test;

public class GcCommitSelectionTest extends GcTestCase {

	@Test
	public void testBitmapSpansNoMerges() throws Exception {
		testBitmapSpansNoMerges(false);
	}

	@Test
	public void testBitmapSpansNoMergesWithTags() throws Exception {
		testBitmapSpansNoMerges(true);
	}

	private void testBitmapSpansNoMerges(boolean withTags) throws Exception {
				{ 1
				{ 200
				{ 301
		int currentCommits = 0;
		BranchBuilder bb = tr.branch("refs/heads/main");

		for (int[] counts : bitmapCounts) {
			int nextCommitCount = counts[0];
			int expectedBitmapCount = counts[1];
			for (int i = currentCommits; i < nextCommitCount; i++) {
				String str = "A" + i;
				RevCommit rc = bb.commit().message(str).add(str
				if (withTags) {
					tr.lightweightTag(str
				}
			}
			currentCommits = nextCommitCount;

			gc.setExpireAgeMillis(0);
			gc.gc();
			assertEquals(currentCommits * 3
					gc.getStatistics().numberOfPackedObjects);
			assertEquals(currentCommits + " commits: "
					gc.getStatistics().numberOfBitmaps);
		}
	}

	@Test
	public void testBitmapSpansWithMerges() throws Exception {
		List<Integer> merges = Arrays.asList(Integer.valueOf(55)
				Integer.valueOf(115)
				Integer.valueOf(235));
				{ 1
				{ 99
				{ 100
				{ 116
				{ 176
				{ 213
				{ 214
				{ 236
				{ 273
				{ 274
				{ 334
				{ 335
				{ 435
				{ 436
		};

		int currentCommits = 0;
		BranchBuilder bb = tr.branch("refs/heads/main");

		for (int[] counts : bitmapCounts) {
			int nextCommitCount = counts[0];
			int expectedBitmapCount = counts[1];
			for (int i = currentCommits; i < nextCommitCount; i++) {
				String str = "A" + i;
				if (!merges.contains(Integer.valueOf(i))) {
					bb.commit().message(str).add(str
				} else {
					BranchBuilder bbN = tr.branch("refs/heads/A" + i);
					bb.commit().message(str).add(str
							.parent(bbN.commit().create()).create();
				}
			}
			currentCommits = nextCommitCount;

			gc.setExpireAgeMillis(0);
			gc.gc();
			assertEquals(currentCommits + " commits: "
					gc.getStatistics().numberOfBitmaps);
		}
	}

	@Test
	public void testBitmapsForExcessiveBranches() throws Exception {
		int oneDayInSeconds = 60 * 60 * 24;

		BranchBuilder bbA = tr.branch("refs/heads/A");
		for (int i = 0; i < 1001; i++) {
			String msg = "A" + i;
			bbA.commit().message(msg).add(msg
		}
		tr.tick(oneDayInSeconds * 90);
		BranchBuilder bbB = tr.branch("refs/heads/B");
		for (int i = 0; i < 1001; i++) {
			String msg = "B" + i;
			bbB.commit().message(msg).add(msg
		}
		for (int i = 0; i < 100; i++) {
			BranchBuilder bb = tr.branch("refs/heads/N" + i);
			String msg = "singlecommit" + i;
			bb.commit().message(msg).add(msg
		}
		tr.tick(oneDayInSeconds);

		final int commitsForSparseBranch = 1 + (1001 / 200);
		final int commitsForFullBranch = 100 + (901 / 200);
		final int commitsForShallowBranches = 100;

		gc.setExpireAgeMillis(0);
		gc.gc();
		assertEquals(
				commitsForSparseBranch + commitsForFullBranch
						+ commitsForShallowBranches
				gc.getStatistics().numberOfBitmaps);
	}

	@Test
	public void testSelectionOrderingWithChains() throws Exception {
		BranchBuilder bb = tr.branch("refs/heads/main");
		RevCommit m0 = addCommit(bb
		RevCommit m1 = addCommit(bb
		RevCommit m2 = addCommit(bb
		RevCommit b3 = addCommit(bb
		RevCommit m4 = addCommit(bb
		RevCommit b5 = addCommit(bb
		RevCommit m6 = addCommit(bb
		RevCommit b7 = addCommit(bb
		RevCommit m8 = addCommit(bb
		RevCommit m9 = addCommit(bb

		List<RevCommit> commits = Arrays.asList(m0
				m8
		PackWriterBitmapPreparer preparer = newPreparer(
				Collections.singleton(m9)
		List<BitmapCommit> selection = new ArrayList<>(
				preparer.selectCommits(commits.size()

		String[] expected = { m0.name()
				m6.name()
				b7.name() };
		assertEquals(expected.length
		for (int i = 0; i < expected.length; i++) {
			assertEquals("Entry " + i
		}
	}

	private RevCommit addCommit(BranchBuilder bb
			RevCommit... parents) throws Exception {
		CommitBuilder commit = bb.commit().message(msg).add(msg
				.noParents();
		for (RevCommit parent : parents) {
			commit.parent(parent);
		}
		return commit.create();
	}

	@Test
	public void testDistributionOnMultipleBranches() throws Exception {
		BranchBuilder[] branches = { tr.branch("refs/heads/main")
				tr.branch("refs/heads/a")
				tr.branch("refs/heads/c") };
		RevCommit[] tips = new RevCommit[branches.length];
		List<RevCommit> commits = createHistory(branches
		PackConfig config = new PackConfig();
		config.setBitmapContiguousCommitCount(1);
		config.setBitmapRecentCommitSpan(5);
		config.setBitmapDistantCommitSpan(20);
		config.setBitmapRecentCommitCount(100);
		Set<RevCommit> wants = new HashSet<>(Arrays.asList(tips));
		PackWriterBitmapPreparer preparer = newPreparer(wants
		List<BitmapCommit> selection = new ArrayList<>(
				preparer.selectCommits(commits.size()
		Set<ObjectId> selected = new HashSet<>();
		for (BitmapCommit c : selection) {
			selected.add(c.toObjectId());
		}

		for (RevCommit c : wants) {
			assertTrue(selected.contains(c.toObjectId()));
			int count = 1;
			int selectedCount = 1;
			RevCommit parent = c;
			while (parent.getParentCount() != 0) {
				parent = parent.getParent(0);
				count++;
				if (selected.contains(parent.toObjectId())) {
					selectedCount++;
				}
			}
			int expectedCount = 10 + (count - 100 - 24) / 25;
			assertTrue(expectedCount <= selectedCount);
		}
	}

	private List<RevCommit> createHistory(BranchBuilder[] branches
			RevCommit[] tips) throws Exception {
		List<RevCommit> commits = new ArrayList<>();
		String[] prefixes = { "m"
		int branchCount = branches.length;
		tips[0] = addCommit(commits
		int counter = 0;

		for (int b = 0; b < branchCount; b++) {
			for (int i = 0; i < 100; i++
				for (int j = 0; j <= b; j++) {
					tips[j] = addCommit(commits
							prefixes[j] + counter);
				}
			}
			if (b < branchCount - 1) {
				tips[b + 1] = addCommit(branches[b + 1]
						"branch_" + prefixes[b + 1]
			}
		}
		return commits;
	}

	private RevCommit addCommit(List<RevCommit> commits
			String msg
		CommitBuilder commit = bb.commit().message(msg).add(msg
		if (parents.length > 0) {
			commit.noParents();
			for (RevCommit parent : parents) {
				commit.parent(parent);
			}
		}
		RevCommit c = commit.create();
		commits.add(c);
		return c;
	}

	private PackWriterBitmapPreparer newPreparer(Set<RevCommit> wants
			List<RevCommit> commits
		List<ObjectToPack> objects = new ArrayList<>(commits.size());
		for (RevCommit commit : commits) {
			objects.add(new ObjectToPack(commit
		}
		PackBitmapIndexBuilder builder = new PackBitmapIndexBuilder(objects);
		return new PackWriterBitmapPreparer(
				tr.getRepository().newObjectReader()
				NullProgressMonitor.INSTANCE
	}
}
