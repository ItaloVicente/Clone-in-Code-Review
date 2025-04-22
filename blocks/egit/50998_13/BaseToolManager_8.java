
package org.eclipse.egit.ui.internal.externaltools;


public class BaseTool implements ITool {

	private String name = null;

	private AttributeSet attributes = new AttributeSet();

	public BaseTool(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPath() {
		return null;
	}

	@Override
	public String getOptions(int... optionsNr) {
		return null;
	}

	@Override
	public String getCommand(int... optionsNr) {
		return null;
	}

	@Override
	public AttributeSet getAttributeSet() {
		return attributes;
	}

}
