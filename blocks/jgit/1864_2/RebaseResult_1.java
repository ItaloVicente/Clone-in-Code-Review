package org.eclipse.jgit.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.RebaseResult.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class RebaseCommand extends GitCommand<RebaseResult> {
	private RevCommit upstreamCommit;

	private RevCommit headCommit;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	private final RevWalk walk;

	private boolean abortRebase = false;

	private boolean continueRebase = false;

	private boolean skipRebase = false;

	protected RebaseCommand(Repository repo) {
		super(repo);
		walk = new RevWalk(repo);
	}

	public RebaseResult call() throws NoHeadException
			JGitInternalException
		checkCallable();
		checkParameters();

		if (this.abortRebase)
			try {
				return abort();
			} catch (IOException ioe) {
				throw new JGitInternalException(ioe.getMessage()
			}

		if (this.skipRebase)
			throw new UnsupportedOperationException(
					"--skip is not yet implemented");

		if (this.continueRebase)
			throw new UnsupportedOperationException(
					"--continue is not yet implemented");

		List<RevCommit> cherryPickList = new ArrayList<RevCommit>();
		try {
			String headName = repo.getFullBranch();
			ObjectId headId = repo.resolve(Constants.HEAD);
			if (headId == null)
				throw new RefNotFoundException(MessageFormat.format(JGitText
						.get().refNotResolved
			headCommit = walk.lookupCommit(headId);

			Git git = new Git(repo);

			monitor.beginTask("Obtain commits that need to be cherry-picked"
					ProgressMonitor.UNKNOWN);
			LogCommand cmd = git.log().addRange(upstreamCommit
			Iterable<RevCommit> commitsToUse = cmd.call();
			for (RevCommit commit : commitsToUse)
				cherryPickList.add(0

			monitor.beginTask("Obtain commits that need to be cherry-picked"
					ProgressMonitor.UNKNOWN);
			if (cherryPickList.isEmpty())
				return new RebaseResult(Status.UP_TO_DATE);
			File rebaseDir = new File(repo.getDirectory()
			rebaseDir.mkdir();

			createFile(repo.getDirectory()
			createFile(rebaseDir
			createFile(rebaseDir
			createFile(rebaseDir
			createFile(rebaseDir
			createFile(rebaseDir
			createFile(rebaseDir
					.size()));
			createFile(rebaseDir

			createFile(rebaseDir
			createFile(rebaseDir
			createFile(rebaseDir
			createFile(rebaseDir
			createFile(rebaseDir
			createFile(rebaseDir

			int total = cherryPickList.size();
			int current = 1;
			for (RevCommit commit : cherryPickList) {
				String filename = Integer.toString(current);
				while (filename.length() < 4)
					filename = "0" + filename;
				createFile(rebaseDir
				File outFile = new File(rebaseDir
				FileOutputStream fos = new FileOutputStream(outFile);
				writePatchHeader(commit
				DiffFormatter df = new DiffFormatter(fos);
				df.setAbbreviationLength(40);
				df.setRepository(repo);

				df.format(commit.getParent(0)
				df.flush();
				fos.close();
				current++;
			}

			current = 1;
			RevCommit newHead = null;
			DateFormat dfmt = new SimpleDateFormat();
			checkoutCommit(upstreamCommit);
			for (RevCommit commitToPick : cherryPickList) {
				createFile(rebaseDir
				StringBuilder info = new StringBuilder();
				info.append("Author: ");
				info.append(commitToPick.getAuthorIdent().getName());
				info.append("\n");
				info.append("Email: ");
				info.append(commitToPick.getAuthorIdent().getEmailAddress());
				info.append("\n");
				info.append("Subject: ");
				info.append(commitToPick.getShortMessage());
				info.append("\n");
				info.append("Date: ");
				info.append(dfmt
						.format(commitToPick.getAuthorIdent().getWhen()));
				createFile(rebaseDir
				createFile(rebaseDir
						.getShortMessage());
				monitor.beginTask("Applying commit " + current + " of " + total
						+ ": " + commitToPick.getShortMessage()
						ProgressMonitor.UNKNOWN);
				newHead = git.cherryPick().include(commitToPick).call();
				monitor.endTask();
				if (newHead == null)
					return new RebaseResult(Status.STOPPED);
				current++;
				createFile(rebaseDir
			}
			new File(rebaseDir
			return new RebaseResult(Status.OK);
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	private void writePatchHeader(RevCommit commit
			throws IOException {
		StringBuilder sb = new StringBuilder();
		final SimpleDateFormat dtfmt;
		dtfmt = new SimpleDateFormat("EEE
		dtfmt.setTimeZone(commit.getAuthorIdent().getTimeZone());
		sb.append("From").append(" ").append(commit.getId().getName()).append(
				" ").append(
				dtfmt.format(Long.valueOf(System.currentTimeMillis()))).append(
				"\n");
		sb.append("From").append(": ")
				.append(commit.getAuthorIdent().getName()).append(" <").append(
						commit.getAuthorIdent().getEmailAddress())
				.append(">\n");
		sb.append("Date").append(": ").append(
				dtfmt.format(commit.getAuthorIdent().getWhen())).append("\n");
		sb.append("Subject").append(": [PATCH] ").append(
				commit.getShortMessage());

		String message = commit.getFullMessage().substring(
				commit.getShortMessage().length());
		sb.append(message).append("\n\n");
		try {
			fos.write(sb.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IOException(e.getMessage());
		}
	}

	private void checkParameters() throws WrongRepositoryStateException {
		if (this.abortRebase || this.continueRebase || this.skipRebase)
			switch (repo.getRepositoryState()) {
			case REBASING:
			case REBASING_INTERACTIVE:
			case REBASING_MERGE:
			case REBASING_REBASING:
				break;
			default:
				throw new WrongRepositoryStateException(MessageFormat.format(
						JGitText.get().wrongRepositoryState
								.getRepositoryState().name()));
			}
		else
			switch (repo.getRepositoryState()) {
			case SAFE:
				return;
			default:
				throw new WrongRepositoryStateException(MessageFormat.format(
						JGitText.get().wrongRepositoryState
								.getRepositoryState().name()));

			}
	}

	private void createFile(File parentDir
			throws IOException {
		File file = new File(parentDir
		file.createNewFile();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(content.getBytes("UTF-8"));
		} finally {
			if (fos != null)
				fos.close();
		}
	}

	private RebaseResult abort() throws IOException {
		try {
			File rebaseDir = new File(repo.getDirectory()
			String commitId = readFile(rebaseDir
			String headName = readFile(rebaseDir
			monitor.beginTask("Aborting rebase: resetting " + headName + " to "
					+ commitId

			boolean failure = true;

			if (!failure)
				deleteRecursive(rebaseDir);
			throw new IOException("Abort not yet implemented");
		} finally {
			monitor.endTask();
		}
	}

	private void deleteRecursive(File fileOrFolder) throws IOException {
		if (fileOrFolder.isDirectory()) {
			for (File child : fileOrFolder.listFiles()) {
				deleteRecursive(child);
			}
		}
		if (!fileOrFolder.delete())
			throw new IOException("Could not delete " + fileOrFolder.getPath());
	}

	private String readFile(File directory
		File file = new File(directory
		FileReader fr = new FileReader(file);
		StringBuilder sb = new StringBuilder();
		char[] chars = new char[100];
		int read = fr.read(chars);
		while (read > -1) {
			sb.append(chars
			read = fr.read(chars);
		}
		return sb.toString();
	}

	private void checkoutCommit(RevCommit commit) throws IOException {
		try {
			monitor.beginTask(
					"Rewinding to commit " + commit.getShortMessage()
					ProgressMonitor.UNKNOWN);
			RevCommit head = walk.parseCommit(repo.resolve(Constants.HEAD));
			DirCacheCheckout dco = new DirCacheCheckout(repo
					.getId()
			dco.setFailOnConflict(true);
			dco.checkout();
			walk.release();
			RefUpdate refUpdate = repo.updateRef(Constants.HEAD
			refUpdate.setNewObjectId(commit);
			Result res = refUpdate.forceUpdate();
			switch (res) {
			case REJECTED:
			case IO_FAILURE:
			case LOCK_FAILURE:
				throw new IOException("Could not rewind to upstream commit");
			default:
				break;
			}
		} finally {
			monitor.endTask();
		}
	}

	public RebaseCommand setUpstream(RevCommit upstream) {
		this.upstreamCommit = upstream;
		return this;
	}

	public RebaseCommand setAbort() {
		this.abortRebase = true;
		this.skipRebase = false;
		this.continueRebase = false;
		return this;
	}

	public RebaseCommand setSkip() {
		this.abortRebase = false;
		this.skipRebase = true;
		this.continueRebase = false;
		return this;
	}

	public RebaseCommand setContinue() {
		this.abortRebase = false;
		this.skipRebase = false;
		this.continueRebase = true;
		return this;
	}

	public RebaseCommand setUpstream(String upstream)
			throws RefNotFoundException {
		try {
			ObjectId upstreamId = repo.resolve(upstream);
			if (upstreamId == null)
				throw new RefNotFoundException(MessageFormat.format(JGitText
						.get().refNotResolved
			upstreamCommit = new RevWalk(repo).parseCommit(repo
					.resolve(upstream));
			return this;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	public RebaseCommand setProgressMonitor(ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}
}
