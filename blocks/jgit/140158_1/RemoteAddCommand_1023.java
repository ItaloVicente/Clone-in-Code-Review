package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.Repository;

public class ReflogCommand extends GitCommand<Collection<ReflogEntry>> {

	private String ref = Constants.HEAD;

	public ReflogCommand(Repository repo) {
		super(repo);
	}

	public ReflogCommand setRef(String ref) {
		checkCallable();
		this.ref = ref;
		return this;
	}

	@Override
	public Collection<ReflogEntry> call() throws GitAPIException
			InvalidRefNameException {
		checkCallable();

		try {
			ReflogReader reader = repo.getReflogReader(ref);
			if (reader == null)
				throw new RefNotFoundException(MessageFormat.format(
						JGitText.get().refNotResolved
			return reader.getReverseEntries();
		} catch (IOException e) {
			throw new InvalidRefNameException(MessageFormat.format(
					JGitText.get().cannotRead
		}
	}

}
