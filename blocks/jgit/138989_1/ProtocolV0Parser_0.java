
package org.eclipse.jgit.transport;

public final class FilterSpec {

	private final long blobLimit;

	private FilterSpec(long blobLimit) {
		this.blobLimit = blobLimit;
	}

	public static FilterSpec blobFilter(long blobLimit) {
		if (blobLimit < 0) {
			throw new IllegalArgumentException(
		}
		return new FilterSpec(blobLimit);
	}

	public static final FilterSpec NO_OP_FILTER = new FilterSpec(-1);

	public long getBlobLimit() {
		return blobLimit;
	}

	public boolean isNoOp() {
		return blobLimit == -1;
	}
}
