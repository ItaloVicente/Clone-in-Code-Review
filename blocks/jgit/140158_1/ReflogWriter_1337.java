package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.LOCK_SUFFIX;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_NOTES;
import static org.eclipse.jgit.lib.Constants.R_REFS;
import static org.eclipse.jgit.lib.Constants.R_REMOTES;

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
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.util.FileUtils;

public class ReflogWriter {

	public static String refLockFor(String name) {
		return name + LOCK_SUFFIX;
	}

	private final RefDirectory refdb;

	private final boolean forceWrite;

	public ReflogWriter(RefDirectory refdb) {
		this(refdb
	}

	public ReflogWriter(RefDirectory refdb
		this.refdb = refdb;
		this.forceWrite = forceWrite;
	}

	public ReflogWriter create() throws IOException {
		FileUtils.mkdir(refdb.logsDir);
		FileUtils.mkdir(refdb.logsRefsDir);
		FileUtils.mkdir(
				new File(refdb.logsRefsDir
		return this;
	}

	public ReflogWriter log(String refName
			throws IOException {
		return log(refName
				entry.getComment());
	}

	public ReflogWriter log(String refName
			ObjectId newId
		byte[] encoded = encode(oldId
		return log(refName
	}

	public ReflogWriter log(RefUpdate update
			boolean deref) throws IOException {
		ObjectId oldId = update.getOldObjectId();
		ObjectId newId = update.getNewObjectId();
		Ref ref = update.getRef();

		PersonIdent ident = update.getRefLogIdent();
		if (ident == null)
			ident = new PersonIdent(refdb.getRepository());
		else
			ident = new PersonIdent(ident);

		byte[] rec = encode(oldId
		if (deref && ref.isSymbolic()) {
			log(ref.getName()
			log(ref.getLeaf().getName()
		} else
			log(ref.getName()

		return this;
	}

	private byte[] encode(ObjectId oldId
			String message) {
		StringBuilder r = new StringBuilder();
		r.append(ObjectId.toString(oldId));
		r.append(' ');
		r.append(ObjectId.toString(newId));
		r.append(' ');
		r.append(ident.toExternalString());
		r.append('\t');
		r.append(
				message.replace("\r\n"
						.replace("\n"
		r.append('\n');
		return Constants.encode(r.toString());
	}

	private FileOutputStream getFileOutputStream(File log) throws IOException {
		try {
			return new FileOutputStream(log
		} catch (FileNotFoundException err) {
			File dir = log.getParentFile();
			if (dir.exists()) {
				throw err;
			}
			if (!dir.mkdirs() && !dir.isDirectory()) {
				throw new IOException(MessageFormat
						.format(JGitText.get().cannotCreateDirectory
			}
			return new FileOutputStream(log
		}
	}

	private ReflogWriter log(String refName
		File log = refdb.logFor(refName);
		boolean write = forceWrite
				|| (isLogAllRefUpdates() && shouldAutoCreateLog(refName))
				|| log.isFile();
		if (!write)
			return this;

		WriteConfig wc = refdb.getRepository().getConfig().get(WriteConfig.KEY);
		try (FileOutputStream out = getFileOutputStream(log)) {
			if (wc.getFSyncRefFiles()) {
				FileChannel fc = out.getChannel();
				ByteBuffer buf = ByteBuffer.wrap(rec);
				while (0 < buf.remaining()) {
					fc.write(buf);
				}
				fc.force(true);
			} else {
				out.write(rec);
			}
		}
		return this;
	}

	private boolean isLogAllRefUpdates() {
		return refdb.getRepository().getConfig().get(CoreConfig.KEY)
				.isLogAllRefUpdates();
	}

	private boolean shouldAutoCreateLog(String refName) {
		return refName.equals(HEAD)
				|| refName.startsWith(R_HEADS)
				|| refName.startsWith(R_REMOTES)
				|| refName.startsWith(R_NOTES);
	}
}
