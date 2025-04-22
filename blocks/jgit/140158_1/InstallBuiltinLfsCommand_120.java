package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandFactory;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.lfs.errors.CorruptMediaFile;
import org.eclipse.jgit.lfs.internal.AtomicObjectOutputStream;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FileUtils;

public class CleanFilter extends FilterCommand {
	public final static FilterCommandFactory FACTORY = new FilterCommandFactory() {

		@Override
		public FilterCommand create(Repository db
				OutputStream out) throws IOException {
			return new CleanFilter(db
		}
	};

	static void register() {
		FilterCommandRegistry
				.register(org.eclipse.jgit.lib.Constants.BUILTIN_FILTER_PREFIX
						+ Constants.ATTR_FILTER_DRIVER_PREFIX
						+ org.eclipse.jgit.lib.Constants.ATTR_FILTER_TYPE_CLEAN
						FACTORY);
	}

	private AtomicObjectOutputStream aOut;

	private Lfs lfsUtil;

	private long size;

	private Path tmpFile;

	public CleanFilter(Repository db
			throws IOException {
		super(in
		lfsUtil = new Lfs(db);
		Files.createDirectories(lfsUtil.getLfsTmpDir());
		tmpFile = lfsUtil.createTmpFile();
		this.aOut = new AtomicObjectOutputStream(tmpFile.toAbsolutePath());
	}

	@Override
	public int run() throws IOException {
		try {
			byte[] buf = new byte[8192];
			int length = in.read(buf);
			if (length != -1) {
				aOut.write(buf
				size += length;
				return length;
			} else {
				aOut.close();
				AnyLongObjectId loid = aOut.getId();
				aOut = null;
				Path mediaFile = lfsUtil.getMediaFile(loid);
				if (Files.isRegularFile(mediaFile)) {
					long fsSize = Files.size(mediaFile);
					if (fsSize != size) {
						throw new CorruptMediaFile(mediaFile
					} else {
						FileUtils.delete(tmpFile.toFile());
					}
				} else {
					Path parent = mediaFile.getParent();
					if (parent != null) {
						FileUtils.mkdirs(parent.toFile()
					}
					FileUtils.rename(tmpFile.toFile()
							StandardCopyOption.ATOMIC_MOVE);
				}
				LfsPointer lfsPointer = new LfsPointer(loid
				lfsPointer.encode(out);
				in.close();
				out.close();
				return -1;
			}
		} catch (IOException e) {
			if (aOut != null) {
				aOut.abort();
			}
			in.close();
			out.close();
			throw e;
		}
	}
}
