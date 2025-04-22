package org.eclipse.jgit.util;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuildIterator;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheCheckout.CheckoutMetadata;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.IndexWriteException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.util.LfsFactory.LfsInputStream;
import org.eclipse.jgit.util.io.EolStreamTypeUtil;

public class WorkTreeUpdater implements Closeable {

	public static class Result {

		public List<String> modifiedFiles = new LinkedList<>();

		public List<String> failedToDelete = new LinkedList<>();

		public ObjectId treeId = null;
	}

	Result result = new Result();

	@Nullable
	private final Repository repo;

	private final boolean inCore;

	private final ObjectInserter inserter;
	private final ObjectReader reader;
	private DirCache dirCache;
	private boolean implicitDirCache = false;

	private DirCacheBuilder builder = null;

	private WorkingTreeOptions workingTreeOptions;

	private int inCoreFileSizeLimit;

	private final Map<String

	private final TreeMap<String

	private Map<String

	private Map<String

	private boolean indexChangesWritten = false;

	private WorkTreeUpdater(
			Repository repo
			DirCache dirCache) {
		this.repo = repo;
		this.dirCache = dirCache;

		this.inCore = false;
		this.inserter = repo.newObjectInserter();
		this.reader = inserter.newReader();
		this.workingTreeOptions = repo.getConfig().get(WorkingTreeOptions.KEY);
		this.checkoutMetadata = new HashMap<>();
		this.cleanupMetadata = new HashMap<>();
		this.inCoreFileSizeLimit = setInCoreFileSizeLimit(repo.getConfig());
	}

	public static WorkTreeUpdater createWorkTreeUpdater(Repository repo
		return new WorkTreeUpdater(repo
	}

	private WorkTreeUpdater(
			Repository repo
			DirCache dirCache
			ObjectInserter oi) {
		this.repo = repo;
		this.dirCache = dirCache;
		this.inserter = oi;

		this.inCore = true;
		this.reader = oi.newReader();
		if (repo != null) {
			this.inCoreFileSizeLimit = setInCoreFileSizeLimit(repo.getConfig());
		}
	}

	public static WorkTreeUpdater createInCoreWorkTreeUpdater(Repository repo
			ObjectInserter oi) {
		return new WorkTreeUpdater(repo
	}

	public interface StreamSupplier {

		InputStream load() throws IOException;
	}

	public static class StreamLoader extends ObjectLoader {

		private final StreamSupplier data;

		private final long size;

		private StreamLoader(StreamSupplier data
			this.data = data;
			this.size = length;
		}

		@Override
		public int getType() {
			return Constants.OBJ_BLOB;
		}

		@Override
		public long getSize() {
			return size;
		}

		@Override
		public boolean isLarge() {
			return true;
		}

		@Override
		public byte[] getCachedBytes() throws LargeObjectException {
			throw new LargeObjectException();
		}

		@Override
		public ObjectStream openStream() throws IOException {
			return new ObjectStream.Filter(getType()
		}
	}

	public static StreamLoader createStreamLoader(StreamSupplier supplier
		return new StreamLoader(supplier
	}

	private static int setInCoreFileSizeLimit(Config config) {
		return config.getInt(
				ConfigConstants.CONFIG_MERGE_SECTION
	}

	public int getInCoreFileSizeLimit() {
		return inCoreFileSizeLimit;
	}

	public DirCache getLockedDirCache() throws IOException {
		if (dirCache == null) {
			implicitDirCache = true;
			if (inCore) {
				dirCache = DirCache.newInCore();
			} else {
				dirCache = nonNullNonBareRepo().lockDirCache();
			}
		}
		if (builder == null) {
			builder = dirCache.builder();
		}
		return dirCache;
	}

	public DirCacheBuildIterator createDirCacheBuildIterator() {
		return new DirCacheBuildIterator(builder);
	}

	public void writeWorkTreeChanges(boolean shouldCheckoutTheirs) throws IOException {
		handleDeletedFiles();

		if (inCore) {
			builder.finish();
			return;
		}
		if (shouldCheckoutTheirs) {
			checkout();
		}

		if (!builder.commit()) {
			revertModifiedFiles();
			throw new IndexWriteException();
		}
	}

	public Result writeIndexChanges() throws IOException {
		result.treeId = getLockedDirCache().writeTree(inserter);
		indexChangesWritten = true;
		return result;
	}

	public void addToCheckout(
			String path
			String cleanupSmudgeCommand
		if (entry != null) {
			toBeCheckedOut.put(path
		}
		addCheckoutMetadata(cleanupMetadata
		addCheckoutMetadata(checkoutMetadata
	}

	public Map<String
		return toBeCheckedOut;
	}

	public void deleteFile(String path
			throws IOException {
		toBeDeleted.put(path
		if (file != null && file.isFile()) {
			addCheckoutMetadata(cleanupMetadata
		}
	}

	private void addCheckoutMetadata(
			Map<String
			String smudgeCommand) {
		if (inCore || map == null) {
			return;
		}
		map.put(path
	}

	public EolStreamType detectCheckoutStreamType(Attributes attributes) {
		if (inCore) {
			return null;
		}
		return EolStreamTypeUtil.detectStreamType(
				OperationType.CHECKOUT_OP
	}

	private void handleDeletedFiles() {
		for (String path : toBeDeleted.descendingKeySet()) {
			File file = inCore ? null : toBeDeleted.get(path);
			if (file != null && !file.delete()) {
				if (!file.isDirectory()) {
					result.failedToDelete.add(path);
				}
			}
		}
	}

	public void markAsModified(String path) {
		result.modifiedFiles.add(path);
	}

	public List<String> getModifiedFiles() {
		return result.modifiedFiles;
	}

	private void checkout() throws NoWorkTreeException
		for (Map.Entry<String
			DirCacheEntry dirCacheEntry = entry.getValue();
			if (dirCacheEntry.getFileMode() == FileMode.GITLINK) {
				new File(nonNullNonBareRepo().getWorkTree()
			} else {
				DirCacheCheckout.checkoutEntry(
						repo
				result.modifiedFiles.add(entry.getKey());
			}
		}
	}

	public void revertModifiedFiles() throws IOException {
		if (inCore) {
			result.modifiedFiles.clear();
			return;
		}
		if (indexChangesWritten) {
			return;
		}
		for (String path : result.modifiedFiles) {
			DirCacheEntry entry = dirCache.getEntry(path);
			if (entry != null) {
				DirCacheCheckout.checkoutEntry(
						repo
			}
		}
	}

	@Override
	public void close() throws IOException {
		if (implicitDirCache) {
			dirCache.unlock();
		}
	}

	public void updateFileWithContent(
			StreamLoader resultStreamLoader
			EolStreamType streamType
			String smudgeCommand
			String path
			File file
			boolean safeWrite)
			throws IOException {
		if (inCore) {
			return;
		}
		CheckoutMetadata checkoutMetadata = new CheckoutMetadata(streamType
		if (safeWrite) {
			try (org.eclipse.jgit.util.TemporaryBuffer buffer =
					new org.eclipse.jgit.util.TemporaryBuffer.LocalFile(null)) {
				DirCacheCheckout.getContent(
						repo
				InputStream bufIn = buffer.openInputStream();
				Files.copy(bufIn
			}
			return;
		}
		OutputStream outputStream = new FileOutputStream(file);
		DirCacheCheckout.getContent(
				repo

	}

	public DirCacheEntry insertToIndex(
			InputStream inputStream
			byte[] path
			FileMode fileMode
			int entryStage
			Instant lastModified
			int len
			Attribute lfsAttribute) throws IOException {
		StreamLoader contentLoader = createStreamLoader(() -> inputStream
		return insertToIndex(contentLoader
				lfsAttribute);
	}

	public DirCacheEntry insertToIndex(
			StreamLoader resultStreamLoader
			byte[] path
			FileMode fileMode
			int entryStage
			Instant lastModified
			int len
			Attribute lfsAttribute) throws IOException {
		return addExistingToIndex(insertResult(resultStreamLoader
				path
	}

	public DirCacheEntry addExistingToIndex(
			ObjectId objectId
			byte[] path
			FileMode fileMode
			int entryStage
			Instant lastModified
			int len) {
		DirCacheEntry dce = new DirCacheEntry(path
		dce.setFileMode(fileMode);
		if (lastModified != null) {
			dce.setLastModified(lastModified);
		}
		dce.setLength(inCore ? 0 : len);

		dce.setObjectId(objectId);
		builder.add(dce);
		return dce;
	}

	private ObjectId insertResult(StreamLoader resultStreamLoader
			throws IOException {
		try (LfsInputStream is =
				org.eclipse.jgit.util.LfsFactory.getInstance()
						.applyCleanFilter(
								repo
								resultStreamLoader.data.load()
								resultStreamLoader.size
								lfsAttribute)) {
			return inserter.insert(OBJ_BLOB
		}
	}

	private Repository nonNullRepo() throws NullPointerException {
		if (repo == null) {
			throw new NullPointerException(JGitText.get().repositoryIsRequired);
		}
		return repo;
	}


	private Repository nonNullNonBareRepo() throws NullPointerException
		if (nonNullRepo().isBare()) {
			throw new NoWorkTreeException();
		}
		return repo;
	}
}
