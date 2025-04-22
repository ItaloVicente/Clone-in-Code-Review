
package org.eclipse.jgit.internal.storage.reftable;

import java.io.IOException;

public class BlockSizeTooSmallException extends IOException {
	private static final long serialVersionUID = 1L;

	private final int minBlockSize;

	BlockSizeTooSmallException(int b) {
		minBlockSize = b;
	}

	public int getMinimumBlockSize() {
		return minBlockSize;
	}
}
