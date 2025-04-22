
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.FileMode.TYPE_FILE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class PushCertificateStore implements AutoCloseable {
	static final String REF_NAME =

	private static class PendingCert {
		PushCertificate cert;
		PersonIdent ident;
		Collection<ReceiveCommand> matching;

		PendingCert(PushCertificate cert
				Collection<ReceiveCommand> matching) {
			this.cert = cert;
			this.ident = ident;
			this.matching = matching;
		}
	}

	private final Repository db;
	private final List<PendingCert> pending;
	ObjectReader reader;
	RevCommit commit;

	public PushCertificateStore(Repository db) {
		this.db = db;
		pending = new ArrayList<>();
	}

	@Override
	public void close() {
		if (reader != null) {
			reader.close();
			reader = null;
			commit = null;
		}
	}

	public PushCertificate get(String refName) throws IOException {
		if (reader == null) {
			load();
		}
		try (TreeWalk tw = newTreeWalk(refName)) {
			return read(tw);
		}
	}

	public Iterable<PushCertificate> getAll(String refName) {
		return new Iterable<PushCertificate>() {
			@Override
			public Iterator<PushCertificate> iterator() {
				return new Iterator<PushCertificate>() {
					private final String path = pathName(refName);
					private PushCertificate next;

					private RevWalk rw;
					{
						try {
							if (reader == null) {
								load();
							}
							if (commit != null) {
								rw = new RevWalk(reader);
								rw.setTreeFilter(AndTreeFilter.create(
										PathFilterGroup.create(
											Collections.singleton(PathFilter.create(path)))
										TreeFilter.ANY_DIFF));
								rw.setRewriteParents(false);
								rw.markStart(rw.parseCommit(commit));
							} else {
								rw = null;
							}
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}

					@Override
					public boolean hasNext() {
						try {
							if (next == null) {
								if (rw == null) {
									return false;
								}
								try {
									RevCommit c = rw.next();
									if (c != null) {
										try (TreeWalk tw = TreeWalk.forPath(
												rw.getObjectReader()
											next = read(tw);
										}
									} else {
										next = null;
									}
								} catch (IOException e) {
									throw new RuntimeException(e);
								}
							}
							return next != null;
						} finally {
							if (next == null && rw != null) {
								rw.close();
								rw = null;
							}
						}
					}

					@Override
					public PushCertificate next() {
						hasNext();
						PushCertificate n = next;
						if (n == null) {
							throw new NoSuchElementException();
						}
						next = null;
						return n;
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}

	void load() throws IOException {
		close();
		reader = db.newObjectReader();
		Ref ref = db.getRefDatabase().exactRef(REF_NAME);
		if (ref == null) {
			return;
		}
		try (RevWalk rw = new RevWalk(reader)) {
			commit = rw.parseCommit(ref.getObjectId());
		}
	}

	static PushCertificate read(TreeWalk tw) throws IOException {
		if (tw == null || (tw.getRawMode(0) & TYPE_FILE) != TYPE_FILE) {
			return null;
		}
		ObjectLoader loader =
				tw.getObjectReader().open(tw.getObjectId(0)
		try (InputStream in = loader.openStream();
				Reader r = new BufferedReader(
						new InputStreamReader(in
			return PushCertificateParser.fromReader(r);
		}
	}

	public void put(PushCertificate cert
		put(cert
	}

	public void put(PushCertificate cert
			Collection<ReceiveCommand> matching) {
		pending.add(new PendingCert(cert
	}

	public RefUpdate.Result save() throws IOException {
		ObjectId newId = write();
		if (newId == null) {
			return RefUpdate.Result.NO_CHANGE;
		}
		try (ObjectInserter inserter = db.newObjectInserter()) {
			RefUpdate.Result result = updateRef(newId);
			switch (result) {
				case FAST_FORWARD:
				case NEW:
				case NO_CHANGE:
					pending.clear();
					break;
				default:
					break;
			}
			return result;
		} finally {
			close();
		}
	}

	public boolean save(BatchRefUpdate batch) throws IOException {
		ObjectId newId = write();
		if (newId == null || newId.equals(commit)) {
			return false;
		}
		batch.addCommand(new ReceiveCommand(
				commit != null ? commit : ObjectId.zeroId()
		return true;
	}

	public void clear() {
		pending.clear();
	}

	private ObjectId write() throws IOException {
		if (pending.isEmpty()) {
			return null;
		}
		if (reader == null) {
			load();
		}
		sortPending(pending);

		ObjectId curr = commit;
		DirCache dc = newDirCache();
		try (ObjectInserter inserter = db.newObjectInserter()) {
			for (PendingCert pc : pending) {
				curr = saveCert(inserter
			}
			inserter.flush();
			return curr;
		}
	}

	private static void sortPending(List<PendingCert> pending) {
		Collections.sort(pending
			@Override
			public int compare(PendingCert a
				return Long.signum(
						a.ident.getWhen().getTime() - b.ident.getWhen().getTime());
			}
		});
	}

	private DirCache newDirCache() throws IOException {
		if (commit != null) {
			return DirCache.read(reader
		}
		return DirCache.newInCore();
	}

	private ObjectId saveCert(ObjectInserter inserter
			PendingCert pc
		Map<String
		if (pc.matching != null) {
			byRef = new HashMap<>();
			for (ReceiveCommand cmd : pc.matching) {
				if (byRef.put(cmd.getRefName()
					throw new IllegalStateException();
				}
			}
		} else {
			byRef = null;
		}

		DirCacheEditor editor = dc.editor();
		String certText = pc.cert.toText() + pc.cert.getSignature();
		final ObjectId certId = inserter.insert(OBJ_BLOB
		boolean any = false;
		for (ReceiveCommand cmd : pc.cert.getCommands()) {
			if (byRef != null && !commandsEqual(cmd
				continue;
			}
			any = true;
			editor.add(new PathEdit(pathName(cmd.getRefName())) {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(FileMode.REGULAR_FILE);
					ent.setObjectId(certId);
				}
			});
		}
		if (!any) {
			return curr;
		}
		editor.finish();
		CommitBuilder cb = new CommitBuilder();
		cb.setAuthor(pc.ident);
		cb.setCommitter(pc.ident);
		cb.setTreeId(dc.writeTree(inserter));
		if (curr != null) {
			cb.setParentId(curr);
		} else {
			cb.setParentIds(Collections.<ObjectId> emptyList());
		}
		cb.setMessage(buildMessage(pc.cert));
		return inserter.insert(OBJ_COMMIT
	}

	private static boolean commandsEqual(ReceiveCommand c1
		if (c1 == null || c2 == null) {
			return c1 == c2;
		}
		return c1.getRefName().equals(c2.getRefName())
				&& c1.getOldId().equals(c2.getOldId())
				&& c1.getNewId().equals(c2.getNewId());
	}

	private RefUpdate.Result updateRef(ObjectId newId) throws IOException {
		RefUpdate ru = db.updateRef(REF_NAME);
		ru.setExpectedOldObjectId(commit != null ? commit : ObjectId.zeroId());
		ru.setNewObjectId(newId);
		ru.setRefLogIdent(pending.get(pending.size() - 1).ident);
		ru.setRefLogMessage(JGitText.get().storePushCertReflog
		try (RevWalk rw = new RevWalk(reader)) {
			return ru.update(rw);
		}
	}

	private TreeWalk newTreeWalk(String refName) throws IOException {
		if (commit == null) {
			return null;
		}
		return TreeWalk.forPath(reader
	}

	static String pathName(String refName) {
	}

	private static String buildMessage(PushCertificate cert) {
		StringBuilder sb = new StringBuilder();
		if (cert.getCommands().size() == 1) {
			sb.append(MessageFormat.format(
					JGitText.get().storePushCertOneRef
					cert.getCommands().get(0).getRefName()));
		} else {
			sb.append(MessageFormat.format(
					JGitText.get().storePushCertMultipleRefs
					Integer.valueOf(cert.getCommands().size())));
		}
		return sb.append('\n').toString();
	}
}
