package org.eclipse.jgit.submodule;

import org.eclipse.jgit.lib.ObjectId;

public class SubmoduleStatus {

	private final SubmoduleStatusType type;

	private final String path;

	private final ObjectId indexId;

	private final ObjectId headId;

	public SubmoduleStatus(final SubmoduleStatusType type
			final ObjectId indexId) {
		this(type
	}

	public SubmoduleStatus(final SubmoduleStatusType type
			final ObjectId indexId
		this.type = type;
		this.path = path;
		this.indexId = indexId;
		this.headId = headId;
	}

	public SubmoduleStatusType getType() {
		return type;
	}

	public String getPath() {
		return path;
	}

	public ObjectId getIndexId() {
		return indexId;
	}

	public ObjectId getHeadId() {
		return headId;
	}
}
