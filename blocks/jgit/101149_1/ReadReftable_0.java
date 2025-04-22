
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

import org.eclipse.jgit.internal.storage.reftable.BlockSource;
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
	}

	@Option(name = "--tries")
	private int tries = 10;

	@Option(name = "--test")
	private Test test = Test.SCAN;

	@Option(name = "--ref")
	private String ref;

	@Argument(index = 0)
	private String lsRemotePath;

	@Argument(index = 1)
	private String reftablePath;

	@Override
	protected void run() throws Exception {
		if (test == Test.SCAN) {
			scan();
		} else if (test == Test.SEEK) {
			seek(ref);
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
				reader.seekToFirstRef();
				while (reader.next()) {
					reader.getRef();
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
	private void seek(String refName) throws Exception {
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
				reader.seek(refName);
				while (reader.next()) {
					reader.getRef();
				}
			}
		}
		tot = System.nanoTime() - start;
		printf("%12s %10d usec  %9.1f usec/run  %5d runs"
				tot / 1000
				(((double) tot) / tries) / 1000
				tries);
	}
}
