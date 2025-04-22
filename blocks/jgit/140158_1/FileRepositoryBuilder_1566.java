
package org.eclipse.jgit.storage.file;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.FileSnapshot;
import org.eclipse.jgit.internal.storage.file.LockFile;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileBasedConfig extends StoredConfig {
	private final static Logger LOG = LoggerFactory
			.getLogger(FileBasedConfig.class);

	private final File configFile;

	private final FS fs;

	private boolean utf8Bom;

	private volatile FileSnapshot snapshot;

	private volatile ObjectId hash;

	public FileBasedConfig(File cfgLocation
		this(null
	}

	public FileBasedConfig(Config base
		super(base);
		configFile = cfgLocation;
		this.fs = fs;
		this.snapshot = FileSnapshot.DIRTY;
		this.hash = ObjectId.zeroId();
	}

	@Override
	protected boolean notifyUponTransientChanges() {
		return false;
	}

	public final File getFile() {
		return configFile;
	}

	@Override
	public void load() throws IOException
		final int maxStaleRetries = 5;
		int retries = 0;
		while (true) {
			final FileSnapshot oldSnapshot = snapshot;
			final FileSnapshot newSnapshot = FileSnapshot.save(getFile());
			try {
				final byte[] in = IO.readFully(getFile());
				final ObjectId newHash = hash(in);
				if (hash.equals(newHash)) {
					if (oldSnapshot.equals(newSnapshot)) {
						oldSnapshot.setClean(newSnapshot);
					} else {
						snapshot = newSnapshot;
					}
				} else {
					final String decoded;
					if (isUtf8(in)) {
						decoded = RawParseUtils.decode(UTF_8
								in
						utf8Bom = true;
					} else {
						decoded = RawParseUtils.decode(in);
					}
					fromText(decoded);
					snapshot = newSnapshot;
					hash = newHash;
				}
				return;
			} catch (FileNotFoundException noFile) {
				if (configFile.exists()) {
					throw noFile;
				}
				clear();
				snapshot = newSnapshot;
				return;
			} catch (IOException e) {
				if (FileUtils.isStaleFileHandle(e)
						&& retries < maxStaleRetries) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(MessageFormat.format(
								JGitText.get().configHandleIsStale
								Integer.valueOf(retries))
					}
					retries++;
					continue;
				}
				throw new IOException(MessageFormat
						.format(JGitText.get().cannotReadFile
			} catch (ConfigInvalidException e) {
				throw new ConfigInvalidException(MessageFormat
						.format(JGitText.get().cannotReadFile
			}
		}
	}

	@Override
	public void save() throws IOException {
		final byte[] out;
		final String text = toText();
		if (utf8Bom) {
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bos.write(0xEF);
			bos.write(0xBB);
			bos.write(0xBF);
			bos.write(text.getBytes(UTF_8));
			out = bos.toByteArray();
		} else {
			out = Constants.encode(text);
		}

		final LockFile lf = new LockFile(getFile());
		if (!lf.lock())
			throw new LockFailedException(getFile());
		try {
			lf.setNeedSnapshot(true);
			lf.write(out);
			if (!lf.commit())
				throw new IOException(MessageFormat.format(JGitText.get().cannotCommitWriteTo
		} finally {
			lf.unlock();
		}
		snapshot = lf.getCommitSnapshot();
		hash = hash(out);
		fireConfigChangedEvent();
	}

	@Override
	public void clear() {
		hash = hash(new byte[0]);
		super.clear();
	}

	private static ObjectId hash(byte[] rawText) {
		return ObjectId.fromRaw(Constants.newMessageDigest().digest(rawText));
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + getFile().getPath() + "]";
	}

	public boolean isOutdated() {
		return snapshot.isModified(getFile());
	}

	@Override
	protected byte[] readIncludedConfig(String relPath)
			throws ConfigInvalidException {
		final File file;
			file = fs.resolve(fs.userHome()
		} else {
			file = fs.resolve(configFile.getParentFile()
		}

		if (!file.exists()) {
			return null;
		}

		try {
			return IO.readFully(file);
		} catch (IOException ioe) {
			throw new ConfigInvalidException(MessageFormat
					.format(JGitText.get().cannotReadFile
		}
	}
}
