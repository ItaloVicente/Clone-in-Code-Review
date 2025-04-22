
package org.eclipse.ui.navigator;

public final class MenuInsertionPoint {
	private String name;

	private boolean isSeparator;
	
	private String toString;

	public MenuInsertionPoint(String aName, boolean toMakeASeparator) {
		name = aName;
		isSeparator = toMakeASeparator;
	}

	public boolean isSeparator() {
		return isSeparator;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() { 
		if(toString == null) {
			toString = "MenuInsertionPoint[name=\""+name+"\", isSeparator="+isSeparator+"]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return toString;
	}
}
