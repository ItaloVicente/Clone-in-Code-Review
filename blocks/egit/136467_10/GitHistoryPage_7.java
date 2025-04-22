package org.eclipse.egit.ui.internal.history;

import org.eclipse.egit.ui.internal.DecorationOverlayDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.ui.model.WorkbenchAdapter;

public class FileDiffWorkbenchAdapter extends WorkbenchAdapter {

	public static final FileDiffWorkbenchAdapter INSTANCE = new FileDiffWorkbenchAdapter();

	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		if (object instanceof FileDiff) {
			FileDiff diff = (FileDiff) object;
			ImageDescriptor base = diff.getBaseImageDescriptor();
			ImageDescriptor decoration = diff.getImageDcoration();
			if (decoration != null) {
				return new DecorationOverlayDescriptor(base, decoration,
						IDecoration.BOTTOM_RIGHT);
			}
			return base;
		}
		return null;
	}

	@Override
	public String getLabel(Object object) {
		if (object instanceof FileDiff) {
			return ((FileDiff) object).getPath();
		}
		return null;
	}
}
