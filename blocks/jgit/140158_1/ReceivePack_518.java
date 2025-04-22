
package org.eclipse.jgit.pgm;

import static java.lang.Character.valueOf;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.RemoteRefUpdate.Status;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Push extends TextBuiltin {
	@Option(name = "--timeout"
	int timeout = -1;

	@Argument(index = 0
	private String remote = Constants.DEFAULT_REMOTE_NAME;

	@Argument(index = 1
	private List<RefSpec> refSpecs = new ArrayList<>();

	@Option(name = "--all")
	private boolean all;

	@Option(name = "--atomic")
	private boolean atomic;

	@Option(name = "--tags")
	private boolean tags;

	@Option(name = "--verbose"
	private boolean verbose = false;

	@Option(name = "--thin")
	private boolean thin = Transport.DEFAULT_PUSH_THIN;

	@Option(name = "--no-thin")
	void nothin(@SuppressWarnings("unused") final boolean ignored) {
		thin = false;
	}

	@Option(name = "--force"
	private boolean force;

	@Option(name = "--receive-pack"
	private String receivePack;

	@Option(name = "--dry-run")
	private boolean dryRun;

	@Option(name = "--push-option"
	private List<String> pushOptions = new ArrayList<>();

	private boolean shownURI;

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			PushCommand push = git.push();
			push.setDryRun(dryRun);
			push.setForce(force);
			push.setProgressMonitor(new TextProgressMonitor(errw));
			push.setReceivePack(receivePack);
			push.setRefSpecs(refSpecs);
			if (all) {
				push.setPushAll();
			}
			if (tags) {
				push.setPushTags();
			}
			push.setRemote(remote);
			push.setThin(thin);
			push.setAtomic(atomic);
			push.setTimeout(timeout);
			if (!pushOptions.isEmpty()) {
				push.setPushOptions(pushOptions);
			}
			Iterable<PushResult> results = push.call();
			for (PushResult result : results) {
				try (ObjectReader reader = db.newObjectReader()) {
					printPushResult(reader
				}
			}
		} catch (GitAPIException | IOException e) {
			throw die(e.getMessage()
		}
	}

	private void printPushResult(final ObjectReader reader
			final PushResult result) throws IOException {
		shownURI = false;
		boolean everythingUpToDate = true;

		for (RemoteRefUpdate rru : result.getRemoteUpdates()) {
			if (rru.getStatus() == Status.UP_TO_DATE) {
				if (verbose)
					printRefUpdateResult(reader
			} else
				everythingUpToDate = false;
		}

		for (RemoteRefUpdate rru : result.getRemoteUpdates()) {
			if (rru.getStatus() == Status.OK)
				printRefUpdateResult(reader
		}

		for (RemoteRefUpdate rru : result.getRemoteUpdates()) {
			if (rru.getStatus() != Status.OK
					&& rru.getStatus() != Status.UP_TO_DATE)
				printRefUpdateResult(reader
		}

		AbstractFetchCommand.showRemoteMessages(errw
		if (everythingUpToDate)
			outw.println(CLIText.get().everythingUpToDate);
	}

	private void printRefUpdateResult(final ObjectReader reader
			final URIish uri
			throws IOException {
		if (!shownURI) {
			shownURI = true;
			outw.println(MessageFormat.format(CLIText.get().pushTo
		}

		final String remoteName = rru.getRemoteName();
		final String srcRef = rru.isDelete() ? null : rru.getSrcRef();

		switch (rru.getStatus()) {
		case OK:
			if (rru.isDelete())
				printUpdateLine('-'
			else {
				final Ref oldRef = result.getAdvertisedRef(remoteName);
				if (oldRef == null) {
					final String summary;
					if (remoteName.startsWith(Constants.R_TAGS))
					else
					printUpdateLine('*'
				} else {
					boolean fastForward = rru.isFastForward();
					final char flag = fastForward ? ' ' : '+';
					final String summary = safeAbbreviate(reader
							.getObjectId())
							+ safeAbbreviate(reader
					final String message = fastForward ? null : CLIText.get().forcedUpdate;
					printUpdateLine(flag
				}
			}
			break;

		case NON_EXISTING:
			printUpdateLine('X'
			break;

		case REJECTED_NODELETE:
			printUpdateLine('!'
					CLIText.get().remoteSideDoesNotSupportDeletingRefs);
			break;

		case REJECTED_NONFASTFORWARD:
			printUpdateLine('!'
					CLIText.get().nonFastForward);
			break;

		case REJECTED_REMOTE_CHANGED:
			final String message = MessageFormat.format(
					CLIText.get().remoteRefObjectChangedIsNotExpectedOne
					safeAbbreviate(reader
			printUpdateLine('!'
			break;

		case REJECTED_OTHER_REASON:
			printUpdateLine('!'
					.getMessage());
			break;

		case UP_TO_DATE:
			if (verbose)
				printUpdateLine('='
			break;

		case NOT_ATTEMPTED:
		case AWAITING_REPORT:
			printUpdateLine('?'
					remoteName
			break;
		}
	}

	private static String safeAbbreviate(ObjectReader reader
		try {
			return reader.abbreviate(id).name();
		} catch (IOException cannotAbbreviate) {
			return id.name();
		}
	}

	private void printUpdateLine(final char flag
			final String srcRef
			throws IOException {
		outw.format(" %c %-17s"

		if (srcRef != null)
			outw.format(" %s ->"
		outw.format(" %s"

		if (message != null)
			outw.format(" (%s)"

		outw.println();
	}
}
