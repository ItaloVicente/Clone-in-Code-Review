package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.internal.storage.reftable.MergedReftable;
import org.eclipse.jgit.internal.storage.reftable.Reftable;
import org.eclipse.jgit.internal.storage.reftable.ReftableCompactor;
import org.eclipse.jgit.internal.storage.reftable.ReftableConfig;
import org.eclipse.jgit.internal.storage.reftable.ReftableReader;
import org.eclipse.jgit.internal.storage.reftable.ReftableWriter;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.FileUtils;

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

import static java.nio.charset.StandardCharsets.UTF_8;

class FileReftableStack implements AutoCloseable {
	private static class StackEntry {
		String name;

		ReftableReader reftableReader;
	}

	public static class CompactionStats {
		long tables;
		long bytes;
		int count;

		CompactionStats() {
			tables = 0;
			bytes = 0;
			count = 0;
		}
	}

	private final CompactionStats stats;

	private long lastNextUpdateIndex;

	private final File stackPath;

	private final File reftableDir;

	private final Runnable onChange;

	private final Supplier<Config> configSupplier;

	private List<StackEntry> stack;

	private MergedReftable mergedReftable;

	FileReftableStack(File stackPath
			@Nullable Runnable onChange
			  Supplier<Config> configSupplier) throws IOException {
		this.stackPath = stackPath;
		this.reftableDir = reftableDir;
		this.stack = new ArrayList<>();
		this.configSupplier = configSupplier;
		this.onChange = onChange;

		lastNextUpdateIndex = 0;
		reload();

		stats = new CompactionStats();
	}

	public CompactionStats getStats() {
		return stats;
	}

	private void reloadOnce() throws IOException {
		Map<String
				.collect(Collectors.toMap(e -> e.name
		stack.clear();

		ReftableReader last = null;
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(stackPath)
			String line;

			while ((line = br.readLine()) != null) {
				StackEntry entry = new StackEntry();
				entry.name = line;

				if (current.containsKey(line)) {
					entry.reftableReader = current.remove(line);
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
				}

				if (last != null) {
					if (last.maxUpdateIndex() >= entry.reftableReader
							.minUpdateIndex()) {
						throw new IllegalStateException(String.format(
								"index numbers not increasing %s: min %d
								line
								last.maxUpdateIndex()));
					}
				}
				last = entry.reftableReader;

				stack.add(entry);
			}
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
			close();
			throw e;
		} finally {
			current.values().forEach(r -> {
				try {
					r.close();
				} catch (IOException e) {

				}
			});
		}
	}

	void reload() throws IOException {
		while (true) {
			try {
				reloadOnce();
				break;
			} catch (RetryException e) {
			}
		}

		mergedReftable = new MergedReftable(stack.stream().map(x -> x.reftableReader).collect(Collectors.toList()));
		long curr = nextUpdateIndex();
		if (lastNextUpdateIndex > 0 && lastNextUpdateIndex != curr
				&& onChange != null) {
			onChange.run();
		}
		lastNextUpdateIndex = curr;
	}

    MergedReftable getMergedReftable() {
        return mergedReftable;
    }

    interface Writer {
		void call(ReftableWriter w) throws IOException;
	}

	private static class RetryException extends IOException {

	}

	public boolean uptodate() throws IOException {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(stackPath)
			for (StackEntry e : stack) {
				if (!e.name.equals(br.readLine())) {
					return false;
				}
			}
			if (br.readLine() != null) {
				return false;
			}
		} catch (FileNotFoundException e) {
			return stack.isEmpty();
		}
		return true;
	}

	@Override
	public void close() {
	}

	public void explicitClose() {
		for (StackEntry entry : stack) {
			try {
				entry.reftableReader.close();
			} catch (Exception e) {
			}
		}
	}

	private long nextUpdateIndex() throws IOException {
		return stack.size() > 0 ? stack.get(stack.size()-1).reftableReader.maxUpdateIndex() + 1: 1;
	}

	private String filename(long low
		return String.format("%06d-%06d"
	}

	boolean addReftable(Writer w) throws IOException {
		LockFile lock = new LockFile(stackPath);
		try {
			if (!lock.lockForAppend()) {
				return false;
			}
			if (!uptodate()) {
				return false;
			}

			String fn = filename(nextUpdateIndex()

			File tmpTable = File.createTempFile(fn + "_"
					stackPath.getParentFile());

			ReftableWriter.Stats stats;
			try (FileOutputStream fos = new FileOutputStream(tmpTable)) {
				ReftableWriter rw = new ReftableWriter(reftableConfig()
				w.call(rw);
				rw.finish();
				stats = rw.getStats();
			}

			fn += stats.refCount() > 0 ? ".ref" : ".log";
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

	ReftableConfig reftableConfig() {
		return new ReftableConfig(configSupplier.get());
	}

	File compactLocked(int first
		ReftableCompactor c = new ReftableCompactor()
				.setConfig(reftableConfig())
				.setMinUpdateIndex(
						stack.get(first).reftableReader.minUpdateIndex())
				.setMaxUpdateIndex(
						stack.get(last).reftableReader.maxUpdateIndex())
				.setIncludeDeletes(first > 0);

		List<ReftableReader> compactMe = new ArrayList<>();
		for (int i = first; i <= last; i++) {
			compactMe.add(stack.get(i).reftableReader);
		}
		c.addAll(compactMe);

		String fn = filename(first
		File tmpTable = File.createTempFile(fn + "_"
				stackPath.getParentFile());

		try (FileOutputStream fos = new FileOutputStream(tmpTable)) {
			c.compact(fos);
		}

		return tmpTable;
	}

	boolean compactRange(int first
		if (first >= last) {
			return true;
		}
		LockFile lock = new LockFile(stackPath);
		File dir = new File(stackPath.getParentFile()

		File tmpTable = null;
		List<LockFile> subtableLocks = new ArrayList<>();

		long bytes = 0;
		long tables = 0;
		try {
			if (!lock.lock()) {
				return false;
			}
			if (!uptodate()) {
				return false;
			}

			List<File> deleteOnSuccess = new ArrayList<>();
			for (int i = first; i <= last; i++) {
				File f = new File(dir
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
			if (!uptodate()) {
				return false;
			}

			String fn = filename(
					stack.get(first).reftableReader.minUpdateIndex()
					stack.get(last).reftableReader.maxUpdateIndex());

			fn += ".ref";
			File dest = new File(reftableDir

			FileUtils.rename(tmpTable
			tmpTable = null;

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < first; i++) {
				sb.append(stack.get(i).name + "\n");
			}
			sb.append(fn + "\n");
			for (int i = last + 1; i < stack.size(); i++) {
				sb.append(stack.get(i).name + "\n");
			}

			lock.write(sb.toString().getBytes(UTF_8));
			if (!lock.commit()) {
				dest.delete();
				return false;
			}

			stats.bytes += bytes;
			stats.tables += tables;
			stats.count++;

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

	int log2(long sz) {
		int l = 0;
		while (sz > 0) {
			l++;
			sz /= 2;
		}

		return l;
	}

	private static class Segment {
		int log;
		int start;

		int size() { return end -start; }
	};

	void autoCompact() throws IOException {
		int i = 0;

		int segmentStart = -1;
		int lastLog = -1;

		List<Segment > segments = new ArrayList<>();
		for (StackEntry e : stack) {
			int l = log2(e.reftableReader.size());
			if (l != lastLog) {
				if (segmentStart > 0) {
					Segment s  = new Segment();
					s.log = l;
					s.start = segmentStart;
					s.end = i;
					segments.add(s);
				}
				segmentStart = i;
			}

			lastLog = l;
			i++;
		}

		if (stack.size() > 0) {
			Segment s  = new Segment();
			s.log = lastLog;
			s.start = segmentStart;
			s.end = stack.size();
			segments.add(s);
		}

		segments = segments.stream().filter(s -> s.size() > 1).collect(Collectors.toList());
		if (segments.isEmpty()) {
			return;
		}

		Optional<Segment> minSeg =  segments.stream().min(Comparator.comparing(s -> s.log));

		int start = minSeg.get().start;
		int end = minSeg.get().end;

		long total = 0;
		for (int j = start; j < end; j++) {
			total += stack.get(j).reftableReader.size();
		}

		while (start > 0) {
			int prev = start -1;
			long prevSize = stack.get(prev).reftableReader.size();
			if (log2(total) < log2(prevSize)) {
				break;
			}
			start = prev;
			total += prevSize;
		}

		compactRange(start
	}
}
