package org.eclipse.jgit.api;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;

public class DescribeCommand extends GitCommand<String> {
	private static final int DEFAULT_ABBREVIATION_LENGTH = 7;

	private int abbreviationLength;
	private ObjectId oid;

	protected DescribeCommand(Repository repo) {
		super(repo);
		abbreviationLength = DEFAULT_ABBREVIATION_LENGTH;
		try {
			oid = repo.resolve("HEAD");
		} catch (IOException e) {
		}
	}

	public DescribeCommand setAbbrev(final int abbrev)
			throws IllegalArgumentException {
		checkCallable();
		if (abbrev < 0 || abbrev == 1 || abbrev > 40) {
			throw new IllegalArgumentException(
					JGitText.get().describeInvalidAbbreviation);
		}
		this.abbreviationLength = abbrev;
		return this;
	}

	public String call() throws NoHeadException
		checkCallable();
		setCallable(false);

		if (oid == null) {
			throw new JGitInternalException(
					JGitText.get().describeObjectIdNotSet
					new NullPointerException());
		}

		RevWalk w = new RevWalk(repo);
        Map<RevCommit
		try {
            RevFlag f = w.newFlag("wanted");
            for (Ref tag : repo.getTags().values()) {
                if (w.parseCommit(tag.getObjectId()).getType() != Constants.OBJ_COMMIT)
                	continue;
                RevCommit rc = w.parseCommit(tag.getObjectId());
                rc.add(f);
                String fullTagName = tag.getName();
                String[] tagParts = fullTagName.split("/");
                String tagName = tagParts[Array.getLength(tagParts)-1];
                if (tagLookup.containsKey(rc)) {
                	tagLookup.get(rc).add(tagName);
                } else {
                	List<String> l = new ArrayList<String>();
                	l.add(tagName);
                    tagLookup.put(rc
                }
            }

            RevCommit start = w.parseCommit(oid);
            RevCommit candidate = null;
            int candidateDistance = 0;

            w.markStart(start);
            w.setRevFilter(RevFilter.ALL);
            w.sort(RevSort.TOPO);
            RevCommit r = null;
            while ((r = w.next()) != null) {
            	if (r.has(f)) {
            		candidate = r;
                    w.markUninteresting(w.parseCommit(r));
            	}
                ++candidateDistance;
            }

			if (candidate == null) {
				return null;
			}

			int age = 0;
			String tagName = null;
            for (Map.Entry<String
            	ObjectId thisOid = w.parseCommit(e.getValue().getObjectId());
            	ObjectId candidateOid = candidate.getId();
            	if (thisOid.equals(candidateOid)) {
            		if (w.parseCommit(thisOid).getCommitTime() > age) {
            			age = w.parseCommit(thisOid).getCommitTime();
            			tagName = e.getKey();
            		}

            	}
            }

			if (candidateDistance == 1 || abbreviationLength == 0) {
				return tagName;
			}
			return tagName + "-" + Integer.toString(candidateDistance-1) + "-" + "g"
					+ repo.getObjectDatabase().newReader()
							.abbreviate(oid
		} catch (MissingObjectException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfDescribeCommand
					e);
		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfDescribeCommand
					e);
		} finally {
			w.release();
		}
	}

	public ObjectId getObjectId() {
		return oid;
	}

	public DescribeCommand setObjectId(ObjectId oid) {
		this.oid = oid;
		return this;
	}

	public static int getDefaultAbbreviationLength() {
		return DEFAULT_ABBREVIATION_LENGTH;
	}
}
