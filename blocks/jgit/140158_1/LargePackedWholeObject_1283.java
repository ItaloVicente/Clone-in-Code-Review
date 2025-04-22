package org.eclipse.jgit.internal.storage.dfs;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.reftable.ReftableConfig;
import org.eclipse.jgit.lib.RefDatabase;

public class InMemoryRepository extends DfsRepository {
	public static class Builder
			extends DfsRepositoryBuilder<Builder
		@Override
		public InMemoryRepository build() throws IOException {
			return new InMemoryRepository(this);
		}
	}

	static final AtomicInteger packId = new AtomicInteger();

	private final MemObjDatabase objdb;
	private final MemRefDatabase refdb;
	private String gitwebDescription;

	public InMemoryRepository(DfsRepositoryDescription repoDesc) {
		this(new Builder().setRepositoryDescription(repoDesc));
	}

	InMemoryRepository(Builder builder) {
		super(builder);
		objdb = new MemObjDatabase(this);
		refdb = new MemRefDatabase();
	}

	@Override
	public MemObjDatabase getObjectDatabase() {
		return objdb;
	}

	@Override
	public RefDatabase getRefDatabase() {
		return refdb;
	}

	public void setPerformsAtomicTransactions(boolean atomic) {
		refdb.performsAtomicTransactions = atomic;
	}

	@Override
	@Nullable
	public String getGitwebDescription() {
		return gitwebDescription;
	}

	@Override
	public void setGitwebDescription(@Nullable String d) {
		gitwebDescription = d;
	}

	public static class MemObjDatabase extends DfsObjDatabase {
		private List<DfsPackDescription> packs = new ArrayList<>();
		private int blockSize;

		MemObjDatabase(DfsRepository repo) {
			super(repo
		}

		public void setReadableChannelBlockSizeForTest(int blockSize) {
			this.blockSize = blockSize;
		}

		@Override
		protected synchronized List<DfsPackDescription> listPacks() {
			return packs;
		}

		@Override
		protected DfsPackDescription newPack(PackSource source) {
			int id = packId.incrementAndGet();
			return new MemPack(
					"pack-" + id + "-" + source.name()
					getRepository().getDescription()
					source);
		}

		@Override
		protected synchronized void commitPackImpl(
				Collection<DfsPackDescription> desc
				Collection<DfsPackDescription> replace) {
			List<DfsPackDescription> n;
			n = new ArrayList<>(desc.size() + packs.size());
			n.addAll(desc);
			n.addAll(packs);
			if (replace != null)
				n.removeAll(replace);
			packs = n;
			clearCache();
		}

		@Override
		protected void rollbackPack(Collection<DfsPackDescription> desc) {
		}

		@Override
		protected ReadableChannel openFile(DfsPackDescription desc
				throws FileNotFoundException
			MemPack memPack = (MemPack) desc;
			byte[] file = memPack.get(ext);
			if (file == null)
				throw new FileNotFoundException(desc.getFileName(ext));
			return new ByteArrayReadableChannel(file
		}

		@Override
		protected DfsOutputStream writeFile(DfsPackDescription desc
				PackExt ext) throws IOException {
			MemPack memPack = (MemPack) desc;
			return new Out() {
				@Override
				public void flush() {
					memPack.put(ext
				}
			};
		}
	}

	private static class MemPack extends DfsPackDescription {
		final byte[][] fileMap = new byte[PackExt.values().length][];

		MemPack(String name
			super(repoDesc
		}

		void put(PackExt ext
			fileMap[ext.getPosition()] = data;
		}

		byte[] get(PackExt ext) {
			return fileMap[ext.getPosition()];
		}
	}

	private abstract static class Out extends DfsOutputStream {
		private final ByteArrayOutputStream dst = new ByteArrayOutputStream();
		private byte[] data;

		@Override
		public void write(byte[] buf
			data = null;
			dst.write(buf
		}

		@Override
		public int read(long position
			byte[] d = getData();
			int n = Math.min(buf.remaining()
			if (n == 0)
				return -1;
			buf.put(d
			return n;
		}

		byte[] getData() {
			if (data == null)
				data = dst.toByteArray();
			return data;
		}

		@Override
		public abstract void flush();

		@Override
		public void close() {
			flush();
		}
	}

	private static class ByteArrayReadableChannel implements ReadableChannel {
		private final byte[] data;
		private final int blockSize;
		private int position;
		private boolean open = true;

		ByteArrayReadableChannel(byte[] buf
			data = buf;
			this.blockSize = blockSize;
		}

		@Override
		public int read(ByteBuffer dst) {
			int n = Math.min(dst.remaining()
			if (n == 0)
				return -1;
			dst.put(data
			position += n;
			return n;
		}

		@Override
		public void close() {
			open = false;
		}

		@Override
		public boolean isOpen() {
			return open;
		}

		@Override
		public long position() {
			return position;
		}

		@Override
		public void position(long newPosition) {
			position = (int) newPosition;
		}

		@Override
		public long size() {
			return data.length;
		}

		@Override
		public int blockSize() {
			return blockSize;
		}

		@Override
		public void setReadAheadBytes(int b) {
		}
	}

	protected class MemRefDatabase extends DfsReftableDatabase {
		boolean performsAtomicTransactions = true;

		protected MemRefDatabase() {
			super(InMemoryRepository.this);
		}

		@Override
		public ReftableConfig getReftableConfig() {
			ReftableConfig cfg = new ReftableConfig();
			cfg.setAlignBlocks(false);
			cfg.setIndexObjects(false);
			cfg.fromConfig(getRepository().getConfig());
			return cfg;
		}

		@Override
		public boolean performsAtomicTransactions() {
			return performsAtomicTransactions;
		}
	}
}
