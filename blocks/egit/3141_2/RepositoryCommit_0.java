package org.eclipse.egit.ui.internal.commit;

import java.text.MessageFormat;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.egit.ui.UIIcons;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class CommitEditorInput extends PlatformObject implements IEditorInput {

	private RepositoryCommit commit;

	public CommitEditorInput(RepositoryCommit commit) {
		Assert.isNotNull(commit, "Commit cannot be null"); //$NON-NLS-1$
		this.commit = commit;
	}

	public Object getAdapter(Class adapter) {
		if (RepositoryCommit.class == adapter)
			return this.commit;

		if (RevCommit.class == adapter)
			return this.commit.getRevCommit();

		if (Repository.class == adapter)
			return this.commit.getRepository();

		return super.getAdapter(adapter);
	}

	public boolean exists() {
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return UIIcons.CHANGESET;
	}

	public String getName() {
		return MessageFormat.format(UIText.CommitEditorInput_Name,
				this.commit.abbreviate(), this.commit.getRepositoryName());
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return MessageFormat.format(UIText.CommitEditorInput_ToolTip,
				this.commit.getRevCommit().name(),
				this.commit.getRepositoryName());
	}

}
