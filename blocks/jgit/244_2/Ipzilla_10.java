
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
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(name = "eclipse-iplog"
class Iplog extends TextBuiltin {
	@Option(name = "--output"
	private File output;

	@Argument(index = 0
	private ObjectId commit;

	@Override
	protected void run() throws Exception {
		if (CookieHandler.getDefault() == null)
			CookieHandler.setDefault(new SimpleCookieManager());

		final IpLogGenerator log = new IpLogGenerator();

		if (commit == null) {
			System.err.println("warning: No commit given on command line
			commit = db.resolve(Constants.HEAD);
		}

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
