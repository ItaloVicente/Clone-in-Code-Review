package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.R_TAGS;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.ignore.internal.IMatcher;
import org.eclipse.jgit.ignore.internal.PathMatcher;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevFlagSet;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;

public class DescribeCommand extends GitCommand<String> {
	private final RevWalk w;

	private RevCommit target;

	private int maxCandidates = 10;

	private boolean longDesc;

	private List<IMatcher> matchers = new ArrayList<>();

	private boolean useTags = false;

	protected DescribeCommand(Repository repo) {
		super(repo);
		w = new RevWalk(repo);
		w.setRetainBody(false);
	}

	public DescribeCommand setTarget(ObjectId target) throws IOException {
		this.target = w.parseCommit(target);
		return this;
	}

	public DescribeCommand setTarget(String rev) throws IOException
			RefNotFoundException {
		ObjectId id = repo.resolve(rev);
		if (id == null)
			throw new RefNotFoundException(MessageFormat.format(JGitText.get().refNotResolved
		return setTarget(id);
	}

	public DescribeCommand setLong(boolean longDesc) {
		this.longDesc = longDesc;
		return this;
	}

	public DescribeCommand setTags(boolean tags) {
		this.useTags = tags;
		return this;
	}

	private String longDescription(Ref tag
			throws IOException {
		return String.format(
				"%s-%d-g%s"
				Integer.valueOf(depth)
						.name());
	}

	public DescribeCommand setMatch(String... patterns) throws InvalidPatternException {
		for (String p : patterns) {
			matchers.add(PathMatcher.createPathMatcher(p
		}
		return this;
	}

	private final Comparator<Ref> TAG_TIE_BREAKER = new Comparator<Ref>() {

		@Override
		public int compare(Ref o1
			try {
				return tagDate(o2).compareTo(tagDate(o1));
			} catch (IOException e) {
				return 0;
			}
		}

		private Date tagDate(Ref tag) throws IOException {
			RevTag t = w.parseTag(tag.getObjectId());
			w.parseBody(t);
			return t.getTaggerIdent().getWhen();
		}
	};

	private Optional<Ref> getBestMatch(List<Ref> tags) {
		if (tags == null || tags.isEmpty()) {
			return Optional.empty();
		} else if (matchers.isEmpty()) {
			Collections.sort(tags
			return Optional.of(tags.get(0));
		} else {
			Stream<Ref> matchingTags = Stream.empty();
			for (IMatcher matcher : matchers) {
				Stream<Ref> m = tags.stream().filter(
						tag -> matcher.matches(tag.getName()
				matchingTags = Stream.of(matchingTags
			}
			return matchingTags.sorted(TAG_TIE_BREAKER).findFirst();
		}
	}

	private ObjectId getObjectIdFromRef(Ref r) throws JGitInternalException {
		try {
			ObjectId key = repo.getRefDatabase().peel(r).getPeeledObjectId();
			if (key == null) {
				key = r.getObjectId();
			}
			return key;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	@Override
	public String call() throws GitAPIException {
		try {
			checkCallable();
			if (target == null) {
				setTarget(Constants.HEAD);
			}

			Collection<Ref> tagList = repo.getRefDatabase()
					.getRefsByPrefix(R_TAGS);
			Map<ObjectId
					.filter(this::filterLightweightTags)
					.collect(Collectors.groupingBy(this::getObjectIdFromRef));

			final RevFlagSet allFlags = new RevFlagSet();

			class Candidate {
				final Ref tag;
				final RevFlag flag;

				int depth;

				Candidate(RevCommit commit
					this.tag = tag;
					this.flag = w.newFlag(tag.getName());
					allFlags.add(flag);
					w.carry(flag);
					commit.add(flag);
					commit.carry(flag);
				}

				boolean reaches(RevCommit c) {
					return c.has(flag);
				}

				String describe(ObjectId tip) throws IOException {
					return longDescription(tag
				}

			}

			Optional<Ref> bestMatch = getBestMatch(tags.get(target));
			if (bestMatch.isPresent()) {
				return longDesc ? longDescription(bestMatch.get()
						bestMatch.get().getName().substring(R_TAGS.length());
			}

			w.markStart(target);

			RevCommit c;
			while ((c = w.next()) != null) {
				if (!c.hasAny(allFlags)) {
					bestMatch = getBestMatch(tags.get(c));
					if (bestMatch.isPresent()) {
						Candidate cd = new Candidate(c
						candidates.add(cd);
						cd.depth = seen;
					}
				}

				for (Candidate cd : candidates) {
					if (!cd.reaches(c))
						cd.depth++;
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
						if (!cd.reaches(c))
							cd.depth++;
					}
				}
			}

			if (candidates.isEmpty())
				return null;

			Candidate best = Collections.min(candidates
				@Override
				public int compare(Candidate o1
					return o1.depth - o2.depth;
				}
			});

			return best.describe(target);
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} finally {
			setCallable(false);
			w.close();
		}
	}

	@SuppressWarnings("null")
	private boolean filterLightweightTags(Ref ref) {
		ObjectId id = ref.getObjectId();
		try {
			return this.useTags || (id != null && (w.parseTag(id) != null));
		} catch (IOException e) {
			return false;
		}
	}
}
