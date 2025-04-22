
package org.eclipse.jgit.pgm.debug;

import static java.lang.Integer.valueOf;
import static java.lang.Long.valueOf;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jgit.diff.DiffAlgorithm;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.MyersDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FS;
import org.kohsuke.args4j.Option;

@Command(usage = "usage_DiffAlgorithms")
class DiffAlgorithms extends TextBuiltin {

	final Algorithm myers = new Algorithm() {
		@Override
		DiffAlgorithm create() {
			return MyersDiff.INSTANCE;
		}
	};

	final Algorithm histogram = new Algorithm() {
		@Override
		DiffAlgorithm create() {
			HistogramDiff d = new HistogramDiff();
			d.setFallbackAlgorithm(null);
			return d;
		}
	};

	final Algorithm histogram_myers = new Algorithm() {
		@Override
		DiffAlgorithm create() {
			HistogramDiff d = new HistogramDiff();
			d.setFallbackAlgorithm(MyersDiff.INSTANCE);
			return d;
		}
	};


	@Option(name = "--algorithm"
	List<String> algorithms = new ArrayList<>();

	@Option(name = "--text-limit"

	@Option(name = "--repository"
	List<File> gitDirs = new ArrayList<>();

	@Option(name = "--count"

	private final RawTextComparator cmp = RawTextComparator.DEFAULT;

	private ThreadMXBean mxBean;

	@Override
	protected boolean requiresRepository() {
		return false;
	}

	@Override
	protected void run() throws Exception {
		mxBean = ManagementFactory.getThreadMXBean();
		if (!mxBean.isCurrentThreadCpuTimeSupported())

		if (gitDirs.isEmpty()) {
					.findGitDir();
			if (rb.getGitDir() == null)
				throw die(CLIText.get().cantFindGitDirectory);
			gitDirs.add(rb.getGitDir());
		}

		for (File dir : gitDirs) {
			RepositoryBuilder rb = new RepositoryBuilder();
			if (RepositoryCache.FileKey.isGitRepository(dir
				rb.setGitDir(dir);
			else
				rb.findGitDir(dir);

			try (Repository repo = rb.build()) {
				run(repo);
			}
		}
	}

	private void run(Repository repo) throws Exception {
		List<Test> all = init();

		long files = 0;
		int commits = 0;
		int minN = Integer.MAX_VALUE;
		int maxN = 0;

		AbbreviatedObjectId startId;
		try (ObjectReader or = repo.newObjectReader();
			RevWalk rw = new RevWalk(or)) {
			final MutableObjectId id = new MutableObjectId();
			TreeWalk tw = new TreeWalk(or);
			tw.setFilter(TreeFilter.ANY_DIFF);
			tw.setRecursive(true);

			ObjectId start = repo.resolve(Constants.HEAD);
			startId = or.abbreviate(start);
			rw.markStart(rw.parseCommit(start));
			for (;;) {
				final RevCommit c = rw.next();
				if (c == null)
					break;
				commits++;
				if (c.getParentCount() != 1)
					continue;

				RevCommit p = c.getParent(0);
				rw.parseHeaders(p);
				tw.reset(p.getTree()
				while (tw.next()) {
					if (!isFile(tw
						continue;

					byte[] raw0;
					try {
						tw.getObjectId(id
						raw0 = or.open(id).getCachedBytes(textLimit * 1024);
					} catch (LargeObjectException tooBig) {
						continue;
					}
					if (RawText.isBinary(raw0))
						continue;

					byte[] raw1;
					try {
						tw.getObjectId(id
						raw1 = or.open(id).getCachedBytes(textLimit * 1024);
					} catch (LargeObjectException tooBig) {
						continue;
					}
					if (RawText.isBinary(raw1))
						continue;

					RawText txt0 = new RawText(raw0);
					RawText txt1 = new RawText(raw1);

					minN = Math.min(minN
					maxN = Math.max(maxN

					for (Test test : all)
						testOne(test
					files++;
				}
				if (count > 0 && files > count)
					break;
			}
		}

		Collections.sort(all
			@Override
			public int compare(Test a
				int result = Long.signum(a.runningTimeNanos - b.runningTimeNanos);
				if (result == 0) {
					result = a.algorithm.name.compareTo(b.algorithm.name);
				}
				return result;
			}
		});

		File directory = repo.getDirectory();
		if (directory != null) {
			String name = directory.getName();
			File parent = directory.getParentFile();
			if (name.equals(Constants.DOT_GIT) && parent != null)
				name = parent.getName();
		}

		outw.format("  %12d files
				valueOf(commits));
		outw.format("  N=%10d min lines
				valueOf(maxN));

		outw.format("%-25s %12s ( %12s  %12s )\n"
				"Algorithm"
		outw.format("%-25s %12s ( %12s  %12s )\n"
				""

		for (Test test : all) {
			outw.format("%-25s %12d ( %12d  %12d )"
					test.algorithm.name
					valueOf(test.runningTimeNanos)
					valueOf(test.minN.runningTimeNanos)
					valueOf(test.maxN.runningTimeNanos));
			outw.println();
		}
		outw.println();
		outw.flush();
	}

	private static boolean isFile(TreeWalk tw
		FileMode fm = tw.getFileMode(ithTree);
		return FileMode.REGULAR_FILE.equals(fm)
				|| FileMode.EXECUTABLE_FILE.equals(fm);
	}

	private static final int minCPUTimerTicks = 10;

	private void testOne(Test test
		final DiffAlgorithm da = test.algorithm.create();
		int cpuTimeChanges = 0;
		int cnt = 0;

		final long startTime = mxBean.getCurrentThreadCpuTime();
		long lastTime = startTime;
		while (cpuTimeChanges < minCPUTimerTicks) {
			da.diff(cmp
			cnt++;

			long interimTime = mxBean.getCurrentThreadCpuTime();
			if (interimTime != lastTime) {
				cpuTimeChanges++;
				lastTime = interimTime;
			}
		}
		final long stopTime = mxBean.getCurrentThreadCpuTime();
		final long runTime = (stopTime - startTime) / cnt;

		test.runningTimeNanos += runTime;

		if (test.minN == null || a.size() + b.size() < test.minN.n) {
			test.minN = new Run();
			test.minN.n = a.size() + b.size();
			test.minN.runningTimeNanos = runTime;
		}

		if (test.maxN == null || a.size() + b.size() > test.maxN.n) {
			test.maxN = new Run();
			test.maxN.n = a.size() + b.size();
			test.maxN.runningTimeNanos = runTime;
		}
	}

	private List<Test> init() {
		List<Test> all = new ArrayList<>();

		try {
			for (Field f : DiffAlgorithms.class.getDeclaredFields()) {
				if (f.getType() == Algorithm.class) {
					f.setAccessible(true);
					Algorithm alg = (Algorithm) f.get(this);
					alg.name = f.getName();
					if (included(alg.name
						Test test = new Test();
						test.algorithm = alg;
						all.add(test);
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw die("Cannot determine names"
		}

		return all;
	}

	private static boolean included(String name
		if (want.isEmpty())
			return true;
		for (String s : want) {
			if (s.equalsIgnoreCase(name))
				return true;
		}
		return false;
	}

	private static abstract class Algorithm {
		String name;

		abstract DiffAlgorithm create();
	}

	private static class Test {
		Algorithm algorithm;

		long runningTimeNanos;

		Run minN;

		Run maxN;
	}

	private static class Run {
		int n;

		long runningTimeNanos;
	}
}
