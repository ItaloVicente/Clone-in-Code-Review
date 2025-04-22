
package org.eclipse.jgit.internal.ketch;

import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;

public class ReplicaFetchRequest {
	private final Set<String> wantRefs;
	private final Set<ObjectId> wantObjects;
	private Map<String

	public ReplicaFetchRequest(Set<String> wantRefs
			Set<ObjectId> wantObjects) {
		this.wantRefs = wantRefs;
		this.wantObjects = wantObjects;
	}

	public Set<String> getWantRefs() {
		return wantRefs;
	}

	public Set<ObjectId> getWantObjects() {
		return wantObjects;
	}

	@Nullable
	public Map<String
		return refs;
	}

	public void setRefs(Map<String
		this.refs = refs;
	}
}
