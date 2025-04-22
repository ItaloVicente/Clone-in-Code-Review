package org.eclipse.egit.ui.internal.components;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class TitleAndImageDialog extends TitleAreaDialog {

	private ResourceManager resourceManager;

	private ImageDescriptor defaultImageDescriptor;

	private boolean explicitImageSet;

	public TitleAndImageDialog(Shell parent, ImageDescriptor defaultImageDescriptor) {
		super(parent);
		this.defaultImageDescriptor = defaultImageDescriptor;
	}

	@Override
	protected Control createContents(Composite parent) {
		if (defaultImageDescriptor != null && !explicitImageSet) {
			super.setTitleImage(
					getResourceManager().createImage(defaultImageDescriptor));
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
		explicitImageSet = true;
		super.setTitleImage(newTitleImage);
	}

	protected ResourceManager getResourceManager() {
		if (resourceManager == null) {
			resourceManager = new LocalResourceManager(
					JFaceResources.getResources());
		}
		return resourceManager;
	}
}
