package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;

public class DeleteTagCommand extends GitCommand<List<String>> {

	private final Set<String> tags = new HashSet<String>();

	protected DeleteTagCommand(Repository repo) {
		super(repo);
	}

	public List<String> call() throws JGitInternalException {
		checkCallable();
		List<String> result = new ArrayList<String>();
		if (tags.isEmpty())
			return result;
		try {
			setCallable(false);
			for (String tagName : tags) {
				if (tagName == null)
					continue;
				Ref currentRef = repo.getRef(tagName);
				if (currentRef == null)
					continue;
				String fullName = currentRef.getName();
				RefUpdate update = repo.updateRef(fullName);
				update.setForceUpdate(true);
				Result deleteResult = update.delete();

				boolean ok = true;
				switch (deleteResult) {
				case IO_FAILURE:
				case LOCK_FAILURE:
				case REJECTED:
					ok = false;
					break;
				default:
					break;
				}

				if (ok) {
					result.add(fullName);
				} else
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().deleteTagUnexpectedResult
							deleteResult.name()));
			}
			return result;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	public DeleteTagCommand setTags(String... tags) {
		checkCallable();
		this.tags.clear();
		for (String tagName : tags)
			this.tags.add(tagName);
		return this;
	}
}
