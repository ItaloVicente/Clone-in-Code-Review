package org.eclipse.jgit.blame;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Tree;
import org.eclipse.jgit.lib.TreeEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;

public class OriginWalk implements Iterable<Origin>

	private static final Origin[] NO_ORIGINS = new Origin[0];

	final Origin initalOrigin;

	final Repository repository;

	HashMap<RevCommit

	LinkedList<Origin> pendingOrigins = new LinkedList<Origin>();

	private RevWalk revWalk;

	private IOriginSearchStrategy[] originSearchStrategies = new IOriginSearchStrategy[] {
			new SameNameOriginSearchStrategy()
			new RenameModifiedSearchStrategy()
			};

	private Origin[] parentOrigins = NO_ORIGINS;

	private Origin currentOrigin;

	private Origin[] ancestorOrigins;

	private final boolean skipFirst;

	private HashSet<Origin> seenOrigins = new HashSet<Origin>();

	public OriginWalk(Origin initalOrigin

			 {
		super();
		try {
			this.initalOrigin = initalOrigin;
			this.skipFirst = skipFirst;
			this.repository = initalOrigin.getRepository();
			revWalk = new RevWalk(repository);
			revWalk.sort(RevSort.TOPO);
			revWalk.markStart(initalOrigin.getCommit());
			RevCommit startCommit = initalOrigin.getCommit();

			if(skipFirst) {
				startCommit = findLastSameCommit(initalOrigin);
			}

			Origin firstOrigin = new Origin(repository
					initalOrigin.filename);
			queueOrigin(firstOrigin);
		} catch (Exception e) {
			throw new RuntimeException("Unable to create Origin walk"
		}
	}

	private void queueOrigin(Origin origin) {
		if(!seenOrigins.contains(origin)) {
			pendingOrigins.add(origin);
			seenOrigins.add(origin);
		}
	}

	public OriginWalk(OriginWalk other) throws MissingObjectException
			IncorrectObjectTypeException
		this(other.initalOrigin
	}

	public OriginWalk(Origin initalOrigin) {
		this(initalOrigin
	}

	public Iterator<Origin> iterator() {
		try {
			return new OriginWalk(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean hasNext() {
		return !pendingOrigins.isEmpty();
	}

	public Origin next() {
		try {
			if(pendingOrigins.isEmpty()) {
				return null;
			}
			currentOrigin = pendingOrigins.remove();
			parentOrigins = NO_ORIGINS;
			HashSet<Origin> pOrigins = new HashSet<Origin>();
			for (IOriginSearchStrategy strategy : originSearchStrategies) {
				parentOrigins = strategy.findOrigins(currentOrigin);
				pOrigins.addAll(Arrays.asList(parentOrigins));
				if (pOrigins.size() > 1) {
					break;
				}
			}
			parentOrigins = pOrigins.toArray(new Origin[0]);
			ancestorOrigins = new Origin[parentOrigins.length];
			for (int i = 0; i < ancestorOrigins.length; i++) {
				Origin parentOrigin = parentOrigins[i];
				RevCommit ancestorCommit = findLastSameCommit(parentOrigin);
				Origin ancestorOrigin = new Origin(repository
						parentOrigin.filename);
				ancestorOrigins[i] = ancestorOrigin;
				queueOrigin(ancestorOrigin);
			}
			return currentOrigin;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private RevCommit findLastSameCommit(Origin origin)
			throws MissingObjectException
			IOException {
		RevWalk rw = new RevWalk(repository);
		rw.markStart(origin.commit);
		rw.sort(RevSort.TOPO);
		RevCommit lastFoundCommit = origin.commit;
		while (true) {
			RevCommit newLastFoundCommit = null;
			for (RevCommit parent : lastFoundCommit.getParents()) {
				parent = rw.parseCommit(parent);
				Tree tree = repository.mapTree(parent.getTree());
				TreeEntry blobMember = tree.findBlobMember(origin.filename);
				if (blobMember != null
						&& blobMember.getId().equals(origin.getObjectId())) {
					newLastFoundCommit = parent;
					break;
				}
			}
			if (newLastFoundCommit != null) {
				lastFoundCommit = newLastFoundCommit;
			} else {
				break;
			}
		}
		return lastFoundCommit;
	}

	public Origin[] getParentOrigins() {
		return parentOrigins;
	}

	public Origin[] getAncestorOrigins() {
		return ancestorOrigins;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

}
