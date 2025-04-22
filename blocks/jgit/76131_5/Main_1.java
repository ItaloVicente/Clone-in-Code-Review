package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.lfs.lib.LongObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FilterCommand;
import org.eclipse.jgit.util.FilterCommandFactory;

public class SmudgeFilter extends FilterCommand {
	public final static FilterCommandFactory FACTORY = new FilterCommandFactory() {
		@Override
		public FilterCommand create(Repository db
				OutputStream out) throws IOException {
			return new SmudgeFilter(db
		}
	};

	public final static void register() {
		Repository.registerCommand(
				org.eclipse.jgit.lib.Constants.BUILTIN_FILTER_PREFIX
						+ "lfs/smudge"
				FACTORY);
	};

	LongObjectId id;

	private InputStream in;

	private LfsUtil lfsUtil;

	public SmudgeFilter(Repository db
			throws IOException {
		super(in
		LfsPointer res = LfsPointer.parseLfsPointer(in);
		if (res != null) {
			Path mediaFile = lfsUtil.getMediaFile(res.getOid());
			if (Files.exists(mediaFile)) {
				this.in = Files.newInputStream(mediaFile);
			}
		}
	}

	@Override
	public int run() throws IOException {
		int b;
		if (in != null) {
			while ((b = in.read()) != -1)
				out.write(b);
			in.close();
		}
		out.close();
		return -1;
	}
}
