package org.eclipse.ui.progress;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressConstants;

public interface IProgressConstants2 extends IProgressConstants {

    static final String PROPERTY_PREFIX = PlatformUI.PLUGIN_ID
            + ".workbench.progress"; //$NON-NLS-1$

	public static final QualifiedName COMMAND_PROPERTY = new QualifiedName(
			PROPERTY_PREFIX, "command"); //$NON-NLS-1$

	public static final QualifiedName SHOW_IN_TASKBAR_ICON_PROPERTY = new QualifiedName(
			PROPERTY_PREFIX, "inTaskBarIcon"); //$NON-NLS-1$
}
