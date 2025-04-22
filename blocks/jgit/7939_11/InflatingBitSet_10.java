
package org.eclipse.jgit.storage.file;

import javaewah.EWAHCompressedBitmap;
import javaewah.IntIterator;

final class InflatingBitSet {
	private static final long[] EMPTY = new long[0];

	private final EWAHCompressedBitmap bitmap;

	private IntIterator iterator;

	private long[] inflated;

	private int nextPosition = -1;

	private final int sizeInBits;

	InflatingBitSet(EWAHCompressedBitmap bitmap) {
		this(bitmap
	}

	private InflatingBitSet(EWAHCompressedBitmap orBitmap
		this.bitmap = orBitmap;
		this.inflated = inflated;
		this.sizeInBits = bitmap.sizeInBits();
	}

	final boolean maybeContains(int position) {
		if (get(position))
			return true;
		return nextPosition <= position && position < sizeInBits;
	}

	final boolean contains(int position) {
		if (get(position))
			return true;
		if (position <= nextPosition || position >= sizeInBits)
			return position == nextPosition;

		if (iterator == null) {
			iterator = bitmap.intIterator();
			if (iterator.hasNext())
				nextPosition = iterator.next();
			else
				return false;
		} else if (!iterator.hasNext())
			return false;

		int positionBlock = block(position);
		if (positionBlock >= inflated.length) {
			long[] tmp = new long[block(sizeInBits) + 1];
			System.arraycopy(inflated
			inflated = tmp;
		}

		int block = block(nextPosition);
		long word = mask(nextPosition);
		int end = Math.max(nextPosition
		while (iterator.hasNext()) {
			nextPosition = iterator.next();
			if (end < nextPosition)
				break;

			int b = block(nextPosition);
			long m = mask(nextPosition);
			if (block == b) {
				word |= m;
			} else {
				inflated[block] = word;
				block = b;
				word = m;
			}
		}
		inflated[block] = word;
		return block == positionBlock && (word & mask(position)) != 0;
	}

	private final boolean get(int position) {
		int b = block(position);
		return b < inflated.length && (inflated[b] & mask(position)) != 0;
	}

	private static final int block(int position) {
		return position >> 6;
	}

	private static final long mask(int position) {
		return 1L << position;
	}

	private final boolean isEmpty() {
		return sizeInBits == 0;
	}

	final InflatingBitSet or(EWAHCompressedBitmap other) {
		if (other.sizeInBits() == 0)
			return this;
		return new InflatingBitSet(bitmap.or(other)
	}

	final InflatingBitSet andNot(EWAHCompressedBitmap other) {
		if (isEmpty())
			return this;
		return new InflatingBitSet(bitmap.andNot(other));
	}

	final InflatingBitSet xor(EWAHCompressedBitmap other) {
		if (isEmpty()) {
			if (other.sizeInBits() == 0)
				return this;
			return new InflatingBitSet(other);
		}
		return new InflatingBitSet(bitmap.xor(other));
	}

	final EWAHCompressedBitmap getBitmap() {
		return bitmap;
	}
}
