package org.eclipse.egit.ui.internal.merge;

import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.contentmergeviewer.ContentMergeViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

public abstract class CompareEditorInputViewerAction extends Action
		implements IWorkbenchAction, IPropertyChangeListener {

	private final CompareEditorInput comparison;

	private ContentMergeViewer viewer;

	protected CompareEditorInputViewerAction(String name,
			CompareEditorInput comparison) {
		super(name);
		this.comparison = comparison;
		comparison.getCompareConfiguration().addPropertyChangeListener(this);
	}

	protected CompareEditorInputViewerAction(String name, int style,
			CompareEditorInput comparison) {
		super(name, style);
		this.comparison = comparison;
		comparison.getCompareConfiguration().addPropertyChangeListener(this);
	}

	@Override
	public void dispose() {
		getInput().getCompareConfiguration().removePropertyChangeListener(this);
	}

	protected CompareEditorInput getInput() {
		return comparison;
	}

	public void setViewer(ContentMergeViewer viewer) {
		this.viewer = viewer;
		if (viewer == null) {
			super.setEnabled(false);
		}
	}

	protected ContentMergeViewer getViewer() {
		return viewer;
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled && viewer != null);
	}
}
