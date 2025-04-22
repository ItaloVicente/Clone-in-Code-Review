package org.eclipse.jgit.storage.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.pack.PackWriter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FileUtils;

public class GC {
	private final FileRepository repo;

	private ProgressMonitor pm;

	private long expireAgeMillis;

	private Map<String

	private long lastRepackTime;

	public GC(FileRepository repo) {
		this.repo = repo;
		this.pm = NullProgressMonitor.INSTANCE;
		this.expireAgeMillis = 14 * 24 * 60 * 60 * 1000L;
	}

	public Collection<PackFile> gc() throws IOException {
		packRefs();
		Collection<PackFile> newPacks = repack();
		prune(Collections.<ObjectId> emptySet());
		return newPacks;
	}

	private void deleteOldPacks(Collection<PackFile> oldPacks
			Collection<PackFile> newPacks
			throws IOException {
		int deleteOptions = FileUtils.RETRY | FileUtils.SKIP_MISSING;
		if (ignoreErrors)
			deleteOptions |= FileUtils.IGNORE_ERRORS;
		oldPackLoop: for (PackFile oldPack : oldPacks) {
			String oldName = oldPack.getPackName();
			for (PackFile newPack : newPacks)
				if (oldName.equals(newPack.getPackName()))
					continue oldPackLoop;

			if (!oldPack.shouldBeKept()) {
				oldPack.close();
				FileUtils.delete(nameFor(oldName
				FileUtils.delete(nameFor(oldName
			}
		}
		repo.getObjectDatabase().close();
	}

	public void prunePacked() throws IOException {
		ObjectDirectory objdb = repo.getObjectDatabase();
		Collection<PackFile> packs = objdb.getPacks();
		File objects = repo.getObjectsDirectory();
		String[] fanout = objects.list();

		if (fanout != null && fanout.length > 0) {
			pm.beginTask(JGitText.get().pruneLoosePackedObjects
			try {
				for (String d : fanout) {
					pm.update(1);
					if (d.length() != 2)
						continue;
					String[] entries = new File(objects
					if (entries == null)
						continue;
					for (String e : entries) {
						if (e.length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
							continue;
						ObjectId id;
						try {
							id = ObjectId.fromString(d + e);
						} catch (IllegalArgumentException notAnObject) {
							continue;
						}
						boolean found = false;
						for (PackFile p : packs)
							if (p.hasObject(id)) {
								found = true;
								break;
							}
						if (found)
							FileUtils.delete(objdb.fileFor(id)
									| FileUtils.SKIP_MISSING
									| FileUtils.IGNORE_ERRORS);
					}
				}
			} finally {
				pm.endTask();
			}
		}
	}

	public void prune(Set<ObjectId> objectsToKeep)
			throws IOException {
		long expireDate = (expireAgeMillis == 0) ? Long.MAX_VALUE : System
				.currentTimeMillis() - expireAgeMillis;

		Map<ObjectId
		Set<ObjectId> indexObjects = null;
		File objects = repo.getObjectsDirectory();
		String[] fanout = objects.list();
		if (fanout != null && fanout.length > 0) {
			pm.beginTask(JGitText.get().pruneLooseUnreferencedObjects
					fanout.length);
			for (String d : fanout) {
				pm.update(1);
				if (d.length() != 2)
					continue;
				File[] entries = new File(objects
				if (entries == null)
					continue;
				for (File f : entries) {
					String fName = f.getName();
					if (fName.length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
						continue;
					if (f.lastModified() >= expireDate)
						continue;
					try {
						ObjectId id = ObjectId.fromString(d + fName);
						if (objectsToKeep.contains(id))
							continue;
						if (indexObjects == null)
							indexObjects = listNonHEADIndexObjects();
						if (indexObjects.contains(id))
							continue;
						deletionCandidates.put(id
					} catch (IllegalArgumentException notAnObject) {
						continue;
					}
				}
			}
		}
		if (deletionCandidates.isEmpty())
			return;

		Map<String
		if (lastPackedRefs == null || lastPackedRefs.isEmpty())
			newRefs = getAllRefs();
		else {
			newRefs = new HashMap<String
			for (Iterator<Map.Entry<String
					.iterator(); i.hasNext();) {
				Entry<String
				Ref old = lastPackedRefs.get(newEntry.getKey());
				if (!equals(newEntry.getValue()
					newRefs.put(newEntry.getKey()
			}
		}

		if (!newRefs.isEmpty()) {
			ObjectWalk w = new ObjectWalk(repo);
			try {
				for (Ref cr : newRefs.values())
					w.markStart(w.parseAny(cr.getObjectId()));
				if (lastPackedRefs != null)
					for (Ref lpr : lastPackedRefs.values())
						w.markUninteresting(w.parseAny(lpr.getObjectId()));
				removeReferenced(deletionCandidates
			} finally {
				w.dispose();
			}
		}

		if (deletionCandidates.isEmpty())
			return;

		ObjectWalk w = new ObjectWalk(repo);
		try {
			for (Ref ar : getAllRefs().values())
				for (ObjectId id : listRefLogObjects(ar
					w.markStart(w.parseAny(id));
			if (lastPackedRefs != null)
				for (Ref lpr : lastPackedRefs.values())
					w.markUninteresting(w.parseAny(lpr.getObjectId()));
			removeReferenced(deletionCandidates
		} finally {
			w.dispose();
		}

		if (deletionCandidates.isEmpty())
			return;

		for (File f : deletionCandidates.values())
			f.delete();

		repo.getObjectDatabase().close();
	}

	private void removeReferenced(Map<ObjectId
			ObjectWalk w) throws MissingObjectException
			IncorrectObjectTypeException
		RevObject ro = w.next();
		while (ro != null) {
			if (id2File.remove(ro.getId()) != null)
				if (id2File.isEmpty())
					return;
			ro = w.next();
		}
		ro = w.nextObject();
		while (ro != null) {
			if (id2File.remove(ro.getId()) != null)
				if (id2File.isEmpty())
					return;
			ro = w.nextObject();
		}
	}

	private static boolean equals(Ref r1
		if (r1 == null || r2 == null)
			return false;
		if (r1.isSymbolic()) {
			if (!r2.isSymbolic())
				return false;
			return r1.getTarget().getName().equals(r2.getTarget().getName());
		} else {
			if (r2.isSymbolic())
				return false;
			return r1.getObjectId().equals(r2.getObjectId());
		}
	}

	public void packRefs() throws IOException {
		Collection<Ref> refs = repo.getAllRefs().values();
		List<String> refsToBePacked = new ArrayList<String>(refs.size());
		pm.beginTask(JGitText.get().packRefs
		try {
			for (Ref ref : refs) {
				if (!ref.isSymbolic() && ref.getStorage().isLoose())
					refsToBePacked.add(ref.getName());
				pm.update(1);
			}
			((RefDirectory) repo.getRefDatabase()).pack(refsToBePacked);
		} finally {
			pm.endTask();
		}
	}

	public Collection<PackFile> repack() throws IOException {
		Collection<PackFile> toBeDeleted = repo.getObjectDatabase().getPacks();

		long time = System.currentTimeMillis();
		Map<String

		Set<ObjectId> allHeads = new HashSet<ObjectId>();
		Set<ObjectId> nonHeads = new HashSet<ObjectId>();
		Set<ObjectId> tagTargets = new HashSet<ObjectId>();
		Set<ObjectId> indexObjects = listNonHEADIndexObjects();

		for (Ref ref : refsBefore.values()) {
			nonHeads.addAll(listRefLogObjects(ref
			if (ref.isSymbolic() || ref.getObjectId() == null)
				continue;
			if (ref.getName().startsWith(Constants.R_HEADS))
				allHeads.add(ref.getObjectId());
			else
				nonHeads.add(ref.getObjectId());
			if (ref.getPeeledObjectId() != null)
				tagTargets.add(ref.getPeeledObjectId());
		}

		List<PackIndex> excluded = new LinkedList<PackIndex>();
		for (PackFile f : repo.getObjectDatabase().getPacks())
			if (f.shouldBeKept())
				excluded.add(f.getIndex());

		tagTargets.addAll(allHeads);
		nonHeads.addAll(indexObjects);

		List<PackFile> ret = new ArrayList<PackFile>(2);
		PackFile heads = null;
		if (!allHeads.isEmpty()) {
			heads = writePack(allHeads
					tagTargets
			if (heads != null) {
				ret.add(heads);
				excluded.add(0
			}
		}
		if (!nonHeads.isEmpty()) {
			PackFile rest = writePack(nonHeads
			if (rest != null)
				ret.add(rest);
		}
		deleteOldPacks(toBeDeleted
		prunePacked();

		lastPackedRefs = refsBefore;
		lastRepackTime = time;
		return ret;
	}

	private Set<ObjectId> listRefLogObjects(Ref ref
		List<ReflogEntry> rlEntries = repo.getReflogReader(ref.getName())
				.getReverseEntries();
		if (rlEntries == null || rlEntries.isEmpty())
			return Collections.<ObjectId> emptySet();
		Set<ObjectId> ret = new HashSet<ObjectId>();
		for (ReflogEntry e : rlEntries) {
			if (e.getWho().getWhen().getTime() < minTime)
				break;
			ret.add(e.getNewId());
			ObjectId oldId = e.getOldId();
			if (oldId != null && !ObjectId.zeroId().equals(oldId))
				ret.add(oldId);
		}
		return ret;
	}

	private Map<String
		Map<String
		for (Ref ref : repo.getRefDatabase().getAdditionalRefs())
			ret.put(ref.getName()
		return ret;
	}

	private Set<ObjectId> listNonHEADIndexObjects()
			throws CorruptObjectException
		RevWalk revWalk = null;
		try {
			if (repo.getIndexFile() == null)
				return Collections.emptySet();
		} catch (NoWorkTreeException e) {
			return Collections.emptySet();
		}
		TreeWalk treeWalk = new TreeWalk(repo);
		try {
			treeWalk.addTree(new DirCacheIterator(repo.readDirCache()));
			ObjectId headID = repo.resolve(Constants.HEAD);
			if (headID != null) {
				revWalk = new RevWalk(repo);
				treeWalk.addTree(revWalk.parseTree(headID));
				revWalk.dispose();
				revWalk = null;
			}

			treeWalk.setFilter(TreeFilter.ANY_DIFF);
			treeWalk.setRecursive(true);
			Set<ObjectId> ret = new HashSet<ObjectId>();

			while (treeWalk.next()) {
				ObjectId objectId = treeWalk.getObjectId(0);
			    switch (treeWalk.getRawMode(0) & FileMode.TYPE_MASK) {
			      case FileMode.TYPE_MISSING:
			      case FileMode.TYPE_GITLINK:
			        continue;
			      case FileMode.TYPE_TREE:
			      case FileMode.TYPE_FILE:
			      case FileMode.TYPE_SYMLINK:
			        ret.add(objectId);
			        continue;
			      default:
					throw new IOException(MessageFormat.format(
							JGitText.get().corruptObjectInvalidMode3
									.format("%o"
											.getRawMode(0))
											(objectId == null) ? "null"
													: objectId.name()
											.getPathString()
											.getIndexFile())));
			    }
			  }
			return ret;
		} finally {
			if (revWalk != null)
				revWalk.dispose();
			treeWalk.release();
		}
	}

	private PackFile writePack(Set<? extends ObjectId> want
			Set<? extends ObjectId> have
			List<PackIndex> excludeObjects) throws IOException {
		File tmpPack = null;
		File tmpIdx = null;
		PackWriter pw = new PackWriter(repo);
		try {
			pw.setDeltaBaseAsOffset(true);
			pw.setReuseDeltaCommits(false);
			if (tagTargets != null)
				pw.setTagTargets(tagTargets);
			if (excludeObjects != null)
				for (PackIndex idx : excludeObjects)
					pw.excludeObjects(idx);
			pw.preparePack(pm
			if (pw.getObjectCount() == 0)
				return null;

			String id = pw.computeName().getName();
			File packdir = new File(repo.getObjectsDirectory()
			tmpPack = File.createTempFile("gc_"
			tmpIdx = new File(packdir
					tmpPack.getName().lastIndexOf('.'))
					+ ".idx_tmp");

			if (!tmpIdx.createNewFile())
				throw new IOException(MessageFormat.format(
						JGitText.get().cannotCreateIndexfile

			FileChannel channel = new FileOutputStream(tmpPack).getChannel();
			OutputStream channelStream = Channels.newOutputStream(channel);
			try {
				pw.writePack(pm
			} finally {
				channel.force(true);
				channelStream.close();
				channel.close();
			}

			FileChannel idxChannel = new FileOutputStream(tmpIdx).getChannel();
			OutputStream idxStream = Channels.newOutputStream(idxChannel);
			try {
				pw.writeIndex(idxStream);
			} finally {
				idxChannel.force(true);
				idxStream.close();
				idxChannel.close();
			}

			File realPack = nameFor(id
			tmpPack.setReadOnly();
			File realIdx = nameFor(id
			realIdx.setReadOnly();
			boolean delete = true;
			try {
				if (!tmpPack.renameTo(realPack))
					return null;
				delete = false;
				if (!tmpIdx.renameTo(realIdx)) {
					File newIdx = new File(realIdx.getParentFile()
							realIdx.getName() + ".new");
					if (!tmpIdx.renameTo(newIdx))
						newIdx = tmpIdx;
					throw new IOException(MessageFormat.format(
							JGitText.get().panicCantRenameIndexFile
							realIdx));
				}
			} finally {
				if (delete && tmpPack.exists())
					tmpPack.delete();
				if (delete && tmpIdx.exists())
					tmpIdx.delete();
			}
			return repo.getObjectDatabase().openPack(realPack
		} finally {
			pw.release();
			if (tmpPack != null && tmpPack.exists())
				tmpPack.delete();
			if (tmpIdx != null && tmpIdx.exists())
				tmpIdx.delete();
		}
	}

	private File nameFor(String name
		File packdir = new File(repo.getObjectsDirectory()
		return new File(packdir
	}

	public class RepoStatistics {
		public long numberOfPackedObjects;

		public long numberOfPackFiles;

		public long numberOfLooseObjects;
	}

	public RepoStatistics getStatistics() throws IOException {
		RepoStatistics ret = new RepoStatistics();
		Collection<PackFile> packs = repo.getObjectDatabase().getPacks();
		for (PackFile f : packs)
			ret.numberOfPackedObjects += f.getIndex().getObjectCount();
		ret.numberOfPackFiles = packs.size();
		File objDir = repo.getObjectsDirectory();
		String[] fanout = objDir.list();
		if (fanout != null && fanout.length > 0) {
			for (String d : fanout) {
				if (d.length() != 2)
					continue;
				String[] entries = new File(objDir
				if (entries == null)
					continue;
				for (String e : entries) {
					if (e.length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
						continue;
					ret.numberOfLooseObjects++;
				}
			}
		}
		return ret;
	}

	public GC setProgressMonitor(ProgressMonitor pm) {
		this.pm = (pm == null) ? NullProgressMonitor.INSTANCE : pm;
		return this;
	}

	public void setExpireAgeMillis(long expireAgeMillis) {
		this.expireAgeMillis = expireAgeMillis;
	}
}
