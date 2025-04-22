package org.eclipse.egit.ui.internal.reflog;

import org.eclipse.core.runtime.Assert;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ReflogCommand;
import org.eclipse.jgit.lib.Repository;

public class ReflogViewContentProvider implements ITreeContentProvider {

	public static class ReflogInput {

		private final Repository repository;

		private final String ref;

		public ReflogInput(Repository repository, String ref) {
			Assert.isNotNull(repository, "Repository cannot be null"); //$NON-NLS-1$
			Assert.isNotNull(ref, "Ref cannot be null"); //$NON-NLS-1$
			this.repository = repository;
			this.ref = ref;
		}

		public Repository getRepository() {
			return repository;
		}

		public String getRef() {
			return ref;
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof ReflogInput) {
			ReflogInput input = (ReflogInput) inputElement;
			try (Git git = new Git(input.repository)) {
				ReflogCommand command = git.reflog();
				command.setRef(input.ref);
				return command.call().toArray();
			} catch (Exception e) {
				Activator.logError("Error running reflog command", e); //$NON-NLS-1$
			}
		}
		return new Object[0];
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}
}
