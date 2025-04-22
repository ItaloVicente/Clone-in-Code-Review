
package org.eclipse.jgit.internal.storage.file;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.internal.storage.reftable.MergedReftable;
import org.eclipse.jgit.internal.storage.reftable.ReftableCompactor;
import org.eclipse.jgit.internal.storage.reftable.ReftableConfig;
import org.eclipse.jgit.internal.storage.reftable.ReftableReader;
import org.eclipse.jgit.internal.storage.reftable.ReftableWriter;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FileReftableStack implements AutoCloseable {

	private final static Logger LOG = LoggerFactory
		.getLogger(FileReftableStack.class);

	private static class StackEntry {

		String name;

		ReftableReader reftableReader;
	}

	private MergedReftable mergedReftable;

	private List<StackEntry> stack;

	private long lastNextUpdateIndex;

	private final File stackPath;

	private final File reftableDir;

	private final Runnable onChange;

	private final Supplier<Config> configSupplier;

	static class CompactionStats {

		long tables;

		long bytes;

		int attempted;
		int failed;
		long refCount;
		long logCount;

		CompactionStats() {
			tables = 0;
			bytes = 0;
			attempted = 0;
			failed = 0;
			logCount = 0;
			refCount = 0;
		}
	}

	private final CompactionStats stats;

	FileReftableStack(File stackPath
		@Nullable Runnable onChange
		throws IOException {
		this.stackPath = stackPath;
		this.reftableDir = reftableDir;
		this.stack = new ArrayList<>();
		this.configSupplier = configSupplier;
		this.onChange = onChange;

		lastNextUpdateIndex = 0;
		reload();

		stats = new CompactionStats();
	}

	CompactionStats getStats() {
		return stats;
	}

	private void reloadOnce() throws IOException {
		List<String> names = readTableNames();

		Map<String
			.collect(Collectors.toMap(e -> e.name

		List<ReftableReader> newTables = new ArrayList<>();
		List<StackEntry> newStack = new ArrayList<>(stack.size() + 1);
		try {
			for (String name : names) {
				StackEntry entry = new StackEntry();
				entry.name = name;

				if (current.containsKey(name)) {
					entry.reftableReader = current.remove(name);
				} else {
					File subtable = new File(reftableDir
					FileInputStream is;
					try {
						is = new FileInputStream(subtable);
					} catch (FileNotFoundException e) {
						throw new RetryException();
					}

					entry.reftableReader = new ReftableReader(
						BlockSource.from(is));
					newTables.add(entry.reftableReader);
				}


				newStack.add(entry);
			}
			stack = newStack;
			newTables.clear();

			current.values().forEach(r -> {
				try {
					r.close();
				} catch (IOException e) {
					throw new AssertionError(e);
				}
			});
		} finally {
			newTables.forEach(t -> {
				try {
					t.close();
				} catch (IOException ioe) {
					throw new AssertionError(ioe);
				}
			});
		}
	}

	void reload() throws IOException {
		long deadline = System.currentTimeMillis() + 2500;
		long min = 1;
		long max = 1000;
		long delay = 0;
		boolean success = false;
		while (System.currentTimeMillis() < deadline) {
			try {
				reloadOnce();
				success = true;
				break;
			} catch (RetryException e) {
			}

			delay = FileUtils.delay(delay
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		if (!success) {
			throw new LockFailedException(stackPath);
		}

		mergedReftable = new MergedReftable(stack.stream()
				.map(x -> x.reftableReader).collect(Collectors.toList()));
		long curr = nextUpdateIndex();
		if (lastNextUpdateIndex > 0 && lastNextUpdateIndex != curr
				&& onChange != null) {
			onChange.run();
		}
		lastNextUpdateIndex = curr;
	}

	public MergedReftable getMergedReftable() {
		return mergedReftable;
	}

	interface Writer {
		void call(ReftableWriter w) throws IOException;
	}

	private static class RetryException extends IOException {

		private static final long serialVersionUID = 1L;
	}

	private List<String> readTableNames() throws IOException {
		List<String> names = new ArrayList<>(stack.size() + 1);

		try (BufferedReader br = new BufferedReader(
			new InputStreamReader(new FileInputStream(stackPath)
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					names.add(line);
				}
			}
		} catch (FileNotFoundException e) {
		}
		return names;
	}

	boolean isUpToDate() throws IOException {
		try {
			List<String> names = readTableNames();
			if (names.size() != stack.size()) {
				return false;
			}
			for (int i = 0; i < names.size(); i++) {
				if (!names.get(i).equals(stack.get(i).name)) {
					return false;
				}
			}
		} catch (FileNotFoundException e) {
			return stack.isEmpty();
		}
		return true;
	}

	@Override
	public void close() {
		for (StackEntry entry : stack) {
			try {
				entry.reftableReader.close();
			} catch (Exception e) {
				throw new AssertionError(e);
			}
		}
	}

	private long nextUpdateIndex() throws IOException {
		return stack.size() > 0
			? stack.get(stack.size() - 1).reftableReader.maxUpdateIndex()
			+ 1
			: 1;
	}

	private String filename(long low
		return String.format("%012x-%012x"
			Long.valueOf(low)
	}

	@SuppressWarnings("nls")
	boolean addReftable(Writer w) throws IOException {
		LockFile lock = new LockFile(stackPath);
		try {
			if (!lock.lockForAppend()) {
				return false;
			}
			if (!isUpToDate()) {
				return false;
			}

			String fn = filename(nextUpdateIndex()

			File tmpTable = File.createTempFile(fn + "_"
				stackPath.getParentFile());

			ReftableWriter.Stats s;
			try (FileOutputStream fos = new FileOutputStream(tmpTable)) {
				ReftableWriter rw = new ReftableWriter(reftableConfig()
				w.call(rw);
				rw.finish();
				s = rw.getStats();
			}

			if (s.minUpdateIndex() < nextUpdateIndex()) {
				return false;
			}

			fn += s.refCount() > 0 ? ".ref" : ".log";
			File dest = new File(reftableDir

			FileUtils.rename(tmpTable
			lock.write((fn + "\n").getBytes(UTF_8));
			if (!lock.commit()) {
				FileUtils.delete(dest);
				return false;
			}

			reload();

			autoCompact();
		} finally {
			lock.unlock();
		}
		return true;
	}

	private ReftableConfig reftableConfig() {
		return new ReftableConfig(configSupplier.get());
	}

	private File compactLocked(int first
		String fn = filename(first

		File tmpTable = File.createTempFile(fn + "_"
			stackPath.getParentFile());
		try (FileOutputStream fos = new FileOutputStream(tmpTable)) {
			ReftableCompactor c = new ReftableCompactor(fos)
				.setConfig(reftableConfig())
				.setMinUpdateIndex(
					stack.get(first).reftableReader.minUpdateIndex())
				.setMaxUpdateIndex(
					stack.get(last).reftableReader.maxUpdateIndex())
				.setIncludeDeletes(first > 0);

			List<ReftableReader> compactMe = new ArrayList<>();
			long totalBytes = 0;
			for (int i = first; i <= last; i++) {
				compactMe.add(stack.get(i).reftableReader);
				totalBytes += stack.get(i).reftableReader.size();
			}
			c.addAll(compactMe);

			c.compact();

			stats.bytes += totalBytes;
			stats.tables += first - last + 1;
			stats.attempted++;
			stats.refCount += c.getStats().refCount();
			stats.logCount += c.getStats().logCount();
		}

		return tmpTable;
	}

	boolean compactRange(int first
		if (first >= last) {
			return true;
		}
		LockFile lock = new LockFile(stackPath);

		File tmpTable = null;
		List<LockFile> subtableLocks = new ArrayList<>();

		long bytes = 0;
		long tables = 0;
		try {
			if (!lock.lock()) {
				return false;
			}
			if (!isUpToDate()) {
				return false;
			}

			List<File> deleteOnSuccess = new ArrayList<>();
			for (int i = first; i <= last; i++) {
				File f = new File(reftableDir
				LockFile lf = new LockFile(f);
				if (!lf.lock()) {
					return false;
				}
				subtableLocks.add(lf);
				deleteOnSuccess.add(f);
				tables++;
				bytes += stack.get(i).reftableReader.size();
			}

			lock.unlock();
			lock = null;

			tmpTable = compactLocked(first

			lock = new LockFile(stackPath);
			if (!lock.lock()) {
				return false;
			}
			if (!isUpToDate()) {
				return false;
			}

			String fn = filename(
				stack.get(first).reftableReader.minUpdateIndex()
				stack.get(last).reftableReader.maxUpdateIndex());

			File dest = new File(reftableDir

			FileUtils.rename(tmpTable
			tmpTable = null;

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < first; i++) {
			}
			for (int i = last + 1; i < stack.size(); i++) {
			}

			lock.write(sb.toString().getBytes(UTF_8));
			if (!lock.commit()) {
				dest.delete();
				return false;
			}

			for (File f : deleteOnSuccess) {
				Files.delete(f.toPath());
			}

			reload();
			return true;
		} finally {
			if (tmpTable != null) {
				tmpTable.delete();
			}
			for (LockFile lf : subtableLocks) {
				lf.unlock();
			}
			if (lock != null) {
				lock.unlock();
			}
		}
	}

	static int log(long sz) {
		long base = 2;
		if (sz <= 0) {
		}
		int l = 0;
		while (sz > 0) {
			l++;
			sz /= base;
		}

		return l-1;
	}

	static class Segment {
		int log;
		long bytes;

		int start;


		int size() {
			return end - start;
		}

		Segment(int start
			this.log = log;
			this.start = start;
			this.end = end;
			this.bytes = bytes;
		}

		Segment() {
			this(0
		}

		@Override
		public int hashCode() {
		}

		@Override
		public boolean equals(Object other) {
			Segment o = (Segment)other;
			return o.bytes == bytes && o.log == log && o.start == start && o.end == end;
		}

		@Override
		public String toString() {
			return String.format("{ [%d
		}
	}

	static List<Segment> segmentSizes(long sizes[]) {
		List<Segment> segments = new ArrayList<>();
		Segment cur = new Segment();
		for (int i = 0; i < sizes.length; i++) {
			int l = log(sizes[i]);
			if (l != cur.log && cur.bytes > 0) {
				segments.add(cur);
				cur = new Segment();
				cur.start = i;
				cur.log = l;
			}

			cur.log = l;
			cur.end = i + 1;
			cur.bytes += sizes[i];
		}
		segments.add(cur);
		return segments;
	}

	private static Optional<Segment> autoCompactCandidate(long sizes[]) {
		if (sizes.length == 0) {
			return Optional.empty();
		}


		List<Segment> segments = segmentSizes(sizes);
		segments = segments.stream().filter(s -> s.size() > 1)
			.collect(Collectors.toList());
		if (segments.isEmpty()) {
			return Optional.empty();
		}

		Optional<Segment> optMinSeg = segments.stream()
			.min(Comparator.comparing(s -> Integer.valueOf(s.log)));
		Segment smallCollected = optMinSeg.get();
		while (smallCollected.start > 0) {
			int prev = smallCollected.start - 1;
			long prevSize = sizes[prev];
			if (log(smallCollected.bytes) < log(prevSize)) {
				break;
			}
			smallCollected.start = prev;
			smallCollected.bytes += prevSize;
		}

		Optional<Segment> longestSeg = segments.stream()
			.max(Comparator.comparing(s -> s.size()));
		if (longestSeg.isPresent() && longestSeg.get().log > smallCollected.log && longestSeg.get().size() > smallCollected.size()
		&& longestSeg.get().size() > 4) {
		}

		return Optional.of(smallCollected);
	}
	private void autoCompact() throws IOException {
		Optional<Segment> cand = autoCompactCandidate(tableSizes());
		if (cand.isPresent()) {
			if (!compactRange(cand.get().start
				stats.failed++;
			}
		}
	}

	private static long OVERHEAD = 100;
	private long[] tableSizes() throws IOException {
		long[] sizes = new long[stack.size()];
		for (int i = 0; i < stack.size(); i++) {
			sizes[i] = stack.get(i).reftableReader.size() - OVERHEAD;
		}
		return sizes;
	}

	void compactFully() throws IOException {
		if (!compactRange(0
			stats.failed++;
		}
		throw new IllegalStateException();
	}
}
