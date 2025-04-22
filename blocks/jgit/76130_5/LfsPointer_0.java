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
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.FilterCommand;
import org.eclipse.jgit.util.FilterCommandFactory;

public class CleanFilter extends FilterCommand {
	public final static FilterCommandFactory FACTORY = new FilterCommandFactory() {
		@Override
		public FilterCommand create(Repository db
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

	private DigestOutputStream dOut;

	private OutputStream out;

	private LfsUtil lfsUtil;

	private long size = 0;

	private Path tmpFile;

	public CleanFilter(Repository db
			throws IOException {
		super(in
		Files.createDirectories(lfsUtil.getLfsTmpDir());
		tmpFile = lfsUtil.createTmpFile();
		this.out = out;
		this.dOut = new DigestOutputStream(
				Files.newOutputStream(tmpFile
				Constants.newMessageDigest());
	}

	public int run() throws IOException {
		int b = in.read();
		if (b != -1) {
			dOut.write(b);
			size++;
			return 1;
		} else {
			dOut.close();
			LongObjectId loid = LongObjectId
					.fromRaw(dOut.getMessageDigest().digest());
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
