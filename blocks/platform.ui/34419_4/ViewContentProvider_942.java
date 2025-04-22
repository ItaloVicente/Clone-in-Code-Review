package org.eclipse.ui.internal.dialogs;

import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.ui.internal.WorkbenchMessages;

public class ViewComparator extends ViewerComparator {

	private final static String EMPTY_STRING = ""; //$NON-NLS-1$

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (WorkbenchMessages.ICategory_other.equals(e1)) {
			return -1;
		}
		if (WorkbenchMessages.ICategory_general.equals(e2)) {
			return 1;
		}

		String str1;
		if (e1 instanceof MPartDescriptor) {
			str1 = ((MPartDescriptor) e1).getLocalizedLabel();
		} else {
			str1 = e1.toString();
		}

		String str2;
		if (e2 instanceof MPartDescriptor) {
			str2 = ((MPartDescriptor) e2).getLocalizedLabel();
		} else {
			str2 = e2.toString();
		}
		if (str1 == null) {
			str1 = EMPTY_STRING;
		}
		if (str2 == null) {
			str2 = EMPTY_STRING;
		}
		String s1 = DialogUtil.removeAccel(str1);
		String s2 = DialogUtil.removeAccel(str2);
		return getComparator().compare(s1, s2);
	}
}
