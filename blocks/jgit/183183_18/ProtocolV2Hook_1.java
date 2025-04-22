package org.eclipse.jgit.transport;

import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;

public final class ObjectInfoRequest {
	private final List<ObjectId> objectIDs;

	private ObjectInfoRequest(List<ObjectId> objectIDs) {
		this.objectIDs = objectIDs;
	}

	public List<ObjectId> getObjectIDs() {
		return this.objectIDs;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private List<ObjectId> objectIDs = Collections.emptyList();

		private Builder() {
		}

		public Builder setObjectIDs(List<ObjectId> value) {
			objectIDs = value;
			return this;
		}

		public ObjectInfoRequest build() {
			return new ObjectInfoRequest(
					Collections.unmodifiableList(objectIDs));
		}
	}
}
