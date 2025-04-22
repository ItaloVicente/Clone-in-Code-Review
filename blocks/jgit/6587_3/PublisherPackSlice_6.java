
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.pack.PackWriter;
import org.eclipse.jgit.transport.PublisherPackSlice.LoadCallback;
import org.eclipse.jgit.transport.PublisherPackSlice.LoadPolicy;
import org.eclipse.jgit.transport.ReceiveCommand.Type;

public class PublisherPackFactory {
	private int sliceSize = 256 * 1024;

	private int sliceMemoryThreshold = 2;

	private AtomicLong packNumber = new AtomicLong();

	private final PublisherBuffer buffer;

	public PublisherPackFactory(PublisherBuffer buffer) {
		this.buffer = buffer;
	}

	public void setSliceSize(int size) {
		sliceSize = size;
	}

	public void setSliceMemoryThreshold(int threshold) {
		sliceMemoryThreshold = threshold;
	}

	public PublisherPack buildPack(int consumers
			Collection<ReceiveCommand> refUpdates
			throws IOException {
		long packNum = packNumber.incrementAndGet();
		List<PublisherPackSlice> slices = new ArrayList<PublisherPackSlice>();
		OutputStream packOut = createSliceStream(consumers
		PacketLineOut packDataLine = new PacketLineOut(packOut);

		Set<ObjectId> remoteObjects = new HashSet<ObjectId>();
		Set<ObjectId> newObjects = new HashSet<ObjectId>();
		PackWriter writer = new PackWriter(repository);
		boolean writePack = false;
		try {
			for (ReceiveCommand r : refUpdates) {
				StringBuilder sb = new StringBuilder();
				sb.append(r.getOldId().name());
				sb.append(" ");
				sb.append(r.getNewId().name());
				sb.append(" ");
				sb.append(r.getRefName());
				packDataLine.writeString(sb.toString());

				if (r.getType() != Type.DELETE)
					writePack = true;
				if (!ObjectId.zeroId().equals(r.getOldId()))
					remoteObjects.add(r.getOldId());
				if (!ObjectId.zeroId().equals(r.getNewId()))
					newObjects.add(r.getNewId());
			}

			packDataLine.end();

			if (writePack) {
				writer.setUseCachedPacks(true);
				writer.setThin(true);
				writer.setReuseValidatingObjects(false);
				writer.setDeltaBaseAsOffset(true);
				writer.preparePack(NullProgressMonitor.INSTANCE
						remoteObjects);
				writer.writePack(NullProgressMonitor.INSTANCE
						NullProgressMonitor.INSTANCE
			}
			packOut.close();
		} catch (IOException e) {
			for (PublisherPackSlice s : slices)
				s.close();
			throw e;
		} finally {
			writer.release();
		}
		return new PublisherPack(repositoryName
	}

	protected PublisherPackSlice createSlice(
			long packNum
		LoadPolicy policy = new LoadPolicy() {
			private AtomicBoolean called = new AtomicBoolean();

			public boolean shouldLoad(PublisherPackSlice slice) {
				return !called.getAndSet(true);
			}
		};

		LoadCallback callback = new LoadCallback() {
			public void loaded(PublisherPackSlice slice) {
				buffer.allocate(slice);
			}

			public void stored(PublisherPackSlice slice) {
				buffer.deallocate(slice);
			}
		};

		return new PublisherPackSliceFile(policy
				"pack-" + packNum + "-" + sliceNumber + ".slice");
	}

	private OutputStream createSliceStream(final int consumers
			final List<PublisherPackSlice> slices
		return new OutputStream() {
			int bufLen;

			byte[] buf = new byte[sliceSize];

			@Override
			public void write(byte[] b
				while (len > 0) {
					int writeLen = Math.min(len
					System.arraycopy(b
					bufLen += writeLen;
					if (writeLen < len)
						appendSlice();
					len -= writeLen;
					off += writeLen;
				}
			}

			@Override
			public void write(int b) throws IOException {
				write(new byte[] { (byte) b });
			}

			@Override
			public void close() throws IOException {
				appendSlice();
			}

			private void appendSlice() throws IOException {
				if (bufLen == 0)
					return;
				if (bufLen < buf.length)
					buf = Arrays.copyOf(buf
				PublisherPackSlice s = createSlice(
						packNum
				if (slices.size() >= sliceMemoryThreshold)
					s.store(false);
				else
					buffer.allocate(s);
				slices.add(s);
				bufLen = 0;
				buf = new byte[sliceSize];
			}
		};
	}
}
