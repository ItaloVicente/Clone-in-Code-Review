
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;

public abstract class GitRepository {
	private final AtomicInteger useCnt = new AtomicInteger(1);

	protected GitRepository() {
	}

	public void incrementOpen() {
		useCnt.incrementAndGet();
	}

	public void close() {
		if (useCnt.decrementAndGet() == 0) {
			getObjectDatabase().close();
			getRefDatabase().close();
		}
	}

	public abstract ObjectDatabase getObjectDatabase();

	public ObjectInserter newObjectInserter() {
		return getObjectDatabase().newInserter();
	}

	public abstract RefDatabase getRefDatabase();

	public abstract Config getConfig();

	public boolean hasObject(final AnyObjectId objectId) {
		return getObjectDatabase().hasObject(objectId);
	}

	public ObjectLoader openObject(final AnyObjectId id) throws IOException {
		final WindowCursor wc = new WindowCursor();
		try {
			return openObject(wc
		} finally {
			wc.release();
		}
	}

	public ObjectLoader openObject(final WindowCursor curs
			throws IOException {
		return getObjectDatabase().openObject(curs
	}

	public RefUpdate updateRef(final String ref) throws IOException {
		return updateRef(ref
	}

	public RefUpdate updateRef(final String ref
			throws IOException {
		return getRefDatabase().newUpdate(ref
	}

	public RefRename renameRef(final String fromRef
			throws IOException {
		return getRefDatabase().newRename(fromRef
	}

	public ObjectId resolve(final String revstr) throws IOException {
		char[] rev = revstr.toCharArray();
		RevObject ref = null;
		RevWalk rw = new RevWalk(this);
		for (int i = 0; i < rev.length; ++i) {
			switch (rev[i]) {
			case '^':
				if (ref == null) {
					ref = parseSimple(rw
					if (ref == null)
						return null;
				}
				if (i + 1 < rev.length) {
					switch (rev[i + 1]) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						int j;
						ref = rw.parseCommit(ref);
						for (j = i + 1; j < rev.length; ++j) {
							if (!Character.isDigit(rev[j]))
								break;
						}
						String parentnum = new String(rev
						int pnum;
						try {
							pnum = Integer.parseInt(parentnum);
						} catch (NumberFormatException e) {
							throw new RevisionSyntaxException(
									JGitText.get().invalidCommitParentNumber
									revstr);
						}
						if (pnum != 0) {
							RevCommit commit = (RevCommit) ref;
							if (pnum > commit.getParentCount())
								ref = null;
							else
								ref = commit.getParent(pnum - 1);
						}
						i = j - 1;
						break;
					case '{':
						int k;
						String item = null;
						for (k = i + 2; k < rev.length; ++k) {
							if (rev[k] == '}') {
								item = new String(rev
								break;
							}
						}
						i = k;
						if (item != null)
							if (item.equals("tree")) {
								ref = rw.parseTree(ref);
							} else if (item.equals("commit")) {
								ref = rw.parseCommit(ref);
							} else if (item.equals("blob")) {
								ref = rw.parseAny(ref);
								ref = peelTag(rw
								if (!(ref instanceof RevBlob))
									throw new IncorrectObjectTypeException(ref
											Constants.TYPE_BLOB);
							} else if (item.equals("")) {
								ref = rw.parseAny(ref);
								ref = peelTag(rw
							} else
								throw new RevisionSyntaxException(revstr);
						else
							throw new RevisionSyntaxException(revstr);
						break;
					default:
						ref = rw.parseAny(ref);
						if (ref instanceof RevCommit) {
							RevCommit commit = ((RevCommit) ref);
							if (commit.getParentCount() == 0)
								ref = null;
							else
								ref = commit.getParent(0);
						} else
							throw new IncorrectObjectTypeException(ref
									Constants.TYPE_COMMIT);

					}
				} else {
					ref = rw.parseAny(ref);
					ref = peelTag(rw
					if (ref instanceof RevCommit) {
						RevCommit commit = ((RevCommit) ref);
						if (commit.getParentCount() == 0)
							ref = null;
						else
							ref = commit.getParent(0);
					} else
						throw new IncorrectObjectTypeException(ref
								Constants.TYPE_COMMIT);
				}
				break;
			case '~':
				if (ref == null) {
					ref = parseSimple(rw
					if (ref == null)
						return null;
				}
				ref = peelTag(rw
				if (!(ref instanceof RevCommit))
					throw new IncorrectObjectTypeException(ref
							Constants.TYPE_COMMIT);
				int l;
				for (l = i + 1; l < rev.length; ++l) {
					if (!Character.isDigit(rev[l]))
						break;
				}
				String distnum = new String(rev
				int dist;
				try {
					dist = Integer.parseInt(distnum);
				} catch (NumberFormatException e) {
					throw new RevisionSyntaxException(
							JGitText.get().invalidAncestryLength
				}
				while (dist > 0) {
					RevCommit commit = (RevCommit) ref;
					if (commit.getParentCount() == 0) {
						ref = null;
						break;
					}
					commit = commit.getParent(0);
					rw.parseHeaders(commit);
					ref = commit;
					--dist;
				}
				i = l - 1;
				break;
			case '@':
				int m;
				String time = null;
				for (m = i + 2; m < rev.length; ++m) {
					if (rev[m] == '}') {
						time = new String(rev
						break;
					}
				}
				if (time != null)
					throw new RevisionSyntaxException(
							JGitText.get().reflogsNotYetSupportedByRevisionParser
							revstr);
				i = m - 1;
				break;
			default:
				if (ref != null)
					throw new RevisionSyntaxException(revstr);
			}
		}
		return ref != null ? ref.copy() : resolveSimple(revstr);
	}

	private RevObject parseSimple(RevWalk rw
		ObjectId id = resolveSimple(revstr);
		return id != null ? rw.parseAny(id) : null;
	}

	private ObjectId resolveSimple(final String revstr) throws IOException {
		if (ObjectId.isId(revstr))
			return ObjectId.fromString(revstr);
		final Ref r = getRefDatabase().getRef(revstr);
		return r != null ? r.getObjectId() : null;
	}

	private RevObject peelTag(RevWalk rw
			throws MissingObjectException
		while (ref instanceof RevTag)
			ref = rw.parseAny(((RevTag) ref).getObject());
		return ref;
	}

	public Ref getRef(final String name) throws IOException {
		return getRefDatabase().getRef(name);
	}

	public Map<String
		return getRefDatabase().getRefs(RefDatabase.ALL);
	}

	public Map<String
		return getRefDatabase().getRefs(Constants.R_TAGS);
	}

	public Ref peel(final Ref ref) throws IOException {
		return getRefDatabase().peel(ref);
	}

	public Map<ObjectId
			throws IOException {
		Map<ObjectId
		for (Ref ref : getAllRefs().values()) {
			ref = peel(ref);
			ObjectId target = ref.getPeeledObjectId();
			if (target == null)
				target = ref.getObjectId();
			Set<Ref> oset = ret.put(target
			if (oset != null) {
				if (oset.size() == 1) {
					oset = new HashSet<Ref>(oset);
				}
				ret.put(target
				oset.add(ref);
			}
		}
		return ret;
	}
}
