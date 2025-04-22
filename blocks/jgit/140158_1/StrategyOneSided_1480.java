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
		for (RevCommit c : squashedCommits) {
			sb.append(c.getName());
			sb.append(toString(c.getAuthorIdent()));
			sb.append(c.getShortMessage());
		}
		return sb.toString();
	}

	private String toString(PersonIdent author) {
		final StringBuilder a = new StringBuilder();

		a.append(author.getName());
		a.append(author.getEmailAddress());
		a.append(dateFormatter.formatDate(author));

		return a.toString();
	}
}
