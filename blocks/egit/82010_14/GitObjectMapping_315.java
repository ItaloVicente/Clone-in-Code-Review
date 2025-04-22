package org.eclipse.egit.ui.internal.synchronize.mapping;

import org.eclipse.egit.ui.internal.synchronize.model.GitModelObject;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelRepository;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelRoot;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class GitModelWorkbenchAdapter implements IWorkbenchAdapter {

	private GitChangeSetLabelProvider gitLabelProvider;

	@Override
	public Object[] getChildren(Object o) {
		if (o instanceof GitModelObject)
			return ((GitModelObject) o).getChildren();

		if (o instanceof GitModelRoot)
			return ((GitModelRoot) o).getChildren();

		return null;
	}

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		if (gitLabelProvider == null)
			gitLabelProvider = new GitChangeSetLabelProvider();

		Image image = gitLabelProvider.getImage(object);

		return ImageDescriptor.createFromImage(image);
	}

	@Override
	public String getLabel(Object o) {
		if (o instanceof GitModelObject)
			return ((GitModelObject) o).getName();

		return null;
	}

	@Override
	public Object getParent(Object o) {
		if (o instanceof GitModelRepository)
			return null; // repository node has no parent

		if (o instanceof GitModelObject)
			return ((GitModelObject) o).getParent();

		return null;
	}

}
