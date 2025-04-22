package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ReflogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.Repository;
import org.kohsuke.args4j.Argument;

@Command(common = true
class Reflog extends TextBuiltin {

	@Argument(metaVar = "metaVar_ref")
	private String ref;

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			ReflogCommand cmd = git.reflog();
			if (ref != null)
				cmd.setRef(ref);
			Collection<ReflogEntry> entries = cmd.call();
			int i = 0;
			for (ReflogEntry entry : entries) {
				outw.println(toString(entry
			}
		} catch (GitAPIException | IOException e) {
			throw die(e.getMessage()
		}
	}

	private String toString(ReflogEntry entry
		final StringBuilder s = new StringBuilder();
		s.append(entry.getNewId().abbreviate(7).name());
		s.append(ref == null ? Constants.HEAD : Repository.shortenRefName(ref));
		s.append(entry.getComment());
		return s.toString();
	}
}
