
package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Constants;

public class ReceivedPackStatistics {
	private long numBytesRead;

	private long numWholeCommit;
	private long numWholeTree;
	private long numWholeBlob;
	private long numWholeTag;
	private long numOfsDelta;
	private long numRefDelta;

	private long numDeltaCommit;
	private long numDeltaTree;
	private long numDeltaBlob;
	private long numDeltaTag;

	public long getNumBytesRead() {
		return numBytesRead;
	}

	public long getNumWholeCommit() {
		return numWholeCommit;
	}

	public long getNumWholeTree() {
		return numWholeTree;
	}

	public long getNumWholeBlob() {
		return numWholeBlob;
	}

	public long getNumWholeTag() {
		return numWholeTag;
	}

	public long getNumOfsDelta() {
		return numOfsDelta;
	}

	public long getNumRefDelta() {
		return numRefDelta;
	}

	public long getNumDeltaCommit() {
		return numDeltaCommit;
	}

	public long getNumDeltaTree() {
		return numDeltaTree;
	}

	public long getNumDeltaBlob() {
		return numDeltaBlob;
	}

	public long getNumDeltaTag() {
		return numDeltaTag;
	}

	public static class Builder {
		private long numBytesRead;

		private long numWholeCommit;
		private long numWholeTree;
		private long numWholeBlob;
		private long numWholeTag;
		private long numOfsDelta;
		private long numRefDelta;

		private long numDeltaCommit;
		private long numDeltaTree;
		private long numDeltaBlob;
		private long numDeltaTag;

		public Builder setNumBytesRead(long numBytesRead) {
			this.numBytesRead = numBytesRead;
			return this;
		}

		public Builder addWholeObject(int type) {
			switch (type) {
				case Constants.OBJ_COMMIT:
					numWholeCommit++;
					break;
				case Constants.OBJ_TREE:
					numWholeTree++;
					break;
				case Constants.OBJ_BLOB:
					numWholeBlob++;
					break;
				case Constants.OBJ_TAG:
					numWholeTag++;
					break;
				default:
					throw new IllegalArgumentException(
			}
			return this;
		}

		public Builder addOffsetDelta() {
			numOfsDelta++;
			return this;
		}

		public Builder addRefDelta() {
			numRefDelta++;
			return this;
		}

		public Builder addDeltaObject(int type) {
			switch (type) {
				case Constants.OBJ_COMMIT:
					numDeltaCommit++;
					break;
				case Constants.OBJ_TREE:
					numDeltaTree++;
					break;
				case Constants.OBJ_BLOB:
					numDeltaBlob++;
					break;
				case Constants.OBJ_TAG:
					numDeltaTag++;
					break;
				default:
					throw new IllegalArgumentException(
			}
			return this;
		}

		ReceivedPackStatistics build() {
			ReceivedPackStatistics s = new ReceivedPackStatistics();
			s.numBytesRead = numBytesRead;
			s.numWholeCommit = numWholeCommit;
			s.numWholeTree = numWholeTree;
			s.numWholeBlob = numWholeBlob;
			s.numWholeTag = numWholeTag;
			s.numOfsDelta = numOfsDelta;
			s.numRefDelta = numRefDelta;
			s.numDeltaCommit = numDeltaCommit;
			s.numDeltaTree = numDeltaTree;
			s.numDeltaBlob = numDeltaBlob;
			s.numDeltaTag = numDeltaTag;
			return s;
		}
	}
}
