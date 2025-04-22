
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.internal.storage.reftable.ReftableConfig;
import org.eclipse.jgit.internal.storage.reftable.ReftableWriter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command
class WriteReftable extends TextBuiltin {
	private static final int KIB = 1 << 10;
	private static final int MIB = 1 << 20;

	@Option(name = "--block-size")
	private int refBlockSize;

	@Option(name = "--log-block-size")
	private int logBlockSize;

	@Option(name = "--restart-interval")
	private int restartInterval;

	@Option(name = "--index-levels")
	private int indexLevels;

	@Option(name = "--reflog-in")
	private String reflogIn;

	@Option(name = "--no-index-objects")
	private boolean noIndexObjects;

	@Argument(index = 0)
	private String in;

	@Argument(index = 1)
	private String out;

	@SuppressWarnings({ "nls"
	@Override
	protected void run() throws Exception {
		List<Ref> refs = readRefs(in);
		List<LogEntry> logs = readLog(reflogIn);

		ReftableWriter.Stats stats;
		try (OutputStream os = new FileOutputStream(out)) {
			ReftableConfig cfg = new ReftableConfig();
			cfg.setIndexObjects(!noIndexObjects);
			if (refBlockSize > 0) {
				cfg.setRefBlockSize(refBlockSize);
			}
			if (logBlockSize > 0) {
				cfg.setLogBlockSize(logBlockSize);
			}
			if (restartInterval > 0) {
				cfg.setRestartInterval(restartInterval);
			}
			if (indexLevels > 0) {
				cfg.setMaxIndexLevels(indexLevels);
			}

			ReftableWriter w = new ReftableWriter(cfg);
			w.setMinUpdateIndex(min(logs)).setMaxUpdateIndex(max(logs));
			w.begin(os);
			w.sortAndWriteRefs(refs);
			for (LogEntry e : logs) {
				w.writeLog(e.ref
						e.oldId
			}
			stats = w.finish().getStats();
		}

		double fileMiB = ((double) stats.totalBytes()) / MIB;
		printf("Summary:");
		printf("  file sz : %.1f MiB (%d bytes)"
		printf("  padding : %d KiB"
		errw.println();

		printf("Refs:");
		printf("  ref blk : %d"
		printf("  restarts: %d"
		printf("  refs    : %d"
		if (stats.refIndexLevels() > 0) {
			int idxSize = (int) Math.round(((double) stats.refIndexSize()) / KIB);
			printf("  idx sz  : %d KiB"
			printf("  idx lvl : %d"
		}
		printf("  avg ref : %d bytes"
		errw.println();

		if (stats.objCount() > 0) {
			int objMiB = (int) Math.round(((double) stats.objBytes()) / MIB);
			int idLen = stats.objIdLength();
			printf("Objects:");
			printf("  obj blk : %d"
			printf("  restarts: %d"
			printf("  objects : %d"
			printf("  obj sz  : %d MiB (%d bytes)"
			if (stats.objIndexSize() > 0) {
				int s = (int) Math.round(((double) stats.objIndexSize()) / KIB);
				printf("  idx sz  : %d KiB"
				printf("  idx lvl : %d"
			}
			printf("  id len  : %d bytes (%d hex digits)"
			printf("  avg obj : %d bytes"
			errw.println();
		}
		if (stats.logCount() > 0) {
			int logMiB = (int) Math.round(((double) stats.logBytes()) / MIB);
			printf("Log:");
			printf("  log blk : %d"
			printf("  logs    : %d"
			printf("  log sz  : %d MiB (%d bytes)"
			printf("  avg log : %d bytes"
			errw.println();
		}
	}

	private void printf(String fmt
		errw.println(String.format(fmt
	}

	static List<Ref> readRefs(String inputFile) throws IOException {
		List<Ref> refs = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(inputFile)
			String line;
			while ((line = br.readLine()) != null) {
				ObjectId id = ObjectId.fromString(line.substring(0
				String name = line.substring(41
					int lastIdx = refs.size() - 1;
					Ref last = refs.get(lastIdx);
					refs.set(lastIdx
							last.getName()
					continue;
				}

				Ref ref;
				if (name.equals(HEAD)) {
					ref = new SymbolicRef(name
							R_HEADS + MASTER
				} else {
					ref = new ObjectIdRef.PeeledNonTag(PACKED
				}
				refs.add(ref);
			}
		}
		Collections.sort(refs
		return refs;
	}

	private static List<LogEntry> readLog(String logPath)
			throws FileNotFoundException
		if (logPath == null) {
			return Collections.emptyList();
		}

		List<LogEntry> log = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(logPath)
			@SuppressWarnings("nls")
			Pattern pattern = Pattern.compile("([^
					+ "
					+ "
					+ "
					+ "
					+ "
			String line;
			while ((line = br.readLine()) != null) {
				Matcher m = pattern.matcher(line);
				if (!m.matches()) {
				}
				String ref = m.group(1);
				double t = Double.parseDouble(m.group(2));
				long time = ((long) t) * 1000L;
				long index = (long) (t * 1e6);
				String user = m.group(3);
				ObjectId oldId = parseId(m.group(4));
				ObjectId newId = parseId(m.group(5));
				String msg = m.group(6);
				PersonIdent who = new PersonIdent(user
				log.add(new LogEntry(ref
			}
		}
		Collections.sort(log
		return log;
	}

	private static long min(List<LogEntry> log) {
		return log.stream().mapToLong(e -> e.updateIndex).min().orElse(0);
	}

	private static long max(List<LogEntry> log) {
		return log.stream().mapToLong(e -> e.updateIndex).max().orElse(0);
	}

	private static ObjectId parseId(String s) {
			return ObjectId.zeroId();
		}
		return ObjectId.fromString(s);
	}

	private static class LogEntry {
		static int compare(LogEntry a
			int cmp = a.ref.compareTo(b.ref);
			if (cmp == 0) {
				cmp = Long.signum(b.updateIndex - a.updateIndex);
			}
			return cmp;
		}

		final String ref;
		final long updateIndex;
		final PersonIdent who;
		final ObjectId oldId;
		final ObjectId newId;
		final String message;

		LogEntry(String ref
				ObjectId oldId
			this.ref = ref;
			this.updateIndex = updateIndex;
			this.who = who;
			this.oldId = oldId;
			this.newId = newId;
			this.message = message;
		}
	}
}
