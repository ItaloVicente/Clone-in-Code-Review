package org.eclipse.jgit.api;

import org.eclipse.jgit.api.errors.FileNotFoundException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.IndexDiff;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class RestoreCommand extends GitCommand<List<String>> {

	private Collection<String> filepatterns;

	private	List<String> actuallyRestoredFiles = new ArrayList<>();

	private boolean cached = false;

	private boolean addAll = false;
	public RestoreCommand(Repository repo) {
		super(repo);
		filepatterns = new LinkedList<>();
	}

	public RestoreCommand addFilepattern(String filepattern) {
		checkCallable();
		filepatterns.add(filepattern);
		return this;
	}

	public RestoreCommand setCached(boolean cached) {
		checkCallable();
		this.cached = cached;
		return this;
	}

	@Override
	public List<String> call() throws GitAPIException

		if (filepatterns.isEmpty()) {
			throw new NoFilepatternException(
					JGitText.get().atLeastOnePatternIsRequired);
		}

		if(!addAll) {
			for (String name : filepatterns) {
				File p = new File(repo.getWorkTree()
				if (!p.exists()) {
					throw new FileNotFoundException(MessageFormat.format(
							JGitText.get().pathspecDidNotMatch
				}
			}
		}

		checkCallable();
		return restore();
	}

	private List<String> restore() throws GitAPIException {
		WorkingTreeIterator workingTreeIt = new FileTreeIterator(repo);
		try {
			IndexDiff diff = new IndexDiff(repo
					workingTreeIt);
			if (!addAll) {
				diff.setFilter(PathFilterGroup.createFromStrings(
						filepatterns));
			}
			diff.diff();

			try (Git git = new Git(repo)) {
				if (cached) {
					if (diff.getChanged().size() > 0 ||
							diff.getAdded().size() > 0) {
						ResetCommand reset = git.reset();
						for (String ent : diff.getChanged()) {
							File p = new File(repo.getWorkTree()
							if (p.isFile()) {
								reset.addPath(ent);
								actuallyRestoredFiles.add(ent);
							}
						}
						for (String ent : diff.getAdded()) {
							File p = new File(repo.getWorkTree()
							if (p.isFile()) {
								reset.addPath(ent);
								actuallyRestoredFiles.add(ent);
							}
						}
						reset.call();
					}
				} else {
					if (diff.getModified().size() > 0) {
						CheckoutCommand checkout = git.checkout();
						for (String ent : diff.getModified()) {
							File p = new File(repo.getWorkTree()
							if (p.isFile()) {
								checkout.addPath(ent);
								actuallyRestoredFiles.add(ent);
							}
						}
						checkout.call();
					}
				}
			}
			setCallable(false);
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
		return actuallyRestoredFiles;
	}
}
