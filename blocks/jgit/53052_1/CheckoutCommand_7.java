
package org.eclipse.jgit.pgm;

import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class UpdateRef extends TextBuiltin {
	@Option(name = "-m"
	private String reason;

	@Argument(index = 0)
	private String ref;

	@Argument(index = 1)
	private String newValue;

	@Override
	protected void run() throws Exception {
		boolean detach = false;
		RefUpdate update = db.updateRef(ref

		update.setRefLogIdent(new PersonIdent(db));

		update.setForceUpdate(true);

		if (reason != null) {
			boolean appendStatus = false;
			update.setRefLogMessage(reason
		}

		Result result = update.update();
		if (result != Result.FORCED) {
		}
	}
}
