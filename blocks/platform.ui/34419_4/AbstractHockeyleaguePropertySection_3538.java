package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.HockeyleaguePropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public abstract class AbstractHockeyleaguePropertySection
	extends AbstractPropertySection {

	protected HockeyleaguePropertySheetPage propertySheetPage;

	protected EObject eObject;

	protected List eObjectList;

	protected int getStandardLabelWidth(Composite parent, String[] labels) {
		int standardLabelWidth = STANDARD_LABEL_WIDTH;
		GC gc = new GC(parent);
		int indent = gc.textExtent("XXX").x; //$NON-NLS-1$
		for (int i = 0; i < labels.length; i++) {
			int width = gc.textExtent(labels[i]).x;
			if (width + indent > standardLabelWidth) {
				standardLabelWidth = width + indent;
			}
		}
		gc.dispose();
		return standardLabelWidth;
	}

	public void createControls(Composite parent,
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		this.propertySheetPage = (HockeyleaguePropertySheetPage) aTabbedPropertySheetPage;
	}

	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		if (((IStructuredSelection) selection).getFirstElement() instanceof EObject) {
			eObject = (EObject) ((IStructuredSelection) selection)
				.getFirstElement();
			eObjectList = ((IStructuredSelection) selection).toList();
		}
	}
}
