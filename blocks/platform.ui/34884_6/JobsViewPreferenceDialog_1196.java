package org.eclipse.ui.internal.progress;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;

public abstract class JobTreeElement implements Comparable {

	public Object getParent() {
		return null;
	}

	abstract boolean hasChildren();

	abstract Object[] getChildren();

	abstract String getDisplayString();

	String getDisplayString(boolean showProgress) {
		return getDisplayString();
	}

	public Image getDisplayImage() {
		return JFaceResources.getImage(ProgressInfoItem.DEFAULT_JOB_KEY);
	}

	String getCondensedDisplayString() {
		return getDisplayString();
	}

	abstract boolean isJobInfo();

	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof JobTreeElement)
			return getDisplayString().compareTo(
					((JobTreeElement) arg0).getDisplayString());
		return 0;
	}

	abstract boolean isActive();

	public boolean isCancellable() {
		return false;
	}

	public void cancel() {
	}
}
