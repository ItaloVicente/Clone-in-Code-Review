
package org.eclipse.ui.views.properties.tabbed;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyRegistryClassSectionFilter;

public abstract class AbstractSectionDescriptor implements ISectionDescriptor {

	private TabbedPropertyRegistryClassSectionFilter classFilter;

	public AbstractSectionDescriptor() {
		super();
		classFilter = new TabbedPropertyRegistryClassSectionFilter(null);
	}

	public AbstractSectionDescriptor(ITypeMapper typeMapper) {
		super();
		classFilter = new TabbedPropertyRegistryClassSectionFilter(typeMapper);
	}

	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		return classFilter.appliesToSelection(this, selection);
	}

	public String getAfterSection() {
		return TOP;
	}

	public int getEnablesFor() {
		return ENABLES_FOR_ANY;
	}

	public IFilter getFilter() {
		return null;
	}

	public List getInputTypes() {
		return new ArrayList();
	}
}
