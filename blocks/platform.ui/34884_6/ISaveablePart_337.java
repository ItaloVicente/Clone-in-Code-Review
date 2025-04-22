
package org.eclipse.ui;

public interface ISaveableFilter {
	
	public boolean select(Saveable saveable, IWorkbenchPart[] containingParts);

}
