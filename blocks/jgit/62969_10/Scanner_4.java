
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.RefUpdate.Result.REJECTED;
import static org.eclipse.jgit.lib.RefUpdate.Result.RENAMED;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.revwalk.RevWalk;

class Rename extends RefRename {
	private final RefTreeDb refdb;

	Rename(RefTreeDb refdb
		super(o
		this.refdb = refdb;
	}

	@Override
	protected Result doRename() throws IOException {
		try (RevWalk rw = new RevWalk(refdb.getRepository())) {
			Batch batch = new Batch(refdb);
			batch.setRefLogIdent(getRefLogIdent());
			batch.setRefLogMessage(getRefLogMessage()
			batch.init(rw);

			Ref head = batch.exactRef(rw.getObjectReader()
			Ref oldRef = batch.exactRef(rw.getObjectReader()
			if (oldRef == null) {
				return REJECTED;
			}

			Ref newRef = asNew(oldRef);
			List<Command> mv = new ArrayList<>(3);
			mv.add(new Command(oldRef
			mv.add(new Command(null
			if (head != null && head.isSymbolic()
					&& head.getTarget().getName().equals(oldRef.getName())) {
				mv.add(new Command(
					head
					new SymbolicRef(head.getName()
			}
			batch.execute(rw
			return Update.translate(mv.get(1).getResult()
		}
	}

	private Ref asNew(Ref o) {
		String name = destination.getName();
		if (o.isSymbolic()) {
			return new SymbolicRef(name
		}

		ObjectId peeled = o.getPeeledObjectId();
		if (peeled != null) {
			return new ObjectIdRef.PeeledTag(
					o.getStorage()
					name
					o.getObjectId()
					peeled);
		}

		return new ObjectIdRef.PeeledNonTag(
				o.getStorage()
				name
				o.getObjectId());
	}
}
