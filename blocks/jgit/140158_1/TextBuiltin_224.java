
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListTagCommand;
import org.eclipse.jgit.api.TagCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.revwalk.RevWalk;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Tag extends TextBuiltin {
	@Option(name = "-f"
	private boolean force;

	@Option(name = "-d"
	private boolean delete;

	@Option(name = "-m"

	@Argument(index = 0
	private String tagName;

	@Argument(index = 1
	private ObjectId object;

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			if (tagName != null) {
				if (delete) {
					List<String> deletedTags = git.tagDelete().setTags(tagName)
							.call();
					if (deletedTags.isEmpty()) {
						throw die(MessageFormat
								.format(CLIText.get().tagNotFound
					}
				} else {
					TagCommand command = git.tag().setForceUpdate(force)
							.setMessage(message).setName(tagName);

					if (object != null) {
						try (RevWalk walk = new RevWalk(db)) {
							command.setObjectId(walk.parseAny(object));
						}
					}
					try {
						command.call();
					} catch (RefAlreadyExistsException e) {
						throw die(MessageFormat.format(
								CLIText.get().tagAlreadyExists
					}
				}
			} else {
				ListTagCommand command = git.tagList();
				List<Ref> list = command.call();
				for (Ref ref : list) {
					outw.println(Repository.shortenRefName(ref.getName()));
				}
			}
		} catch (GitAPIException | IOException e) {
			throw die(e.getMessage()
		}
	}
}
