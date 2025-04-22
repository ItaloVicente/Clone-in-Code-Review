package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.DigestOutputStream;

import org.eclipse.jgit.lfs.errors.CorruptMediaFile;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lfs.lib.LongObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.BuiltinCommand;
import org.eclipse.jgit.util.BuiltinCommandFactory;
import org.eclipse.jgit.util.FileUtils;

public class CleanFilter extends BuiltinCommand {
	public final static BuiltinCommandFactory FACTORY = new BuiltinCommandFactory() {
		@Override
		public BuiltinCommand create(Repository db
				OutputStream out) throws IOException {
			return new CleanFilter(db
		}
	};

	public final static void register() {
		Repository.registerCommand(
				org.eclipse.jgit.lib.Constants.BUILTIN_FILTER_PREFIX
						+ "lfs/clean"
				FACTORY);
	};

	private DigestOutputStream mOut;

	private LfsUtil lfsUtil;

	private long size = 0;

	private Path tmpFile;

	public CleanFilter(Repository db
			throws IOException {
		super(in
		Files.createDirectories(lfsUtil.getLfsTmpDir());
		tmpFile = lfsUtil.getTmpFile();
		mOut = new DigestOutputStream(
				Files.newOutputStream(tmpFile
				Constants.newMessageDigest());
	}

	public int run() throws IOException {
		int b = in.read();
		if (b != -1) {
			mOut.write(b);
			size++;
			return 1;
		} else {
			mOut.close();
			LongObjectId loid = LongObjectId
					.fromRaw(mOut.getMessageDigest().digest());
			Path mediaFile = lfsUtil.getMediaFile(loid);
			if (Files.isRegularFile(mediaFile)) {
				long fsSize = Files.size(mediaFile);
				if (fsSize != size) {
					throw new CorruptMediaFile(mediaFile
				}
			} else {
				FileUtils.mkdirs(mediaFile.getParent().toFile()
				FileUtils.rename(tmpFile.toFile()
			}
			LfsPointer lfsPointer = new LfsPointer(loid
			lfsPointer.encode(out);
			out.close();
			return -1;
		}
	}
}
