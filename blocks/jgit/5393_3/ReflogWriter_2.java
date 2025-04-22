package org.eclipse.jgit.storage.file;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.LOGS;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_REFS;
import static org.eclipse.jgit.lib.Constants.R_REMOTES;
import static org.eclipse.jgit.lib.Constants.R_STASH;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;

public class ReflogWriter {

	public static String refLockFor(final String name) {
		return name + LockFile.SUFFIX;
	}

	private final Repository parent;

	private final File logsDir;

	private final File logsRefsDir;

	private final boolean forceWrite;

	public ReflogWriter(final Repository repository) {
		this(repository
	}

	public ReflogWriter(final Repository repository
		final FS fs = repository.getFS();
		parent = repository;
		File gitDir = repository.getDirectory();
		logsDir = fs.resolve(gitDir
		logsRefsDir = fs.resolve(gitDir
		this.forceWrite = forceWrite;
	}

	public Repository getRepository() {
		return parent;
	}

	public ReflogWriter create() throws IOException {
		FileUtils.mkdir(logsDir);
		FileUtils.mkdir(logsRefsDir);
		FileUtils.mkdir(new File(logsRefsDir
				R_HEADS.substring(R_REFS.length())));
		return this;
	}

	public File logFor(String name) {
		if (name.startsWith(R_REFS)) {
			name = name.substring(R_REFS.length());
			return new File(logsRefsDir
		}
		return new File(logsDir
	}

	public ReflogWriter log(final String refName
			throws IOException {
		return log(refName
				entry.getComment());
	}

	public ReflogWriter log(final String refName
			final ObjectId newId
			throws IOException {
		byte[] encoded = encode(oldId
		return log(refName
	}

	public ReflogWriter log(final RefUpdate update
			final boolean deref) throws IOException {
		final ObjectId oldId = update.getOldObjectId();
		final ObjectId newId = update.getNewObjectId();
		final Ref ref = update.getRef();

		PersonIdent ident = update.getRefLogIdent();
		if (ident == null)
			ident = new PersonIdent(parent);
		else
			ident = new PersonIdent(ident);

		final byte[] rec = encode(oldId
		if (deref && ref.isSymbolic()) {
			log(ref.getName()
			log(ref.getLeaf().getName()
		} else
			log(ref.getName()

		return this;
	}

	private byte[] encode(ObjectId oldId
			String message) {
		final StringBuilder r = new StringBuilder();
		r.append(ObjectId.toString(oldId));
		r.append(' ');
		r.append(ObjectId.toString(newId));
		r.append(' ');
		r.append(ident.toExternalString());
		r.append('\t');
		r.append(message);
		r.append('\n');
		return Constants.encode(r.toString());
	}

	private ReflogWriter log(final String refName
			throws IOException {
		final File log = logFor(refName);
		final boolean write = forceWrite
				|| (isLogAllRefUpdates() && shouldAutoCreateLog(refName))
				|| log.isFile();
		if (!write)
			return this;

		WriteConfig wc = getRepository().getConfig().get(WriteConfig.KEY);
		FileOutputStream out;
		try {
			out = new FileOutputStream(log
		} catch (FileNotFoundException err) {
			final File dir = log.getParentFile();
			if (dir.exists())
				throw err;
			if (!dir.mkdirs() && !dir.isDirectory())
				throw new IOException(MessageFormat.format(
						JGitText.get().cannotCreateDirectory
			out = new FileOutputStream(log
		}
		try {
			if (wc.getFSyncRefFiles()) {
				FileChannel fc = out.getChannel();
				ByteBuffer buf = ByteBuffer.wrap(rec);
				while (0 < buf.remaining())
					fc.write(buf);
				fc.force(true);
			} else
				out.write(rec);
		} finally {
			out.close();
		}
		return this;
	}

	private boolean isLogAllRefUpdates() {
		return parent.getConfig().get(CoreConfig.KEY).isLogAllRefUpdates();
	}

	private boolean shouldAutoCreateLog(final String refName) {
				|| refName.equals(R_STASH);
	}
}
