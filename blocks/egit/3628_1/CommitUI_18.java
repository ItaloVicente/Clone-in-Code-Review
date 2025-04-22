package org.eclipse.egit.ui.internal.commit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.UserConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Text;

public class CommitHelper {

	private Repository repository;

	boolean canCommit;

	String cannotCommitMessage;

	private RevCommit previousCommit;

	String author;

	String committer;

	boolean isMergedResolved;

	boolean isCherryPickResolved;

	private String commitMessage;

	public CommitHelper(Repository repository) {
		this.repository = repository;
		calculateCommitInfo();
	}

	private void calculateCommitInfo() {
		Repository mergeRepository = null;
		isMergedResolved = false;
		isCherryPickResolved = false;
		RepositoryState state = repository.getRepositoryState();
		canCommit = state.canCommit();
		if (!canCommit) {
			cannotCommitMessage = NLS.bind(UIText.CommitAction_repositoryState,
					state.getDescription());
			return;
		}
		if (state.equals(RepositoryState.MERGING_RESOLVED)) {
			isMergedResolved = true;
			mergeRepository = repository;
		} else if (state.equals(RepositoryState.CHERRY_PICKING_RESOLVED)) {
			isCherryPickResolved = true;
			mergeRepository = repository;
		}
		previousCommit = getHeadCommit(repository);
		final UserConfig config = repository.getConfig().get(UserConfig.KEY);
		author = config.getAuthorName();
		final String authorEmail = config.getAuthorEmail();
		author = author + " <" + authorEmail + ">"; //$NON-NLS-1$ //$NON-NLS-2$

		committer = config.getCommitterName();
		final String committerEmail = config.getCommitterEmail();
		committer = committer + " <" + committerEmail + ">"; //$NON-NLS-1$ //$NON-NLS-2$

		if (isMergedResolved || isCherryPickResolved) {
			commitMessage = getMergeResolveMessage(mergeRepository);
		}

		if (isCherryPickResolved) {
			author = getCherryPickOriginalAuthor(mergeRepository);
		}
	}

	private static RevCommit getHeadCommit(Repository repository) {
		RevCommit headCommit = null;
		try {
			ObjectId parentId = repository.resolve(Constants.HEAD);
			if (parentId != null)
				headCommit = new RevWalk(repository).parseCommit(parentId);
		} catch (IOException e) {
			Activator.handleError(UIText.CommitAction_errorRetrievingCommit, e,
					true);
		}
		return headCommit;
	}

	private String getMergeResolveMessage(Repository mergeRepository) {
		File mergeMsg = new File(mergeRepository.getDirectory(),
				Constants.MERGE_MSG);
		FileReader reader;
		try {
			reader = new FileReader(mergeMsg);
			BufferedReader br = new BufferedReader(reader);
			try {
				StringBuilder message = new StringBuilder();
				String s;
				String newLine = newLine();
				while ((s = br.readLine()) != null)
					message.append(s).append(newLine);
				return message.toString();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			} finally {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	private static String getCherryPickOriginalAuthor(Repository mergeRepository) {
		try {
			ObjectId cherryPickHead = mergeRepository.readCherryPickHead();
			PersonIdent author = new RevWalk(mergeRepository).parseCommit(
					cherryPickHead).getAuthorIdent();
			return author.getName() + " <" + author.getEmailAddress() + ">"; //$NON-NLS-1$//$NON-NLS-2$
		} catch (IOException e) {
			Activator.handleError(UIText.CommitAction_errorRetrievingCommit, e,
					true);
			throw new IllegalStateException(e);
		}
	}

	private String newLine() {
		return System.getProperty("line.separator"); //$NON-NLS-1$
	}

	public boolean canCommit() {
		return canCommit;
	}

	public String getCannotCommitMessage() {
		return cannotCommitMessage;
	}

	public String getAuthor() {
		return author;
	}

	public String getCommitter() {
		return committer;
	}

	public String getCommitMessage() {
		return commitMessage;
	}

	public boolean isMergedResolved() {
		return isMergedResolved;
	}

	public boolean isCherryPickResolved() {
		return isCherryPickResolved;
	}

	public RevCommit getPreviousCommit() {
		return previousCommit;
	}

	public static CommitInfo getHeadCommitInfo(Repository repository) {
		RevCommit headCommit = getHeadCommit(repository);
		if (headCommit == null)
			return null;
		String commitMessage = headCommit.getFullMessage().replaceAll(
				"\n", Text.DELIMITER); //$NON-NLS-1$
		PersonIdent authorIdent = headCommit.getAuthorIdent();
		String author = authorIdent.getName()
				+ " <" + authorIdent.getEmailAddress() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
		return new CommitInfo(headCommit, author, commitMessage);
	}

	public static class CommitInfo {
		private RevCommit commit;
		private String author;
		private String commitMessage;

		public CommitInfo(RevCommit commit, String author, String commitMessage) {
			super();
			this.commit = commit;
			this.author = author;
			this.commitMessage = commitMessage;
		}

		public RevCommit getCommit() {
			return commit;
		}

		public String getAuthor() {
			return author;
		}

		public String getCommitMessage() {
			return commitMessage;
		}
	}

}
