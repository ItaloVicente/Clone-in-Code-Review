
package org.eclipse.jgit.lib;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class CachedObjectDirectory extends CachedObjectDatabase {
	private final HashSet<AnyObjectId> unpackedObjects = new HashSet<AnyObjectId>();

	public CachedObjectDirectory(ObjectDirectory wrapped) {
		super(wrapped);
		File objects = wrapped.getDirectory();
		String[] objectDirs = objects.list();
		if (objectDirs != null) {
			for (String d : objectDirs) {
				if (isHexString(2
					String[] files = new File(objects
					if (files != null) {
						for (String f : files) {
							if (isHexString(38
								unpackedObjects.add(ObjectId.fromString(d + f));
							}
						}
					}
				}
			}
		}
	}

	private static final boolean isHexString(int length
		if (s.length() != length) {
			return false;
		}
		for (int i = 0; i < length; i++) {
			char ch = s.charAt(i);
			if (!(('0' <= ch && ch <= '9') || ('a' <= ch && ch <= 'f') || ('A' <= ch && ch <= 'F'))) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected ObjectLoader openObject2(WindowCursor curs
			AnyObjectId objectId) throws IOException {
		return unpackedObjects.contains(objectId) ? super.openObject2(curs
				objectName
	}
}
