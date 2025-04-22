
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefComparator;

@Command(usage = "usage_ShowRef")
class ShowRef extends TextBuiltin {
	@Override
	protected void run() {
		try {
			for (Ref r : getSortedRefs()) {
				show(r.getObjectId()
				if (r.getPeeledObjectId() != null) {
					show(r.getPeeledObjectId()
				}
			}
		} catch (IOException e) {
			throw die(e.getMessage()
		}
	}

	private Iterable<Ref> getSortedRefs() throws IOException {
		List<Ref> all = db.getRefDatabase().getRefs();
		return RefComparator.sort(all);
	}

	private void show(AnyObjectId id
			throws IOException {
		outw.print(id.name());
		outw.print('\t');
		outw.print(name);
		outw.println();
	}
}
