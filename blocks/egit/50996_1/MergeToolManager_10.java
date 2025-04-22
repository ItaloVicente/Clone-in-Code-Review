
package org.eclipse.egit.ui.internal.externaltools;

public interface ITool {
	public String getName();

	public String getPath();

	public String getOptions();

	public String getCommand();

	public AttributeSet getAttributeSet();

}
