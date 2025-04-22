
package org.eclipse.ui.tests.statushandlers;

import org.eclipse.core.tests.session.Setup;
import org.eclipse.core.tests.session.SetupManager.SetupException;
import org.eclipse.ui.tests.session.WorkbenchSessionTest;

public class StatusHandlerConfigurationSuite extends WorkbenchSessionTest {

	public StatusHandlerConfigurationSuite(String dataLocation) {
		super(dataLocation);
		System.out.println("initalization1");
	}
	
	public StatusHandlerConfigurationSuite(String dataLocation, Class clazz){
		super(dataLocation, clazz);
		System.out.println("initalization2");
	}

	@Override
	protected Setup newSetup() throws SetupException {
		Setup base = super.newSetup();
		System.out.println(base);
		base.setEclipseArgument("statushandler",
				"org.eclipse.ui.tests.statushandlers.FreeStatusHandler");
		System.out.println(base);
		return base;
	}
	
	

}
