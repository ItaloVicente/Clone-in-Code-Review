package org.eclipse.ui.internal.editorsupport;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.util.Util;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.internal.util.PrefUtil;
import org.osgi.framework.Bundle;

public final class ComponentSupport {

    public static boolean inPlaceEditorSupported() {
    	if (PrefUtil.getAPIPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.DISABLE_OPEN_EDITOR_IN_PLACE)) {
    		return false;
    	}
        return Util.isWindows();
    }

    public static IEditorPart getSystemInPlaceEditor() {
        if (inPlaceEditorSupported()) {
            return getOleEditor();
        }
        return null;
    }

    public static boolean inPlaceEditorAvailable(String filename) {
        if (inPlaceEditorSupported()) {
            return testForOleEditor(filename);
        } else {
            return false;
        }
    }

    private static IEditorPart getOleEditor() {
        Bundle bundle = Platform.getBundle("org.eclipse.ui.ide"); //$NON-NLS-1$

        if (!BundleUtility.isActivated(bundle)) {
			return null;
		}

        try {
            Class c = bundle
                    .loadClass("org.eclipse.ui.internal.editorsupport.win32.OleEditor"); //$NON-NLS-1$
            return (IEditorPart) c.newInstance();
        } catch (ClassNotFoundException exception) {
            return null;
        } catch (IllegalAccessException exception) {
            return null;
        } catch (InstantiationException exception) {
            return null;
        }
    }

    public static boolean testForOleEditor(String filename) {
        int nDot = filename.lastIndexOf('.');
        if (nDot >= 0) {
            try {
                String strName = filename.substring(nDot);
                Class oleClass = Class.forName("org.eclipse.swt.ole.win32.OLE"); //$NON-NLS-1$
                Method findMethod = oleClass.getDeclaredMethod(
                        "findProgramID", new Class[] { String.class }); //$NON-NLS-1$
                strName = (String) findMethod.invoke(null,
                        new Object[] { strName });
                if (strName.length() > 0) {
					return true;
				}
            } catch (ClassNotFoundException exception) {
                return false;
            } catch (NoSuchMethodException exception) {
                return false;
            } catch (IllegalAccessException exception) {
                return false;
            } catch (InvocationTargetException exception) {
                return false;
            }

        }
        return false;
    }
}
