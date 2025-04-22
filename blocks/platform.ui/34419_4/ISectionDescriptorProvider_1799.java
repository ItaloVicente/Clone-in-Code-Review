package org.eclipse.ui.views.properties.tabbed;

import java.util.List;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

public interface ISectionDescriptor {

	public final int ENABLES_FOR_ANY = -1;

	public static final String TOP = "top"; //$NON-NLS-1$

	public String getId();

	public IFilter getFilter();

	public List getInputTypes();

	public ISection getSectionClass();

	public String getTargetTab();

	public int getEnablesFor();

	public boolean appliesTo(IWorkbenchPart part, ISelection selection);

	public String getAfterSection();
}
