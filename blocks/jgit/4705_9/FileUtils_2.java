package org.eclipse.jgit.storage.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.storage.pack.PackWriter;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FileUtils;

public class GC {
	public static Collection<PackFile> gc(ProgressMonitor pm
			FileRepository repo
			throws IOException {
		if (pm == null)
			pm = NullProgressMonitor.INSTANCE;

		Collection<PackFile> newPacks = repack(pm
		prune(pm
		return newPacks;
	}

	private static void deleteOldPacks(FileRepository repo
			Collection<PackFile> oldPacks
			boolean ignoreErrors)
			throws IOException {

		int deleteOptions = FileUtils.RETRY | FileUtils.SKIP_MISSING;
		if (ignoreErrors)
			deleteOptions |= FileUtils.IGNORE_ERRORS;
		oldPackLoop: for (PackFile oldPack : oldPacks) {
			String oldName = oldPack.getPackName();
			for (PackFile newPack : newPacks)
				if (oldName.equals(newPack.getPackName()))
					continue oldPackLoop;
			FileUtils.delete(nameFor(repo
			FileUtils.delete(nameFor(repo
		}
	}

	public static void prunePacked(ProgressMonitor pm
			Set<ObjectId> objectsToKeep) throws IOException {
		ObjectDirectory objdb = repo.getObjectDatabase();
		Collection<PackFile> packs = objdb.getPacks();
		File objects = repo.getObjectsDirectory();
		String[] fanout = objects.list();

		if (fanout != null && fanout.length > 0) {
			if (pm == null)
				pm = NullProgressMonitor.INSTANCE;
			pm.beginTask("prune loose objects"
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
						if (found && !objectsToKeep.contains(id))
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

	public static void prune(ProgressMonitor pm
			Set<ObjectId> objectsToKeep
		ObjectDirectory objdb = repo.getObjectDatabase();
		File objects = repo.getObjectsDirectory();
		String[] fanout = objects.list();
		long expireDate = System.currentTimeMillis()
				- (expireDays * 24 * 60 * 60 * 1000);

		if (fanout != null && fanout.length > 0) {
			if (pm == null)
				pm = NullProgressMonitor.INSTANCE;
			pm.beginTask("prune loose objects"
			try {
				ObjectWalk w=new ObjectWalk(repo);
				for (Ref f:repo.getAllRefs().values())
					w.markStart(w.parseAny(f.getObjectId()));
				for (Ref f:repo.getRefDatabase().getAdditionalRefs())
					w.markStart(w.parseAny(f.getObjectId()));
				for (ObjectId oid:listNonHEADIndexObjects(repo))
					w.markStart(w.parseAny(oid));
				while(w.nextObject()!=null);

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
			}
		}
	}

	public static Collection<PackFile> repack(ProgressMonitor pm
			FileRepository repo)
			throws IOException {
		Collection<PackFile> toBeDeleted = repo.getObjectDatabase().getPacks();

		PackConfig packConfig = new PackConfig(repo);
		if (packConfig.getIndexVersion() != 2)
			throw new IllegalStateException("Only index version 2");

		Map<String
		for (Ref ref : repo.getRefDatabase().getAdditionalRefs())
			refsBefore.put(ref.getName()

		Set<ObjectId> allHeads = new HashSet<ObjectId>();
		Set<ObjectId> nonHeads = new HashSet<ObjectId>();
		Set<ObjectId> tagTargets = new HashSet<ObjectId>();
		Set<ObjectId> indexObjects = listNonHEADIndexObjects(repo);

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
			PackFile heads = writePack(pm
					Collections.<ObjectId> emptySet()
			if (heads != null)
				ret.add(heads);
		}
		if (!nonHeads.isEmpty()) {
			PackFile rest = writePack(pm
			if (rest != null)
				ret.add(rest);
		}
		deleteOldPacks(repo
		prunePacked(pm
		return ret;
	}

	private static Set<ObjectId> listNonHEADIndexObjects(FileRepository repo)
			throws CorruptObjectException
			IOException {
		RevWalk revWalk = null;
		DirCache dc = null;
		try {
			if (repo.getIndexFile() == null)
				return (Collections.emptySet());
		} catch (NoWorkTreeException e) {
			return (Collections.emptySet());
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
				if (objectId != ObjectId.zeroId())
					ret.add(objectId);
			}
			return ret;
		} finally {
			if (revWalk != null)
				revWalk.dispose();
			treeWalk.release();
		}
	}

	private static PackFile writePack(ProgressMonitor pm
			Set<? extends ObjectId> want
			Set<ObjectId> tagTargets)
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
				File pack = nameFor(repo
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(pack));
				try {
					pw.writePack(pm
				} finally {
					out.close();
				}
				pack.setReadOnly();

				File idx = nameFor(repo
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

	private static File nameFor(FileRepository repo
		File packdir = new File(repo.getObjectsDirectory()
		return new File(packdir
	}
}
