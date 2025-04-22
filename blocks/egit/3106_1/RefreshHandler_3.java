package org.eclipse.egit.ui.internal.staging;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.internal.actions.CompareWithCommitActionHandler;
import org.eclipse.egit.ui.internal.actions.CompareWithIndexActionHandler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenCommand extends AbstractHandler {
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final TreeItem selectedItem = getSelectedNodes(event).get(0);
		final IFile file = (IFile) selectedItem.getData();
		TreeItem parentItem = selectedItem.getParentItem();
		ResourceNode resourceNode = (ResourceNode) parentItem
				.getData();
		StatusNode data = resourceNode.getRoot();

		if (data instanceof StagedNode || data instanceof UntrackedNode) {
			return new CompareWithCommitActionHandler() {
				@Override
				protected IResource[] getSelectedResources(ExecutionEvent event) {
					return new IResource[] { file };
				}
			}.execute(event);
		} else {
			return new CompareWithIndexActionHandler() {
				@Override
				protected IResource[] getSelectedResources() {
					return new IResource[] { file };
				}
			}.execute(event);
		}
	}

	@SuppressWarnings("unchecked")
	public List<TreeItem> getSelectedNodes(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		return selection.toList();
	}
}
