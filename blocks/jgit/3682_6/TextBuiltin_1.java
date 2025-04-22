
package org.eclipse.jgit.pgm;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

@Command(usage = "usage_Status"
class Status extends TextBuiltin {

	protected final String lineFormat = CLIText.get().lineFormat;

	protected final String statusFileListFormat = CLIText.get().statusFileListFormat;

	protected final String statusFileListFormatWithPrefix = CLIText.get().statusFileListFormatWithPrefix;

	@Override
	protected void run() throws Exception {
		final Ref head = db.getRef(Constants.HEAD);
		boolean firstHeader = true;
		if (head != null && head.isSymbolic()) {
			String branch = Repository.shortenRefName(head.getLeaf().getName());
			out.println(CLIText.formatLine(
					MessageFormat.format(CLIText.get().onBranch
		} else
			out.println(CLIText.formatLine(CLIText.get().notOnAnyBranch));
		org.eclipse.jgit.api.Status status = new Git(db).status().call();
		Collection<String> added = status.getAdded();
		Collection<String> changed = status.getChanged();
		Collection<String> removed = status.getRemoved();
		Collection<String> modified = status.getModified();
		Collection<String> missing = status.getMissing();
		Collection<String> untracked = status.getUntracked();
		Collection<String> unmerged = status.getConflicting();
		Collection<String> toBeCommitted = new ArrayList<String>(added);
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
		Collection<String> notStagedForCommit = new ArrayList<String>(modified);
		notStagedForCommit.addAll(missing);
		int nbNotStagedForCommit = notStagedForCommit.size();
		if (nbNotStagedForCommit > 0) {
			if (!firstHeader)
				printSectionHeader("");
			printSectionHeader(CLIText.get().changesNotStagedForCommit);
			printList(CLIText.get().statusModified
					CLIText.get().statusRemoved
					modified
			firstHeader = false;
		}
		int nbUnmerged = unmerged.size();
		if (nbUnmerged > 0) {
			if (!firstHeader)
				printSectionHeader("");
			printSectionHeader(CLIText.get().unmergedPaths);
			printList(unmerged);
			firstHeader = false;
		}
		int nbUntracked = untracked.size();
		if (nbUntracked > 0) {
			if (!firstHeader)
				printSectionHeader("");
			printSectionHeader(CLIText.get().untrackedFiles);
			printList(untracked);
		}
	}

	protected void printSectionHeader(String pattern
		out.println(CLIText.formatLine(MessageFormat.format(pattern
		if (!pattern.equals(""))
			out.println(CLIText.formatLine(""));
		out.flush();
	}

	protected int printList(Collection<String> list) {
		if (!list.isEmpty()) {
			List<String> sortedList = new ArrayList<String>(list);
			java.util.Collections.sort(sortedList);
			for (String filename : sortedList) {
				out.println(CLIText.formatLine(String.format(
						statusFileListFormat
			}
			out.flush();
			return list.size();
		} else
			return 0;
	}

	protected int printList(String status1
			Collection<String> list
			Collection<String> set2
			@SuppressWarnings("unused") Collection<String> set3) {
		List<String> sortedList = new ArrayList<String>(list);
		java.util.Collections.sort(sortedList);
		for (String filename : sortedList) {
			String prefix;
			if (set1.contains(filename))
				prefix = status1;
			else if (set2.contains(filename))
				prefix = status2;
			else
				prefix = status3;
			out.println(CLIText.formatLine(String.format(
					statusFileListFormatWithPrefix
			out.flush();
		}
		return list.size();
	}
}
