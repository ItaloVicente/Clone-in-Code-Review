package org.eclipse.jgit.transport;

import java.util.Collections;
import java.util.List;

public final class ObjectInfoRequest {
	private final List<String> objectIDs;

	private ObjectInfoRequest(List<String> objectIDs) {
		this.objectIDs = objectIDs;
	}

	public List<String> getObjectIDs() {
		return this.objectIDs;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private List<String> objectIDs = Collections.emptyList();

		private Builder() {
		}

		public Builder setObjectIDs(List<String> value) {
			objectIDs = value;
			return this;
		}

		public ObjectInfoRequest build() {
			return new ObjectInfoRequest(
					Collections.unmodifiableList(objectIDs));
		}
	}
}
