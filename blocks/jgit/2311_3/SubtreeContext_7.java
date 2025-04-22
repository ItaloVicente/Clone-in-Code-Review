
package org.eclipse.jgit.subtree;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

public abstract class SubtreeContext {

	public static final RevCommit NO_SUBTREE = new RevCommit(ObjectId.zeroId()) {
		public String toString() {
			return "NO_SUBTREE";
		}
	};

	private Map<ObjectId

	protected String id;

	SubtreeContext(String id) {
		this.id = id;
	}

	abstract String getPath(Config conf);

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return getId();
	}

	public RevCommit getSplitCommit(ObjectId srcCommit) {
		return mNewParents.get(srcCommit);
	}

	void setSplitCommit(ObjectId srcCommit
		mNewParents.put(srcCommit
	}

}
