
package org.eclipse.jgit.lfs.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.lfs.lib.LargeFileRepository;
import org.eclipse.jgit.lfs.lib.LongObjectId;

class TransferHandler {


	private final LargeFileRepository repository;
	private final List<LfsObject> objects;

	TransferHandler(LargeFileRepository repository
		this.repository = repository;
		this.objects = objects;
	}

	Response.Body process() {
		Response.Body body = new Response.Body();
		if (objects.size() > 0) {
			body.objects = new ArrayList<>();
			for (LfsObject o : objects) {
				Response.ObjectInfo info = new Response.ObjectInfo();
				body.objects.add(info);
				info.oid = o.oid;
				info.size = o.size;
				info.actions = new HashMap<>();

				LongObjectId oid = LongObjectId.fromString(o.oid);
				addAction(UPLOAD
				if (repository.exists(oid)) {
					addAction(DOWNLOAD
				}
			}
		}
		return body;
	}

	private void addAction(String name
			Map<String
		Response.Action action = new Response.Action();
		action.href = repository.getUrl() + oid.getName();
		action.header = new HashMap<>();
		action.header.put("Authorization"
		actions.put(name
	}
}
