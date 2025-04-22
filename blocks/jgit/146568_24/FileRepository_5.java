package org.eclipse.jgit.internal.storage.file;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.internal.storage.reftable.Reftable;
import org.eclipse.jgit.internal.storage.reftable.ReftableCompactor;
import org.eclipse.jgit.internal.storage.reftable.ReftableConfig;
import org.eclipse.jgit.internal.storage.reftable.ReftableReader;
import org.eclipse.jgit.internal.storage.reftable.ReftableStack;
import org.eclipse.jgit.internal.storage.reftable.ReftableWriter;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

class FileReftableStack implements ReftableStack {
	private FileReftableStack(File path) throws IOException {
		stackPath = path;
		stack = new ArrayList<>();
		reload();
	}

	private void reloadOnce() throws IOException {
		Map<String
			Collectors.toMap(e -> e.name
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
						File subtable = new File(reftableDir()
						FileInputStream is;
						try {
							is = new FileInputStream(subtable);
						} catch (FileNotFoundException e) {
							throw new RetryException();
						}

						entry.reftableReader = new ReftableReader(BlockSource.from(is));
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

	private void reload() throws IOException {
		while (true) {
			try {
				reloadOnce();
				return;
			} catch (RetryException e) {
			}
		}
	}

	@Override
	public List<Reftable> readers() {
		return stack.stream().map(x -> x.reftableReader)
				.collect(Collectors.toList());
	}

	public static FileReftableStack open(File path) throws IOException {
		return new FileReftableStack(path);
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

	private static class StackEntry {
		String name;
		ReftableReader reftableReader;
	}

	final File stackPath;

	private List<StackEntry> stack;

	@Override
	public void close() {
		for (StackEntry entry : stack) {
			try {
				entry.reftableReader.close();
			} catch (Exception e) {
			}
		}
	}

	private File reftableDir() {
		return new File(stackPath.getParentFile()
	}

	@Override
	public long nextUpdateIndex() throws IOException {
		long updateIndex = 0;
		for (StackEntry e : stack) {
			updateIndex = Math.max(updateIndex
					e.reftableReader.maxUpdateIndex());

		}
		return updateIndex + 1;
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
						fos);
				w.call(rw);
				rw.finish();
				stats = rw.getStats();
			}

			fn += stats.refCount() > 0 ? ".ref" : ".log";
			File dest = new File(reftableDir()

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


	ReftableConfig reftableConfig() { return new ReftableConfig(); }

	File compactLocked(int first
		ReftableCompactor c = new ReftableCompactor()
			.setConfig(reftableConfig())
			.setMinUpdateIndex(stack.get(first).reftableReader.minUpdateIndex())
			.setMaxUpdateIndex(stack.get(last).reftableReader.maxUpdateIndex())
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
			File dest = new File(reftableDir()

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

	void autoCompact() throws IOException {
		long size = 0;
		for (StackEntry e : stack) {
			size += e.reftableReader.size();
		}

		long threshold = size / 2;
		int first = -1;
		int i = 0;
		for (StackEntry e : stack) {
			if (e.reftableReader.size() < threshold) {
				threshold -= e.reftableReader.size();
				first = i;
				break;
			}
			i++;
			threshold /= 2;
		}

		if (first < 0) {
			return;
		}

		int last = first;
		while (first >= 0 && threshold >= 0 && last < stack.size()-1) {
			threshold -= stack.get(last).reftableReader.size();
			last ++;
		}

		compactRange(first
	}
}
