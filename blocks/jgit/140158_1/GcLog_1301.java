package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.internal.storage.pack.PackExt.BITMAP_INDEX;
import static org.eclipse.jgit.internal.storage.pack.PackExt.INDEX;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.CancelledException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.internal.storage.reftree.RefTreeNames;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSet;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.internal.WorkQueue;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.GitDateParser;
import org.eclipse.jgit.util.SystemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GC {
	private final static Logger LOG = LoggerFactory
			.getLogger(GC.class);



	private static final Pattern PATTERN_LOOSE_OBJECT = Pattern


			+ PackExt.BITMAP_INDEX.getExtension();


	private static final int DEFAULT_AUTOPACKLIMIT = 50;

	private static final int DEFAULT_AUTOLIMIT = 6700;

	private static volatile ExecutorService executor;

	public static void setExecutor(ExecutorService e) {
		executor = e;
	}

	private final FileRepository repo;

	private ProgressMonitor pm;

	private long expireAgeMillis = -1;

	private Date expire;

	private long packExpireAgeMillis = -1;

	private Date packExpire;

	private PackConfig pconfig = null;

	private Collection<Ref> lastPackedRefs;

	private long lastRepackTime;

	private boolean automatic;

	private boolean background;

	public GC(FileRepository repo) {
		this.repo = repo;
		this.pm = NullProgressMonitor.INSTANCE;
	}

	@SuppressWarnings("FutureReturnValueIgnored")
	public Collection<PackFile> gc() throws IOException
		if (!background) {
			return doGc();
		}
		final GcLog gcLog = new GcLog(repo);
		if (!gcLog.lock()) {
			return Collections.emptyList();
		}

		Callable<Collection<PackFile>> gcTask = () -> {
			try {
				Collection<PackFile> newPacks = doGc();
				if (automatic && tooManyLooseObjects()) {
					String message = JGitText.get().gcTooManyUnpruned;
					gcLog.write(message);
					gcLog.commit();
				}
				return newPacks;
			} catch (IOException | ParseException e) {
				try {
					gcLog.write(e.getMessage());
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					gcLog.write(sw.toString());
					gcLog.commit();
				} catch (IOException e2) {
					e2.addSuppressed(e);
					LOG.error(e2.getMessage()
				}
			} finally {
				gcLog.unlock();
			}
			return Collections.emptyList();
		};
		executor().submit(gcTask);
		return Collections.emptyList();
	}

	private ExecutorService executor() {
		return (executor != null) ? executor : WorkQueue.getExecutor();
	}

	private Collection<PackFile> doGc() throws IOException
		if (automatic && !needGc()) {
			return Collections.emptyList();
		}
		packRefs();
		Collection<PackFile> newPacks = repack();
		prune(Collections.emptySet());
		return newPacks;
	}

	private void loosen(ObjectDirectoryInserter inserter
			throws IOException {
		for (PackIndex.MutableEntry entry : pack) {
			ObjectId oid = entry.toObjectId();
			if (existing.contains(oid)) {
				continue;
			}
			existing.add(oid);
			ObjectLoader loader = reader.open(oid);
			inserter.insert(loader.getType()
					loader.getSize()
					loader.openStream()
		}
	}

	private void deleteOldPacks(Collection<PackFile> oldPacks
			Collection<PackFile> newPacks) throws ParseException
		HashSet<ObjectId> ids = new HashSet<>();
		for (PackFile pack : newPacks) {
			for (PackIndex.MutableEntry entry : pack) {
				ids.add(entry.toObjectId());
			}
		}
		ObjectReader reader = repo.newObjectReader();
		ObjectDirectory dir = repo.getObjectDatabase();
		ObjectDirectoryInserter inserter = dir.newInserter();
			getExpireDate() < Long.MAX_VALUE;

		prunePreserved();
		long packExpireDate = getPackExpireDate();
		oldPackLoop: for (PackFile oldPack : oldPacks) {
			checkCancelled();
			String oldName = oldPack.getPackName();
			for (PackFile newPack : newPacks)
				if (oldName.equals(newPack.getPackName()))
					continue oldPackLoop;

			if (!oldPack.shouldBeKept()
					&& repo.getFS().lastModified(
							oldPack.getPackFile()) < packExpireDate) {
				oldPack.close();
				if (shouldLoosen) {
					loosen(inserter
				}
				prunePack(oldName);
			}
		}

		repo.getObjectDatabase().close();
	}

	private void removeOldPack(File packFile
			int deleteOptions) throws IOException {
		if (pconfig != null && pconfig.isPreserveOldPacks()) {
			File oldPackDir = repo.getObjectDatabase().getPreservedDirectory();
			FileUtils.mkdir(oldPackDir

			File oldPackFile = new File(oldPackDir
			FileUtils.rename(packFile
		} else {
			FileUtils.delete(packFile
		}
	}

	private void prunePreserved() {
		if (pconfig != null && pconfig.isPrunePreserved()) {
			try {
				FileUtils.delete(repo.getObjectDatabase().getPreservedDirectory()
						FileUtils.RECURSIVE | FileUtils.RETRY | FileUtils.SKIP_MISSING);
			} catch (IOException e) {
			}
		}
	}

	private void prunePack(String packName) {
		PackExt[] extensions = PackExt.values();
		try {
			int deleteOptions = FileUtils.RETRY | FileUtils.SKIP_MISSING;
			for (PackExt ext : extensions)
				if (PackExt.PACK.equals(ext)) {
					File f = nameFor(packName
					removeOldPack(f
					break;
				}
			deleteOptions |= FileUtils.IGNORE_ERRORS;
			for (PackExt ext : extensions) {
				if (!PackExt.PACK.equals(ext)) {
					File f = nameFor(packName
					removeOldPack(f
				}
			}
		} catch (IOException e) {
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
					checkCancelled();
					pm.update(1);
					if (d.length() != 2)
						continue;
					String[] entries = new File(objects
					if (entries == null)
						continue;
					for (String e : entries) {
						checkCancelled();
						if (e.length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
							continue;
						ObjectId id;
						try {
							id = ObjectId.fromString(d + e);
						} catch (IllegalArgumentException notAnObject) {
							continue;
						}
						boolean found = false;
						for (PackFile p : packs) {
							checkCancelled();
							if (p.hasObject(id)) {
								found = true;
								break;
							}
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

	public void prune(Set<ObjectId> objectsToKeep) throws IOException
			ParseException {
		long expireDate = getExpireDate();

		Map<ObjectId
		Set<ObjectId> indexObjects = null;
		File objects = repo.getObjectsDirectory();
		String[] fanout = objects.list();
		if (fanout == null || fanout.length == 0) {
			return;
		}
		pm.beginTask(JGitText.get().pruneLooseUnreferencedObjects
				fanout.length);
		try {
			for (String d : fanout) {
				checkCancelled();
				pm.update(1);
				if (d.length() != 2)
					continue;
				File dir = new File(objects
				File[] entries = dir.listFiles();
				if (entries == null || entries.length == 0) {
					FileUtils.delete(dir
					continue;
				}
				for (File f : entries) {
					checkCancelled();
					String fName = f.getName();
					if (fName.length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
						continue;
					if (repo.getFS().lastModified(f) >= expireDate)
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
					}
				}
			}
		} finally {
			pm.endTask();
		}

		if (deletionCandidates.isEmpty()) {
			return;
		}

		checkCancelled();

		Collection<Ref> newRefs;
		if (lastPackedRefs == null || lastPackedRefs.isEmpty())
			newRefs = getAllRefs();
		else {
			Map<String
			for (Ref r : lastPackedRefs) {
				last.put(r.getName()
			}
			newRefs = new ArrayList<>();
			for (Ref r : getAllRefs()) {
				Ref old = last.get(r.getName());
				if (!equals(r
					newRefs.add(r);
				}
			}
		}

		if (!newRefs.isEmpty()) {
			ObjectWalk w = new ObjectWalk(repo);
			try {
				for (Ref cr : newRefs) {
					checkCancelled();
					w.markStart(w.parseAny(cr.getObjectId()));
				}
				if (lastPackedRefs != null)
					for (Ref lpr : lastPackedRefs) {
						w.markUninteresting(w.parseAny(lpr.getObjectId()));
					}
				removeReferenced(deletionCandidates
			} finally {
				w.dispose();
			}
		}

		if (deletionCandidates.isEmpty())
			return;

		ObjectWalk w = new ObjectWalk(repo);
		try {
			for (Ref ar : getAllRefs())
				for (ObjectId id : listRefLogObjects(ar
					checkCancelled();
					w.markStart(w.parseAny(id));
				}
			if (lastPackedRefs != null)
				for (Ref lpr : lastPackedRefs) {
					checkCancelled();
					w.markUninteresting(w.parseAny(lpr.getObjectId()));
				}
			removeReferenced(deletionCandidates
		} finally {
			w.dispose();
		}

		if (deletionCandidates.isEmpty())
			return;

		checkCancelled();

		Set<File> touchedFanout = new HashSet<>();
		for (File f : deletionCandidates.values()) {
			if (f.lastModified() < expireDate) {
				f.delete();
				touchedFanout.add(f.getParentFile());
			}
		}

		for (File f : touchedFanout) {
			FileUtils.delete(f
					FileUtils.EMPTY_DIRECTORIES_ONLY | FileUtils.IGNORE_ERRORS);
		}

		repo.getObjectDatabase().close();
	}

	private long getExpireDate() throws ParseException {
		long expireDate = Long.MAX_VALUE;

		if (expire == null && expireAgeMillis == -1) {
			String pruneExpireStr = getPruneExpireStr();
			if (pruneExpireStr == null)
				pruneExpireStr = PRUNE_EXPIRE_DEFAULT;
			expire = GitDateParser.parse(pruneExpireStr
					.getInstance().getLocale());
			expireAgeMillis = -1;
		}
		if (expire != null)
			expireDate = expire.getTime();
		if (expireAgeMillis != -1)
			expireDate = System.currentTimeMillis() - expireAgeMillis;
		return expireDate;
	}

	private String getPruneExpireStr() {
		return repo.getConfig().getString(
                        ConfigConstants.CONFIG_GC_SECTION
                        ConfigConstants.CONFIG_KEY_PRUNEEXPIRE);
	}

	private long getPackExpireDate() throws ParseException {
		long packExpireDate = Long.MAX_VALUE;

		if (packExpire == null && packExpireAgeMillis == -1) {
			String prunePackExpireStr = repo.getConfig().getString(
					ConfigConstants.CONFIG_GC_SECTION
					ConfigConstants.CONFIG_KEY_PRUNEPACKEXPIRE);
			if (prunePackExpireStr == null)
				prunePackExpireStr = PRUNE_PACK_EXPIRE_DEFAULT;
			packExpire = GitDateParser.parse(prunePackExpireStr
					SystemReader.getInstance().getLocale());
			packExpireAgeMillis = -1;
		}
		if (packExpire != null)
			packExpireDate = packExpire.getTime();
		if (packExpireAgeMillis != -1)
			packExpireDate = System.currentTimeMillis() - packExpireAgeMillis;
		return packExpireDate;
	}

	private void removeReferenced(Map<ObjectId
			ObjectWalk w) throws MissingObjectException
			IncorrectObjectTypeException
		RevObject ro = w.next();
		while (ro != null) {
			checkCancelled();
			if (id2File.remove(ro.getId()) != null && id2File.isEmpty()) {
				return;
			}
			ro = w.next();
		}
		ro = w.nextObject();
		while (ro != null) {
			checkCancelled();
			if (id2File.remove(ro.getId()) != null && id2File.isEmpty()) {
				return;
			}
			ro = w.nextObject();
		}
	}

	private static boolean equals(Ref r1
		if (r1 == null || r2 == null) {
			return false;
		}
		if (r1.isSymbolic()) {
			return r2.isSymbolic() && r1.getTarget().getName()
					.equals(r2.getTarget().getName());
		}
		return !r2.isSymbolic()
				&& Objects.equals(r1.getObjectId()
	}

	public void packRefs() throws IOException {
		Collection<Ref> refs = repo.getRefDatabase()
				.getRefsByPrefix(Constants.R_REFS);
		List<String> refsToBePacked = new ArrayList<>(refs.size());
		pm.beginTask(JGitText.get().packRefs
		try {
			for (Ref ref : refs) {
				checkCancelled();
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
		Collection<Ref> refsBefore = getAllRefs();

		Set<ObjectId> allHeadsAndTags = new HashSet<>();
		Set<ObjectId> allHeads = new HashSet<>();
		Set<ObjectId> allTags = new HashSet<>();
		Set<ObjectId> nonHeads = new HashSet<>();
		Set<ObjectId> txnHeads = new HashSet<>();
		Set<ObjectId> tagTargets = new HashSet<>();
		Set<ObjectId> indexObjects = listNonHEADIndexObjects();
		RefDatabase refdb = repo.getRefDatabase();

		for (Ref ref : refsBefore) {
			checkCancelled();
			nonHeads.addAll(listRefLogObjects(ref
			if (ref.isSymbolic() || ref.getObjectId() == null) {
				continue;
			}
			if (isHead(ref)) {
				allHeads.add(ref.getObjectId());
			} else if (isTag(ref)) {
				allTags.add(ref.getObjectId());
			} else if (RefTreeNames.isRefTree(refdb
				txnHeads.add(ref.getObjectId());
			} else {
				nonHeads.add(ref.getObjectId());
			}
			if (ref.getPeeledObjectId() != null) {
				tagTargets.add(ref.getPeeledObjectId());
			}
		}

		List<ObjectIdSet> excluded = new LinkedList<>();
		for (PackFile f : repo.getObjectDatabase().getPacks()) {
			checkCancelled();
			if (f.shouldBeKept())
				excluded.add(f.getIndex());
		}

		allTags.removeAll(allHeads);
		allHeadsAndTags.addAll(allHeads);
		allHeadsAndTags.addAll(allTags);

		tagTargets.addAll(allHeadsAndTags);
		nonHeads.addAll(indexObjects);

		if (pconfig != null && pconfig.getSinglePack()) {
			allHeadsAndTags.addAll(nonHeads);
			nonHeads.clear();
		}

		List<PackFile> ret = new ArrayList<>(2);
		PackFile heads = null;
		if (!allHeadsAndTags.isEmpty()) {
			heads = writePack(allHeadsAndTags
					tagTargets
			if (heads != null) {
				ret.add(heads);
				excluded.add(0
			}
		}
		if (!nonHeads.isEmpty()) {
			PackFile rest = writePack(nonHeads
					tagTargets
			if (rest != null)
				ret.add(rest);
		}
		if (!txnHeads.isEmpty()) {
			PackFile txn = writePack(txnHeads
					null
			if (txn != null)
				ret.add(txn);
		}
		try {
			deleteOldPacks(toBeDeleted
		} catch (ParseException e) {
			throw new IOException(e);
		}
		prunePacked();
		deleteEmptyRefsFolders();
		deleteOrphans();
		deleteTempPacksIdx();

		lastPackedRefs = refsBefore;
		lastRepackTime = time;
		return ret;
	}

	private static boolean isHead(Ref ref) {
		return ref.getName().startsWith(Constants.R_HEADS);
	}

	private static boolean isTag(Ref ref) {
		return ref.getName().startsWith(Constants.R_TAGS);
	}

	private void deleteEmptyRefsFolders() throws IOException {
		Path refs = repo.getDirectory().toPath().resolve(Constants.R_REFS);
		Instant threshold = Instant.now().minus(30
		try (Stream<Path> entries = Files.list(refs)
				.filter(Files::isDirectory)) {
			Iterator<Path> iterator = entries.iterator();
			while (iterator.hasNext()) {
				try (Stream<Path> s = Files.list(iterator.next())) {
					s.filter(path -> canBeSafelyDeleted(path
				}
			}
		}
	}

	private boolean canBeSafelyDeleted(Path path
		try {
			return Files.getLastModifiedTime(path).toInstant().isBefore(threshold);
		}
		catch (IOException e) {
			LOG.warn(MessageFormat.format(
					JGitText.get().cannotAccessLastModifiedForSafeDeletion
					path)
			return false;
		}
	}

	private void deleteDir(Path dir) {
		try (Stream<Path> dirs = Files.walk(dir)) {
			dirs.filter(this::isDirectory).sorted(Comparator.reverseOrder())
					.forEach(this::delete);
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}
	}

	private boolean isDirectory(Path p) {
		return p.toFile().isDirectory();
	}

	private void delete(Path d) {
		try {
			Files.delete(d);
		} catch (DirectoryNotEmptyException e) {
		} catch (IOException e) {
			LOG.error(MessageFormat.format(JGitText.get().cannotDeleteFile
					e);
		}
	}

	private void deleteOrphans() {
		Path packDir = repo.getObjectDatabase().getPackDirectory().toPath();
		List<String> fileNames = null;
		try (Stream<Path> files = Files.list(packDir)) {
			fileNames = files.map(path -> path.getFileName().toString())
					.filter(name -> (name.endsWith(PACK_EXT)
							|| name.endsWith(BITMAP_EXT)
							|| name.endsWith(INDEX_EXT)))
					.sorted(Collections.reverseOrder())
					.collect(Collectors.toList());
		} catch (IOException e1) {
		}
		if (fileNames == null) {
			return;
		}

		String base = null;
		for (String n : fileNames) {
			if (n.endsWith(PACK_EXT)) {
				base = n.substring(0
			} else {
				if (base == null || !n.startsWith(base)) {
					try {
						Files.delete(packDir.resolve(n));
					} catch (IOException e) {
						LOG.error(e.getMessage()
					}
				}
			}
		}
	}

	private void deleteTempPacksIdx() {
		Path packDir = repo.getObjectDatabase().getPackDirectory().toPath();
		Instant threshold = Instant.now().minus(1
		if (!Files.exists(packDir)) {
			return;
		}
		try (DirectoryStream<Path> stream =
				Files.newDirectoryStream(packDir
			stream.forEach(t -> {
				try {
					Instant lastModified = Files.getLastModifiedTime(t)
							.toInstant();
					if (lastModified.isBefore(threshold)) {
						Files.deleteIfExists(t);
					}
				} catch (IOException e) {
					LOG.error(e.getMessage()
				}
			});
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}
	}

	private Set<ObjectId> listRefLogObjects(Ref ref
		ReflogReader reflogReader = repo.getReflogReader(ref.getName());
		if (reflogReader == null) {
			return Collections.emptySet();
		}
		List<ReflogEntry> rlEntries = reflogReader
				.getReverseEntries();
		if (rlEntries == null || rlEntries.isEmpty())
			return Collections.emptySet();
		Set<ObjectId> ret = new HashSet<>();
		for (ReflogEntry e : rlEntries) {
			if (e.getWho().getWhen().getTime() < minTime)
				break;
			ObjectId newId = e.getNewId();
			if (newId != null && !ObjectId.zeroId().equals(newId))
				ret.add(newId);
			ObjectId oldId = e.getOldId();
			if (oldId != null && !ObjectId.zeroId().equals(oldId))
				ret.add(oldId);
		}
		return ret;
	}

	private Collection<Ref> getAllRefs() throws IOException {
		RefDatabase refdb = repo.getRefDatabase();
		Collection<Ref> refs = refdb.getRefs();
		List<Ref> addl = refdb.getAdditionalRefs();
		if (!addl.isEmpty()) {
			List<Ref> all = new ArrayList<>(refs.size() + addl.size());
			all.addAll(refs);
			for (Ref r : addl) {
				checkCancelled();
				if (r.getName().startsWith(Constants.R_REFS)) {
					all.add(r);
				}
			}
			return all;
		}
		return refs;
	}

	private Set<ObjectId> listNonHEADIndexObjects()
			throws CorruptObjectException
		if (repo.isBare()) {
			return Collections.emptySet();
		}
		try (TreeWalk treeWalk = new TreeWalk(repo)) {
			treeWalk.addTree(new DirCacheIterator(repo.readDirCache()));
			ObjectId headID = repo.resolve(Constants.HEAD);
			if (headID != null) {
				try (RevWalk revWalk = new RevWalk(repo)) {
					treeWalk.addTree(revWalk.parseTree(headID));
				}
			}

			treeWalk.setFilter(TreeFilter.ANY_DIFF);
			treeWalk.setRecursive(true);
			Set<ObjectId> ret = new HashSet<>();

			while (treeWalk.next()) {
				checkCancelled();
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
							String.format("%o"
									Integer.valueOf(treeWalk.getRawMode(0)))
							(objectId == null) ? "null" : objectId.name()
							treeWalk.getPathString()
							repo.getIndexFile()));
				}
			}
			return ret;
		}
	}

	private PackFile writePack(@NonNull Set<? extends ObjectId> want
			@NonNull Set<? extends ObjectId> have
			Set<ObjectId> tagTargets
			throws IOException {
		checkCancelled();
		File tmpPack = null;
		Map<PackExt
			if (o1 == o2) {
				return 0;
			}
			if (o1 == PackExt.INDEX) {
				return 1;
			}
			if (o2 == PackExt.INDEX) {
				return -1;
			}
			return Integer.signum(o1.hashCode() - o2.hashCode());
		});
		try (PackWriter pw = new PackWriter(
				(pconfig == null) ? new PackConfig(repo) : pconfig
				repo.newObjectReader())) {
			pw.setDeltaBaseAsOffset(true);
			pw.setReuseDeltaCommits(false);
			if (tagTargets != null) {
				pw.setTagTargets(tagTargets);
			}
			if (excludeObjects != null)
				for (ObjectIdSet idx : excludeObjects)
					pw.excludeObjects(idx);
			pw.preparePack(pm
			if (pw.getObjectCount() == 0)
				return null;
			checkCancelled();

			String id = pw.computeName().getName();
			File packdir = repo.getObjectDatabase().getPackDirectory();
			tmpPack = File.createTempFile("gc_"
			final String tmpBase = tmpPack.getName()
					.substring(0
			File tmpIdx = new File(packdir
			tmpExts.put(INDEX

			if (!tmpIdx.createNewFile())
				throw new IOException(MessageFormat.format(
						JGitText.get().cannotCreateIndexfile

			try (FileOutputStream fos = new FileOutputStream(tmpPack);
					FileChannel channel = fos.getChannel();
					OutputStream channelStream = Channels
							.newOutputStream(channel)) {
				pw.writePack(pm
				channel.force(true);
			}

			try (FileOutputStream fos = new FileOutputStream(tmpIdx);
					FileChannel idxChannel = fos.getChannel();
					OutputStream idxStream = Channels
							.newOutputStream(idxChannel)) {
				pw.writeIndex(idxStream);
				idxChannel.force(true);
			}

			if (pw.prepareBitmapIndex(pm)) {
				File tmpBitmapIdx = new File(packdir
				tmpExts.put(BITMAP_INDEX

				if (!tmpBitmapIdx.createNewFile())
					throw new IOException(MessageFormat.format(
							JGitText.get().cannotCreateIndexfile
							tmpBitmapIdx.getPath()));

				try (FileOutputStream fos = new FileOutputStream(tmpBitmapIdx);
						FileChannel idxChannel = fos.getChannel();
						OutputStream idxStream = Channels
								.newOutputStream(idxChannel)) {
					pw.writeBitmapIndex(idxStream);
					idxChannel.force(true);
				}
			}

			File realPack = nameFor(id

			repo.getObjectDatabase().closeAllPackHandles(realPack);
			tmpPack.setReadOnly();

			FileUtils.rename(tmpPack
			for (Map.Entry<PackExt
				File tmpExt = tmpEntry.getValue();
				tmpExt.setReadOnly();

				File realExt = nameFor(id
				try {
					FileUtils.rename(tmpExt
							StandardCopyOption.ATOMIC_MOVE);
				} catch (IOException e) {
					File newExt = new File(realExt.getParentFile()
					try {
						FileUtils.rename(tmpExt
								StandardCopyOption.ATOMIC_MOVE);
					} catch (IOException e2) {
						newExt = tmpExt;
						e = e2;
					}
					throw new IOException(MessageFormat.format(
							JGitText.get().panicCantRenameIndexFile
							realExt)
				}
			}

			return repo.getObjectDatabase().openPack(realPack);
		} finally {
			if (tmpPack != null && tmpPack.exists())
				tmpPack.delete();
			for (File tmpExt : tmpExts.values()) {
				if (tmpExt.exists())
					tmpExt.delete();
			}
		}
	}

	private File nameFor(String name
		File packdir = repo.getObjectDatabase().getPackDirectory();
		return new File(packdir
	}

	private void checkCancelled() throws CancelledException {
		if (pm.isCancelled() || Thread.currentThread().isInterrupted()) {
			throw new CancelledException(JGitText.get().operationCanceled);
		}
	}

	public static class RepoStatistics {
		public long numberOfPackedObjects;

		public long numberOfPackFiles;

		public long numberOfLooseObjects;

		public long sizeOfLooseObjects;

		public long sizeOfPackedObjects;

		public long numberOfLooseRefs;

		public long numberOfPackedRefs;

		public long numberOfBitmaps;

		@Override
		public String toString() {
			final StringBuilder b = new StringBuilder();
			b.append("
			b.append("
			b.append("
			b.append("
			b.append("
			b.append("
			b.append("
			return b.toString();
		}
	}

	public RepoStatistics getStatistics() throws IOException {
		RepoStatistics ret = new RepoStatistics();
		Collection<PackFile> packs = repo.getObjectDatabase().getPacks();
		for (PackFile f : packs) {
			ret.numberOfPackedObjects += f.getIndex().getObjectCount();
			ret.numberOfPackFiles++;
			ret.sizeOfPackedObjects += f.getPackFile().length();
			if (f.getBitmapIndex() != null)
				ret.numberOfBitmaps += f.getBitmapIndex().getBitmapCount();
		}
		File objDir = repo.getObjectsDirectory();
		String[] fanout = objDir.list();
		if (fanout != null && fanout.length > 0) {
			for (String d : fanout) {
				if (d.length() != 2)
					continue;
				File[] entries = new File(objDir
				if (entries == null)
					continue;
				for (File f : entries) {
					if (f.getName().length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
						continue;
					ret.numberOfLooseObjects++;
					ret.sizeOfLooseObjects += f.length();
				}
			}
		}

		RefDatabase refDb = repo.getRefDatabase();
		for (Ref r : refDb.getRefs()) {
			Storage storage = r.getStorage();
			if (storage == Storage.LOOSE || storage == Storage.LOOSE_PACKED)
				ret.numberOfLooseRefs++;
			if (storage == Storage.PACKED || storage == Storage.LOOSE_PACKED)
				ret.numberOfPackedRefs++;
		}

		return ret;
	}

	public GC setProgressMonitor(ProgressMonitor pm) {
		this.pm = (pm == null) ? NullProgressMonitor.INSTANCE : pm;
		return this;
	}

	public void setExpireAgeMillis(long expireAgeMillis) {
		this.expireAgeMillis = expireAgeMillis;
		expire = null;
	}

	public void setPackExpireAgeMillis(long packExpireAgeMillis) {
		this.packExpireAgeMillis = packExpireAgeMillis;
		expire = null;
	}

	public void setPackConfig(PackConfig pconfig) {
		this.pconfig = pconfig;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
		expireAgeMillis = -1;
	}

	public void setPackExpire(Date packExpire) {
		this.packExpire = packExpire;
		packExpireAgeMillis = -1;
	}

	public void setAuto(boolean auto) {
		this.automatic = auto;
	}

	void setBackground(boolean background) {
		this.background = background;
	}

	private boolean needGc() {
		if (tooManyPacks()) {
			addRepackAllOption();
		} else {
			return tooManyLooseObjects();
		}
		return true;
	}

	private void addRepackAllOption() {
	}

	boolean tooManyPacks() {
		int autopacklimit = repo.getConfig().getInt(
				ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTOPACKLIMIT
				DEFAULT_AUTOPACKLIMIT);
		if (autopacklimit <= 0) {
			return false;
		}
		return repo.getObjectDatabase().getPacks().size() > (autopacklimit + 1);
	}

	boolean tooManyLooseObjects() {
		int auto = getLooseObjectLimit();
		if (auto <= 0) {
			return false;
		}
		int n = 0;
		int threshold = (auto + 255) / 256;
		if (!dir.toFile().exists()) {
			return false;
		}
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir
					Path fileName = file.getFileName();
					return file.toFile().isFile() && fileName != null
							&& PATTERN_LOOSE_OBJECT.matcher(fileName.toString())
									.matches();
				})) {
			for (Iterator<Path> iter = stream.iterator(); iter.hasNext(); iter
					.next()) {
				if (++n > threshold) {
					return true;
				}
			}
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}
		return false;
	}

	private int getLooseObjectLimit() {
		return repo.getConfig().getInt(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTO
	}
}
