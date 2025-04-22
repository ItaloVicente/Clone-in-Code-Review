package org.eclipse.jgit.lfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.lfs.lib.LongObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.BuiltinCommand;
import org.eclipse.jgit.util.BuiltinCommandFactory;

public class SmudgeFilter extends BuiltinCommand {
	public final static BuiltinCommandFactory FACTORY = new BuiltinCommandFactory() {
		@Override
		public BuiltinCommand create(Repository db
			try {
				return new SmudgeFilter(db
			} catch (IOException e) {
			}
		}
	};

	LongObjectId id;

	private InputStream mIn;

	private LfsUtil lfsUtil;

	private OutputStream out;

	public SmudgeFilter(Repository db
			throws IOException {
		super(in
		byte buffer[] = new byte[1024];
		LfsParserResult res = LfsPointer.parseLfsPointer(in
		if (res.pointer != null) {
			Path mediaFile = lfsUtil.getMediaFile(res.pointer.getOid());
			if (Files.exists(mediaFile)) {
				mIn = Files.newInputStream(mediaFile);
			}
		}
	}

	@Override
	public int run() throws IOException {
		int b;
		if (mIn != null) {
			while ((b = mIn.read()) != -1)
				out.write(b);
			mIn.close();
		}
		out.close();
		return -1;
	}
}
