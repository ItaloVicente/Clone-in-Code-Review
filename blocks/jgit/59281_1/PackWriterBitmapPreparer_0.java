
package org.eclipse.jgit.internal.storage.pack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.internal.storage.file.GcTestCase;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndexBuilder;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.PackWriterBitmapPreparer;
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
    int[][] bitmapCounts = {
        {1
        {300
    };
    int currentCommits = 0;
    BranchBuilder bb = tr.branch("refs/heads/main");

    for (int[] counts : bitmapCounts) {
      int nextCommitCount = counts[0];
      int expectedBitmapCount = counts[1];
      for (int i = currentCommits; i < nextCommitCount; i++) {
        String str = "A" + i;
        bb.commit().message(str).add(str
      }
      currentCommits = nextCommitCount;

      gc.gc();
      assertEquals(currentCommits * 3
          gc.getStatistics().numberOfPackedObjects);
      assertEquals(
          currentCommits + " commits: "
    }
  }

  @Test
  public void testBitmapSpansWithMerges() throws Exception {
    List<Integer> merges = Arrays.asList(
        Integer.valueOf(55)
        {1
        {99
        {100
        {116
        {176
        {213
        {214
        {236
        {273
        {274
        {334
        {335
        {435
        {436
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
        }
      }
      currentCommits = nextCommitCount;

      gc.gc();
      assertEquals(
          currentCommits + " commits: "
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

    gc.gc();
    assertEquals(
        commitsForSparseBranch + commitsForFullBranch + commitsForShallowBranches
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
    PackWriterBitmapPreparer preparer = newPeparer(m9
    List<BitmapCommit> selection = new ArrayList<>(preparer.selectCommits(commits.size()));

    String[] expected = {m0.name()
        m9.name()
    assertEquals(expected.length
    for (int i = 0; i < expected.length; i++) {
      assertEquals("Entry " + i
    }
  }

  private RevCommit addCommit(BranchBuilder bb
    CommitBuilder commit = bb.commit().message(msg).add(msg
    for (RevCommit parent : parents) {
      commit.parent(parent);
    }
    return commit.create();
  }

  private PackWriterBitmapPreparer newPeparer(RevCommit want
      throws IOException {
    List<ObjectToPack> objects = new ArrayList<>(commits.size());
    for (RevCommit commit : commits) {
      objects.add(new ObjectToPack(commit
    }
    Set<ObjectId> wants = Collections.singleton((ObjectId) want);
    PackConfig config = new PackConfig();
    PackBitmapIndexBuilder builder = new PackBitmapIndexBuilder(objects);
    return new PackWriterBitmapPreparer(
        tr.getRepository().newObjectReader()
  }
}
