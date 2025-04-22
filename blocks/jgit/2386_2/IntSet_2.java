
package org.eclipse.jgit.storage.pack;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_TREE;

import java.io.IOException;
import java.util.Set;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSubclassMap;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

class BaseSearch {
	private static final int M_BLOB = FileMode.REGULAR_FILE.getBits();

	private static final int M_TREE = FileMode.TREE.getBits();

	private final ProgressMonitor progress;

	private final ObjectReader reader;

	private final ObjectId[] baseTrees;

	private final ObjectIdSubclassMap<ObjectToPack> edgeObjects;

	private final IntSet alreadyProcessed;

	private final ObjectIdSubclassMap<TreeWithData> treeCache;

	private final CanonicalTreeParser parser;

	private final MutableObjectId idBuf;

	BaseSearch(ProgressMonitor countingMonitor
			ObjectIdSubclassMap<ObjectToPack> edges
		progress = countingMonitor;
		reader = or;
		baseTrees = bases.toArray(new ObjectId[bases.size()]);
		edgeObjects = edges;

		alreadyProcessed = new IntSet();
		treeCache = new ObjectIdSubclassMap<TreeWithData>();
		parser = new CanonicalTreeParser();
		idBuf = new MutableObjectId();
	}

	void addBase(int objectType
			throws IOException {
		final int tailMode = modeForType(objectType);
		if (tailMode == 0)
			return;

		if (!alreadyProcessed.add(pathHash))
			return;

		if (pathLen == 0) {
			for (ObjectId root : baseTrees)
				add(root
			return;
		}

		final int firstSlash = nextSlash(pathBuf

		CHECK_BASE: for (ObjectId root : baseTrees) {
			int ptr = 0;
			int end = firstSlash;
			int mode = end != pathLen ? M_TREE : tailMode;

			parser.reset(readTree(root));
			while (!parser.eof()) {
				int cmp = parser.pathCompare(pathBuf

				if (cmp < 0) {
					parser.next();
					continue;
				}

				if (cmp > 0)
					continue CHECK_BASE;

				if (end == pathLen) {
					if (parser.getEntryFileMode().getObjectType() == objectType) {
						idBuf.fromRaw(parser.idBuffer()
						add(idBuf
					}
					continue CHECK_BASE;
				}

				if (!FileMode.TREE.equals(parser.getEntryRawMode()))
					continue CHECK_BASE;

				ptr = end + 1;
				end = nextSlash(pathBuf
				mode = end != pathLen ? M_TREE : tailMode;

				idBuf.fromRaw(parser.idBuffer()
				parser.reset(readTree(idBuf));
			}
		}
	}

	private static int modeForType(int typeCode) {
		switch (typeCode) {
		case OBJ_TREE:
			return M_TREE;

		case OBJ_BLOB:
			return M_BLOB;

		default:
			return 0;
		}
	}

	private static int nextSlash(byte[] pathBuf
		while (ptr < end && pathBuf[ptr] != '/')
			ptr++;
		return ptr;
	}

	private void add(AnyObjectId id
		ObjectToPack obj = new ObjectToPack(id
		obj.setEdge();
		obj.setPathHash(pathHash);

		if (edgeObjects.addIfAbsent(obj) == obj)
			progress.update(1);
	}

	private byte[] readTree(AnyObjectId id) throws MissingObjectException
			IncorrectObjectTypeException
		TreeWithData tree = treeCache.get(id);
		if (tree != null)
			return tree.buf;

		ObjectLoader ldr = reader.open(id
		byte[] buf = ldr.getCachedBytes(Integer.MAX_VALUE);
		treeCache.add(new TreeWithData(id
		return buf;
	}

	private static class TreeWithData extends ObjectId {
		final byte[] buf;

		TreeWithData(AnyObjectId id
			super(id);
			this.buf = buf;
		}
	}
}
