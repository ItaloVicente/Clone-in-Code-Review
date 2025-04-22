
package org.eclipse.jgit.storage.dht;

import java.util.List;

import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.storage.pack.PackWriter;

final class RepresentationSelector extends BatchObjectLookup<DhtObjectToPack> {
	private final PackWriter packer;

	private final DhtObjectRepresentation rep;

	RepresentationSelector(PackWriter packer
			ProgressMonitor monitor) {
		super(reader
		setRetryMissingObjects(true);

		this.packer = packer;
		this.rep = new DhtObjectRepresentation();
	}

	protected void onResult(DhtObjectToPack obj
		for (int i = info.size() - 1; 0 <= i; i--) {
			rep.set(info.get(i));
			packer.select(obj
		}
	}
}
