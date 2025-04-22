package org.eclipse.egit.ui.internal.search;

import java.text.MessageFormat;

import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class CommitSearchResult extends AbstractTextSearchResult implements
		IWorkbenchAdapter {

	private ISearchQuery query;

	public CommitSearchResult(ISearchQuery query) {
		this.query = query;
	}

	public CommitSearchResult addResult(RepositoryCommit commit) {
		if (commit != null)
			addMatch(new CommitMatch(commit));
		return this;
	}

	@Override
	public String getLabel() {
		int matches = getMatchCount();
		String pattern = ((CommitSearchQuery) query).getPattern();
		if (matches != 1)
			return MessageFormat.format(UIText.CommitSearchResult_LabelPlural,
					pattern, Integer.valueOf(matches));
		else
			return MessageFormat.format(UIText.CommitSearchResult_LabelSingle,
					pattern);
	}

	@Override
	public String getTooltip() {
		return getLabel();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public ISearchQuery getQuery() {
		return this.query;
	}

	@Override
	public IEditorMatchAdapter getEditorMatchAdapter() {
		return null;
	}

	@Override
	public IFileMatchAdapter getFileMatchAdapter() {
		return null;
	}

	@Override
	public Object[] getChildren(Object o) {
		return getElements();
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	@Override
	public String getLabel(Object o) {
		return getLabel();
	}

	@Override
	public Object getParent(Object o) {
		return null;
	}

}
