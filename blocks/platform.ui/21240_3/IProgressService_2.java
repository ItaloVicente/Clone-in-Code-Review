package org.eclipse.e4.ui.progress;

import org.eclipse.core.runtime.QualifiedName;

public interface IProgressConstants {

    static final String PROPERTY_PREFIX = "org.eclipse.e4.ui.progress"; //$NON-NLS-1$

    static final String PLUGIN_ID = PROPERTY_PREFIX; //$NON-NLS-1$


    public static final QualifiedName KEEP_PROPERTY = new QualifiedName(
            PROPERTY_PREFIX, "keep"); //$NON-NLS-1$

    public static final QualifiedName KEEPONE_PROPERTY = new QualifiedName(
            PROPERTY_PREFIX, "keepone"); //$NON-NLS-1$

    public static final QualifiedName ACTION_PROPERTY = new QualifiedName(
            PROPERTY_PREFIX, "action"); //$NON-NLS-1$

    public static final QualifiedName ICON_PROPERTY = new QualifiedName(
            PROPERTY_PREFIX, "icon"); //$NON-NLS-1$

    public static final QualifiedName PROPERTY_IN_DIALOG = new QualifiedName(
            IProgressConstants.PROPERTY_PREFIX, "inDialog"); //$NON-NLS-1$

    public static final QualifiedName NO_IMMEDIATE_ERROR_PROMPT_PROPERTY = new QualifiedName(
            PROPERTY_PREFIX, "delayErrorPrompt"); //$NON-NLS-1$

	public static final String SHOW_SYSTEM_JOBS = "SHOW_SYSTEM_JOBS";//$NON-NLS-1$

	public static final QualifiedName COMMAND_PROPERTY = new QualifiedName(
			PROPERTY_PREFIX, "command"); //$NON-NLS-1$

	public static final QualifiedName SHOW_IN_TASKBAR_ICON_PROPERTY = new QualifiedName(
			PROPERTY_PREFIX, "inTaskBarIcon"); //$NON-NLS-1$
}
