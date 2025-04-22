package org.eclipse.egit.ui.internal.search;

import java.text.DateFormat;
import java.text.MessageFormat;

import org.eclipse.egit.ui.UIIcons;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.swt.graphics.Image;

public class CommitResultLabelProvider extends LabelProvider implements
		IStyledLabelProvider {

	private DateFormat dateFormat = DateFormat.getDateTimeInstance(
			DateFormat.MEDIUM, DateFormat.SHORT);

	private Image commitImage = UIIcons.CHANGESET.createImage();

	public void dispose() {
		this.commitImage.dispose();
		super.dispose();
	}

	public Image getImage(Object element) {
		return this.commitImage;
	}

	public StyledString getStyledText(Object element) {
		StyledString styled = new StyledString();
		if (element instanceof RepositoryCommit) {
			RepositoryCommit commit = (RepositoryCommit) element;
			RevCommit revCommit = commit.getRevCommit();

			styled.append(MessageFormat.format(
					UIText.CommitResultLabelProvider_SectionMessage,
					commit.abbreviate(), revCommit.getShortMessage()));

			PersonIdent author = revCommit.getAuthorIdent();
			if (author != null)
				styled.append(MessageFormat.format(
						UIText.CommitResultLabelProvider_SectionAuthor,
						author.getName(), dateFormat.format(author.getWhen())),
						StyledString.QUALIFIER_STYLER);

			styled.append(MessageFormat.format(
					UIText.CommitResultLabelProvider_SectionRepository,
					commit.getRepositoryName()),
					StyledString.DECORATIONS_STYLER);
		}
		return styled;
	}
}
