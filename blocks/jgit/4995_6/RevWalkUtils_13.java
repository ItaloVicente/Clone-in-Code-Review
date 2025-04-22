package org.eclipse.jgit.merge;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

public class SquashMessageFormatter {
	public String format(List<RevCommit> squashedCommits
		StringBuilder sb = new StringBuilder();
		sb.append("Squashed commit of the following:\n");
		for (RevCommit c : squashedCommits) {
			sb.append("\ncommit ");
			sb.append(c.getName());
			sb.append("\n");
			sb.append(toString(c.getAuthorIdent()));
			sb.append("\n\t");
			sb.append(c.getShortMessage());
			sb.append("\n");
		}
		return sb.toString();
	}

	private String toString(PersonIdent author) {
		final StringBuilder a = new StringBuilder();
		final SimpleDateFormat dtfmt;
		dtfmt = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z"
		dtfmt.setTimeZone(author.getTimeZone());

		a.append("Author: ");
		a.append(author.getName());
		a.append(" <");
		a.append(author.getEmailAddress());
		a.append(">\n");
		a.append("Date:   ");
		a.append(dtfmt.format(author.getWhen()));
		a.append("\n");

		return a.toString();
	}
}
