
package org.eclipse.jgit.lib;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.revwalk.RevObject;

public interface ObjectReuseAsIs {
	public ObjectToPack newObjectToPack(RevObject obj);

	public void selectObjectRepresentation(PackWriter packer
			throws IOException
}
