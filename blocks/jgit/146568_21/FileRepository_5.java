package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.internal.storage.reftable.Reftable;
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
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

class FileReftableStack implements ReftableStack {
	private FileReftableStack(File path) throws IOException {
		stackPath = path;

		List<StackEntry> stack = new ArrayList<>();
		ReftableReader last = null;
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(stackPath)
			String line;

			try {
				while ((line = br.readLine()) != null) {
					File subtable = new File(reftableDir()
					FileInputStream is;
					try {
						is = new FileInputStream(subtable);
					} catch (FileNotFoundException e) {
						throw new RetryException();
					}

					StackEntry e = new StackEntry();
					e.reftableReader = new ReftableReader(BlockSource.from(is));
					if (last != null) {
						if (last.maxUpdateIndex() >= e.reftableReader
								.minUpdateIndex()) {
							throw new IllegalStateException(String.format(
									"index numbers not increasing %s: min %d
									line
									last.maxUpdateIndex()));
						}
					}
					last = e.reftableReader;

					e.name = line;
					stack.add(e);
				}
			} catch (Exception e) {
				closeStack(stack);
				throw e;
			}
		} catch (FileNotFoundException e) {
		}
		this.stack = stack;

	}

	@Override
	public List<Reftable> readers() {
		return stack.stream().map(x -> x.reftableReader)
				.collect(Collectors.toList());
	}

	public static FileReftableStack open(File path) throws IOException {
		while (true) {
			try {
				FileReftableStack s = new FileReftableStack(path);
				return s;
			} catch (RetryException e) {

			}
		}
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

	File stackPath;

	private List<StackEntry> stack;

	private static void closeStack(List<StackEntry> stack) {
		for (StackEntry entry : stack) {
			try {
				entry.reftableReader.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void close() {
		closeStack(stack);
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

	private String filename(long index) {
		return String.format("%06d"
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

			String fn = filename(nextUpdateIndex());

			File tmpTable = File.createTempFile(fn + "_"
					stackPath.getParentFile());

			ReftableWriter.Stats stats;
			try (FileOutputStream fos = new FileOutputStream(tmpTable)) {
				ReftableWriter rw = new ReftableWriter(new ReftableConfig()
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

		} finally {
			lock.unlock();
		}
		return true;
	}
}
