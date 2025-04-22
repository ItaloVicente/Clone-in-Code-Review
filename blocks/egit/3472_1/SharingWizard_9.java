package org.eclipse.egit.ui.internal.sharing;

import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.egit.ui.internal.repository.RepositoriesView;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.graphics.Image;

public class RepoComboLabelProvider extends BaseLabelProvider implements
		ILabelProvider {
	private RepositoryUtil util = Activator.getDefault().getRepositoryUtil();

	public Image getImage(Object element) {
		return null;
	}

	public String getText(Object element) {
		Repository repo = (Repository) element;
		String repoName = util.getRepositoryName(repo);
		return repoName + " - " + repo.getDirectory().getPath(); //$NON-NLS-1$
	}
}
