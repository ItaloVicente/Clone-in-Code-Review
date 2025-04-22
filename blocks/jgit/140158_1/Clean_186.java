
package org.eclipse.jgit.pgm;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.RestOfArgumentsHandler;

@Command(common = true
class Checkout extends TextBuiltin {

	@Option(name = "-b"
	private boolean createBranch = false;

	@Option(name = "-B"
	private boolean forceSwitchBranch = false;

	@Option(name = "--force"
	private boolean forced = false;

	@Option(name = "--orphan"
	private boolean orphan = false;

	@Argument(required = false
	private String name;

	@Option(name = "--"
	private List<String> paths = new ArrayList<>();

	@Override
	protected void run() throws Exception {
		if (createBranch) {
			final ObjectId head = db.resolve(Constants.HEAD);
			if (head == null)
				throw die(CLIText.get().onBranchToBeBorn);
		}

		try (Git git = new Git(db)) {
			CheckoutCommand command = git.checkout()
					.setProgressMonitor(new TextProgressMonitor(errw));
			if (paths.size() > 0) {
				command.setStartPoint(name);
					command.setAllPaths(true);
				} else {
					command.addPaths(paths);
				}
			} else {
				command.setCreateBranch(createBranch);
				command.setName(name);
				command.setForceRefUpdate(forceSwitchBranch);
				command.setForced(forced);
				command.setOrphan(orphan);
			}
			try {
				String oldBranch = db.getBranch();
				Ref ref = command.call();
				if (ref == null)
					return;
				if (Repository.shortenRefName(ref.getName()).equals(oldBranch)) {
					outw.println(MessageFormat.format(
							CLIText.get().alreadyOnBranch
							name));
					return;
				}
				if (createBranch || orphan)
					outw.println(MessageFormat.format(
							CLIText.get().switchedToNewBranch
				else
					outw.println(MessageFormat.format(
							CLIText.get().switchedToBranch
							Repository.shortenRefName(ref.getName())));
			} catch (RefNotFoundException e) {
				throw die(MessageFormat
						.format(CLIText.get().pathspecDidNotMatch
			} catch (RefAlreadyExistsException e) {
				throw die(MessageFormat
						.format(CLIText.get().branchAlreadyExists
			} catch (CheckoutConflictException e) {
				StringBuilder builder = new StringBuilder();
				builder.append(CLIText.get().checkoutConflict);
				builder.append(System.lineSeparator());
				for (String path : e.getConflictingPaths()) {
					builder.append(MessageFormat.format(
							CLIText.get().checkoutConflictPathLine
					builder.append(System.lineSeparator());
				}
				throw die(builder.toString()
			}
		}
	}
}
