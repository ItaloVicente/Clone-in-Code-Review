
package org.eclipse.jgit.internal.storage.reftable;

class ReftableConstants {
	static final byte[] FILE_HEADER_MAGIC = { '\1'

	static final int FILE_HEADER_LEN = 8;
	static final int FILE_FOOTER_LEN = 36;
	static final byte VERSION_1 = (byte) 1;

	static final byte FILE_BLOCK_TYPE = '\1';
	static final byte REF_BLOCK_TYPE = 'r';
	static final byte LOG_BLOCK_TYPE = 'g';
	static final byte INDEX_BLOCK_TYPE = (byte) 0x80;

	static final int MAX_RESTARTS = 65536;

	private ReftableConstants() {
	}
}
