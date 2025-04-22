
package org.eclipse.jgit.storage.dht;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.jgit.storage.pack.ObjectToPack;
import org.eclipse.jgit.storage.pack.PackOutputStream;

final class ObjectWriter {
	private final DhtReader ctx;

	private Prefetcher prefetch;

	private LinkedHashMap<ChunkKey

	private int curVisit;

	ObjectWriter(DhtReader ctx) {
		this.ctx = ctx;
	}

	void writeObjects(PackOutputStream out
			throws IOException {
		prefetch = ctx.beginPrefetch();
		prefetch.setIncludeFragments(true);
		plan(list);

		for (ObjectToPack otp : list)
			out.writeObject(otp);
	}

	private void plan(List<DhtObjectToPack> list) {
		allVisits = new LinkedHashMap<ChunkKey
		curVisit = 1;

		for (DhtObjectToPack obj : list)
			visit(obj);
		prefetch.push(allVisits.keySet());
		allVisits = null;

		Collections.sort(list
			public int compare(DhtObjectToPack a
				return a.visitOrder - b.visitOrder;
			}
		});
	}

	private void visit(DhtObjectToPack obj) {
		DhtObjectToPack base = (DhtObjectToPack) obj.getDeltaBase();
		if (base != null && base.visitOrder == 0) {
			obj.visitOrder = curVisit;
			visit(base);
		}

		ChunkKey key = obj.chunk;
		if (key != null) {
			Integer i = allVisits.get(key);
			if (i == null) {
				i = Integer.valueOf(1 + allVisits.size());
				allVisits.put(key
			}
			curVisit = i.intValue();
		}

		obj.visitOrder = curVisit;
	}
}
