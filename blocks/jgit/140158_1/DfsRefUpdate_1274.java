
package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;

final class DfsRefRename extends RefRename {
	DfsRefRename(RefUpdate src
		super(src
	}

	@Override
	protected Result doRename() throws IOException {

		destination.setExpectedOldObjectId(ObjectId.zeroId());
		destination.setNewObjectId(source.getRef().getObjectId());
		switch (destination.update()) {
		case NEW:
			source.delete();
			return Result.RENAMED;

		default:
			return destination.getResult();
		}
	}
}
