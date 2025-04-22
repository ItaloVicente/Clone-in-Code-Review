
package org.eclipse.jgit.pgm.eclipse;

import java.io.File;
import java.io.OutputStream;
import java.net.CookieHandler;

import org.eclipse.jgit.iplog.IpLogGenerator;
import org.eclipse.jgit.iplog.SimpleCookieManager;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.LockFile;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(name = "eclipse-iplog"
class Iplog extends TextBuiltin {
	@Option(name = "--version"
	private String version;

	@Option(name = "--output"
	private File output;

	@Argument(index = 0
	private ObjectId commitId;

	@Override
	protected void run() throws Exception {
		if (CookieHandler.getDefault() == null)
			CookieHandler.setDefault(new SimpleCookieManager());

		final IpLogGenerator log = new IpLogGenerator();

		if (commitId == null) {
			System.err.println("warning: No commit given on command line
			commitId = db.resolve(Constants.HEAD);
		}

		final RevWalk rw = new RevWalk(db);
		final RevObject start = rw.parseAny(commitId);
		if (version == null && start instanceof RevTag)
			version = ((RevTag) start).getTagName();
		else if (version == null)
			throw die(start.name() + " is not a tag

		log.scan(db

		if (output != null) {
			if (!output.getParentFile().exists())
				output.getParentFile().mkdirs();
			LockFile lf = new LockFile(output);
			if (!lf.lock())
				throw die("Cannot lock " + output);
			try {
				OutputStream os = lf.getOutputStream();
				try {
					log.writeTo(os);
				} finally {
					os.close();
				}
				if (!lf.commit())
					throw die("Cannot write " + output);
			} finally {
				lf.unlock();
			}
		} else {
			log.writeTo(System.out);
			System.out.flush();
		}
	}
}
