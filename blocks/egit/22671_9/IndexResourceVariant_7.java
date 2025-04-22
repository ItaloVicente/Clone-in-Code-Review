package org.eclipse.egit.core.internal.storage;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.variants.IResourceVariant;

public abstract class AbstractGitResourceVariant implements IResourceVariant {
	protected final Repository repository;

	protected final String path;

	protected final String fileName;

	protected final boolean isContainer;

	protected final ObjectId objectId;

	protected final int rawMode;

	protected AbstractGitResourceVariant(Repository repository, String path,
			String fileName, boolean isContainer, ObjectId objectId, int rawMode) {
		this.repository = repository;
		this.path = path;
		this.fileName = fileName;
		this.isContainer = isContainer;
		this.objectId = objectId;
		this.rawMode = rawMode;
	}

	public String getName() {
		return fileName;
	}

	public boolean isContainer() {
		return isContainer;
	}

	public String getContentIdentifier() {
		return objectId.name();
	}

	public byte[] asBytes() {
		return objectId.name().getBytes();
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public int getRawMode() {
		return rawMode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof AbstractGitResourceVariant) {
			AbstractGitResourceVariant other = (AbstractGitResourceVariant) obj;
			return this.path.equals(other.path)
					&& this.repository.equals(other.repository)
					&& this.objectId.equals(other.objectId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = 37;
		hash = 37 * hash + (path != null ? path.hashCode() : 0);
		hash = 37 * hash + (repository != null ? repository.hashCode() : 0);
		hash = 37 * hash + (objectId != null ? objectId.hashCode() : 0);
		return hash;
	}
}
