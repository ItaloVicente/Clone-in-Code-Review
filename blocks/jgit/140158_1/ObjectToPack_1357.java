
package org.eclipse.jgit.internal.storage.pack;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BitmapIndex.BitmapBuilder;
import org.eclipse.jgit.lib.ProgressMonitor;

public interface ObjectReuseAsIs {
	ObjectToPack newObjectToPack(AnyObjectId objectId

	void selectObjectRepresentation(PackWriter packer
			ProgressMonitor monitor
			throws IOException

	void writeObjects(PackOutputStream out
			throws IOException;

	void copyObjectAsIs(PackOutputStream out
			boolean validate) throws IOException
			StoredObjectRepresentationNotAvailableException;

	void copyPackAsIs(PackOutputStream out
			throws IOException;

	Collection<CachedPack> getCachedPacksAndUpdate(
			BitmapBuilder needBitmap) throws IOException;
}
