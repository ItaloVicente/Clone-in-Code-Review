package org.eclipse.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.testing.TestableObject;

public final class PlatformUI {
    public static final String PLUGIN_ID = "org.eclipse.ui"; //$NON-NLS-1$

    public static final int RETURN_OK = 0;

    public static final int RETURN_RESTART = 1;

    public static final int RETURN_UNSTARTABLE = 2;

    public static final int RETURN_EMERGENCY_CLOSE = 3;

    private PlatformUI() {
    }

    public static IWorkbench getWorkbench() {
        if (Workbench.getInstance() == null) {
            throw new IllegalStateException(WorkbenchMessages.PlatformUI_NoWorkbench); 
        }
        return Workbench.getInstance();
    }

    public static boolean isWorkbenchRunning() {
        return Workbench.getInstance() != null
                && Workbench.getInstance().isRunning();
    }

    public static int createAndRunWorkbench(Display display,
            WorkbenchAdvisor advisor) {
        return Workbench.createAndRunWorkbench(display, advisor);
    }

    public static Display createDisplay() {
        return Workbench.createDisplay();
    }

    public static TestableObject getTestableObject() {
		TestableObject testableObject = WorkbenchPlugin.getDefault().getTestableObject();
		if (testableObject == null) {
			return Workbench.getWorkbenchTestable();
		}
		return testableObject;
    }

    public static IPreferenceStore getPreferenceStore() {
        return PrefUtil.getAPIPreferenceStore();
    }
}
