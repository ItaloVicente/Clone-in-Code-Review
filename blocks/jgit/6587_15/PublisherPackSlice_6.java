
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.pack.PackWriter;
import org.eclipse.jgit.transport.Publisher.PublisherException;
import org.eclipse.jgit.transport.PublisherPackSlice.Allocator;
import org.eclipse.jgit.transport.PublisherPackSlice.Deallocator;
import org.eclipse.jgit.transport.PublisherPackSlice.LoadPolicy;
import org.eclipse.jgit.transport.ReceiveCommand.Type;

public class PublisherPackFactory {
	private int sliceSize = 256 * 1024;

	private int sliceMemoryInMemoryLimit = 2;

	private final PublisherMemoryPool buffer;

	public PublisherPackFactory(PublisherMemoryPool buffer) {
		this.buffer = buffer;
	}

	public void setSliceSize(int size) {
		sliceSize = size;
	}

	public void setSliceInMemoryLimit(int threshold) {
		sliceMemoryInMemoryLimit = threshold;
	}

	public PublisherPack buildPack(Repository repository
			Collection<ReceiveCommand> refUpdates
			Collection<SubscribeSpec> existingSpecs
			throws IOException {
		Set<String> updatedRefs = new LinkedHashSet<String>();
		for (ReceiveCommand rc : refUpdates)
			updatedRefs.add(rc.getRefName());
		Set<ObjectId> existingObjects = new HashSet<ObjectId>();
		RefDatabase refdb = repository.getRefDatabase();
		Collection<Ref> matchingRefs = Collections.emptyList();
		for (SubscribeSpec spec : existingSpecs) {
			String refName = spec.getRefName();
			if (spec.isWildcard()) {
				String refNamePrefix = SubscribeSpec.stripWildcard(refName);
				Map<String
				for (String updatedRef : updatedRefs) {
					if (updatedRef.startsWith(refNamePrefix))
						refMap.remove(updatedRef.substring(
								refNamePrefix.length()));
				}
				matchingRefs = refMap.values();
			} else if (updatedRefs.contains(refName))
				continue;
			else {
				Ref ref = refdb.getRef(refName);
				if (ref == null)
					continue;
				matchingRefs = Collections.singleton(ref);
			}

			for (Ref r : matchingRefs)
				existingObjects.add(r.getLeaf().getObjectId());
		}
		List<PublisherPackSlice> slices = new ArrayList<PublisherPackSlice>();
		OutputStream packOut = createSliceStream(slices
		PacketLineOut packDataLine = new PacketLineOut(packOut);

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
					existingObjects.add(r.getOldId());
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
						existingObjects);
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
		return new PublisherPack(slices);
	}

	protected PublisherPackSlice createSlice(
			String packId
		LoadPolicy policy = new LoadPolicy() {
			private AtomicBoolean called = new AtomicBoolean();

			public boolean shouldLoad(PublisherPackSlice slice) {
				return !called.getAndSet(true);
			}
		};

		Allocator allocator = new Allocator() {
			public Deallocator allocate(final PublisherPackSlice slice)
					throws PublisherException {
				return buffer.allocate(slice);
			}
		};

		return new PublisherPackSliceFile(policy
				+ sliceNumber + ".slice");
	}

	private OutputStream createSliceStream(
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
				PublisherPackSlice s = createSlice(packId
				if (slices.size() >= sliceMemoryInMemoryLimit) {
					s.setDeallocator(Deallocator.INSTANCE);
					s.store();
				} else
					s.setDeallocator(buffer.allocate(s));
				slices.add(s);
				bufLen = 0;
				buf = new byte[sliceSize];
			}
		};
	}
}
