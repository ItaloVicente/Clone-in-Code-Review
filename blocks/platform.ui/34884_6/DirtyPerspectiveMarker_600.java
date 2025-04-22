
package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPart2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.Saveable;

public class DefaultSaveable extends Saveable {

	private IWorkbenchPart part;

	public DefaultSaveable(IWorkbenchPart part) {
		this.part = part;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if (part instanceof ISaveablePart) {
			ISaveablePart saveable = (ISaveablePart) part;
			saveable.doSave(monitor);
		}
	}

	@Override
	public String getName() {
		if (part instanceof IWorkbenchPart2) {
			return ((IWorkbenchPart2) part).getPartName();
		}
		return part.getTitle();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		Image image = part.getTitleImage();
		if (image == null) {
			return null;
		}
		return ImageDescriptor.createFromImage(image);
	}

	@Override
	public String getToolTipText() {
		return part.getTitleToolTip();
	}

	@Override
	public boolean isDirty() {
		if (part instanceof ISaveablePart) {
			return ((ISaveablePart) part).isDirty();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return part.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DefaultSaveable other = (DefaultSaveable) obj;
		if (part == null) {
			if (other.part != null)
				return false;
		} else if (!part.equals(other.part))
			return false;
		return true;
	}
	
	@Override
	public boolean show(IWorkbenchPage page) {
		IWorkbenchPartReference reference = page.getReference(part);
		if (reference != null) {
			page.activate(part);
			return true;
		}
		if (part instanceof IViewPart) {
			IViewPart viewPart = (IViewPart) part;
			try {
				page.showView(viewPart.getViewSite().getId(), viewPart
						.getViewSite().getSecondaryId(),
						IWorkbenchPage.VIEW_ACTIVATE);
			} catch (PartInitException e) {
				return false;
			}
			return true;
		}
		return false;
	}

}
