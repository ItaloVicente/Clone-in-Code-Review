package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.opt.GpgSignHandler;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.RawParseUtils;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Commit extends TextBuiltin {

	@Option(name = "--author"
	private String author;

	@Option(name = "--message"
	private String message;

	@Option(name = "--only"
	private boolean only;

	@Option(name = "--all"
	private boolean all;

	@Option(name = "--amend"
	private boolean amend;

	@Option(name = "--gpg-sign"
			"--no-gpg-sign" }
	private String gpgSigningKey;

	@Option(name = "--no-gpg-sign"
	private boolean noGpgSign;

	@Argument(metaVar = "metaVar_commitPaths"
	private List<String> paths = new ArrayList<>();

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			CommitCommand commitCmd = git.commit();
			if (author != null) {
				commitCmd.setAuthor(RawParseUtils.parsePersonIdent(author));
			}
			if (message != null) {
				commitCmd.setMessage(message);
			}
			if (noGpgSign) {
				commitCmd.setSign(Boolean.FALSE);
			} else if (gpgSigningKey != null) {
				commitCmd.setSign(Boolean.TRUE);
				if (!gpgSigningKey.equals(GpgSignHandler.DEFAULT)) {
					commitCmd.setSigningKey(gpgSigningKey);
				}
			}
			if (only && paths.isEmpty()) {
				throw die(CLIText.get().pathsRequired);
			}
			if (only && all) {
				throw die(CLIText.get().onlyOneCommitOptionAllowed);
			}
			if (!paths.isEmpty()) {
				for (String p : paths) {
					commitCmd.setOnly(p);
				}
			}
			commitCmd.setAmend(amend);
			commitCmd.setAll(all);
			Ref head = db.exactRef(Constants.HEAD);
			if (head == null) {
				throw die(CLIText.get().onBranchToBeBorn);
			}
			RevCommit commit;
			try {
				commit = commitCmd.call();
			} catch (JGitInternalException | GitAPIException e) {
				throw die(e.getMessage()
			}

			String branchName;
			if (!head.isSymbolic()) {
				branchName = CLIText.get().branchDetachedHEAD;
			} else {
				branchName = head.getTarget().getName();
				if (branchName.startsWith(Constants.R_HEADS)) {
					branchName = branchName
							.substring(Constants.R_HEADS.length());
				}
			}
					+ commit.getShortMessage());
		} catch (IOException e) {
			throw die(e.getMessage()
		}
	}
}
