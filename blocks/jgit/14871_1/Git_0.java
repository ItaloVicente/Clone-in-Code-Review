package org.eclipse.jgit.api;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevFlagSet;
import org.eclipse.jgit.revwalk.RevWalk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.eclipse.jgit.lib.Constants.R_TAGS;

public class DescribeCommand extends GitCommand<String> {
	private final RevWalk w;

	private RevCommit target;

	private int maxCandidates = 10;

	protected DescribeCommand(Repository repo) {
		super(repo);
		w = new RevWalk(repo);
		w.setRetainBody(false);
	}

	DescribeCommand setCommitish(ObjectId target) throws IOException {
		this.target = w.parseCommit(target);
		return this;
	}

	DescribeCommand setCommitish(String rev) throws IOException {
		ObjectId id = repo.resolve(rev);
		if (id == null)
			throw new IllegalArgumentException("No such commit: " + id);
		return setCommitish(id);
	}

	@Override
	public String call() throws GitAPIException {
		try {
			checkCallable();

			Map<ObjectId
			for (Ref r : repo.getTags().values()) {
				ObjectId key = repo.peel(r).getPeeledObjectId();
				if (key == null) key = r.getObjectId();
				tags.put(key
			}


			class Candidate {
				final RevCommit commit;
				final Ref tag;
				final RevFlag flag;

				int depth;

				Candidate(RevCommit commit
					this.commit = commit;
					this.tag = tag;
					this.flag = w.newFlag(tag.getName());
					w.carry(flag);
					commit.add(flag);
					allFlags.add(flag);
				}

				public boolean reaches(RevCommit c) {
					return c.has(flag);
				}

				public String describe(ObjectId tip) throws IOException {
					return String.format("%s-%d-g%s"
							depth
				}
			}

			Ref lucky = tags.get(target);
			if (lucky != null)
				return lucky.getName().substring(R_TAGS.length());

			w.markStart(target);

			RevCommit c;
			while ((c = w.next()) != null) {
				if (!c.hasAny(allFlags)) {
					Ref t = tags.get(c);
					if (t != null) {
						Candidate cd = new Candidate(c
						candidates.add(cd);
						cd.depth = seen;
					}
				}

				for (Candidate cd : candidates) {
					if (!cd.reaches(c)) {
						cd.depth++;
					}
				}

				if (candidates.size() >= maxCandidates)
					break;


				seen++;
			}

			while ((c = w.next()) != null) {
				if (c.hasAll(allFlags)) {
					for (RevCommit p : c.getParents())
						p.add(RevFlag.SEEN);
				} else {
					for (Candidate cd : candidates) {
						if (!cd.reaches(c)) {
							cd.depth++;
						}
					}
				}
			}

			if (candidates.isEmpty())
				return null;

			Collections.sort(candidates
				public int compare(Candidate o1
					return o1.depth - o2.depth;
				}
			});

			return candidates.get(0).describe(target);
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}
}
