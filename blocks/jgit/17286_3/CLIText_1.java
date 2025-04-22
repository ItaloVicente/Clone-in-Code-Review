package org.eclipse.jgit.pgm;

import org.eclipse.jgit.api.DescribeCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.Argument;

@Command(common = true
class Describe extends TextBuiltin {

	@Argument(index = 0
	private ObjectId tree;

	@Override
	protected void run() throws Exception {
		DescribeCommand cmd = new Git(db).describe();
		if (tree != null)
			cmd.setTarget(tree);
		String result = null;
		try {
			result = cmd.call();
		} catch (RefNotFoundException e) {
			throw die(CLIText.get().noNamesFound
		}
		if (result == null)
			throw die(CLIText.get().noNamesFound);

		outw.println(result);
	}

}
