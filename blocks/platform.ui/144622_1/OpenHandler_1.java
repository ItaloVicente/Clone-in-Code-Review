
package org.eclipse.e4.ui.tests.workbench;

import org.eclipse.e4.core.di.annotations.Execute;

public class OpenHandler {
	@Execute
	public void execute() {
		System.out.println("OpenHandler.execute()");
	}

}
