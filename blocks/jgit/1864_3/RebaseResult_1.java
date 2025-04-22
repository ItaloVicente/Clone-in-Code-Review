package org.eclipse.jgit.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.RebaseResult.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;

public class RebaseCommand extends GitCommand<RebaseResult> {
	public enum Operation {
		BEGIN
		CONTINUE
		SKIP
		ABORT;
	}

	private Operation operation = Operation.BEGIN;

	private RevCommit upstreamCommit;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	private final RevWalk walk;

	private File rebaseDir = new File(repo.getDirectory()

	protected RebaseCommand(Repository repo) {
		super(repo);
		walk = new RevWalk(repo);
	}

	public RebaseResult call() throws NoHeadException
			JGitInternalException
		checkCallable();
		checkParameters();
		try {
			switch (operation) {
			case ABORT:
				try {
					return abort();
				} catch (IOException ioe) {
					throw new JGitInternalException(ioe.getMessage()
				}
			case SKIP:
			case CONTINUE:
				String upstreamCommitName = readFile(rebaseDir
				this.upstreamCommit = walk.parseCommit(repo
						.resolve(upstreamCommitName));
				break;
			case BEGIN:
				RebaseResult res = initFilesAndRewind();
				if (res != null)
					return res;
			}

			if (this.operation == Operation.CONTINUE)
				throw new UnsupportedOperationException(
						"--continue Not yet implemented");

			if (this.operation == Operation.SKIP)
				throw new UnsupportedOperationException(
						"--skip Not yet implemented");

			RevCommit newHead = null;

			String nextPatchCommit = getNextCommit(false);
			while (nextPatchCommit != null) {
				RevCommit commitToPick = walk.parseCommit(repo
						.resolve(nextPatchCommit));
				monitor.beginTask("Applying " + commitToPick.getShortMessage()
						ProgressMonitor.UNKNOWN);
				newHead = new Git(repo).cherryPick().include(commitToPick)
						.call();
				monitor.endTask();
				if (newHead == null)
					return new RebaseResult(Status.STOPPED);
				nextPatchCommit = getNextCommit(true);
			}
			RebaseResult result = new RebaseResult(Status.OK);
			if (newHead != null) {
				String headName = readFile(rebaseDir
				RefUpdate rup = repo.updateRef(headName);
				rup.setNewObjectId(newHead);
				rup.forceUpdate();
				rup = repo.updateRef(Constants.HEAD);
				rup.link(headName);
				deleteRecursive(rebaseDir);
			}
			return result;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	private String getNextCommit(boolean removeCurrent) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(
				rebaseDir
		BufferedWriter bw = null;
		try {
			if (removeCurrent) {
				br.readLine();
			}
			String line = br.readLine();
			String commitLine = line;
			if (removeCurrent && line != null) {
				bw = new BufferedWriter(new FileWriter(new File(rebaseDir
						"git-rebase-todo")));
				while (line != null) {
					bw.write(line);
					bw.newLine();
					line = br.readLine();
				}
			}
			if (commitLine != null) {
				StringTokenizer stok = new StringTokenizer(line
				if (stok.countTokens() > 2) {
					stok.nextToken();
					return stok.nextToken();
				}
			}
		} finally {
			br.close();
			if (bw != null)
				bw.close();
		}
		return null;
	}

	private RebaseResult initFilesAndRewind() throws RefNotFoundException
			IOException

		List<RevCommit> cherryPickList = new ArrayList<RevCommit>();

		String headName = repo.getFullBranch();
		ObjectId headId = repo.resolve(Constants.HEAD);
		if (headId == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved
		RevCommit headCommit = walk.lookupCommit(headId);
		monitor.beginTask("Obtaining commits that need to be cherry-picked"
				ProgressMonitor.UNKNOWN);
		LogCommand cmd = new Git(repo).log().addRange(upstreamCommit
				headCommit);
		Iterable<RevCommit> commitsToUse = cmd.call();
		for (RevCommit commit : commitsToUse) {
			if (walk.isMergedInto(commit
				continue;
			cherryPickList.add(commit);
		}

		if (cherryPickList.isEmpty())
			return RebaseResult.UP_TO_DATE_RESULT;

		rebaseDir.mkdir();

		createFile(repo.getDirectory()
		createFile(rebaseDir
		createFile(rebaseDir
		createFile(rebaseDir
		FileWriter fw = new FileWriter(new File(rebaseDir
		try {
			StringBuilder sb = new StringBuilder();
			ObjectReader reader = repo.newObjectReader();
			for (RevCommit commit : cherryPickList) {
				sb.setLength(0);
				sb.append("pick ");
				sb.append(reader.abbreviate(commit).name());
				sb.append(" ");
				sb.append(commit.getShortMessage());
			}
			fw.write(sb.toString());
		} finally {
			fw.close();
		}

		monitor.endTask();
		monitor.beginTask("Rewinding"
		checkoutCommit(upstreamCommit);
		monitor.endTask();
		return null;
	}

	private void checkParameters() throws WrongRepositoryStateException {
		if (this.operation != Operation.BEGIN) {
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
		} else
			switch (repo.getRepositoryState()) {
			case SAFE:
				if (this.upstreamCommit == null)
					throw new JGitInternalException(MessageFormat
							.format(JGitText.get().missingRequiredParameter
									"upstream"));
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
		FileOutputStream fos = new FileOutputStream(file);
		try {
			fos.write(content.getBytes("UTF-8"));
		} finally {
			fos.close();
		}
	}

	private RebaseResult abort() throws IOException {
		try {
			String commitId = readFile(repo.getDirectory()
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
		return RawParseUtils
				.decode(IO.readFully(new File(directory
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
			refUpdate.setExpectedOldObjectId(head);
			refUpdate.setNewObjectId(commit);
			Result res = refUpdate.forceUpdate();
			switch (res) {
			case FAST_FORWARD:
			case NO_CHANGE:
			case FORCED:
				break;
			default:
				throw new IOException("Could not rewind to upstream commit");
			}
		} finally {
			monitor.endTask();
		}
	}

	public RebaseCommand setUpstream(RevCommit upstream) {
		this.upstreamCommit = upstream;
		return this;
	}

	public RebaseCommand setUpstream(String upstream)
			throws RefNotFoundException {
		try {
			ObjectId upstreamId = repo.resolve(upstream);
			if (upstreamId == null)
				throw new RefNotFoundException(MessageFormat.format(JGitText
						.get().refNotResolved
			upstreamCommit = walk.parseCommit(repo.resolve(upstream));
			return this;
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		}
	}

	public RebaseCommand setAbort() {
		this.operation = Operation.ABORT;
		return this;
	}

	public RebaseCommand setSkip() {
		this.operation = Operation.SKIP;
		return this;
	}

	public RebaseCommand setContinue() {
		this.operation = Operation.CONTINUE;
		return this;
	}

	public RebaseCommand setProgressMonitor(ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}
}
