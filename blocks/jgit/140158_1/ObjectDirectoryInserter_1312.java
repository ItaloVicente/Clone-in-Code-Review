
package org.eclipse.jgit.internal.storage.file;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.internal.storage.pack.PackExt.INDEX;
import static org.eclipse.jgit.internal.storage.pack.PackExt.PACK;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.PackInvalidException;
import org.eclipse.jgit.errors.PackMismatchException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectDirectory extends FileObjectDatabase {
	private final static Logger LOG = LoggerFactory
			.getLogger(ObjectDirectory.class);

	private static final PackList NO_PACKS = new PackList(
			FileSnapshot.DIRTY

	private static final int RESOLVE_ABBREV_LIMIT = 256;

	private final AlternateHandle handle = new AlternateHandle(this);

	private final Config config;

	private final File objects;

	private final File infoDirectory;

	private final File packDirectory;

	private final File preservedDirectory;

	private final File alternatesFile;

	private final AtomicReference<PackList> packList;

	private final FS fs;

	private final AtomicReference<AlternateHandle[]> alternates;

	private final UnpackedObjectCache unpackedObjectCache;

	private final File shallowFile;

	private FileSnapshot shallowFileSnapshot = FileSnapshot.DIRTY;

	private Set<ObjectId> shallowCommitsIds;

	public ObjectDirectory(final Config cfg
			File[] alternatePaths
		config = cfg;
		objects = dir;
		infoDirectory = new File(objects
		packDirectory = new File(objects
		preservedDirectory = new File(packDirectory
		alternatesFile = new File(infoDirectory
		packList = new AtomicReference<>(NO_PACKS);
		unpackedObjectCache = new UnpackedObjectCache();
		this.fs = fs;
		this.shallowFile = shallowFile;

		alternates = new AtomicReference<>();
		if (alternatePaths != null) {
			AlternateHandle[] alt;

			alt = new AlternateHandle[alternatePaths.length];
			for (int i = 0; i < alternatePaths.length; i++)
				alt[i] = openAlternate(alternatePaths[i]);
			alternates.set(alt);
		}
	}

	@Override
	public final File getDirectory() {
		return objects;
	}

	public final File getPackDirectory() {
		return packDirectory;
	}

	public final File getPreservedDirectory() {
		return preservedDirectory;
	}

	@Override
	public boolean exists() {
		return fs.exists(objects);
	}

	@Override
	public void create() throws IOException {
		FileUtils.mkdirs(objects);
		FileUtils.mkdir(infoDirectory);
		FileUtils.mkdir(packDirectory);
	}

	@Override
	public ObjectDirectoryInserter newInserter() {
		return new ObjectDirectoryInserter(this
	}

	public PackInserter newPackInserter() {
		return new PackInserter(this);
	}

	@Override
	public void close() {
		unpackedObjectCache.clear();

		final PackList packs = packList.get();
		if (packs != NO_PACKS && packList.compareAndSet(packs
			for (PackFile p : packs.packs)
				p.close();
		}

		AlternateHandle[] alt = alternates.get();
		if (alt != null && alternates.compareAndSet(alt
			for(AlternateHandle od : alt)
				od.close();
		}
	}

	@Override
	public Collection<PackFile> getPacks() {
		PackList list = packList.get();
		if (list == NO_PACKS)
			list = scanPacks(list);
		PackFile[] packs = list.packs;
		return Collections.unmodifiableCollection(Arrays.asList(packs));
	}

	@Override
	public PackFile openPack(File pack)
			throws IOException {
		final String p = pack.getName();
			throw new IOException(MessageFormat.format(JGitText.get().notAValidPack

		int extensions = PACK.getBit() | INDEX.getBit();
		final String base = p.substring(0
		for (PackExt ext : PackExt.values()) {
			if ((extensions & ext.getBit()) == 0) {
				final String name = base + ext.getExtension();
				if (new File(pack.getParentFile()
					extensions |= ext.getBit();
			}
		}

		PackFile res = new PackFile(pack
		insertPack(res);
		return res;
	}

	@Override
	public String toString() {
	}

	@Override
	public boolean has(AnyObjectId objectId) {
		return unpackedObjectCache.isUnpacked(objectId)
				|| hasPackedInSelfOrAlternate(objectId
				|| hasLooseInSelfOrAlternate(objectId
	}

	private boolean hasPackedInSelfOrAlternate(AnyObjectId objectId
			Set<AlternateHandle.Id> skips) {
		if (hasPackedObject(objectId)) {
			return true;
		}
		skips = addMe(skips);
		for (AlternateHandle alt : myAlternates()) {
			if (!skips.contains(alt.getId())) {
				if (alt.db.hasPackedInSelfOrAlternate(objectId
					return true;
				}
			}
		}
		return false;
	}

	private boolean hasLooseInSelfOrAlternate(AnyObjectId objectId
			Set<AlternateHandle.Id> skips) {
		if (fileFor(objectId).exists()) {
			return true;
		}
		skips = addMe(skips);
		for (AlternateHandle alt : myAlternates()) {
			if (!skips.contains(alt.getId())) {
				if (alt.db.hasLooseInSelfOrAlternate(objectId
					return true;
				}
			}
		}
		return false;
	}

	boolean hasPackedObject(AnyObjectId objectId) {
		PackList pList;
		do {
			pList = packList.get();
			for (PackFile p : pList.packs) {
				try {
					if (p.hasObject(objectId))
						return true;
				} catch (IOException e) {
					LOG.warn(MessageFormat.format(
							JGitText.get().unableToReadPackfile
							p.getPackFile().getAbsolutePath())
					removePack(p);
				}
			}
		} while (searchPacksAgain(pList));
		return false;
	}

	@Override
	void resolve(Set<ObjectId> matches
			throws IOException {
		resolve(matches
	}

	private void resolve(Set<ObjectId> matches
			Set<AlternateHandle.Id> skips)
			throws IOException {
		int oldSize = matches.size();
		PackList pList;
		do {
			pList = packList.get();
			for (PackFile p : pList.packs) {
				try {
					p.resolve(matches
					p.resetTransientErrorCount();
				} catch (IOException e) {
					handlePackError(e
				}
				if (matches.size() > RESOLVE_ABBREV_LIMIT)
					return;
			}
		} while (matches.size() == oldSize && searchPacksAgain(pList));

		String fanOut = id.name().substring(0
		String[] entries = new File(getDirectory()
		if (entries != null) {
			for (String e : entries) {
				if (e.length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
					continue;
				try {
					ObjectId entId = ObjectId.fromString(fanOut + e);
					if (id.prefixCompare(entId) == 0)
						matches.add(entId);
				} catch (IllegalArgumentException notId) {
					continue;
				}
				if (matches.size() > RESOLVE_ABBREV_LIMIT)
					return;
			}
		}

		skips = addMe(skips);
		for (AlternateHandle alt : myAlternates()) {
			if (!skips.contains(alt.getId())) {
				alt.db.resolve(matches
				if (matches.size() > RESOLVE_ABBREV_LIMIT) {
					return;
				}
			}
		}
	}

	@Override
	ObjectLoader openObject(WindowCursor curs
			throws IOException {
		if (unpackedObjectCache.isUnpacked(objectId)) {
			ObjectLoader ldr = openLooseObject(curs
			if (ldr != null) {
				return ldr;
			}
		}
		ObjectLoader ldr = openPackedFromSelfOrAlternate(curs
		if (ldr != null) {
			return ldr;
		}
		return openLooseFromSelfOrAlternate(curs
	}

	private ObjectLoader openPackedFromSelfOrAlternate(WindowCursor curs
			AnyObjectId objectId
		ObjectLoader ldr = openPackedObject(curs
		if (ldr != null) {
			return ldr;
		}
		skips = addMe(skips);
		for (AlternateHandle alt : myAlternates()) {
			if (!skips.contains(alt.getId())) {
				ldr = alt.db.openPackedFromSelfOrAlternate(curs
				if (ldr != null) {
					return ldr;
				}
			}
		}
		return null;
	}

	private ObjectLoader openLooseFromSelfOrAlternate(WindowCursor curs
			AnyObjectId objectId
					throws IOException {
		ObjectLoader ldr = openLooseObject(curs
		if (ldr != null) {
			return ldr;
		}
		skips = addMe(skips);
		for (AlternateHandle alt : myAlternates()) {
			if (!skips.contains(alt.getId())) {
				ldr = alt.db.openLooseFromSelfOrAlternate(curs
				if (ldr != null) {
					return ldr;
				}
			}
		}
		return null;
	}

	ObjectLoader openPackedObject(WindowCursor curs
		PackList pList;
		do {
			SEARCH: for (;;) {
				pList = packList.get();
				for (PackFile p : pList.packs) {
					try {
						ObjectLoader ldr = p.get(curs
						p.resetTransientErrorCount();
						if (ldr != null)
							return ldr;
					} catch (PackMismatchException e) {
						if (searchPacksAgain(pList))
							continue SEARCH;
					} catch (IOException e) {
						handlePackError(e
					}
				}
				break SEARCH;
			}
		} while (searchPacksAgain(pList));
		return null;
	}

	@Override
	ObjectLoader openLooseObject(WindowCursor curs
			throws IOException {
		File path = fileFor(id);
		try (FileInputStream in = new FileInputStream(path)) {
			unpackedObjectCache.add(id);
			return UnpackedObject.open(in
		} catch (FileNotFoundException noFile) {
			if (path.exists()) {
				throw noFile;
			}
			unpackedObjectCache.remove(id);
			return null;
		}
	}

	@Override
	long getObjectSize(WindowCursor curs
			throws IOException {
		if (unpackedObjectCache.isUnpacked(id)) {
			long len = getLooseObjectSize(curs
			if (0 <= len) {
				return len;
			}
		}
		long len = getPackedSizeFromSelfOrAlternate(curs
		if (0 <= len) {
			return len;
		}
		return getLooseSizeFromSelfOrAlternate(curs
	}

	private long getPackedSizeFromSelfOrAlternate(WindowCursor curs
			AnyObjectId id
		long len = getPackedObjectSize(curs
		if (0 <= len) {
			return len;
		}
		skips = addMe(skips);
		for (AlternateHandle alt : myAlternates()) {
			if (!skips.contains(alt.getId())) {
				len = alt.db.getPackedSizeFromSelfOrAlternate(curs
				if (0 <= len) {
					return len;
				}
			}
		}
		return -1;
	}

	private long getLooseSizeFromSelfOrAlternate(WindowCursor curs
			AnyObjectId id
		long len = getLooseObjectSize(curs
		if (0 <= len) {
			return len;
		}
		skips = addMe(skips);
		for (AlternateHandle alt : myAlternates()) {
			if (!skips.contains(alt.getId())) {
				len = alt.db.getLooseSizeFromSelfOrAlternate(curs
				if (0 <= len) {
					return len;
				}
			}
		}
		return -1;
	}

	private long getPackedObjectSize(WindowCursor curs
		PackList pList;
		do {
			SEARCH: for (;;) {
				pList = packList.get();
				for (PackFile p : pList.packs) {
					try {
						long len = p.getObjectSize(curs
						p.resetTransientErrorCount();
						if (0 <= len)
							return len;
					} catch (PackMismatchException e) {
						if (searchPacksAgain(pList))
							continue SEARCH;
					} catch (IOException e) {
						handlePackError(e
					}
				}
				break SEARCH;
			}
		} while (searchPacksAgain(pList));
		return -1;
	}

	private long getLooseObjectSize(WindowCursor curs
			throws IOException {
		File f = fileFor(id);
		try (FileInputStream in = new FileInputStream(f)) {
			unpackedObjectCache.add(id);
			return UnpackedObject.getSize(in
		} catch (FileNotFoundException noFile) {
			if (f.exists()) {
				throw noFile;
			}
			unpackedObjectCache.remove(id);
			return -1;
		}
	}

	@Override
	void selectObjectRepresentation(PackWriter packer
																	WindowCursor curs) throws IOException {
		selectObjectRepresentation(packer
	}

	private void selectObjectRepresentation(PackWriter packer
			WindowCursor curs
		PackList pList = packList.get();
		SEARCH: for (;;) {
			for (PackFile p : pList.packs) {
				try {
					LocalObjectRepresentation rep = p.representation(curs
					p.resetTransientErrorCount();
					if (rep != null)
						packer.select(otp
				} catch (PackMismatchException e) {
					pList = scanPacks(pList);
					continue SEARCH;
				} catch (IOException e) {
					handlePackError(e
				}
			}
			break SEARCH;
		}

		skips = addMe(skips);
		for (AlternateHandle h : myAlternates()) {
			if (!skips.contains(h.getId())) {
				h.db.selectObjectRepresentation(packer
			}
		}
	}

	private void handlePackError(IOException e
		String warnTmpl = null;
		int transientErrorCount = 0;
		String errTmpl = JGitText.get().exceptionWhileReadingPack;
		if ((e instanceof CorruptObjectException)
				|| (e instanceof PackInvalidException)) {
			warnTmpl = JGitText.get().corruptPack;
			LOG.warn(MessageFormat.format(warnTmpl
					p.getPackFile().getAbsolutePath())
			removePack(p);
		} else if (e instanceof FileNotFoundException) {
			if (p.getPackFile().exists()) {
				errTmpl = JGitText.get().packInaccessible;
				transientErrorCount = p.incrementTransientErrorCount();
			} else {
				warnTmpl = JGitText.get().packWasDeleted;
				removePack(p);
			}
		} else if (FileUtils.isStaleFileHandleInCausalChain(e)) {
			warnTmpl = JGitText.get().packHandleIsStale;
			removePack(p);
		} else {
			transientErrorCount = p.incrementTransientErrorCount();
		}
		if (warnTmpl != null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(MessageFormat.format(warnTmpl
						p.getPackFile().getAbsolutePath())
			} else {
				LOG.warn(MessageFormat.format(warnTmpl
						p.getPackFile().getAbsolutePath()));
			}
		} else {
			if (doLogExponentialBackoff(transientErrorCount)) {
				LOG.error(MessageFormat.format(errTmpl
						p.getPackFile().getAbsolutePath()
						Integer.valueOf(transientErrorCount))
			}
		}
	}

	private boolean doLogExponentialBackoff(int n) {
		return (n & (n - 1)) == 0;
	}

	@Override
	InsertLooseObjectResult insertUnpackedObject(File tmp
			boolean createDuplicate) throws IOException {
		if (unpackedObjectCache.isUnpacked(id)) {
			FileUtils.delete(tmp
			return InsertLooseObjectResult.EXISTS_LOOSE;
		}
		if (!createDuplicate && has(id)) {
			FileUtils.delete(tmp
			return InsertLooseObjectResult.EXISTS_PACKED;
		}

		final File dst = fileFor(id);
		if (dst.exists()) {
			FileUtils.delete(tmp
			return InsertLooseObjectResult.EXISTS_LOOSE;
		}
		try {
			Files.move(FileUtils.toPath(tmp)
					StandardCopyOption.ATOMIC_MOVE);
			dst.setReadOnly();
			unpackedObjectCache.add(id);
			return InsertLooseObjectResult.INSERTED;
		} catch (AtomicMoveNotSupportedException e) {
			LOG.error(e.getMessage()
		} catch (IOException e) {
		}

		FileUtils.mkdir(dst.getParentFile()
		try {
			Files.move(FileUtils.toPath(tmp)
					StandardCopyOption.ATOMIC_MOVE);
			dst.setReadOnly();
			unpackedObjectCache.add(id);
			return InsertLooseObjectResult.INSERTED;
		} catch (AtomicMoveNotSupportedException e) {
			LOG.error(e.getMessage()
		} catch (IOException e) {
			LOG.debug(e.getMessage()
		}

		if (!createDuplicate && has(id)) {
			FileUtils.delete(tmp
			return InsertLooseObjectResult.EXISTS_PACKED;
		}

		FileUtils.delete(tmp
		return InsertLooseObjectResult.FAILURE;
	}

	private boolean searchPacksAgain(PackList old) {
		boolean trustFolderStat = config.getBoolean(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT

		return ((!trustFolderStat) || old.snapshot.isModified(packDirectory))
				&& old != scanPacks(old);
	}

	@Override
	Config getConfig() {
		return config;
	}

	@Override
	FS getFS() {
		return fs;
	}

	@Override
	Set<ObjectId> getShallowCommits() throws IOException {
		if (shallowFile == null || !shallowFile.isFile())
			return Collections.emptySet();

		if (shallowFileSnapshot == null
				|| shallowFileSnapshot.isModified(shallowFile)) {
			shallowCommitsIds = new HashSet<>();

			try (BufferedReader reader = open(shallowFile)) {
				String line;
				while ((line = reader.readLine()) != null) {
					try {
						shallowCommitsIds.add(ObjectId.fromString(line));
					} catch (IllegalArgumentException ex) {
						throw new IOException(MessageFormat
								.format(JGitText.get().badShallowLine
					}
				}
			}

			shallowFileSnapshot = FileSnapshot.save(shallowFile);
		}

		return shallowCommitsIds;
	}

	private void insertPack(PackFile pf) {
		PackList o
		do {
			o = packList.get();

			final PackFile[] oldList = o.packs;
			final String name = pf.getPackFile().getName();
			for (PackFile p : oldList) {
				if (name.equals(p.getPackFile().getName()))
					return;
			}

			final PackFile[] newList = new PackFile[1 + oldList.length];
			newList[0] = pf;
			System.arraycopy(oldList
			n = new PackList(o.snapshot
		} while (!packList.compareAndSet(o
	}

	private void removePack(PackFile deadPack) {
		PackList o
		do {
			o = packList.get();

			final PackFile[] oldList = o.packs;
			final int j = indexOf(oldList
			if (j < 0)
				break;

			final PackFile[] newList = new PackFile[oldList.length - 1];
			System.arraycopy(oldList
			System.arraycopy(oldList
			n = new PackList(o.snapshot
		} while (!packList.compareAndSet(o
		deadPack.close();
	}

	private static int indexOf(PackFile[] list
		for (int i = 0; i < list.length; i++) {
			if (list[i] == pack)
				return i;
		}
		return -1;
	}

	private PackList scanPacks(PackList original) {
		synchronized (packList) {
			PackList o
			do {
				o = packList.get();
				if (o != original) {
					return o;
				}
				n = scanPacksImpl(o);
				if (n == o)
					return n;
			} while (!packList.compareAndSet(o
			return n;
		}
	}

	private PackList scanPacksImpl(PackList old) {
		final Map<String
		final FileSnapshot snapshot = FileSnapshot.save(packDirectory);
		final Set<String> names = listPackDirectory();
		final List<PackFile> list = new ArrayList<>(names.size() >> 2);
		boolean foundNew = false;
		for (String indexName : names) {
				continue;

			final String base = indexName.substring(0
			int extensions = 0;
			for (PackExt ext : PackExt.values()) {
				if (names.contains(base + ext.getExtension()))
					extensions |= ext.getBit();
			}

			if ((extensions & PACK.getBit()) == 0) {
				continue;
			}

			final String packName = base + PACK.getExtension();
			final File packFile = new File(packDirectory
			final PackFile oldPack = forReuse.remove(packName);
			if (oldPack != null && oldPack.getFileSnapshot().isModified(packFile)) {
				list.add(oldPack);
				continue;
			}

			list.add(new PackFile(packFile
			foundNew = true;
		}

		if (!foundNew && forReuse.isEmpty() && snapshot.equals(old.snapshot)) {
			old.snapshot.setClean(snapshot);
			return old;
		}

		for (PackFile p : forReuse.values()) {
			p.close();
		}

		if (list.isEmpty())
			return new PackList(snapshot

		final PackFile[] r = list.toArray(new PackFile[0]);
		Arrays.sort(r
		return new PackList(snapshot
	}

	private static Map<String
		final Map<String
		for (PackFile p : old.packs) {
			if (p.invalid()) {
				p.close();
				continue;
			}

			final PackFile prior = forReuse.put(p.getPackFile().getName()
			if (prior != null) {
				forReuse.put(prior.getPackFile().getName()
				p.close();
			}
		}
		return forReuse;
	}

	private Set<String> listPackDirectory() {
		final String[] nameList = packDirectory.list();
		if (nameList == null)
			return Collections.emptySet();
		final Set<String> nameSet = new HashSet<>(nameList.length << 1);
		for (String name : nameList) {
				nameSet.add(name);
		}
		return nameSet;
	}

	void closeAllPackHandles(File packFile) {
		if (packFile.exists()) {
			for (PackFile p : getPacks()) {
				if (packFile.getPath().equals(p.getPackFile().getPath())) {
					p.close();
					break;
				}
			}
		}
	}

	AlternateHandle[] myAlternates() {
		AlternateHandle[] alt = alternates.get();
		if (alt == null) {
			synchronized (alternates) {
				alt = alternates.get();
				if (alt == null) {
					try {
						alt = loadAlternates();
					} catch (IOException e) {
						alt = new AlternateHandle[0];
					}
					alternates.set(alt);
				}
			}
		}
		return alt;
	}

	Set<AlternateHandle.Id> addMe(Set<AlternateHandle.Id> skips) {
		if (skips == null) {
			skips = new HashSet<>();
		}
		skips.add(handle.getId());
		return skips;
	}

	private AlternateHandle[] loadAlternates() throws IOException {
		final List<AlternateHandle> l = new ArrayList<>(4);
		try (BufferedReader br = open(alternatesFile)) {
			String line;
			while ((line = br.readLine()) != null) {
				l.add(openAlternate(line));
			}
		}
		return l.toArray(new AlternateHandle[0]);
	}

	private static BufferedReader open(File f)
			throws IOException
		return Files.newBufferedReader(f.toPath()
	}

	private AlternateHandle openAlternate(String location)
			throws IOException {
		final File objdir = fs.resolve(objects
		return openAlternate(objdir);
	}

	private AlternateHandle openAlternate(File objdir) throws IOException {
		final File parent = objdir.getParentFile();
		if (FileKey.isGitRepository(parent
			FileKey key = FileKey.exact(parent
			FileRepository db = (FileRepository) RepositoryCache.open(key);
			return new AlternateRepository(db);
		}

		ObjectDirectory db = new ObjectDirectory(config
		return new AlternateHandle(db);
	}

	@Override
	public File fileFor(AnyObjectId objectId) {
		String n = objectId.name();
		String d = n.substring(0
		String f = n.substring(2);
		return new File(new File(getDirectory()
	}

	private static final class PackList {
		final FileSnapshot snapshot;

		final PackFile[] packs;

		PackList(FileSnapshot monitor
			this.snapshot = monitor;
			this.packs = packs;
		}
	}

	static class AlternateHandle {
		static class Id {
			String alternateId;

			public Id(File object) {
				try {
					this.alternateId = object.getCanonicalPath();
				} catch (Exception e) {
					alternateId = null;
				}
			}

			@Override
			public boolean equals(Object o) {
				if (o == this) {
					return true;
				}
				if (o == null || !(o instanceof Id)) {
					return false;
				}
				Id aId = (Id) o;
				return Objects.equals(alternateId
			}

			@Override
			public int hashCode() {
				if (alternateId == null) {
					return 1;
				}
				return alternateId.hashCode();
			}
		}

		final ObjectDirectory db;

		AlternateHandle(ObjectDirectory db) {
			this.db = db;
		}

		void close() {
			db.close();
		}

		public Id getId(){
			return db.getAlternateId();
		}
	}

	static class AlternateRepository extends AlternateHandle {
		final FileRepository repository;

		AlternateRepository(FileRepository r) {
			super(r.getObjectDatabase());
			repository = r;
		}

		@Override
		void close() {
			repository.close();
		}
	}

	@Override
	public ObjectDatabase newCachedDatabase() {
		return newCachedFileObjectDatabase();
	}

	CachedObjectDirectory newCachedFileObjectDatabase() {
		return new CachedObjectDirectory(this);
	}

	AlternateHandle.Id getAlternateId() {
		return new AlternateHandle.Id(objects);
	}
}
