package org.eclipse.ui.tests.views.properties.tabbed.override;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.tests.views.properties.tabbed.model.Element;
import org.eclipse.ui.tests.views.properties.tabbed.model.Error;
import org.eclipse.ui.tests.views.properties.tabbed.model.File;
import org.eclipse.ui.tests.views.properties.tabbed.model.Folder;
import org.eclipse.ui.tests.views.properties.tabbed.model.Information;
import org.eclipse.ui.tests.views.properties.tabbed.model.Warning;

public class OverrideTestsContentProvider implements IStructuredContentProvider {

	private Element[] elements;

	public void dispose() {
	}

	public Object[] getElements(Object parent) {
		if (elements == null) {
			elements = new Element[] { new Information("Information"), //$NON-NLS-1$
					new Warning("Warning"), new Error("Error"), //$NON-NLS-1$ //$NON-NLS-2$
					new File("File"), new Folder("Folder") }; //$NON-NLS-1$//$NON-NLS-2$
		}
		return elements;
	}

	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}
}
