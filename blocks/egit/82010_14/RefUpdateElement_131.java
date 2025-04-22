package org.eclipse.egit.ui.internal.push;

import org.eclipse.egit.core.op.PushOperationResult;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.ui.model.WorkbenchContentProvider;

class RefUpdateContentProvider extends WorkbenchContentProvider {

	@Override
	public Object[] getElements(final Object element) {
		return element instanceof Object[] ? (Object[]) element : new Object[0];
	}

	@Override
	public Object[] getChildren(Object element) {
		if (element instanceof RepositoryCommit) {
			return ((RepositoryCommit) element).getDiffs();
		}
		return super.getChildren(element);
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof RepositoryCommit) {
			return true;
		}
		return super.hasChildren(element);
	}
}
