
package org.eclipse.jgit.internal.storage.file;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.time.Instant;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.GitDateParser;
import org.eclipse.jgit.util.SystemReader;

class GcLog {
	private final FileRepository repo;

	private final File logFile;

	private final LockFile lock;

	private Instant gcLogExpire;


	private boolean nonEmpty = false;

	GcLog(FileRepository repo) {
		this.repo = repo;
		logFile = new File(repo.getDirectory()
		lock = new LockFile(logFile);
	}

	private Instant getLogExpiry() throws ParseException {
		if (gcLogExpire == null) {
			String logExpiryStr = repo.getConfig().getString(
					ConfigConstants.CONFIG_GC_SECTION
					ConfigConstants.CONFIG_KEY_LOGEXPIRY);
			if (logExpiryStr == null) {
				logExpiryStr = LOG_EXPIRY_DEFAULT;
			}
			gcLogExpire = GitDateParser.parse(logExpiryStr
					SystemReader.getInstance().getLocale()).toInstant();
		}
		return gcLogExpire;
	}

	private boolean autoGcBlockedByOldLockFile() {
		try {
			FileTime lastModified = Files.getLastModifiedTime(FileUtils.toPath(logFile));
			if (lastModified.toInstant().compareTo(getLogExpiry()) > 0) {
				return true;
			}
		} catch (NoSuchFileException e) {
		} catch (IOException | ParseException e) {
			throw new JGitInternalException(e.getMessage()
		}
		return false;
	}

	boolean lock() {
		try {
			if (!lock.lock()) {
				return false;
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
		if (autoGcBlockedByOldLockFile()) {
			lock.unlock();
			return false;
		}
		return true;
	}

	void unlock() {
		lock.unlock();
	}

	boolean commit() {
		if (nonEmpty) {
			return lock.commit();
		} else {
			logFile.delete();
			lock.unlock();
			return true;
		}
	}

	void write(String content) throws IOException {
		if (content.length() > 0) {
			nonEmpty = true;
		}
		lock.write(content.getBytes(UTF_8));
	}
}
