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
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.pack.PackWriter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FileUtils;

public class GC {
	private FileRepository repo;

	private ProgressMonitor pm;


	public GC(FileRepository repo
		this.repo = repo;
		this.pm = (pm == null) ? NullProgressMonitor.INSTANCE : pm;
	}

	public Collection<PackFile> gc(long expireAgeMillis) throws IOException {
		Collection<PackFile> newPacks = repack();
		prune(Collections.<ObjectId> emptySet()
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
			if (pm == null)
				pm = NullProgressMonitor.INSTANCE;
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

	public void prune(Set<ObjectId> objectsToKeep
			throws IOException {
		ObjectDirectory objdb = repo.getObjectDatabase();
		File objects = repo.getObjectsDirectory();
		String[] fanout = objects.list();
		long expireDate = System.currentTimeMillis() - expireAgeMillis;

		if (fanout != null && fanout.length > 0) {
			if (pm == null)
				pm = NullProgressMonitor.INSTANCE;
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
				w.checkConnectivity();
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
							if (f.lastModified() < expireDate)
								FileUtils.delete(f
										| FileUtils.SKIP_MISSING
										| FileUtils.IGNORE_ERRORS);
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
				for (ReflogEntry e : rlEntries)
					nonHeads.add(e.getNewId());
		}
		tagTargets.addAll(allHeads);
		nonHeads.addAll(indexObjects);

		List<PackFile> ret = new ArrayList<PackFile>(2);
		if (!allHeads.isEmpty()) {
			PackFile heads = writePack(allHeads
					Collections.<ObjectId> emptySet()
			if (heads != null)
				ret.add(heads);
		}
		if (!nonHeads.isEmpty()) {
			PackFile rest = writePack(nonHeads
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
		DirCache dc = null;
		try {
			if (repo.getIndexFile() == null)
				return Collections.emptySet();
		} catch (NoWorkTreeException e) {
			return Collections.emptySet();
		}
		TreeWalk treeWalk = new TreeWalk(repo);
		try {
			dc = repo.readDirCache();
			treeWalk.addTree(new DirCacheIterator(dc));
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
			throws IOException {

		PackWriter pw = new PackWriter(repo);
		pw.setDeltaBaseAsOffset(true);
		pw.setReuseDeltaCommits(false);
		if (tagTargets != null)
			pw.setTagTargets(tagTargets);
		try {
			pw.preparePack(pm
			if (0 < pw.getObjectCount()) {
				String id = pw.computeName().getName();
				File pack = nameFor(id
				File idx = nameFor(id
				if (!pack.createNewFile()) {
					for (PackFile f : repo.getObjectDatabase().getPacks())
						if (f.getPackName().equals(id))
							return f;
					throw new IOException(
							MessageFormat.format(
									JGitText.get().cannotCreatePackfile
									pack.getPath()));
				}
				if (!idx.createNewFile())
					throw new IOException(
							MessageFormat.format(
									JGitText.get().cannotCreateIndexfile
									idx.getPath()));
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
			} else
				return null;
		} finally {
			pw.release();
		}
	}

	private File nameFor(String name
		File packdir = new File(repo.getObjectsDirectory()
		return new File(packdir
	}

	class RepoStatistics {
		public long nrOfPackedObjects;

		public long nrOfPackFiles;

		public long nrOfLooseObjects;
	}

	public RepoStatistics getStatistics() throws IOException {
		RepoStatistics ret = new RepoStatistics();
		ret.nrOfPackedObjects = 0;
		for (PackFile f : repo.getObjectDatabase().getPacks())
			ret.nrOfPackedObjects += f.getObjectCount();
		ret.nrOfPackFiles = repo.getObjectDatabase().getPacks().size();
		ret.nrOfLooseObjects = 0;
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
					ret.nrOfLooseObjects++;
				}
			}
		}
		return ret;
	}
}
