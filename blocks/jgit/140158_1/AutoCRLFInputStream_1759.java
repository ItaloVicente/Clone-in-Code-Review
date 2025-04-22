
package org.eclipse.jgit.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;

public abstract class TemporaryBuffer extends OutputStream {
	protected static final int DEFAULT_IN_CORE_LIMIT = 1024 * 1024;

	ArrayList<Block> blocks;

	private int inCoreLimit;

	private int initialBlocks;

	private OutputStream overflow;

	protected TemporaryBuffer(int limit) {
		this(limit
	}

	protected TemporaryBuffer(int estimatedSize
		if (estimatedSize > limit)
			throw new IllegalArgumentException();
		this.inCoreLimit = limit;
		this.initialBlocks = (estimatedSize - 1) / Block.SZ + 1;
		reset();
	}

	@Override
	public void write(int b) throws IOException {
		if (overflow != null) {
			overflow.write(b);
			return;
		}

		Block s = last();
		if (s.isFull()) {
			if (reachedInCoreLimit()) {
				overflow.write(b);
				return;
			}

			s = new Block();
			blocks.add(s);
		}
		s.buffer[s.count++] = (byte) b;
	}

	@Override
	public void write(byte[] b
		if (overflow == null) {
			while (len > 0) {
				Block s = last();
				if (s.isFull()) {
					if (reachedInCoreLimit())
						break;

					s = new Block();
					blocks.add(s);
				}

				final int n = Math.min(s.buffer.length - s.count
				System.arraycopy(b
				s.count += n;
				len -= n;
				off += n;
			}
		}

		if (len > 0)
			overflow.write(b
	}

	protected void doFlush() throws IOException {
		if (overflow == null)
			switchToOverflow();
		overflow.flush();
	}

	public void copy(InputStream in) throws IOException {
		if (blocks != null) {
			for (;;) {
				Block s = last();
				if (s.isFull()) {
					if (reachedInCoreLimit())
						break;
					s = new Block();
					blocks.add(s);
				}

				int n = in.read(s.buffer
				if (n < 1)
					return;
				s.count += n;
			}
		}

		final byte[] tmp = new byte[Block.SZ];
		int n;
		while ((n = in.read(tmp)) > 0)
			overflow.write(tmp
	}

	public long length() {
		return inCoreLength();
	}

	private long inCoreLength() {
		final Block last = last();
		return ((long) blocks.size() - 1) * Block.SZ + last.count;
	}

	public byte[] toByteArray() throws IOException {
		final long len = length();
		if (Integer.MAX_VALUE < len)
			throw new OutOfMemoryError(JGitText.get().lengthExceedsMaximumArraySize);
		final byte[] out = new byte[(int) len];
		int outPtr = 0;
		for (Block b : blocks) {
			System.arraycopy(b.buffer
			outPtr += b.count;
		}
		return out;
	}

	public byte[] toByteArray(int limit) throws IOException {
		final long len = Math.min(length()
		if (Integer.MAX_VALUE < len)
			throw new OutOfMemoryError(
					JGitText.get().lengthExceedsMaximumArraySize);
		final byte[] out = new byte[(int) len];
		int outPtr = 0;
		for (Block b : blocks) {
			System.arraycopy(b.buffer
			outPtr += b.count;
		}
		return out;
	}

	public void writeTo(OutputStream os
			throws IOException {
		if (pm == null)
			pm = NullProgressMonitor.INSTANCE;
		for (Block b : blocks) {
			os.write(b.buffer
			pm.update(b.count / 1024);
		}
	}

	public InputStream openInputStream() throws IOException {
		return new BlockInputStream();
	}

	public InputStream openInputStreamWithAutoDestroy() throws IOException {
		return new BlockInputStream() {
			@Override
			public void close() throws IOException {
				super.close();
				destroy();
			}
		};
	}

	public void reset() {
		if (overflow != null) {
			destroy();
		}
		if (blocks != null)
			blocks.clear();
		else
			blocks = new ArrayList<>(initialBlocks);
		blocks.add(new Block(Math.min(inCoreLimit
	}

	protected abstract OutputStream overflow() throws IOException;

	private Block last() {
		return blocks.get(blocks.size() - 1);
	}

	private boolean reachedInCoreLimit() throws IOException {
		if (inCoreLength() < inCoreLimit)
			return false;

		switchToOverflow();
		return true;
	}

	private void switchToOverflow() throws IOException {
		overflow = overflow();

		final Block last = blocks.remove(blocks.size() - 1);
		for (Block b : blocks)
			overflow.write(b.buffer
		blocks = null;

		overflow = new BufferedOutputStream(overflow
		overflow.write(last.buffer
	}

	@Override
	public void close() throws IOException {
		if (overflow != null) {
			try {
				overflow.close();
			} finally {
				overflow = null;
			}
		}
	}

	public void destroy() {
		blocks = null;

		if (overflow != null) {
			try {
				overflow.close();
			} catch (IOException err) {
			} finally {
				overflow = null;
			}
		}
	}

	public static class LocalFile extends TemporaryBuffer {
		private final File directory;

		private File onDiskFile;

		public LocalFile(File directory) {
			this(directory
		}

		public LocalFile(File directory
			super(inCoreLimit);
			this.directory = directory;
		}

		@Override
		protected OutputStream overflow() throws IOException {
			onDiskFile = File.createTempFile("jgit_"
			return new BufferedOutputStream(new FileOutputStream(onDiskFile));
		}

		@Override
		public long length() {
			if (onDiskFile == null) {
				return super.length();
			}
			return onDiskFile.length();
		}

		@Override
		public byte[] toByteArray() throws IOException {
			if (onDiskFile == null) {
				return super.toByteArray();
			}

			final long len = length();
			if (Integer.MAX_VALUE < len)
				throw new OutOfMemoryError(JGitText.get().lengthExceedsMaximumArraySize);
			final byte[] out = new byte[(int) len];
			try (FileInputStream in = new FileInputStream(onDiskFile)) {
				IO.readFully(in
			}
			return out;
		}

		@Override
		public void writeTo(OutputStream os
				throws IOException {
			if (onDiskFile == null) {
				super.writeTo(os
				return;
			}
			if (pm == null)
				pm = NullProgressMonitor.INSTANCE;
			try (FileInputStream in = new FileInputStream(onDiskFile)) {
				int cnt;
				final byte[] buf = new byte[Block.SZ];
				while ((cnt = in.read(buf)) >= 0) {
					os.write(buf
					pm.update(cnt / 1024);
				}
			}
		}

		@Override
		public InputStream openInputStream() throws IOException {
			if (onDiskFile == null)
				return super.openInputStream();
			return new FileInputStream(onDiskFile);
		}

		@Override
		public InputStream openInputStreamWithAutoDestroy() throws IOException {
			if (onDiskFile == null) {
				return super.openInputStreamWithAutoDestroy();
			}
			return new FileInputStream(onDiskFile) {
				@Override
				public void close() throws IOException {
					super.close();
					destroy();
				}
			};
		}

		@Override
		public void destroy() {
			super.destroy();

			if (onDiskFile != null) {
				try {
					if (!onDiskFile.delete())
						onDiskFile.deleteOnExit();
				} finally {
					onDiskFile = null;
				}
			}
		}
	}

	public static class Heap extends TemporaryBuffer {
		public Heap(int limit) {
			super(limit);
		}

		public Heap(int estimatedSize
			super(estimatedSize
		}

		@Override
		protected OutputStream overflow() throws IOException {
			throw new IOException(JGitText.get().inMemoryBufferLimitExceeded);
		}
	}

	static class Block {
		static final int SZ = 8 * 1024;

		final byte[] buffer;

		int count;

		Block() {
			buffer = new byte[SZ];
		}

		Block(int sz) {
			buffer = new byte[sz];
		}

		boolean isFull() {
			return count == buffer.length;
		}
	}

	private class BlockInputStream extends InputStream {
		private byte[] singleByteBuffer;
		private int blockIndex;
		private Block block;
		private int blockPos;

		BlockInputStream() {
			block = blocks.get(blockIndex);
		}

		@Override
		public int read() throws IOException {
			if (singleByteBuffer == null)
				singleByteBuffer = new byte[1];
			int n = read(singleByteBuffer);
			return n == 1 ? singleByteBuffer[0] & 0xff : -1;
		}

		@Override
		public long skip(long cnt) throws IOException {
			long skipped = 0;
			while (0 < cnt) {
				int n = (int) Math.min(block.count - blockPos
				if (0 < n) {
					blockPos += n;
					skipped += n;
					cnt -= n;
				} else if (nextBlock())
					continue;
				else
					break;
			}
			return skipped;
		}

		@Override
		public int read(byte[] b
			if (len == 0)
				return 0;
			int copied = 0;
			while (0 < len) {
				int c = Math.min(block.count - blockPos
				if (0 < c) {
					System.arraycopy(block.buffer
					blockPos += c;
					off += c;
					len -= c;
					copied += c;
				} else if (nextBlock())
					continue;
				else
					break;
			}
			return 0 < copied ? copied : -1;
		}

		private boolean nextBlock() {
			if (++blockIndex < blocks.size()) {
				block = blocks.get(blockIndex);
				blockPos = 0;
				return true;
			}
			return false;
		}
	}
}
