package org.eclipse.jgit.internal.storage.file;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.NB;

public abstract class PackObjectSizeIndexWriter {

	private static final int MAX_24BITS_UINT = 0xffffff;

	private static final PackObjectSizeIndexWriter NULL_WRITER = new PackObjectSizeIndexWriter() {
		@Override
		public void write(List<? extends PackedObjectInfo> objs) {
		}
	};

	protected static final byte[] HEADER = { -1

	public static PackObjectSizeIndexWriter createWriter(OutputStream os
			int minSize) {
		if (minSize < 0) {
			return NULL_WRITER;
		}
		return new PackObjectSizeWriterV1(os
	}

	public abstract void write(List<? extends PackedObjectInfo> objs)
			throws IOException;

	static class PackObjectSizeWriterV1 extends PackObjectSizeIndexWriter {

		private final OutputStream os;

		private final int minObjSize;

		private final byte[] intBuffer = new byte[4];

		PackObjectSizeWriterV1(OutputStream os
			this.os = new BufferedOutputStream(os);
			this.minObjSize = minSize;
		}

		@Override
		public void write(List<? extends PackedObjectInfo> allObjects)
				throws IOException {
			os.write(HEADER);
			writeInt32(minObjSize);

			PackedObjectStats stats = countIndexableObjects(allObjects);
			int[] indexablePositions = findIndexablePositions(allObjects
					stats.indexableObjs);
			if (indexablePositions.length == 0) {
				os.flush();
				return;
			}

			if (stats.pos24Bits > 0) {
				writeUInt8(24);
				writeInt32(stats.pos24Bits);
				applyToRange(indexablePositions
						this::writeInt24);
			}
			if (stats.pos31Bits > 0) {
				writeUInt8(32);
				writeInt32(stats.pos31Bits);
				applyToRange(indexablePositions
						stats.pos24Bits + stats.pos31Bits
			}
			writeUInt8(0);
			writeSizes(allObjects
			os.flush();
		}

		private void writeUInt8(int i) throws IOException {
			if (i > 255) {
				throw new IllegalStateException(
						JGitText.get().numberDoesntFit);
			}
			NB.encodeInt32(intBuffer
			os.write(intBuffer
		}

		private void writeInt24(int i) throws IOException {
			NB.encodeInt24(intBuffer
			os.write(intBuffer
		}

		private void writeInt32(int i) throws IOException {
			NB.encodeInt32(intBuffer
			os.write(intBuffer);
		}

		private void writeSizes(List<? extends PackedObjectInfo> allObjects
				int[] indexablePositions
				throws IOException {
			if (indexablePositions.length == 0) {
				writeInt32(0);
				return;
			}

			byte[] sizes64bits = new byte[8 * objsBiggerThan2Gb];
			int s64 = 0;
			for (int i = 0; i < indexablePositions.length; i++) {
				PackedObjectInfo info = allObjects.get(indexablePositions[i]);
				if (info.getFullSize() < Integer.MAX_VALUE) {
					writeInt32((int) info.getFullSize());
				} else {
					writeInt32(-1 * (s64 + 1));
					NB.encodeInt64(sizes64bits
					s64++;
				}
			}
			if (objsBiggerThan2Gb > 0) {
				writeInt32(objsBiggerThan2Gb);
				os.write(sizes64bits);
			}
			writeInt32(0);
		}

		private int[] findIndexablePositions(
				List<? extends PackedObjectInfo> allObjects
				int indexableObjs) {
			int[] positions = new int[indexableObjs];
			int positionIdx = 0;
			for (int i = 0; i < allObjects.size(); i++) {
				PackedObjectInfo o = allObjects.get(i);
				if (!shouldIndex(o)) {
					continue;
				}
				positions[positionIdx++] = i;
			}
			return positions;
		}

		private PackedObjectStats countIndexableObjects(
				List<? extends PackedObjectInfo> objs) {
			PackedObjectStats stats = new PackedObjectStats();
			for (int i = 0; i < objs.size(); i++) {
				PackedObjectInfo o = objs.get(i);
				if (!shouldIndex(o)) {
					continue;
				}
				stats.indexableObjs++;
				if (o.getFullSize() > Integer.MAX_VALUE) {
					stats.sizeOver2GB++;
				}
				if (i <= MAX_24BITS_UINT) {
					stats.pos24Bits++;
				} else {
					stats.pos31Bits++;
				}
			}
			return stats;
		}

		private boolean shouldIndex(PackedObjectInfo o) {
			return (o.getType() == Constants.OBJ_BLOB)
					&& (o.getFullSize() >= minObjSize);
		}

		private static class PackedObjectStats {
			int indexableObjs;

			int pos24Bits;

			int pos31Bits;

			int sizeOver2GB;
		}

		@FunctionalInterface
		interface IntEncoder {
			void encode(int i) throws IOException;
		}

		private static void applyToRange(int[] allPositions
				IntEncoder encoder) throws IOException {
			for (int i = start; i < end; i++) {
				encoder.encode(allPositions[i]);
			}
		}
	}
}
