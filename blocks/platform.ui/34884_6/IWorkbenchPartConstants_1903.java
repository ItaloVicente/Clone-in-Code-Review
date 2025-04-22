
package org.eclipse.ui;

import java.util.Map;

import org.eclipse.jface.util.IPropertyChangeListener;

public interface IWorkbenchPart3 extends IWorkbenchPart2 {
	public void addPartPropertyListener(IPropertyChangeListener listener);

	public void removePartPropertyListener(IPropertyChangeListener listener);

	public String getPartProperty(String key);

	public void setPartProperty(String key, String value);

	public Map getPartProperties();
}
