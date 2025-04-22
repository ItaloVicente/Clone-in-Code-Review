
package org.eclipse.jgit.revplot;

import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_REMOTES;
import static org.eclipse.jgit.lib.Constants.R_TAGS;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;

public class PlotWalk extends RevWalk {

	private Map<AnyObjectId

	private Map<AnyObjectId

	private Repository repository;

	@Override
	public void dispose() {
		super.dispose();
		if (reverseRefMap != null) {
			reverseRefMap.clear();
			reverseRefMap = null;
		}
		if (additionalRefMap != null) {
			additionalRefMap.clear();
			additionalRefMap = null;
		}
		repository = null;
	}

	public PlotWalk(Repository repo) {
		super(repo);
		super.sort(RevSort.TOPO
		additionalRefMap = new HashMap<>();
		repository = repo;
	}

	public void addAdditionalRefs(Iterable<Ref> refs) throws IOException {
		for (Ref ref : refs) {
			Set<Ref> set = additionalRefMap.get(ref.getObjectId());
			if (set == null)
				set = Collections.singleton(ref);
			else {
				set = new HashSet<>(set);
				set.add(ref);
			}
			additionalRefMap.put(ref.getObjectId()
		}
	}

	@Override
	public void sort(RevSort s
		if (s == RevSort.TOPO && !use)
			throw new IllegalArgumentException(JGitText.get().topologicalSortRequired);
		super.sort(s
	}

	@Override
	protected RevCommit createCommit(AnyObjectId id) {
		return new PlotCommit(id);
	}

	@Override
	public RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		PlotCommit<?> pc = (PlotCommit) super.next();
		if (pc != null)
			pc.refs = getRefs(pc);
		return pc;
	}

	private Ref[] getRefs(AnyObjectId commitId) {
		if (reverseRefMap == null) {
			reverseRefMap = repository.getAllRefsByPeeledObjectId();
			for (Map.Entry<AnyObjectId
					.entrySet()) {
				Set<Ref> set = reverseRefMap.get(entry.getKey());
				Set<Ref> additional = entry.getValue();
				if (set != null) {
					if (additional.size() == 1) {
						additional = new HashSet<>(additional);
					}
					additional.addAll(set);
				}
				reverseRefMap.put(entry.getKey()
			}
			additionalRefMap.clear();
			additionalRefMap = null;
		}
		Collection<Ref> list = reverseRefMap.get(commitId);
		if (list == null) {
			return PlotCommit.NO_REFS;
		} else {
			Ref[] tags = list.toArray(new Ref[0]);
			Arrays.sort(tags
			return tags;
		}
	}

	class PlotRefComparator implements Comparator<Ref> {
		@Override
		public int compare(Ref o1
			try {
				RevObject obj1 = parseAny(o1.getObjectId());
				RevObject obj2 = parseAny(o2.getObjectId());
				long t1 = timeof(obj1);
				long t2 = timeof(obj2);
				if (t1 > t2)
					return -1;
				if (t1 < t2)
					return 1;
			} catch (IOException e) {
			}

			int cmp = kind(o1) - kind(o2);
			if (cmp == 0)
				cmp = o1.getName().compareTo(o2.getName());
			return cmp;
		}

		long timeof(RevObject o) {
			if (o instanceof RevCommit)
				return ((RevCommit) o).getCommitTime();
			if (o instanceof RevTag) {
				RevTag tag = (RevTag) o;
				try {
					parseBody(tag);
				} catch (IOException e) {
					return 0;
				}
				PersonIdent who = tag.getTaggerIdent();
				return who != null ? who.getWhen().getTime() : 0;
			}
			return 0;
		}

		int kind(Ref r) {
			if (r.getName().startsWith(R_TAGS))
				return 0;
			if (r.getName().startsWith(R_HEADS))
				return 1;
			if (r.getName().startsWith(R_REMOTES))
				return 2;
			return 3;
		}
	}
}
