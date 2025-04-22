package org.eclipse.jgit.blame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.IntList;
import org.eclipse.jgit.util.RawParseUtils;

public class CopyModifiedSearchStrategy implements IOriginSearchStrategy {
	final static double MAX_SCORE = 1000;

	double maxScore = MAX_SCORE;

	double thresholdScore = MAX_SCORE / 5;

	int maxCandidates = 2;

	private Repository repository;

	public Origin[] findOrigins(Origin source) {
		RevCommit commit = source.commit;
		repository = source.repository;
		try {
			ArrayList<Origin> resultList = new ArrayList<Origin>();
			for (RevCommit parent : commit.getParents()) {
				RevWalk revWalk = new RevWalk(repository);
				parent = revWalk.parseCommit(parent);
				resultList.addAll(findOrigins(source
			}
			return resultList.toArray(new Origin[0]);
		} catch (Exception e) {
			throw new RuntimeException(
					"could not retrieve scapegoats for commit " + commit
		}
	}

	private Collection<Origin> findOrigins(Origin source
		IDiff diff = new MyersDiffImpl();
		ScoreList<Integer
				maxCandidates);
		ArrayList<Origin> resultList = new ArrayList<Origin>();
		try {
			TreeWalk treeWalk = createTreeWalk(source
			while (treeWalk.next()) {
				if (!treeWalk.isSubtree()) {
					ObjectId objectId = treeWalk.getObjectId(0);
					if (objectId == null || objectId.equals(ObjectId.zeroId())) {
						continue;
					}
					ObjectLoader openBlob = repository.open(objectId);
					byte[] parentBytes = openBlob.getBytes();
					byte[] targetBytes = source.getBytes();
					IntList parentLines = RawParseUtils.lineMap(parentBytes
							parentBytes.length);
					IntList targetLines = RawParseUtils.lineMap(targetBytes
							targetBytes.length);
					IDifference[] differences = diff.diff(parentBytes
							parentLines
					int maxLines = Math.max(targetLines.size()
							.size()) - 1;
					int totalLines = targetLines.size() + parentLines.size()
							- 2;
					int changedlines = 0;
					for (IDifference difference : differences) {
						changedlines += difference.getLengthA()
								+ difference.getLengthB();
					}
					int commonLines = (totalLines - changedlines) / 2;
					int score = (int) (commonLines * maxScore / maxLines);
					String pathString = treeWalk
					.getPathString();
					if (score > thresholdScore) {
						scoreList.add(Integer.valueOf(score)
					}
				}
			}
			for (String path : scoreList.getEntries()) {
				resultList.add(new Origin(repository
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return resultList;
	}

	protected TreeWalk createTreeWalk(Origin source
			throws MissingObjectException
			CorruptObjectException
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.reset(parent.getTree());
		treeWalk.setRecursive(true);
		return treeWalk;
	}
}
