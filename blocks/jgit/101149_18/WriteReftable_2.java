
package org.eclipse.jgit.pgm.debug;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.internal.storage.reftable.RefCursor;
import org.eclipse.jgit.internal.storage.reftable.ReftableReader;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefComparator;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.kohsuke.args4j.Argument;

@Command
class VerifyReftable extends TextBuiltin {
	private static final long SEED1 = 0xaba8bb4de4caf86cL;
	private static final long SEED2 = 0x28bb5c25ad43ecb5L;

	@Argument(index = 0)
	private String lsRemotePath;

	@Argument(index = 1)
	private String reftablePath;

	@Override
	protected void run() throws Exception {
		List<Ref> refs = WriteReftable.read(lsRemotePath);

		try (FileInputStream in = new FileInputStream(reftablePath);
				BlockSource src = BlockSource.from(in);
				ReftableReader reader = new ReftableReader(src)) {
			scan(refs
			seek(refs
			byId(refs
		}
	}

	@SuppressWarnings("nls")
	private void scan(List<Ref> refs
			throws IOException {
		errw.print(String.format("%-20s"
		errw.flush();
		try (RefCursor rc = reader.allRefs()) {
			for (Ref exp : refs) {
				verify(exp
			}
			if (rc.next()) {
				throw die("expected end of table");
			}
		}
		errw.println(" OK");
	}

	@SuppressWarnings("nls")
	private void seek(List<Ref> refs
			throws IOException {
		List<Ref> rnd = new ArrayList<>(refs);
		Collections.shuffle(rnd

		TextProgressMonitor pm = new TextProgressMonitor(errw);
		pm.beginTask("random seek"
		for (Ref exp : rnd) {
			try (RefCursor rc = reader.seek(exp.getName())) {
				verify(exp
				if (rc.next()) {
					throw die("should not have ref after " + exp.getName());
				}
			}
			pm.update(1);
		}
		pm.endTask();
	}

	@SuppressWarnings("nls")
	private void byId(List<Ref> refs
			throws IOException {
		Map<ObjectId
		List<List<Ref>> rnd = new ArrayList<>(want.values());
		Collections.shuffle(rnd

		TextProgressMonitor pm = new TextProgressMonitor(errw);
		pm.beginTask("byObjectId"
		for (List<Ref> exp : rnd) {
			Collections.sort(exp
			ObjectId id = exp.get(0).getObjectId();
			try (RefCursor rc = reader.byObjectId(id)) {
				for (Ref r : exp) {
					verify(r
				}
			}
			pm.update(1);
		}
		pm.endTask();
	}

	private static Map<ObjectId
		Map<ObjectId
		for (Ref r : refs) {
			ObjectId id = r.getObjectId();
			if (id != null) {
				List<Ref> c = m.get(id);
				if (c == null) {
					c = new ArrayList<>(2);
					m.put(id
				}
				c.add(r);
			}
		}
		return m;
	}

	@SuppressWarnings("nls")
	private void verify(Ref exp
		if (!rc.next()) {
			throw die("ended before " + exp.getName());
		}

		Ref act = rc.getRef();
		if (!exp.getName().equals(act.getName())) {
			throw die(String.format("expected %s
					exp.getName()
					act.getName()));
		}

		if (exp.isSymbolic()) {
			if (!act.isSymbolic()) {
				throw die("expected " + act.getName() + " to be symbolic");
			}
			if (!exp.getTarget().getName().equals(act.getTarget().getName())) {
				throw die(String.format("expected %s to be %s
						exp.getName()
						exp.getLeaf().getName()
						act.getLeaf().getName()));
			}
			return;
		}

		if (!AnyObjectId.equals(exp.getObjectId()
			throw die(String.format("expected %s to be %s
					exp.getName()
					id(exp.getObjectId())
					id(act.getObjectId())));
		}

		if (exp.getPeeledObjectId() != null
				&& !AnyObjectId.equals(exp.getPeeledObjectId()
			throw die(String.format("expected %s to be %s
					exp.getName()
					id(exp.getPeeledObjectId())
					id(act.getPeeledObjectId())));
		}
	}

	@SuppressWarnings("nls")
	private static String id(ObjectId id) {
		return id != null ? id.name() : "<null>";
	}
}
