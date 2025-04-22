
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.StatusCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.IndexDiff.StageState;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.opt.UntrackedFilesHandler;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.RestOfArgumentsHandler;

@Command(usage = "usage_Status"
class Status extends TextBuiltin {

	protected final String statusFileListFormat = CLIText.get().statusFileListFormat;

	protected final String statusFileListFormatWithPrefix = CLIText.get().statusFileListFormatWithPrefix;

	protected final String statusFileListFormatUnmerged = CLIText.get().statusFileListFormatUnmerged;

	@Option(name = "--porcelain"
	protected boolean porcelain;

	@Option(name = "--untracked-files"

	@Argument(required = false
	@Option(name = "--"
	protected List<String> filterPaths;

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			StatusCommand statusCommand = git.status();
			if (filterPaths != null && filterPaths.size() > 0) {
				for (String path : filterPaths) {
					statusCommand.addPath(path);
				}
			}
			org.eclipse.jgit.api.Status status = statusCommand.call();
			printStatus(status);
		} catch (GitAPIException | NoWorkTreeException | IOException e) {
			throw die(e.getMessage()
		}
	}

	private void printStatus(org.eclipse.jgit.api.Status status)
			throws IOException {
		if (porcelain)
			printPorcelainStatus(status);
		else
			printLongStatus(status);
	}

	private void printPorcelainStatus(org.eclipse.jgit.api.Status status)
			throws IOException {

		Collection<String> added = status.getAdded();
		Collection<String> changed = status.getChanged();
		Collection<String> removed = status.getRemoved();
		Collection<String> modified = status.getModified();
		Collection<String> missing = status.getMissing();
		Map<String

		TreeSet<String> sorted = new TreeSet<>();
		sorted.addAll(added);
		sorted.addAll(changed);
		sorted.addAll(removed);
		sorted.addAll(modified);
		sorted.addAll(missing);
		sorted.addAll(conflicting.keySet());

		for (String path : sorted) {
			char x = ' ';
			char y = ' ';

			if (added.contains(path))
				x = 'A';
			else if (changed.contains(path))
				x = 'M';
			else if (removed.contains(path))
				x = 'D';

			if (modified.contains(path))
				y = 'M';
			else if (missing.contains(path))
				y = 'D';

			if (conflicting.containsKey(path)) {
				StageState stageState = conflicting.get(path);

				switch (stageState) {
				case BOTH_DELETED:
					x = 'D';
					y = 'D';
					break;
				case ADDED_BY_US:
					x = 'A';
					y = 'U';
					break;
				case DELETED_BY_THEM:
					x = 'U';
					y = 'D';
					break;
				case ADDED_BY_THEM:
					x = 'U';
					y = 'A';
					break;
				case DELETED_BY_US:
					x = 'D';
					y = 'U';
					break;
				case BOTH_ADDED:
					x = 'A';
					y = 'A';
					break;
				case BOTH_MODIFIED:
					x = 'U';
					y = 'U';
					break;
				default:
							+ stageState);
				}
			}

			printPorcelainLine(x
		}

			TreeSet<String> untracked = new TreeSet<>(
					status.getUntracked());
			for (String path : untracked)
				printPorcelainLine('?'
		}
	}

	private void printPorcelainLine(char x
			throws IOException {
		StringBuilder lineBuilder = new StringBuilder();
		lineBuilder.append(x).append(y).append(' ').append(path);
		outw.println(lineBuilder.toString());
	}

	private void printLongStatus(org.eclipse.jgit.api.Status status)
			throws IOException {
		final Ref head = db.exactRef(Constants.HEAD);
		if (head != null && head.isSymbolic()) {
			String branch = Repository.shortenRefName(head.getLeaf().getName());
			outw.println(CLIText.formatLine(MessageFormat.format(
					CLIText.get().onBranch
		} else
			outw.println(CLIText.formatLine(CLIText.get().notOnAnyBranch));

		boolean firstHeader = true;

		Collection<String> added = status.getAdded();
		Collection<String> changed = status.getChanged();
		Collection<String> removed = status.getRemoved();
		Collection<String> modified = status.getModified();
		Collection<String> missing = status.getMissing();
		Collection<String> untracked = status.getUntracked();
		Map<String
				.getConflictingStageState();
		Collection<String> toBeCommitted = new ArrayList<>(added);
		toBeCommitted.addAll(changed);
		toBeCommitted.addAll(removed);
		int nbToBeCommitted = toBeCommitted.size();
		if (nbToBeCommitted > 0) {
			printSectionHeader(CLIText.get().changesToBeCommitted);
			printList(CLIText.get().statusNewFile
					CLIText.get().statusModified
					toBeCommitted
			firstHeader = false;
		}
		Collection<String> notStagedForCommit = new ArrayList<>(modified);
		notStagedForCommit.addAll(missing);
		int nbNotStagedForCommit = notStagedForCommit.size();
		if (nbNotStagedForCommit > 0) {
			if (!firstHeader)
			printSectionHeader(CLIText.get().changesNotStagedForCommit);
			printList(CLIText.get().statusModified
					CLIText.get().statusRemoved
					modified
			firstHeader = false;
		}
		int nbUnmerged = unmergedStates.size();
		if (nbUnmerged > 0) {
			if (!firstHeader)
			printSectionHeader(CLIText.get().unmergedPaths);
			printUnmerged(unmergedStates);
			firstHeader = false;
		}
		int nbUntracked = untracked.size();
			if (!firstHeader)
			printSectionHeader(CLIText.get().untrackedFiles);
			printList(untracked);
		}
	}

	protected void printSectionHeader(String pattern
			throws IOException {
		if (!porcelain) {
			outw.println(CLIText.formatLine(MessageFormat.format(pattern
					arguments)));
			outw.flush();
		}
	}

	protected int printList(Collection<String> list) throws IOException {
		if (!list.isEmpty()) {
			List<String> sortedList = new ArrayList<>(list);
			java.util.Collections.sort(sortedList);
			for (String filename : sortedList) {
				outw.println(CLIText.formatLine(String.format(
						statusFileListFormat
			}
			outw.flush();
			return list.size();
		} else
			return 0;
	}

	protected int printList(String status1
			Collection<String> list
			Collection<String> set2
			Collection<String> set3)
			throws IOException {
		List<String> sortedList = new ArrayList<>(list);
		java.util.Collections.sort(sortedList);
		for (String filename : sortedList) {
			String prefix;
			if (set1.contains(filename))
				prefix = status1;
			else if (set2.contains(filename))
				prefix = status2;
			else
				prefix = status3;
			outw.println(CLIText.formatLine(String.format(
					statusFileListFormatWithPrefix
			outw.flush();
		}
		return list.size();
	}

	private void printUnmerged(Map<String
			throws IOException {
		List<String> paths = new ArrayList<>(unmergedStates.keySet());
		Collections.sort(paths);
		for (String path : paths) {
			StageState state = unmergedStates.get(path);
			String stateDescription = getStageStateDescription(state);
			outw.println(CLIText.formatLine(String.format(
					statusFileListFormatUnmerged
			outw.flush();
		}
	}

	private static String getStageStateDescription(StageState stageState) {
		CLIText text = CLIText.get();
		switch (stageState) {
		case BOTH_DELETED:
			return text.statusBothDeleted;
		case ADDED_BY_US:
			return text.statusAddedByUs;
		case DELETED_BY_THEM:
			return text.statusDeletedByThem;
		case ADDED_BY_THEM:
			return text.statusAddedByThem;
		case DELETED_BY_US:
			return text.statusDeletedByUs;
		case BOTH_ADDED:
			return text.statusBothAdded;
		case BOTH_MODIFIED:
			return text.statusBothModified;
		default:
					+ stageState);
		}
	}
}
