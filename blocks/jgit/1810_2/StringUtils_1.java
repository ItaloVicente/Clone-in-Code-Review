package org.eclipse.jgit.merge;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.util.StringUtils;

public class MergeMessageFormatter {
	public String format(List<Ref> refsToMerge
		StringBuilder sb = new StringBuilder();
		sb.append("Merge ");

		List<String> branches = new ArrayList<String>();
		List<String> remoteBranches = new ArrayList<String>();
		List<String> tags = new ArrayList<String>();
		List<String> commits = new ArrayList<String>();
		List<String> others = new ArrayList<String>();
		for (Ref ref : refsToMerge) {
			if (ref.getName().startsWith(Constants.R_HEADS))
				branches.add("'" + getShortName(ref.getName()) + "'");

			else if (ref.getName().startsWith(Constants.R_REMOTES))
				remoteBranches.add("'" + getShortName(ref.getName()) + "'");

			else if (ref.getName().startsWith(Constants.R_TAGS))
				tags.add("'" + getShortName(ref.getName()) + "'");

			else if (ref.getName().equals(ref.getObjectId().getName()))
				commits.add("'" + ref.getName() + "'");

			else
				others.add(ref.getName());
		}

		List<String> listings = new ArrayList<String>();

		if (!branches.isEmpty())
			listings.add(joinNames(branches

		if (!remoteBranches.isEmpty())
			listings.add(joinNames(remoteBranches
					"remote branches"));

		if (!tags.isEmpty())
			listings.add(joinNames(tags

		if (!commits.isEmpty())
			listings.add(joinNames(commits

		if (!others.isEmpty())
			listings.add(StringUtils.join(others

		sb.append(StringUtils.join(listings

		if (!target.getName().equals(Constants.R_HEADS + Constants.MASTER)) {
			String targetShortName = getShortName(target.getName());
			sb.append(" into " + targetShortName);
		}

		return sb.toString();
	}

	private static String joinNames(List<String> names
			String plural) {
		if (names.size() == 1)
			return singular + " " + names.get(0);
		else
			return plural + " " + StringUtils.join(names
	}

	private static String getShortName(String refName) {
		int shortNameStart = refName.indexOf('/'
		return refName.substring(shortNameStart);
	}
}
