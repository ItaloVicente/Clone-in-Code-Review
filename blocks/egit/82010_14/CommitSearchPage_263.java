package org.eclipse.egit.ui.internal.search;

import java.text.MessageFormat;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.PreferenceBasedDateFormatter;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class CommitResultLabelProvider extends WorkbenchLabelProvider {

	private int layout;

	private final IPropertyChangeListener uiPrefsListener;

	private PreferenceBasedDateFormatter dateFormatter;

	public CommitResultLabelProvider(int layout) {
		this.layout = layout;
		dateFormatter = PreferenceBasedDateFormatter.create();
		uiPrefsListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				String property = event.getProperty();
				if (UIPreferences.DATE_FORMAT.equals(property)
						|| UIPreferences.DATE_FORMAT_CHOICE.equals(property)) {
					dateFormatter = PreferenceBasedDateFormatter.create();
					fireLabelProviderChanged(new LabelProviderChangedEvent(
							CommitResultLabelProvider.this));
				}
			}
		};
		Activator.getDefault().getPreferenceStore()
				.addPropertyChangeListener(uiPrefsListener);
	}

	@Override
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
						author.getName(), dateFormatter.formatDate(author)),
						StyledString.QUALIFIER_STYLER);

			if (layout == AbstractTextSearchViewPage.FLAG_LAYOUT_FLAT)
				styled.append(MessageFormat.format(
						UIText.CommitResultLabelProvider_SectionRepository,
						commit.getRepositoryName()),
						StyledString.DECORATIONS_STYLER);
		} else if (element instanceof RepositoryMatch) {
			RepositoryMatch repository = (RepositoryMatch) element;
			styled.append(repository.getLabel(repository));
			styled.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			styled.append(repository.getRepository().getDirectory()
					.getAbsolutePath(), StyledString.QUALIFIER_STYLER);
			styled.append(MessageFormat.format(" ({0})", //$NON-NLS-1$
					Integer.valueOf(repository.getMatchCount())),
					StyledString.COUNTER_STYLER);
		}
		return styled;
	}

	@Override
	public void dispose() {
		Activator.getDefault().getPreferenceStore()
				.removePropertyChangeListener(uiPrefsListener);
		super.dispose();
	}
}
