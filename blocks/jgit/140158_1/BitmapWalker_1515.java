
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AsyncOperation;

public interface AsyncRevObjectQueue extends AsyncOperation {
	RevObject next() throws MissingObjectException
}
