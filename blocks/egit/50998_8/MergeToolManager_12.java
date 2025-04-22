
package org.eclipse.egit.ui.internal.externaltools;

public interface ITool {
	public String getName();

	public String getPath();

	public String getOptions(int... optionsNr);

	public String getCommand(int... optionsNr);

	public AttributeSet getAttributeSet();

}
