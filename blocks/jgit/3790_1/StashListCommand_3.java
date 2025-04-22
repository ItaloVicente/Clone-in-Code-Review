package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.merge.MergeMessageFormatter;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ResolveMerger;
import org.eclipse.jgit.merge.ThreeWayMerger;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.FileTreeIterator;

public class StashCreateCommand extends GitCommand<RevCommit> {
	private List<ObjectId> parents = new LinkedList<ObjectId>();

	private PersonIdent author = new PersonIdent("Abhishek Bhatnagar"
			"abhatnag@redhat.com");

	private PersonIdent committer = new PersonIdent("Test User"
			"test@redhat.com");

	private MergeStrategy mergeStrategy = MergeStrategy.RESOLVE;

	protected StashCreateCommand(Repository repo) {
		super(repo);
	}

	public RevCommit call() throws Exception {

		ObjectId headCommitId = repo.resolve(Constants.HEAD + "^{commit}");
		System.out.println("Head commit id: " + headCommitId);
		if (headCommitId == null) {
			RevCommit previousCommit = new RevWalk(repo)
					.parseCommit(headCommitId);
			RevCommit[] p = previousCommit.getParents();
			for (int i = 0; i < p.length; i++)
				parents.add(0
		} else {
			parents.add(0
		}

		DirCache index = repo.lockDirCache();

		try {
			ObjectInserter odi = repo.newObjectInserter();
			try {
				ObjectId indexTreeId = index.writeTree(odi);
				System.out.println("indexTreeId: " + indexTreeId);

				DirCacheBuilder b = index.builder();
				DirCacheEntry ent = new DirCacheEntry("a");
				ent.setFileMode(FileMode.REGULAR_FILE);
				ent.setObjectId(new ObjectInserter.Formatter().idFor(OBJ_BLOB
						Constants.encode("a")));
				b.add(ent);
				b.finish();

				CommitBuilder newCommit = new CommitBuilder();
				newCommit.setCommitter(committer);
				newCommit.setAuthor(author);
				newCommit.setMessage("index on master: 1e41dc first commit");
				newCommit.setParentIds(parents);
				newCommit.setTreeId(indexTreeId);

				ObjectId newCommitId = odi.insert(newCommit);
				odi.flush();
				System.out.println("first commit made");

				System.out.println("Commit 1: " + headCommitId);
				System.out.println("Commit 2: " + newCommitId);

				RevWalk revWalk = new RevWalk(repo);
				try {
					RevCommit revCommit = revWalk.parseCommit(headCommitId);

					boolean noProblems = false;
					Map<String
					List<String> unmergedPaths = null;

					repo.writeMergeHeads(Arrays.asList(headCommitId
							newCommitId));
					ThreeWayMerger merger = (ThreeWayMerger) mergeStrategy
							.newMerger(repo);

					if (merger instanceof ResolveMerger) {
						ResolveMerger resolveMerger = (ResolveMerger) merger;
						resolveMerger.setCommitNames(new String[] { "BASE"
								"HEAD"
						resolveMerger
								.setWorkingTreeIterator(new FileTreeIterator(
										repo));
						RevObject[] sourceObjects = null;
						ObjectReader reader = repo.newObjectReader();
						RevWalk walk = new RevWalk(reader);
						RevCommit[] sourceCommits = null;
						RevTree[] sourceTrees = null;
						AnyObjectId[] tips = new AnyObjectId[2];
						tips[0] = headCommitId;
						tips[1] = newCommitId;

						sourceObjects = new RevObject[tips.length];
						for (int i = 0; i < tips.length; i++)
							sourceObjects[i] = walk.parseAny(tips[i]);

						sourceCommits = new RevCommit[sourceObjects.length];
						for (int i = 0; i < sourceObjects.length; i++) {
							try {
								sourceCommits[i] = walk
										.parseCommit(sourceObjects[i]);
							} catch (IncorrectObjectTypeException err) {
								sourceCommits[i] = null;
							}
						}

						sourceTrees = new RevTree[sourceObjects.length];
						for (int i = 0; i < sourceObjects.length; i++)
							sourceTrees[i] = walk.parseTree(sourceObjects[i]);
						failingPaths = resolveMerger.getFailingPaths();
						unmergedPaths = resolveMerger.getUnmergedPaths();
						noProblems = true;
					} else {
						noProblems = merger.merge(headCommitId
					}

					if (noProblems) {
						System.out.println("NO PROBLEMS");
						CommitBuilder latestCommit = new CommitBuilder();
						latestCommit.setCommitter(committer);
						latestCommit.setAuthor(author);
						latestCommit
								.setMessage("index on master: e4783e2 first commit");
						latestCommit.setParentIds(parents);
						latestCommit.setTreeId(indexTreeId);

						ObjectId newHead = odi.insert(newCommit);
						odi.flush();

						RefUpdate ru = repo.updateRef(Constants.R_STASH);
						ru.setNewObjectId(newHead);

						ru.setExpectedOldObjectId(ObjectId.zeroId());
						Result rc = ru.forceUpdate();

						switch (rc) {
						case FAST_FORWARD: {
							setCallable(false);
							return revCommit;
						}
						case NEW: {
							setCallable(false);
							return revCommit;
						}
						case LOCK_FAILURE:
							throw new ConcurrentRefUpdateException(
									JGitText.get().couldNotLockHEAD
									ru.getRef()
						default:
							System.out.println("ERROR: " + rc);
							throw new JGitInternalException(
									"Something went wrong");
						}
					} else {
						System.out.println("PROBLEMS: " + !noProblems);
						if (failingPaths != null) {
							System.out.println("Not good
							repo.writeMergeCommitMsg(null);
							repo.writeMergeHeads(null);
						} else {
							String mergeMessageWithConflicts = new MergeMessageFormatter()
									.formatWithConflicts(
											"index on master: e4783e2 first commit"
											unmergedPaths);
							repo.writeMergeCommitMsg(mergeMessageWithConflicts);
						}
					}
				} finally {
					revWalk.release();
				}
			} finally {
				odi.release();
			}
		} finally {
			index.unlock();
		}
		return null;
	}
}
