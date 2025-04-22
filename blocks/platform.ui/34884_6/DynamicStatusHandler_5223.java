
package org.eclipse.ui.dynamic;

import org.eclipse.ui.IStartup;

public class DynamicStartup implements IStartup {

	public static Throwable history;
	
	public DynamicStartup() {
		super();		
	}

	public void earlyStartup() {
		history = new Throwable();
		history.fillInStackTrace();
	}
}
