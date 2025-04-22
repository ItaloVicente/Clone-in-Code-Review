package org.eclipse.jgit.merge;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.ChangeIdUtil;
import org.eclipse.jgit.util.StringUtils;

public class MergeMessageFormatter {
	public String format(List<Ref> refsToMerge
		StringBuilder sb = new StringBuilder();

		List<String> branches = new ArrayList<>();
		List<String> remoteBranches = new ArrayList<>();
		List<String> tags = new ArrayList<>();
		List<String> commits = new ArrayList<>();
		List<String> others = new ArrayList<>();
		for (Ref ref : refsToMerge) {
			if (ref.getName().startsWith(Constants.R_HEADS)) {
			} else if (ref.getName().startsWith(Constants.R_REMOTES)) {
			} else if (ref.getName().startsWith(Constants.R_TAGS)) {
			} else {
				ObjectId objectId = ref.getObjectId();
				if (objectId != null && ref.getName().equals(objectId.getName())) {
				} else {
					others.add(ref.getName());
				}
			}
		}

		List<String> listings = new ArrayList<>();

		if (!branches.isEmpty())
			listings.add(joinNames(branches

		if (!remoteBranches.isEmpty())
			listings.add(joinNames(remoteBranches

		if (!tags.isEmpty())
			listings.add(joinNames(tags

		if (!commits.isEmpty())
			listings.add(joinNames(commits

		if (!others.isEmpty())
			listings.add(StringUtils.join(others

		sb.append(StringUtils.join(listings

		String targetName = target.getLeaf().getName();
		if (!targetName.equals(Constants.R_HEADS + Constants.MASTER)) {
			String targetShortName = Repository.shortenRefName(targetName);
		}

		return sb.toString();
	}

	public String formatWithConflicts(String message
			List<String> conflictingPaths) {
		StringBuilder sb = new StringBuilder();
		int firstFooterLine = ChangeIdUtil.indexOfFirstFooterLine(lines);
		for (int i = 0; i < firstFooterLine; i++)
			sb.append(lines[i]).append('\n');
		if (firstFooterLine == lines.length && message.length() != 0)
			sb.append('\n');
		addConflictsMessage(conflictingPaths
		if (firstFooterLine < lines.length)
			sb.append('\n');
		for (int i = firstFooterLine; i < lines.length; i++)
			sb.append(lines[i]).append('\n');
		return sb.toString();
	}

	private static void addConflictsMessage(List<String> conflictingPaths
			StringBuilder sb) {
		for (String conflictingPath : conflictingPaths)
			sb.append('\t').append(conflictingPath).append('\n');
	}

	private static String joinNames(List<String> names
			String plural) {
		if (names.size() == 1)
		else
			return plural + " " + StringUtils.join(names
	}
}
