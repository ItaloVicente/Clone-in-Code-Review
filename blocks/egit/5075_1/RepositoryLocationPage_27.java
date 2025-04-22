package org.eclipse.egit.ui.internal.clone;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.ui.UIIcons;
import org.eclipse.egit.ui.internal.clone.GitCloneSourceProviderExtension.CloneSourceProvider;
import org.eclipse.egit.ui.internal.provisional.wizards.RepositoryServerInfo;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

class RepositoryLocationLabelProvider extends LabelProvider {

	private Image repoImage = UIIcons.CLONEGIT.createImage();

	private List<Image> images = new ArrayList<Image>();

	@Override
	public String getText(Object element) {
		if (element instanceof CloneSourceProvider)
			return ((CloneSourceProvider) element).getLabel();
		else if (element instanceof RepositoryServerInfo)
			return ((RepositoryServerInfo) element).getLabel();
		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof CloneSourceProvider) {
			Image image = ((CloneSourceProvider) element).getImage().createImage();
			images.add(image);
			return image;
		}
		else if (element instanceof RepositoryServerInfo)
			return repoImage;
		return null;
	}

	public void dispose() {
		repoImage.dispose();
		for (Image image  : images)
			image.dispose();
	}

}
