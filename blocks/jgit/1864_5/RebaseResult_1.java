package org.eclipse.jgit.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.RebaseResult.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
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

	private final File rebaseDir;

	protected RebaseCommand(Repository repo) {
		super(repo);
		walk = new RevWalk(repo);
		rebaseDir = new File(repo.getDirectory()
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

			List<Step> steps = loadSteps();
			ObjectReader or = repo.newObjectReader();
			int stepsToPop = 0;

			for (Step step : steps) {
				if (step.action != Action.PICK)
					continue;
				Collection<ObjectId> ids = or.resolve(step.commit);
				if (ids.size() != 1)
					throw new JGitInternalException(
							"Could not resolve uniquely the abbreviated object ID");
				RevCommit commitToPick = walk
						.parseCommit(ids.iterator().next());
				monitor.beginTask("Applying " + commitToPick.getShortMessage()
						ProgressMonitor.UNKNOWN);
				newHead = new Git(repo).cherryPick().include(commitToPick)
						.call();
				monitor.endTask();
				if (newHead == null) {
					popSteps(stepsToPop);
					return new RebaseResult(Status.STOPPED);
				}
				stepsToPop++;
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

	private void popSteps(int numSteps) throws IOException {
		if (numSteps == 0)
			return;
		List<String> lines = new ArrayList<String>();
		File file = new File(rebaseDir
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)
		int popped = 0;
		try {
			while (popped < numSteps) {
				String popCandidate = br.readLine();
				if (popCandidate == null)
					break;
				int spaceIndex = popCandidate.indexOf(' ');
				boolean pop = false;
				if (spaceIndex >= 0) {
					String actionToken = popCandidate.substring(0
					pop = Action.parse(actionToken) != null;
				}
				if (pop)
					popped++;
				else
					lines.add(popCandidate);
			}
			String readLine = br.readLine();
			while (readLine != null) {
				lines.add(readLine);
				readLine = br.readLine();
			}
		} finally {
			br.close();
		}

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file)
		try {
			for (String writeLine : lines) {
				bw.write(writeLine);
				bw.newLine();
			}
		} finally {
			bw.close();
		}
	}

	private RebaseResult initFilesAndRewind() throws RefNotFoundException
			IOException

		List<RevCommit> cherryPickList = new ArrayList<RevCommit>();

		Ref head = repo.getRef(Constants.HEAD);
		if (head == null || head.getObjectId() == null)
			throw new RefNotFoundException(MessageFormat.format(
					JGitText.get().refNotResolved

		String headName;
		if (head.isSymbolic())
			headName = head.getTarget().getName();
		else
			headName = "detached HEAD";
		ObjectId headId = head.getObjectId();
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
			cherryPickList.add(commit);
		}

		if (cherryPickList.isEmpty())
			return RebaseResult.UP_TO_DATE_RESULT;

		Collections.reverse(cherryPickList);
		rebaseDir.mkdir();

		createFile(repo.getDirectory()
		createFile(rebaseDir
		createFile(rebaseDir
		createFile(rebaseDir
		BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(new File(rebaseDir
				"UTF-8"));
		fw.write("# Created by EGit");
		fw.newLine();
		try {
			StringBuilder sb = new StringBuilder();
			ObjectReader reader = walk.getObjectReader();
			for (RevCommit commit : cherryPickList) {
				sb.setLength(0);
				sb.append(Action.PICK.toToken());
				sb.append(" ");
				sb.append(reader.abbreviate(commit).name());
				sb.append(" ");
				sb.append(commit.getShortMessage());
				fw.write(sb.toString());
				fw.newLine();
			}
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
		File[] children = fileOrFolder.listFiles();
		if (children != null) {
			for (File child : children)
				deleteRecursive(child);
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
					repo.lockDirCache()
			dco.setFailOnConflict(true);
			dco.checkout();
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
			walk.release();
			monitor.endTask();
		}
	}

	private List<Step> loadSteps() throws IOException {
		byte[] buf = IO.readFully(new File(rebaseDir
		int ptr = 0;
		int tokenBegin = 0;
		ArrayList<Step> r = new ArrayList<Step>();
		while (ptr < buf.length) {
			tokenBegin = ptr;
			ptr = RawParseUtils.nextLF(buf
			int nextSpace = 0;
			int tokenCount = 0;
			Step current = null;
			while (tokenCount < 3 && nextSpace < ptr) {
				switch (tokenCount) {
				case 0:
					nextSpace = RawParseUtils.next(buf
					String actionToken = new String(buf
							- tokenBegin - 1);
					tokenBegin = nextSpace;
					Action action = Action.parse(actionToken);
					if (action != null)
						current = new Step(Action.parse(actionToken));
					break;
				case 1:
					if (current == null)
						break;
					nextSpace = RawParseUtils.next(buf
					String commitToken = new String(buf
							- tokenBegin - 1);
					tokenBegin = nextSpace;
					current.commit = AbbreviatedObjectId
							.fromString(commitToken);
					break;
				case 2:
					if (current == null)
						break;
					nextSpace = ptr;
					int length = ptr - tokenBegin;
					current.shortMessage = new byte[length];
					System.arraycopy(buf
							length);
					r.add(current);
					break;
				}
				tokenCount++;
			}
		}
		return r;
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

	static enum Action {

		private final String token;

		private Action(String token) {
			this.token = token;
		}

		public String toToken() {
			return this.token;
		}

		static Action parse(String token) {
			if (token.equals("pick") || token.equals("p"))
				return PICK;
			return null;
		}
	}

	static class Step {
		Action action;

		AbbreviatedObjectId commit;

		byte[] shortMessage;

		Step(Action action) {
			this.action = action;
		}
	}
}
