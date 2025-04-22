
package org.eclipse.ui.internal.navigator;

import org.eclipse.jface.viewers.ViewerLabel;

public class ReusableViewerLabel extends ViewerLabel {

	private ViewerLabel original = null;
	public ReusableViewerLabel() {
		super(null, null);
	}


	public void reset(ViewerLabel theOriginal) {
		original = theOriginal;
		setBackground(original.getBackground());
		setFont(original.getFont());
		setForeground(original.getForeground());
		setImage(null);
		setText(null);
	}
	
	public void fill(ViewerLabel theOriginal) {

		theOriginal.setBackground(getBackground());
		theOriginal.setFont(getFont());
		theOriginal.setForeground(getForeground());
		theOriginal.setImage(getImage());
		theOriginal.setText(getText() != null ? getText() : ""); //$NON-NLS-1$
	}

	public boolean hasChanged() {
		
		boolean changed = false;
		if(original != null) {
			if(original.getText() == null ^ getText() != null)
				changed |= getText() != null;
			if(original.getText() != null && getImage() != null)
				changed |= !getImage().equals(original.getImage());
		}
		return changed;
	}

	public boolean isValid() {
		return getText() != null && getText().length() > 0 && getImage() != null;

	}

}
