
package org.eclipse.jgit.transport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.errors.CompoundException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.ObjectDirectory;
import org.eclipse.jgit.internal.storage.file.PackIndex;
import org.eclipse.jgit.internal.storage.file.PackLock;
import org.eclipse.jgit.internal.storage.file.UnpackedObject;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.DateRevQueue;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FileUtils;

class WalkFetchConnection extends BaseFetchConnection {
	final Repository local;

	final ObjectChecker objCheck;

	private final List<WalkRemoteObjectDatabase> remotes;

	private int lastRemoteIdx;

	private final RevWalk revWalk;

	private final TreeWalk treeWalk;

	private final RevFlag COMPLETE;

	private final RevFlag IN_WORK_QUEUE;

	private final RevFlag LOCALLY_SEEN;

	private final DateRevQueue localCommitQueue;

	private LinkedList<ObjectId> workQueue;

	private final LinkedList<WalkRemoteObjectDatabase> noPacksYet;

	private final LinkedList<WalkRemoteObjectDatabase> noAlternatesYet;

	private final LinkedList<RemotePack> unfetchedPacks;

	private final Set<String> packsConsidered;

	private final MutableObjectId idBuffer = new MutableObjectId();

	private final HashMap<ObjectId

	String lockMessage;

	final List<PackLock> packLocks;

	final ObjectInserter inserter;

	private final ObjectReader reader;

	WalkFetchConnection(WalkTransport t
		Transport wt = (Transport)t;
		local = wt.local;
		objCheck = wt.getObjectChecker();
		inserter = local.newObjectInserter();
		reader = inserter.newReader();

		remotes = new ArrayList<>();
		remotes.add(w);

		unfetchedPacks = new LinkedList<>();
		packsConsidered = new HashSet<>();

		noPacksYet = new LinkedList<>();
		noPacksYet.add(w);

		noAlternatesYet = new LinkedList<>();
		noAlternatesYet.add(w);

		fetchErrors = new HashMap<>();
		packLocks = new ArrayList<>(4);

		revWalk = new RevWalk(reader);
		revWalk.setRetainBody(false);
		treeWalk = new TreeWalk(reader);

		localCommitQueue = new DateRevQueue();
		workQueue = new LinkedList<>();
	}

	@Override
	public boolean didFetchTestConnectivity() {
		return true;
	}

	@Override
	protected void doFetch(final ProgressMonitor monitor
			final Collection<Ref> want
			throws TransportException {
		markLocalRefsComplete(have);
		queueWants(want);

		while (!monitor.isCancelled() && !workQueue.isEmpty()) {
			final ObjectId id = workQueue.removeFirst();
			if (!(id instanceof RevObject) || !((RevObject) id).has(COMPLETE))
				downloadObject(monitor
			process(id);
		}

		try {
			inserter.flush();
		} catch (IOException e) {
			throw new TransportException(e.getMessage()
		}
	}

	@Override
	public Collection<PackLock> getPackLocks() {
		return packLocks;
	}

	@Override
	public void setPackLockMessage(String message) {
		lockMessage = message;
	}

	@Override
	public void close() {
		inserter.close();
		reader.close();
		for (RemotePack p : unfetchedPacks) {
			if (p.tmpIdx != null)
				p.tmpIdx.delete();
		}
		for (WalkRemoteObjectDatabase r : remotes)
			r.close();
	}

	private void queueWants(Collection<Ref> want)
			throws TransportException {
		final HashSet<ObjectId> inWorkQueue = new HashSet<>();
		for (Ref r : want) {
			final ObjectId id = r.getObjectId();
			if (id == null) {
				throw new NullPointerException(MessageFormat.format(
						JGitText.get().transportProvidedRefWithNoObjectId
			}
			try {
				final RevObject obj = revWalk.parseAny(id);
				if (obj.has(COMPLETE))
					continue;
				if (inWorkQueue.add(id)) {
					obj.add(IN_WORK_QUEUE);
					workQueue.add(obj);
				}
			} catch (MissingObjectException e) {
				if (inWorkQueue.add(id))
					workQueue.add(id);
			} catch (IOException e) {
				throw new TransportException(MessageFormat.format(JGitText.get().cannotRead
			}
		}
	}

	private void process(ObjectId id) throws TransportException {
		final RevObject obj;
		try {
			if (id instanceof RevObject) {
				obj = (RevObject) id;
				if (obj.has(COMPLETE))
					return;
				revWalk.parseHeaders(obj);
			} else {
				obj = revWalk.parseAny(id);
				if (obj.has(COMPLETE))
					return;
			}
		} catch (IOException e) {
			throw new TransportException(MessageFormat.format(JGitText.get().cannotRead
		}

		switch (obj.getType()) {
		case Constants.OBJ_BLOB:
			processBlob(obj);
			break;
		case Constants.OBJ_TREE:
			processTree(obj);
			break;
		case Constants.OBJ_COMMIT:
			processCommit(obj);
			break;
		case Constants.OBJ_TAG:
			processTag(obj);
			break;
		default:
			throw new TransportException(MessageFormat.format(JGitText.get().unknownObjectType
		}

		fetchErrors.remove(id);
	}

	private void processBlob(RevObject obj) throws TransportException {
		try {
			if (reader.has(obj
				obj.add(COMPLETE);
			else
				throw new TransportException(MessageFormat.format(JGitText
						.get().cannotReadBlob
						new MissingObjectException(obj
		} catch (IOException error) {
			throw new TransportException(MessageFormat.format(
					JGitText.get().cannotReadBlob
		}
	}

	private void processTree(RevObject obj) throws TransportException {
		try {
			treeWalk.reset(obj);
			while (treeWalk.next()) {
				final FileMode mode = treeWalk.getFileMode(0);
				final int sType = mode.getObjectType();

				switch (sType) {
				case Constants.OBJ_BLOB:
				case Constants.OBJ_TREE:
					treeWalk.getObjectId(idBuffer
					needs(revWalk.lookupAny(idBuffer
					continue;

				default:
					if (FileMode.GITLINK.equals(mode))
						continue;
					treeWalk.getObjectId(idBuffer
					throw new CorruptObjectException(MessageFormat.format(JGitText.get().invalidModeFor
							
				}
			}
		} catch (IOException ioe) {
			throw new TransportException(MessageFormat.format(JGitText.get().cannotReadTree
		}
		obj.add(COMPLETE);
	}

	private void processCommit(RevObject obj) throws TransportException {
		final RevCommit commit = (RevCommit) obj;
		markLocalCommitsComplete(commit.getCommitTime());
		needs(commit.getTree());
		for (RevCommit p : commit.getParents())
			needs(p);
		obj.add(COMPLETE);
	}

	private void processTag(RevObject obj) {
		final RevTag tag = (RevTag) obj;
		needs(tag.getObject());
		obj.add(COMPLETE);
	}

	private void needs(RevObject obj) {
		if (obj.has(COMPLETE))
			return;
		if (!obj.has(IN_WORK_QUEUE)) {
			obj.add(IN_WORK_QUEUE);
			workQueue.add(obj);
		}
	}

	private void downloadObject(ProgressMonitor pm
			throws TransportException {
		if (alreadyHave(id))
			return;

		for (;;) {
			if (downloadPackedObject(pm
				return;

			final String idStr = id.name();
			final String subdir = idStr.substring(0
			final String file = idStr.substring(2);

			for (int i = lastRemoteIdx; i < remotes.size(); i++) {
				if (downloadLooseObject(id
					lastRemoteIdx = i;
					return;
				}
			}
			for (int i = 0; i < lastRemoteIdx; i++) {
				if (downloadLooseObject(id
					lastRemoteIdx = i;
					return;
				}
			}

			while (!noPacksYet.isEmpty()) {
				final WalkRemoteObjectDatabase wrr = noPacksYet.removeFirst();
				final Collection<String> packNameList;
				try {
					pm.beginTask(JGitText.get().listingPacks
							ProgressMonitor.UNKNOWN);
					packNameList = wrr.getPackNames();
				} catch (IOException e) {
					recordError(id
					continue;
				} finally {
					pm.endTask();
				}

				if (packNameList == null || packNameList.isEmpty())
					continue;
				for (String packName : packNameList) {
					if (packsConsidered.add(packName))
						unfetchedPacks.add(new RemotePack(wrr
				}
				if (downloadPackedObject(pm
					return;
			}

			Collection<WalkRemoteObjectDatabase> al = expandOneAlternate(id
			if (al != null && !al.isEmpty()) {
				for (WalkRemoteObjectDatabase alt : al) {
					remotes.add(alt);
					noPacksYet.add(alt);
					noAlternatesYet.add(alt);
				}
				continue;
			}

			List<Throwable> failures = fetchErrors.get(id);
			final TransportException te;

			te = new TransportException(MessageFormat.format(JGitText.get().cannotGet
			if (failures != null && !failures.isEmpty()) {
				if (failures.size() == 1)
					te.initCause(failures.get(0));
				else
					te.initCause(new CompoundException(failures));
			}
			throw te;
		}
	}

	private boolean alreadyHave(AnyObjectId id) throws TransportException {
		try {
			return reader.has(id);
		} catch (IOException error) {
			throw new TransportException(MessageFormat.format(
					JGitText.get().cannotReadObject
		}
	}

	private boolean downloadPackedObject(final ProgressMonitor monitor
			final AnyObjectId id) throws TransportException {
		final Iterator<RemotePack> packItr = unfetchedPacks.iterator();
		while (packItr.hasNext() && !monitor.isCancelled()) {
			final RemotePack pack = packItr.next();
			try {
				pack.openIndex(monitor);
			} catch (IOException err) {
				recordError(id
				packItr.remove();
				continue;
			}

			if (monitor.isCancelled()) {
				return false;
			}

			if (!pack.index.hasObject(id)) {
				continue;
			}

			try {
				pack.downloadPack(monitor);
			} catch (IOException err) {
				recordError(id
				continue;
			} finally {
				try {
					if (pack.tmpIdx != null)
						FileUtils.delete(pack.tmpIdx);
				} catch (IOException e) {
					throw new TransportException(e.getMessage()
				}
				packItr.remove();
			}

			if (!alreadyHave(id)) {
				recordError(id
						JGitText.get().objectNotFoundIn
				continue;
			}

			final Iterator<ObjectId> pending = swapFetchQueue();
			while (pending.hasNext()) {
				final ObjectId p = pending.next();
				if (pack.index.hasObject(p)) {
					pending.remove();
					process(p);
				} else {
					workQueue.add(p);
				}
			}
			return true;

		}
		return false;
	}

	private Iterator<ObjectId> swapFetchQueue() {
		final Iterator<ObjectId> r = workQueue.iterator();
		workQueue = new LinkedList<>();
		return r;
	}

	private boolean downloadLooseObject(final AnyObjectId id
			final String looseName
			throws TransportException {
		try {
			final byte[] compressed = remote.open(looseName).toArray();
			verifyAndInsertLooseObject(id
			return true;
		} catch (FileNotFoundException e) {
			recordError(id
			return false;
		} catch (IOException e) {
			throw new TransportException(MessageFormat.format(JGitText.get().cannotDownload
		}
	}

	private void verifyAndInsertLooseObject(final AnyObjectId id
			final byte[] compressed) throws IOException {
		final ObjectLoader uol;
		try {
			uol = UnpackedObject.parse(compressed
		} catch (CorruptObjectException parsingError) {
			final FileNotFoundException e;
			e = new FileNotFoundException(id.name());
			e.initCause(parsingError);
			throw e;
		}

		final int type = uol.getType();
		final byte[] raw = uol.getCachedBytes();
		if (objCheck != null) {
			try {
				objCheck.check(id
			} catch (CorruptObjectException e) {
				throw new TransportException(MessageFormat.format(
						JGitText.get().transportExceptionInvalid
						Constants.typeString(type)
			}
		}

		ObjectId act = inserter.insert(type
		if (!AnyObjectId.equals(id
			throw new TransportException(MessageFormat.format(
					JGitText.get().incorrectHashFor
					Constants.typeString(type)
					Integer.valueOf(compressed.length)));
		}
	}

	private Collection<WalkRemoteObjectDatabase> expandOneAlternate(
			final AnyObjectId id
		while (!noAlternatesYet.isEmpty()) {
			final WalkRemoteObjectDatabase wrr = noAlternatesYet.removeFirst();
			try {
				pm.beginTask(JGitText.get().listingAlternates
				Collection<WalkRemoteObjectDatabase> altList = wrr
						.getAlternates();
				if (altList != null && !altList.isEmpty())
					return altList;
			} catch (IOException e) {
				recordError(id
			} finally {
				pm.endTask();
			}
		}
		return null;
	}

	private void markLocalRefsComplete(Set<ObjectId> have) throws TransportException {
		List<Ref> refs;
		try {
			refs = local.getRefDatabase().getRefs();
		} catch (IOException e) {
			throw new TransportException(e.getMessage()
		}
		for (Ref r : refs) {
			try {
				markLocalObjComplete(revWalk.parseAny(r.getObjectId()));
			} catch (IOException readError) {
				throw new TransportException(MessageFormat.format(JGitText.get().localRefIsMissingObjects
			}
		}
		for (ObjectId id : have) {
			try {
				markLocalObjComplete(revWalk.parseAny(id));
			} catch (IOException readError) {
				throw new TransportException(MessageFormat.format(JGitText.get().transportExceptionMissingAssumed
			}
		}
	}

	private void markLocalObjComplete(RevObject obj) throws IOException {
		while (obj.getType() == Constants.OBJ_TAG) {
			obj.add(COMPLETE);
			obj = ((RevTag) obj).getObject();
			revWalk.parseHeaders(obj);
		}

		switch (obj.getType()) {
		case Constants.OBJ_BLOB:
			obj.add(COMPLETE);
			break;
		case Constants.OBJ_COMMIT:
			pushLocalCommit((RevCommit) obj);
			break;
		case Constants.OBJ_TREE:
			markTreeComplete((RevTree) obj);
			break;
		}
	}

	private void markLocalCommitsComplete(int until)
			throws TransportException {
		try {
			for (;;) {
				final RevCommit c = localCommitQueue.peek();
				if (c == null || c.getCommitTime() < until)
					return;
				localCommitQueue.next();

				markTreeComplete(c.getTree());
				for (RevCommit p : c.getParents())
					pushLocalCommit(p);
			}
		} catch (IOException err) {
			throw new TransportException(JGitText.get().localObjectsIncomplete
		}
	}

	private void pushLocalCommit(RevCommit p)
			throws MissingObjectException
		if (p.has(LOCALLY_SEEN))
			return;
		revWalk.parseHeaders(p);
		p.add(LOCALLY_SEEN);
		p.add(COMPLETE);
		p.carry(COMPLETE);
		localCommitQueue.add(p);
	}

	private void markTreeComplete(RevTree tree) throws IOException {
		if (tree.has(COMPLETE))
			return;
		tree.add(COMPLETE);
		treeWalk.reset(tree);
		while (treeWalk.next()) {
			final FileMode mode = treeWalk.getFileMode(0);
			final int sType = mode.getObjectType();

			switch (sType) {
			case Constants.OBJ_BLOB:
				treeWalk.getObjectId(idBuffer
				revWalk.lookupAny(idBuffer
				continue;

			case Constants.OBJ_TREE: {
				treeWalk.getObjectId(idBuffer
				final RevObject o = revWalk.lookupAny(idBuffer
				if (!o.has(COMPLETE)) {
					o.add(COMPLETE);
					treeWalk.enterSubtree();
				}
				continue;
			}
			default:
				if (FileMode.GITLINK.equals(mode))
					continue;
				treeWalk.getObjectId(idBuffer
				throw new CorruptObjectException(MessageFormat.format(JGitText.get().corruptObjectInvalidMode3
						
			}
		}
	}

	private void recordError(AnyObjectId id
		final ObjectId objId = id.copy();
		List<Throwable> errors = fetchErrors.get(objId);
		if (errors == null) {
			errors = new ArrayList<>(2);
			fetchErrors.put(objId
		}
		errors.add(what);
	}

	private class RemotePack {
		final WalkRemoteObjectDatabase connection;

		final String packName;

		final String idxName;

		File tmpIdx;

		PackIndex index;

		RemotePack(WalkRemoteObjectDatabase c
			connection = c;
			packName = pn;
			idxName = packName.substring(0

			String tn = idxName;
				tn = tn.substring(5);
				tn = tn.substring(0

			if (local.getObjectDatabase() instanceof ObjectDirectory) {
				tmpIdx = new File(((ObjectDirectory) local.getObjectDatabase())
								.getDirectory()
			}
		}

		void openIndex(ProgressMonitor pm) throws IOException {
			if (index != null)
				return;
			if (tmpIdx == null)
				tmpIdx = File.createTempFile("jgit-walk-"
			else if (tmpIdx.isFile()) {
				try {
					index = PackIndex.open(tmpIdx);
					return;
				} catch (FileNotFoundException err) {
				}
			}

			final WalkRemoteObjectDatabase.FileStream s;
			pm.beginTask("Get " + idxName.substring(0
					s.length < 0 ? ProgressMonitor.UNKNOWN
							: (int) (s.length / 1024));
			try (FileOutputStream fos = new FileOutputStream(tmpIdx)) {
				final byte[] buf = new byte[2048];
				int cnt;
				while (!pm.isCancelled() && (cnt = s.in.read(buf)) >= 0) {
					fos.write(buf
					pm.update(cnt / 1024);
				}
			} catch (IOException err) {
				FileUtils.delete(tmpIdx);
				throw err;
			} finally {
				s.in.close();
			}
			pm.endTask();

			if (pm.isCancelled()) {
				FileUtils.delete(tmpIdx);
				return;
			}

			try {
				index = PackIndex.open(tmpIdx);
			} catch (IOException e) {
				FileUtils.delete(tmpIdx);
				throw e;
			}
		}

		void downloadPack(ProgressMonitor monitor) throws IOException {
			WalkRemoteObjectDatabase.FileStream s = connection.open(name);
			try {
				PackParser parser = inserter.newPackParser(s.in);
				parser.setAllowThin(false);
				parser.setObjectChecker(objCheck);
				parser.setLockMessage(lockMessage);
				PackLock lock = parser.parse(monitor);
				if (lock != null)
					packLocks.add(lock);
			} finally {
				s.in.close();
			}
		}
	}
}
