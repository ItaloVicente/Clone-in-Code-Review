
package org.eclipse.jgit.lib;

import java.io.File;
import java.io.IOException;

public class CachedObjectDirectory extends CachedObjectDatabase {
	private final ObjectIdSubclassMap<ObjectId> unpackedObjects = new ObjectIdSubclassMap<ObjectId>();

	public CachedObjectDirectory(ObjectDirectory wrapped) {
		super(wrapped);
		File objects = wrapped.getDirectory();
		String[] fanout = objects.list();
		if (fanout == null)
			fanout = new String[0];
		for (String d : fanout) {
			if (d.length() != 2)
				continue;
			String[] entries = new File(objects
			if (entries == null)
				continue;
			for (String e : entries) {
				if (e.length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
					continue;
				try {
					unpackedObjects.add(ObjectId.fromString(d + e));
				} catch (IllegalArgumentException notAnObject) {
				}
			}
		}
	}

	@Override
	protected ObjectLoader openObject2(WindowCursor curs
			AnyObjectId objectId) throws IOException {
		if (unpackedObjects.get(objectId) == null)
			return null;
		return super.openObject2(curs
	}

	@Override
	protected boolean hasObject1(AnyObjectId objectId) {
		if (unpackedObjects.get(objectId) != null)
		return super.hasObject1(objectId);
	}

	@Override
	protected boolean hasObject2(String name) {
	}
}
