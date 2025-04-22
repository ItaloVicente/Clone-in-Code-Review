
package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.lib.Constants.LOCK_SUFFIX;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.LockToken;
import org.eclipse.jgit.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockFile {
	private final static Logger LOG = LoggerFactory.getLogger(LockFile.class);

	public static boolean unlock(File file) {
		final File lockFile = getLockFile(file);
		final int flags = FileUtils.RETRY | FileUtils.SKIP_MISSING;
		try {
			FileUtils.delete(lockFile
		} catch (IOException ignored) {
		}
		return !lockFile.exists();
	}

	static File getLockFile(File file) {
		return new File(file.getParentFile()
				file.getName() + LOCK_SUFFIX);
	}

	static final FilenameFilter FILTER = new FilenameFilter() {
		@Override
		public boolean accept(File dir
			return !name.endsWith(LOCK_SUFFIX);
		}
	};

	private final File ref;

	private final File lck;

	private boolean haveLck;

	FileOutputStream os;

	private boolean needSnapshot;

	boolean fsync;

	private FileSnapshot commitSnapshot;

	private LockToken token;

	public LockFile(File f) {
		ref = f;
		lck = getLockFile(ref);
	}

	public boolean lock() throws IOException {
		FileUtils.mkdirs(lck.getParentFile()
		token = FS.DETECTED.createNewFileAtomic(lck);
		if (token.isCreated()) {
			haveLck = true;
			try {
				os = new FileOutputStream(lck);
			} catch (IOException ioe) {
				unlock();
				throw ioe;
			}
		} else {
			closeToken();
		}
		return haveLck;
	}

	public boolean lockForAppend() throws IOException {
		if (!lock())
			return false;
		copyCurrentContent();
		return true;
	}

	public void copyCurrentContent() throws IOException {
		requireLock();
		try {
			try (FileInputStream fis = new FileInputStream(ref)) {
				if (fsync) {
					FileChannel in = fis.getChannel();
					long pos = 0;
					long cnt = in.size();
					while (0 < cnt) {
						long r = os.getChannel().transferFrom(in
						pos += r;
						cnt -= r;
					}
				} else {
					final byte[] buf = new byte[2048];
					int r;
					while ((r = fis.read(buf)) >= 0)
						os.write(buf
				}
			}
		} catch (FileNotFoundException fnfe) {
			if (ref.exists()) {
				unlock();
				throw fnfe;
			}
		} catch (IOException | RuntimeException | Error ioe) {
			unlock();
			throw ioe;
		}
	}

	public void write(ObjectId id) throws IOException {
		byte[] buf = new byte[Constants.OBJECT_ID_STRING_LENGTH + 1];
		id.copyTo(buf
		buf[Constants.OBJECT_ID_STRING_LENGTH] = '\n';
		write(buf);
	}

	public void write(byte[] content) throws IOException {
		requireLock();
		try {
			if (fsync) {
				FileChannel fc = os.getChannel();
				ByteBuffer buf = ByteBuffer.wrap(content);
				while (0 < buf.remaining())
					fc.write(buf);
				fc.force(true);
			} else {
				os.write(content);
			}
			os.close();
			os = null;
		} catch (IOException | RuntimeException | Error ioe) {
			unlock();
			throw ioe;
		}
	}

	public OutputStream getOutputStream() {
		requireLock();

		final OutputStream out;
		if (fsync)
			out = Channels.newOutputStream(os.getChannel());
		else
			out = os;

		return new OutputStream() {
			@Override
			public void write(byte[] b
					throws IOException {
				out.write(b
			}

			@Override
			public void write(byte[] b) throws IOException {
				out.write(b);
			}

			@Override
			public void write(int b) throws IOException {
				out.write(b);
			}

			@Override
			public void close() throws IOException {
				try {
					if (fsync)
						os.getChannel().force(true);
					out.close();
					os = null;
				} catch (IOException | RuntimeException | Error ioe) {
					unlock();
					throw ioe;
				}
			}
		};
	}

	void requireLock() {
		if (os == null) {
			unlock();
			throw new IllegalStateException(MessageFormat.format(JGitText.get().lockOnNotHeld
		}
	}

	public void setNeedStatInformation(boolean on) {
		setNeedSnapshot(on);
	}

	public void setNeedSnapshot(boolean on) {
		needSnapshot = on;
	}

	public void setFSync(boolean on) {
		fsync = on;
	}

	public void waitForStatChange() throws InterruptedException {
		FileSnapshot o = FileSnapshot.save(ref);
		FileSnapshot n = FileSnapshot.save(lck);
		while (o.equals(n)) {
			lck.setLastModified(System.currentTimeMillis());
			n = FileSnapshot.save(lck);
		}
	}

	public boolean commit() {
		if (os != null) {
			unlock();
			throw new IllegalStateException(MessageFormat.format(JGitText.get().lockOnNotClosed
		}

		saveStatInformation();
		try {
			FileUtils.rename(lck
			haveLck = false;
			closeToken();
			return true;
		} catch (IOException e) {
			unlock();
			return false;
		}
	}

	private void closeToken() {
		if (token != null) {
			token.close();
			token = null;
		}
	}

	private void saveStatInformation() {
		if (needSnapshot)
			commitSnapshot = FileSnapshot.save(lck);
	}

	public long getCommitLastModified() {
		return commitSnapshot.lastModified();
	}

	public FileSnapshot getCommitSnapshot() {
		return commitSnapshot;
	}

	public void createCommitSnapshot() {
		saveStatInformation();
	}

	public void unlock() {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				LOG.error(MessageFormat
						.format(JGitText.get().unlockLockFileFailed
			}
			os = null;
		}

		if (haveLck) {
			haveLck = false;
			try {
				FileUtils.delete(lck
			} catch (IOException e) {
				LOG.error(MessageFormat
						.format(JGitText.get().unlockLockFileFailed
			} finally {
				closeToken();
			}
		}
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "LockFile[" + lck + "
	}
}
