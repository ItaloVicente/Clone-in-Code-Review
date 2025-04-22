package org.eclipse.jgit.submodule;

import org.eclipse.jgit.lib.ObjectId;

public class SubmoduleStatus {

	private final SubmoduleStatusType type;

	private final String path;

	private final ObjectId id;

	public SubmoduleStatus(SubmoduleStatusType type
		this.type = type;
		this.path = path;
		this.id = id;
	}

	public SubmoduleStatusType getType() {
		return type;
	}

	public String getPath() {
		return path;
	}

	public ObjectId getId() {
		return id;
	}

	public String toString() {
		StringBuilder buffer = new StringBuilder();
		if (type == SubmoduleStatusType.UNINITIALIZED)
			buffer.append('-');
		else if (type == SubmoduleStatusType.REV_CHECKED_OUT)
			buffer.append('+');
		else
			buffer.append(' ');
		buffer.append(id.name());
		buffer.append(' ');
		buffer.append(path);
		if (type == SubmoduleStatusType.MISSING)
			buffer.append(" (missing)");
		return buffer.toString();
	}
}
