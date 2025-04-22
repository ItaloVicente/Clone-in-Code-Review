package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.util.GitDateParser;
import org.eclipse.jgit.util.SystemReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.attribute.FileTime;
import java.text.MessageFormat;
import java.text.ParseException;
import java.time.Instant;

public class GcLog {
    private final FileRepository repo;
    private final File logFile;
    private final LockFile lock;
    private Instant gcLogExpire;
    private boolean nonEmpty = false;

    public GcLog(FileRepository repo) {
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
                    .getInstance().getLocale()).toInstant();
        }
        return gcLogExpire;
    }

    private boolean autoGcBlockedByOldLockFile(boolean failOnExistingLockFile) {
        try {
            FileTime lastModified = Files.getLastModifiedTime(logFile.toPath());
            if (lastModified.toInstant().compareTo(getLogExpiry()) < 0) {
                if (!failOnExistingLockFile) {
                    try (BufferedReader reader = Files.newBufferedReader(logFile.toPath())) {
                        char[] buf = new char[1000];
                        int len = reader.read(buf
                        String oldError = new String(buf

                        throw new JGitInternalException("A previous GC run reported an error: " +
                                oldError +
                                "\nAutomatic gc will fail until the file " + logFile +
                                " is removed");
                    }
                }
                return true;
            }
        } catch (NoSuchFileException e) {
        } catch (IOException | ParseException e) {
            throw new JGitInternalException(e.getMessage()
        }
        return false;
    }

    public boolean lock(boolean failOnExistingLockFile) {
        try {
            if (!lock.lock()) {
                return false;
            }
        } catch (IOException e) {
            throw new JGitInternalException(e.getMessage()
        }
        if (autoGcBlockedByOldLockFile(failOnExistingLockFile)) {
            lock.unlock();;
            return false;
        }
        return true;
    }

    public void unlock() {
        lock.unlock();
    }

    public void cleanUp() {
        logFile.delete();
    }

    public boolean commit() {
        if (nonEmpty) {
            return lock.commit();
        } else {
            logFile.delete();
            lock.unlock();
            return true;
        }
    }

    public void write(byte[] content) throws IOException {
        if (content.length > 0) {
            nonEmpty = true;
        }
        lock.write(content);
    }
}
