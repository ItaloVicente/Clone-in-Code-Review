package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.ReflogEntry;
import org.eclipse.jgit.storage.file.ReflogReader;

public class ReflogCommand extends GitCommand<Collection<ReflogEntry>> {

	private String ref = Constants.R_HEADS + Constants.MASTER;

	public ReflogCommand(Repository repo) {
		super(repo);
	}

	public ReflogCommand setRef(String ref) {
		checkCallable();
		this.ref = ref;
		return this;
	}

	public Collection<ReflogEntry> call() throws Exception {
		checkCallable();

		try {
			ReflogReader reader = new ReflogReader(repo
			return reader.getReverseEntries();
		} catch (IOException e) {
			throw new InvalidRemoteException(MessageFormat.format(
					JGitText.get().cannotRead
		}
	}

}
