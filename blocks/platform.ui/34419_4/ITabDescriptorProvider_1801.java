
package org.eclipse.ui.views.properties.tabbed;

import java.util.List;

public interface ITabDescriptor extends ITabItem {

	public static final String TOP = "top"; //$NON-NLS-1$

	public TabContents createTab();

	public String getAfterTab();

	public String getCategory();

	public String getId();

	public String getLabel();

	public List getSectionDescriptors();
}
