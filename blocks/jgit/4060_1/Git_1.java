package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class DescribeCommand extends GitCommand<String> {
	private static final int DEFAULT_ABBREVIATION_LENGTH = 7;

	private int abbreviationLength;
	private ObjectId oid;

	protected DescribeCommand(Repository repo) {
		super(repo);
		abbreviationLength = DEFAULT_ABBREVIATION_LENGTH;
	}

	public void setAbbrev(final int abbrev) {
		this.abbreviationLength = abbrev;
	}

	public String call() throws NoHeadException
		checkCallable();

		RevWalk w = new RevWalk(repo);
		String tagName = null;
		Map<String
		Integer shortestLengthSoFar = null;
		for (Entry<String

			RevCommit base;
			RevCommit tip;
			try {
				RevObject ro = w.parseCommit(w.parseTag(
						tag.getValue().getObjectId()).getObject());
				if (ro.getType() != Constants.OBJ_COMMIT) {
					continue;
				}
				base = w.parseCommit(ro.getId());
				tip = w.parseCommit(oid);

				if (!w.isMergedInto(base
					continue;
				}

				int i = countCommits(tip.getId()
				if (shortestLengthSoFar == null) {
					shortestLengthSoFar = i;
					tagName = tag.getKey();
				}
				if (shortestLengthSoFar > i) {
					shortestLengthSoFar = i;
					tagName = tag.getKey();
				}

			} catch (MissingObjectException e) {
				e.printStackTrace();
				continue;
			} catch (IncorrectObjectTypeException e) {
				continue;
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
		if (shortestLengthSoFar == null) {
			setCallable(false);
			return null;
		}
		setCallable(false);
		return tagName + "-" + shortestLengthSoFar.toString() + "-" + "g"
				+ oid.abbreviate(abbreviationLength).name();
	}

	private int countCommits(final ObjectId tip
			throws MissingObjectException
			IOException {
		RevWalk w = new RevWalk(repo);
		w.reset();
		w.setRevFilter(RevFilter.ALL);
		w.setTreeFilter(TreeFilter.ALL);
		w.markStart(w.parseCommit(tip));
		w.markUninteresting(w.parseCommit(end));
		int count = 0;
		while (w.next() != null) {
			++count;
		}
		return count;
	}

	public ObjectId getObjectId() {
		return oid;
	}

	public void setObjectId(ObjectId oid) {
		this.oid = oid;
	}
}
