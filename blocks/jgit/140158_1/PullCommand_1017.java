
package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FIFORevQueue;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;

public class NameRevCommand extends GitCommand<Map<ObjectId
	private static final int COMMIT_TIME_SLOP = 60 * 60 * 24;

	private static final int MERGE_COST = 65535;

	private static class NameRevCommit extends RevCommit {
		private String tip;
		private int distance;
		private long cost;

		private NameRevCommit(AnyObjectId id) {
			super(id);
		}

		private StringBuilder format() {
			StringBuilder sb = new StringBuilder(tip);
			if (distance > 0)
				sb.append('~').append(distance);
			return sb;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(getClass().getSimpleName())
				.append('[');
			if (tip != null)
				sb.append(format());
			else
				sb.append((Object) null);
			sb.append('
				.append(super.toString()).toString();
			return sb.toString();
		}
	}

	private final RevWalk walk;
	private final List<String> prefixes;
	private final List<ObjectId> revs;
	private List<Ref> refs;
	private int mergeCost;

	protected NameRevCommand(Repository repo) {
		super(repo);
		mergeCost = MERGE_COST;
		prefixes = new ArrayList<>(2);
		revs = new ArrayList<>(2);
		walk = new RevWalk(repo) {
			@Override
			public NameRevCommit createCommit(AnyObjectId id) {
				return new NameRevCommit(id);
			}
		};
	}

	@Override
	public Map<ObjectId
		try {
			Map<ObjectId
			FIFORevQueue pending = new FIFORevQueue();
			if (refs != null) {
				for (Ref ref : refs)
					addRef(ref
			}
			addPrefixes(nonCommits
			int cutoff = minCommitTime() - COMMIT_TIME_SLOP;

			while (true) {
				NameRevCommit c = (NameRevCommit) pending.next();
				if (c == null)
					break;
				if (c.getCommitTime() < cutoff)
					continue;
				for (int i = 0; i < c.getParentCount(); i++) {
					NameRevCommit p = (NameRevCommit) walk.parseCommit(c.getParent(i));
					long cost = c.cost + (i > 0 ? mergeCost : 1);
					if (p.tip == null || compare(c.tip
						if (i > 0) {
							p.tip = c.format().append('^').append(i + 1).toString();
							p.distance = 0;
						} else {
							p.tip = c.tip;
							p.distance = c.distance + 1;
						}
						p.cost = cost;
						pending.add(p);
					}
				}
			}

			Map<ObjectId
				new LinkedHashMap<>(revs.size());
			for (ObjectId id : revs) {
				RevObject o = walk.parseAny(id);
				if (o instanceof NameRevCommit) {
					NameRevCommit c = (NameRevCommit) o;
					if (c.tip != null)
						result.put(id
				} else {
					String name = nonCommits.get(id);
					if (name != null)
						result.put(id
				}
			}

			setCallable(false);
			return result;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} finally {
			walk.close();
		}
	}

	public NameRevCommand add(ObjectId id) throws MissingObjectException
			JGitInternalException {
		checkCallable();
		try {
			walk.parseAny(id);
		} catch (MissingObjectException e) {
			throw e;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
		revs.add(id.copy());
		return this;
	}

	public NameRevCommand add(Iterable<ObjectId> ids)
			throws MissingObjectException
		for (ObjectId id : ids)
			add(id);
		return this;
	}

	public NameRevCommand addPrefix(String prefix) {
		checkCallable();
		prefixes.add(prefix);
		return this;
	}

	public NameRevCommand addAnnotatedTags() {
		checkCallable();
		if (refs == null)
			refs = new ArrayList<>();
		try {
			for (Ref ref : repo.getRefDatabase()
					.getRefsByPrefix(Constants.R_TAGS)) {
				ObjectId id = ref.getObjectId();
				if (id != null && (walk.parseAny(id) instanceof RevTag))
					addRef(ref);
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
		return this;
	}

	public NameRevCommand addRef(Ref ref) {
		checkCallable();
		if (refs == null)
			refs = new ArrayList<>();
		refs.add(ref);
		return this;
	}

	NameRevCommand setMergeCost(int cost) {
		mergeCost = cost;
		return this;
	}

	private void addPrefixes(Map<ObjectId
			FIFORevQueue pending) throws IOException {
		if (!prefixes.isEmpty()) {
			for (String prefix : prefixes)
				addPrefix(prefix
		} else if (refs == null)
			addPrefix(Constants.R_REFS
	}

	private void addPrefix(String prefix
			FIFORevQueue pending) throws IOException {
		for (Ref ref : repo.getRefDatabase().getRefsByPrefix(prefix))
			addRef(ref
	}

	private void addRef(Ref ref
			FIFORevQueue pending) throws IOException {
		if (ref.getObjectId() == null)
			return;
		RevObject o = walk.parseAny(ref.getObjectId());
		while (o instanceof RevTag) {
			RevTag t = (RevTag) o;
			nonCommits.put(o
			o = t.getObject();
			walk.parseHeaders(o);
		}
		if (o instanceof NameRevCommit) {
			NameRevCommit c = (NameRevCommit) o;
			if (c.tip == null)
				c.tip = ref.getName();
			pending.add(c);
		} else if (!nonCommits.containsKey(o))
			nonCommits.put(o
	}

	private int minCommitTime() throws IOException {
		int min = Integer.MAX_VALUE;
		for (ObjectId id : revs) {
			RevObject o = walk.parseAny(id);
			while (o instanceof RevTag) {
				o = ((RevTag) o).getObject();
				walk.parseHeaders(o);
			}
			if (o instanceof RevCommit) {
				RevCommit c = (RevCommit) o;
				if (c.getCommitTime() < min)
					min = c.getCommitTime();
			}
		}
		return min;
	}

	private long compare(String leftTip
		long c = leftCost - rightCost;
		if (c != 0 || prefixes.isEmpty())
			return c;
		int li = -1;
		int ri = -1;
		for (int i = 0; i < prefixes.size(); i++) {
			String prefix = prefixes.get(i);
			if (li < 0 && leftTip.startsWith(prefix))
				li = i;
			if (ri < 0 && rightTip.startsWith(prefix))
				ri = i;
		}
		return li - ri;
	}

	private static String simplify(String refName) {
		if (refName.startsWith(Constants.R_HEADS))
			return refName.substring(Constants.R_HEADS.length());
		if (refName.startsWith(Constants.R_TAGS))
			return refName.substring(Constants.R_TAGS.length());
		if (refName.startsWith(Constants.R_REFS))
			return refName.substring(Constants.R_REFS.length());
		return refName;
	}
}
