
package org.eclipse.jgit.storage.pack;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.revwalk.RevObject;

public interface ObjectReuseAsIs {
	public ObjectToPack newObjectToPack(RevObject obj);

	public void selectObjectRepresentation(PackWriter packer
			throws IOException

	public void copyObjectAsIs(PackOutputStream out
			throws IOException
}
