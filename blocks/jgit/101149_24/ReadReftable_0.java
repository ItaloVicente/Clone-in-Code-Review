
package org.eclipse.jgit.pgm.debug;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.MASTER;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.internal.storage.reftable.RefCursor;
import org.eclipse.jgit.internal.storage.reftable.ReftableReader;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.util.RefList;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command
class BenchmarkReftable extends TextBuiltin {
	enum Test {
		SCAN
		SEEK_COLD
		BY_ID_COLD
	}

	@Option(name = "--tries")
	private int tries = 10;

	@Option(name = "--test")
	private Test test = Test.SCAN;

	@Option(name = "--ref")
	private String ref;

	@Option(name = "--object-id")
	private String objectId;

	@Argument(index = 0)
	private String lsRemotePath;

	@Argument(index = 1)
	private String reftablePath;

	@Override
	protected void run() throws Exception {
		switch (test) {
		case SCAN:
			scan();
			break;

		case SEEK_COLD:
			seekCold(ref);
			break;
		case SEEK_HOT:
			seekHot(ref);
			break;

		case BY_ID_COLD:
			byIdCold(ObjectId.fromString(objectId));
			break;
		case BY_ID_HOT:
			byIdHot(ObjectId.fromString(objectId));
			break;
		}
	}

	private void printf(String fmt
		errw.println(String.format(fmt
	}

	@SuppressWarnings({ "nls"
	private void scan() throws Exception {
		long start

		start = System.currentTimeMillis();
		for (int i = 0; i < tries; i++) {
			readLsRemote();
		}
		tot = System.currentTimeMillis() - start;
		printf("%12s %10d ms  %6d ms/run"

		start = System.currentTimeMillis();
		for (int i = 0; i < tries; i++) {
			try (FileInputStream in = new FileInputStream(reftablePath);
					BlockSource src = BlockSource.from(in);
					ReftableReader reader = new ReftableReader(src)) {
				try (RefCursor rc = reader.allRefs()) {
					while (rc.next()) {
						rc.getRef();
					}
				}
			}
		}
		tot = System.currentTimeMillis() - start;
		printf("%12s %10d ms  %6d ms/run"
	}

	private RefList<Ref> readLsRemote()
			throws IOException
		RefList.Builder<Ref> list = new RefList.Builder<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(lsRemotePath)
			Ref last = null;
			String line;
			while ((line = br.readLine()) != null) {
				ObjectId id = ObjectId.fromString(line.substring(0
				String name = line.substring(41
					last = new ObjectIdRef.PeeledTag(PACKED
							last.getObjectId()
					list.set(list.size() - 1
					continue;
				}

				if (name.equals(HEAD)) {
					last = new SymbolicRef(name
							R_HEADS + MASTER
				} else {
					last = new ObjectIdRef.PeeledNonTag(PACKED
				}
				list.add(last);
			}
		}
		list.sort();
		return list.toRefList();
	}

	@SuppressWarnings({ "nls"
	private void seekCold(String refName) throws Exception {
		long start

		int lsTries = Math.min(tries
		start = System.nanoTime();
		for (int i = 0; i < lsTries; i++) {
			readLsRemote().get(refName);
		}
		tot = System.nanoTime() - start;
		printf("%12s %10d usec  %9.1f usec/run  %5d runs"
				tot / 1000
				(((double) tot) / lsTries) / 1000
				lsTries);

		start = System.nanoTime();
		for (int i = 0; i < tries; i++) {
			try (FileInputStream in = new FileInputStream(reftablePath);
					BlockSource src = BlockSource.from(in);
					ReftableReader reader = new ReftableReader(src)) {
				try (RefCursor rc = reader.seekRef(refName)) {
					while (rc.next()) {
						rc.getRef();
					}
				}
			}
		}
		tot = System.nanoTime() - start;
		printf("%12s %10d usec  %9.1f usec/run  %5d runs"
				tot / 1000
				(((double) tot) / tries) / 1000
				tries);
	}

	@SuppressWarnings({ "nls"
	private void seekHot(String refName) throws Exception {
		long start

		int lsTries = Math.min(tries
		start = System.nanoTime();
		RefList<Ref> lsRemote = readLsRemote();
		for (int i = 0; i < lsTries; i++) {
			lsRemote.get(refName);
		}
		tot = System.nanoTime() - start;
		printf("%12s %10d usec  %9.1f usec/run  %5d runs"
				tot / 1000

		start = System.nanoTime();
		try (FileInputStream in = new FileInputStream(reftablePath);
				BlockSource src = BlockSource.from(in);
				ReftableReader reader = new ReftableReader(src)) {
			for (int i = 0; i < tries; i++) {
				try (RefCursor rc = reader.seekRef(refName)) {
					while (rc.next()) {
						rc.getRef();
					}
				}
			}
		}
		tot = System.nanoTime() - start;
		printf("%12s %10d usec  %9.1f usec/run  %5d runs"
				tot / 1000
	}

	@SuppressWarnings({ "nls"
	private void byIdCold(ObjectId id) throws Exception {
		long start

		int lsTries = Math.min(tries
		start = System.nanoTime();
		for (int i = 0; i < lsTries; i++) {
			for (Ref r : readLsRemote()) {
				if (id.equals(r.getObjectId())) {
					continue;
				}
			}
		}
		tot = System.nanoTime() - start;
		printf("%12s %10d usec  %9.1f usec/run  %5d runs"
				tot / 1000

		start = System.nanoTime();
		for (int i = 0; i < tries; i++) {
			try (FileInputStream in = new FileInputStream(reftablePath);
					BlockSource src = BlockSource.from(in);
					ReftableReader reader = new ReftableReader(src)) {
				try (RefCursor rc = reader.byObjectId(id)) {
					while (rc.next()) {
						rc.getRef();
					}
				}
			}
		}
		tot = System.nanoTime() - start;
		printf("%12s %10d usec  %9.1f usec/run  %5d runs"
				tot / 1000
	}

	@SuppressWarnings({ "nls"
	private void byIdHot(ObjectId id) throws Exception {
		long start

		int lsTries = Math.min(tries
		start = System.nanoTime();
		RefList<Ref> lsRemote = readLsRemote();
		for (int i = 0; i < lsTries; i++) {
			for (Ref r : lsRemote) {
				if (id.equals(r.getObjectId())) {
					continue;
				}
			}
		}
		tot = System.nanoTime() - start;
		printf("%12s %10d usec  %9.1f usec/run  %5d runs"
				tot / 1000

		start = System.nanoTime();
		try (FileInputStream in = new FileInputStream(reftablePath);
				BlockSource src = BlockSource.from(in);
				ReftableReader reader = new ReftableReader(src)) {
			for (int i = 0; i < tries; i++) {
				try (RefCursor rc = reader.byObjectId(id)) {
					while (rc.next()) {
						rc.getRef();
					}
				}
			}
		}
		tot = System.nanoTime() - start;
		printf("%12s %10d usec  %9.1f usec/run  %5d runs"
				tot / 1000
	}
}
