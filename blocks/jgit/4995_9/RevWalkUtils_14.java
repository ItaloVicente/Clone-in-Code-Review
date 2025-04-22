package org.eclipse.jgit.merge;

import java.util.List;

import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.GitDateFormatter;
import org.eclipse.jgit.util.GitDateFormatter.Format;

public class SquashMessageFormatter {

	private GitDateFormatter dateFormatter;

	public SquashMessageFormatter() {
		dateFormatter = new GitDateFormatter(Format.DEFAULT);
	}
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

		a.append("Author: ");
		a.append(author.getName());
		a.append(" <");
		a.append(author.getEmailAddress());
		a.append(">\n");
		a.append("Date:   ");
		a.append(dateFormatter.formatDate(author));
		a.append("\n");

		return a.toString();
	}
}
