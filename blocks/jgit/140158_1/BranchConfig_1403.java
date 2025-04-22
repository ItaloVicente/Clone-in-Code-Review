
package org.eclipse.jgit.lib;

import org.eclipse.jgit.errors.CorruptObjectException;

public interface BlobObjectChecker {
	BlobObjectChecker NULL_CHECKER =
			new BlobObjectChecker() {
				@Override
				public void update(byte[] in
				}

				@Override
				public void endBlob(AnyObjectId id) {
				}
			};

	void update(byte[] in

	void endBlob(AnyObjectId id) throws CorruptObjectException;
}
