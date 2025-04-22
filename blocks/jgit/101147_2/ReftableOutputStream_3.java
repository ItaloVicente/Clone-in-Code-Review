
package org.eclipse.jgit.internal.storage.reftable;

import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;

class ReftableConstants {
	static final byte[] FILE_HEADER_MAGIC = { '\1'
	static final byte[] INDEX_MAGIC = { '\1'

	static final int FILE_HEADER_LEN = 8;
	static final int FILE_FOOTER_LEN = 16;
	static final byte VERSION_1 = (byte) 1;

	static final int RAW_IDLEN = OBJECT_ID_LENGTH;

	private ReftableConstants() {
	}
}
