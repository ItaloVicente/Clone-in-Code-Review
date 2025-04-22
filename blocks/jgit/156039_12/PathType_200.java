package org.eclipse.jgit.niofs.internal.op.model;

import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;

import static org.eclipse.jgit.lib.FileMode.TYPE_FILE;

public class PathInfo {

	private final long size;
	private final ObjectId objectId;
	private final String path;
	private final PathType pathType;

	public PathInfo(final ObjectId objectId
		this(objectId
	}

	public PathInfo(final ObjectId objectId
		this(objectId
	}

	public PathInfo(final ObjectId objectId
		this(objectId
	}

	public PathInfo(final ObjectId objectId
		this.objectId = objectId;
		this.path = path;
		this.pathType = pathType;
		this.size = size;
	}

	private static PathType convert(final FileMode fileMode) {
		if (fileMode.equals(FileMode.TYPE_TREE)) {
			return PathType.DIRECTORY;
		} else if (fileMode.equals(TYPE_FILE)) {
			return PathType.FILE;
		}
		return null;
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public String getPath() {
		return path;
	}

	public PathType getPathType() {
		return pathType;
	}

	public long getSize() {
		return size;
	}
}
