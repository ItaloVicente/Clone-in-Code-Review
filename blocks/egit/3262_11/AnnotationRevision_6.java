package org.eclipse.egit.ui.internal.blame;

import java.util.Date;

import org.eclipse.jface.text.revisions.Revision;
import org.eclipse.jface.text.source.LineRange;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.graphics.RGB;

public class AnnotationRevision extends Revision {

	private int start;

	private int lines = 1;

	private org.eclipse.jgit.blame.Revision revision;

	private Repository repository;

	public Object getHoverInfo() {
		return this;
	}

	public RGB getColor() {
		return AuthorColors.getDefault().getCommitterRGB(getAuthor());
	}

	public String getId() {
		return revision.getCommit().abbreviate(7).name();
	}

	public Date getDate() {
		return revision.getCommit().getAuthorIdent().getWhen();
	}

	public AnnotationRevision register() {
		addRange(new LineRange(start, lines));
		return this;
	}

	public AnnotationRevision addLine() {
		lines++;
		return this;
	}

	public AnnotationRevision reset(int number) {
		start = number;
		lines = 1;
		return this;
	}

	public AnnotationRevision setRevision(
			org.eclipse.jgit.blame.Revision revision) {
		this.revision = revision;
		return this;
	}

	public org.eclipse.jgit.blame.Revision getRevision() {
		return this.revision;
	}

	public AnnotationRevision setRepository(Repository repository) {
		this.repository = repository;
		return this;
	}

	public Repository getRepository() {
		return this.repository;
	}

	public String getAuthor() {
		return revision.getCommit().getAuthorIdent().getName();
	}

}
