
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;

public class RevBlob extends RevObject {
	protected RevBlob(AnyObjectId id) {
		super(id);
	}

	@Override
	public final int getType() {
		return Constants.OBJ_BLOB;
	}

	@Override
	void parseHeaders(RevWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		if (walk.reader.has(this))
			flags |= PARSED;
		else
			throw new MissingObjectException(this
	}

	@Override
	void parseBody(RevWalk walk) throws MissingObjectException
			IncorrectObjectTypeException
		if ((flags & PARSED) == 0)
			parseHeaders(walk);
	}
}
