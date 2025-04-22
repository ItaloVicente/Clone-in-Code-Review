
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.opt.CmdLineParser;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;

@Command(usage = "usage_RevParse")
class RevParse extends TextBuiltin {
	@Option(name = "--all"
	boolean all;

	@Option(name = "--verify"
	boolean verify;

	@Argument(index = 0
	private List<ObjectId> commits = new ArrayList<>();

	@Override
	protected void run() {
		try {
			if (all) {
				for (Ref r : db.getRefDatabase().getRefs()) {
					ObjectId objectId = r.getObjectId();
					if (objectId == null) {
						throw new NullPointerException();
					}
					outw.println(objectId.name());
				}
			} else {
				if (verify && commits.size() > 1) {
					final CmdLineParser clp = new CmdLineParser(this);
					throw new CmdLineException(clp
							CLIText.format(CLIText.get().needSingleRevision));
				}

				for (ObjectId o : commits) {
					outw.println(o.name());
				}
			}
		} catch (IOException | CmdLineException e) {
			throw die(e.getMessage()
		}
	}
}
