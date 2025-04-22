package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.util.Arrays;
import org.eclipse.jgit.util.NB;

class PackObjectSizeIndexV1 implements PackObjectSizeIndex {

	private static final byte BITS_24 = 0x18;

	private static final byte BITS_32 = 0x20;

	private final CompactInt3Array positions24;

	private final int[] positions32;

	private final int[] sizes32;

	private final long[] sizes64;

	static PackObjectSizeIndex parse(InputStream in) throws IOException {
		IndexInputStreamReader stream = new IndexInputStreamReader(in);
		int objCount = stream.readInt();
		if (objCount == 0) {
			return PackObjectSizeIndex.EMPTY;
		}
		return new PackObjectSizeIndexV1(stream
	}

	private PackObjectSizeIndexV1(IndexInputStreamReader stream
		CompactInt3Array pos24 = null;
		int[] pos32 = null;

		byte positionEncoding;
		while ((positionEncoding = stream.readByte()) != 0) {
			if (Byte.compareUnsigned(positionEncoding
				int sz = stream.readInt();
				pos24 = new CompactInt3Array(stream.readNBytes(sz * 3));
			} else if (Byte.compareUnsigned(positionEncoding
				int sz = stream.readInt();
				pos32 = stream.readIntArray(sz);
			} else {
				throw new UnsupportedEncodingException(
						String.format("Unknown position encoding %s"
								Integer.toHexString(positionEncoding)));
			}
		}
		positions24 = pos24 != null ? pos24 : CompactInt3Array.EMPTY;
		positions32 = pos32 != null ? pos32 : new int[0];

		sizes32 = stream.readIntArray(objCount);
		int c64sizes = stream.readInt();
		if (c64sizes == 0) {
			sizes64 = new long[0];
			return;
		}
		sizes64 = stream.readLongArray(c64sizes);
		int c128sizes = stream.readInt();
		if (c128sizes != 0) {
			throw new IOException("Unsupported sizes in object-size-index");
		}
	}

	@Override
	public long getSize(int idxOffset) {
		int pos = -1;
		if (!positions24.isEmpty() && idxOffset <= positions24.getLastValue()) {
			pos = positions24.find(idxOffset);
		} else if (positions32.length > 0 && idxOffset >= positions32[0]){
			int pos32 = Arrays.binarySearch(positions32
			if (pos32 >= 0) {
				pos = pos32 + positions24.size();
			}
		}
		if (pos < 0) {
			return -1;
		}

		int objSize = sizes32[pos];
		if (objSize < 0) {
			int secondPos = Math.abs(objSize) - 1;
			return sizes64[secondPos];
		}
		return objSize;
	}

	@Override
	public long getObjectCount() {
		return positions24.size() + positions32.length;
	}

	private static class IndexInputStreamReader {

		private final byte[] buffer = new byte[8];

		private final InputStream in;

		IndexInputStreamReader(InputStream in) {
			this.in = in;
		}

		int readInt() throws IOException {
			int n = in.readNBytes(buffer
			if (n < 4) {
				throw new IOException(
						"Unable to read a full int from the stream");
			}
			return NB.decodeInt32(buffer
		}

		int[] readIntArray(int intsCount) throws IOException {
			if (intsCount == 0) {
				return new int[0];
			}

			int[] dest = new int[intsCount];
			for (int i = 0; i < intsCount; i++) {
				dest[i] = readInt();
			}
			return dest;
		}

		long readLong() throws IOException {
			int n = in.readNBytes(buffer
			if (n < 8) {
				throw new IOException(
						"Unable to read a full int from the stream");
			}
			return NB.decodeInt64(buffer
		}

		long[] readLongArray(int longsCount) throws IOException {
			if (longsCount == 0) {
				return new long[0];
			}

			long[] dest = new long[longsCount];
			for (int i = 0; i < longsCount; i++) {
				dest[i] = readLong();
			}
			return dest;
		}

		byte readByte() throws IOException {
			int n = in.readNBytes(buffer
			if (n != 1) {
				throw new IOException("Cannot read byte from stream");
			}
			return buffer[0];
		}

		byte[] readNBytes(int sz) throws IOException {
			return in.readNBytes(sz);
		}
	}

}
