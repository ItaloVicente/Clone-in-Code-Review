package org.eclipse.egit.ui.internal.components;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class TitleAndImageDialog extends TitleAreaDialog {

	private ResourceManager resourceManager;

	private ImageDescriptor imageDescriptor;

	private boolean explicitImageSet;

	public TitleAndImageDialog(Shell parent, ImageDescriptor imageDescriptor) {
		super(parent);
		this.imageDescriptor = imageDescriptor;
	}

	@Override
	protected Control createContents(Composite parent) {
		if (imageDescriptor != null && !explicitImageSet) {
			super.setTitleImage(
					getResourceManager().createImage(imageDescriptor));
		}
		Control control = super.createContents(parent);
		control.addDisposeListener(event -> {
			if (resourceManager != null) {
				resourceManager.dispose();
				resourceManager = null;
			}
		});
		return control;
	}

	@Override
	public void setTitleImage(Image newTitleImage) {
		explicitImageSet = newTitleImage != null;
		super.setTitleImage(newTitleImage);
	}

	protected @NonNull ResourceManager getResourceManager() {
		if (resourceManager == null) {
			resourceManager = new LocalResourceManager(
					JFaceResources.getResources());
		}
		return resourceManager;
	}
}
