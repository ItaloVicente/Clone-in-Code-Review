
package org.eclipse.jgit.storage.dht;

import java.util.List;

import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.storage.dht.spi.Database;
import org.eclipse.jgit.storage.pack.PackWriter;

final class RepresentationSelector extends BatchObjectLookup<DhtObjectToPack> {
	private final PackWriter packer;

	private final DhtObjectRepresentation rep;

	RepresentationSelector(PackWriter packer
			DhtReader reader
		super(repo
		setRetryMissingObjects(true);

		this.packer = packer;
		this.rep = new DhtObjectRepresentation();
	}

	protected void onResult(DhtObjectToPack obj
		if (!tmp.isEmpty()) {
			rep.set(tmp.get(0));
			packer.select(obj
		}
	}
}
