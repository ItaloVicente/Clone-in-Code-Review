
package org.eclipse.jgit.notes;

import java.io.IOException;

import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;

class FanoutBucket extends InMemoryNoteBucket {
	private final NoteBucket[] table;

	FanoutBucket(int prefixLen) {
		super(prefixLen);
		table = new NoteBucket[256];
	}

	void parseOneEntry(int cell
		table[cell] = new LazyNoteBucket(id);
	}

	@Override
	ObjectId get(AnyObjectId objId
		NoteBucket b = table[cell(objId)];
		return b != null ? b.get(objId
	}

	private int cell(AnyObjectId id) {
		return id.getByte(prefixLen >> 1);
	}

	private class LazyNoteBucket extends NoteBucket {
		private final ObjectId treeId;

		LazyNoteBucket(ObjectId treeId) {
			this.treeId = treeId;
		}

		@Override
		ObjectId get(AnyObjectId objId
			return load(objId
		}

		private NoteBucket load(AnyObjectId objId
				throws IOException {
			AbbreviatedObjectId p = objId.abbreviate(prefixLen + 2);
			NoteBucket self = NoteParser.parse(p
			table[cell(objId)] = self;
			return self;
		}
	}
}
