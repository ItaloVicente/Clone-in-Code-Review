
package org.eclipse.ui.navigator;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.ISaveablesLifecycleListener;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.Saveable;

public interface INavigatorSaveablesService {

	public void init(ISaveablesSource source, StructuredViewer viewer,
			ISaveablesLifecycleListener listener);

	public Saveable[] getSaveables();

	public Saveable[] getActiveSaveables();

}
