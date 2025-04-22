package org.eclipse.egit.ui.internal.blame;

import java.util.Date;

import org.eclipse.jface.text.revisions.Revision;
import org.eclipse.jface.text.source.LineRange;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.swt.graphics.RGB;

public class BlameRevision extends Revision {

	private int start;

	private int lines = 1;

	private RevCommit commit;

	private Repository repository;

	public Object getHoverInfo() {
		return this;
	}

	public RGB getColor() {
		return AuthorColors.getDefault().getCommitterRGB(getAuthor());
	}

	public String getId() {
		return commit.abbreviate(7).name();
	}

	public Date getDate() {
		return commit.getAuthorIdent().getWhen();
	}

	public BlameRevision register() {
		addRange(new LineRange(start, lines));
		return this;
	}

	public BlameRevision addLine() {
		lines++;
		return this;
	}

	public BlameRevision reset(int number) {
		start = number;
		lines = 1;
		return this;
	}

	public BlameRevision setCommit(RevCommit commit) {
		this.commit = commit;
		return this;
	}

	public RevCommit getCommit() {
		return this.commit;
	}

	public BlameRevision setRepository(Repository repository) {
		this.repository = repository;
		return this;
	}

	public Repository getRepository() {
		return this.repository;
	}

	public String getAuthor() {
		return commit.getAuthorIdent().getName();
	}

}
