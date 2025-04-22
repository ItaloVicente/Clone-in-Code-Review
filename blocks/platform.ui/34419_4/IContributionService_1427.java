
package org.eclipse.ui.model;

import org.eclipse.core.runtime.IAdaptable;

public interface IComparableContribution extends IAdaptable {

	public static final int PRIORITY_DEFAULT = 0;

	int getPriority();

	String getLabel();

	@Override
	public Object getAdapter(Class adapter);

}
