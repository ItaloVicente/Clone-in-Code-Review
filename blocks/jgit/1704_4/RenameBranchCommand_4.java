package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

public class ListBranchCommand extends GitCommand<List<Ref>> {
	private ListMode listMode;

	public enum ListMode {
		ALL
		REMOTE;
	}

	protected ListBranchCommand(Repository repo) {
		super(repo);
	}

	public List<Ref> call() throws JGitInternalException {
		checkCallable();
		Map<String
		try {
			if (listMode == null) {
				refList = repo.getRefDatabase().getRefs(Constants.R_HEADS);
			} else if (listMode == ListMode.REMOTE) {
				refList = repo.getRefDatabase().getRefs(Constants.R_REMOTES);
			} else {
				refList = repo.getRefDatabase().getRefs(Constants.R_HEADS);
				refList.putAll(repo.getRefDatabase().getRefs(
						Constants.R_REMOTES));
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
		List<Ref> resultRefs = new ArrayList<Ref>();
		resultRefs.addAll(refList.values());
		Collections.sort(resultRefs
			public int compare(Ref o1
				return o1.getName().compareTo(o2.getName());
			}
		});
		setCallable(false);
		return resultRefs;
	}

	public ListBranchCommand setListMode(ListMode listMode) {
		checkCallable();
		this.listMode = listMode;
		return this;
	}
}
