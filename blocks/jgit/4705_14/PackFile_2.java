package org.eclipse.jgit.storage.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevCommit;
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

	public GC(FileRepository repo) {
		ProgressMonitor pm = NullProgressMonitor.INSTANCE;
		this.repo = repo;
		this.pm = (pm == null) ? NullProgressMonitor.INSTANCE : pm;
		this.expireAgeMillis = 14 * 24 * 60 * 60 * 1000l;
	}

	public Collection<PackFile> gc() throws IOException {
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
			oldPack.close();
			FileUtils.delete(nameFor(oldName
			FileUtils.delete(nameFor(oldName
		}
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
						boolean found = false;
						if (e.length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
							continue;
						ObjectId id;
						try {
							id = ObjectId.fromString(d + e);
						} catch (IllegalArgumentException notAnObject) {
							continue;
						}
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
		ObjectDirectory objdb = repo.getObjectDatabase();
		File objects = repo.getObjectsDirectory();
		String[] fanout = objects.list();
		long expireDate = System.currentTimeMillis() - expireAgeMillis;

		if (fanout != null && fanout.length > 0) {
			pm.beginTask(JGitText.get().pruneLooseUnreferencedObjects
					fanout.length);
			ObjectWalk w = null;
			try {
				w = new ObjectWalk(repo);
				for (Ref f : repo.getAllRefs().values())
					w.markStart(w.parseAny(f.getObjectId()));
				for (Ref f : repo.getRefDatabase().getAdditionalRefs())
					w.markStart(w.parseAny(f.getObjectId()));
				for (ObjectId oid : listNonHEADIndexObjects())
					w.markStart(w.parseAny(oid));
				for (;;) {
					final RevCommit c = w.next();
					if (c == null)
						break;
				}
				for (;;) {
					final RevObject o = w.nextObject();
					if (o == null)
						break;
				}
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

						if (w.lookupOrNull(id) == null
								&& !objectsToKeep.contains(id)) {
							File f = objdb.fileFor(id);
							if (f.lastModified() < expireDate) {
								FileUtils.delete(f
										| FileUtils.SKIP_MISSING
										| FileUtils.IGNORE_ERRORS);
								try {
									objdb.open(id);
								} catch (MissingObjectException moe) {
								}
							}
						}
					}
				}

			} finally {
				pm.endTask();
				if (w != null)
					w.dispose();
			}
		}
	}

	public Collection<PackFile> repack() throws IOException {
		Collection<PackFile> toBeDeleted = repo.getObjectDatabase().getPacks();

		Map<String
		for (Ref ref : repo.getRefDatabase().getAdditionalRefs())
			refsBefore.put(ref.getName()

		Set<ObjectId> allHeads = new HashSet<ObjectId>();
		Set<ObjectId> nonHeads = new HashSet<ObjectId>();
		Set<ObjectId> tagTargets = new HashSet<ObjectId>();
		Set<ObjectId> indexObjects = listNonHEADIndexObjects();

		for (Ref ref : refsBefore.values()) {
			if (ref.isSymbolic() || ref.getObjectId() == null)
				continue;
			if (ref.getName().startsWith(Constants.R_HEADS))
				allHeads.add(ref.getObjectId());
			else
				nonHeads.add(ref.getObjectId());
			if (ref.getPeeledObjectId() != null)
				tagTargets.add(ref.getPeeledObjectId());
			List<ReflogEntry> rlEntries = repo.getReflogReader(ref.getName())
					.getReverseEntries();
			if (rlEntries != null)
				for (ReflogEntry e : rlEntries) {
					nonHeads.add(e.getNewId());
					ObjectId oldId = e.getOldId();
					if (oldId != null && !ObjectId.zeroId().equals(oldId))
						nonHeads.add(oldId);
				}
		}
		tagTargets.addAll(allHeads);
		nonHeads.addAll(indexObjects);

		List<PackFile> ret = new ArrayList<PackFile>(2);
		PackFile heads = null;
		if (!allHeads.isEmpty()) {
			heads = writePack(allHeads
					tagTargets
			if (heads != null)
				ret.add(heads);
		}
		if (!nonHeads.isEmpty()) {
			PackFile rest = writePack(nonHeads
					(heads == null) ? null : Collections.singleton(heads
							.getIndex()));
			if (rest != null)
				ret.add(rest);
		}
		deleteOldPacks(toBeDeleted
		prunePacked();
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
			}

			treeWalk.setFilter(TreeFilter.ANY_DIFF);
			treeWalk.setRecursive(true);
			Set<ObjectId> ret = new HashSet<ObjectId>();
			while (treeWalk.next()) {
				ObjectId objectId = treeWalk.getObjectId(0);
				if (!ObjectId.zeroId().equals(objectId))
					ret.add(objectId);
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
			Set<PackIndex> excludeObjects) throws IOException {
		PackWriter pw = new PackWriter(repo);
		pw.setDeltaBaseAsOffset(true);
		pw.setReuseDeltaCommits(false);
		if (tagTargets != null)
			pw.setTagTargets(tagTargets);
		try {
			if (excludeObjects != null)
				for (PackIndex idx : excludeObjects)
					pw.excludeObjects(idx);
			pw.preparePack(pm
			if (pw.getObjectCount() == 0)
				return null;
			String id = pw.computeName().getName();
			File pack = nameFor(id
			File idx = nameFor(id

			if (!pack.createNewFile()) {
				for (PackFile f : repo.getObjectDatabase().getPacks())
					if (f.getPackName().equals(id))
						return f;
				throw new IOException(MessageFormat.format(
						JGitText.get().cannotCreatePackfile
			}
			if (!idx.createNewFile())
				throw new IOException(MessageFormat.format(
						JGitText.get().cannotCreateIndexfile
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(pack));

			try {
				pw.writePack(pm
			} finally {
				out.close();
			}
			pack.setReadOnly();

			out = new BufferedOutputStream(new FileOutputStream(idx));
			try {
				pw.writeIndex(out);
			} finally {
				out.close();
			}
			idx.setReadOnly();
			return repo.getObjectDatabase().openPack(pack
		} finally {
			pw.release();
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
		ret.numberOfPackedObjects = 0;
		for (PackFile f : repo.getObjectDatabase().getPacks())
			ret.numberOfPackedObjects += f.getObjectCount();
		ret.numberOfPackFiles = repo.getObjectDatabase().getPacks().size();
		ret.numberOfLooseObjects = 0;
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

	public void setProgressMonitor(ProgressMonitor pm) {
		this.pm = ((pm == null) ? NullProgressMonitor.INSTANCE : pm);
	}

	public void setExpireAgeMillis(long expireAgeMillis) {
		this.expireAgeMillis = expireAgeMillis;
	}
}
