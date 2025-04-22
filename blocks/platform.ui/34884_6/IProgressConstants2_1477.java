package org.eclipse.ui.progress;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.ui.PlatformUI;

public interface IProgressConstants {

    static final String PROPERTY_PREFIX = PlatformUI.PLUGIN_ID
            + ".workbench.progress"; //$NON-NLS-1$

    public static final QualifiedName KEEP_PROPERTY = new QualifiedName(
            PROPERTY_PREFIX, "keep"); //$NON-NLS-1$

    public static final QualifiedName KEEPONE_PROPERTY = new QualifiedName(
            PROPERTY_PREFIX, "keepone"); //$NON-NLS-1$

    public static final QualifiedName ACTION_PROPERTY = new QualifiedName(
            PROPERTY_PREFIX, "action"); //$NON-NLS-1$

    public static final QualifiedName ICON_PROPERTY = new QualifiedName(
            PROPERTY_PREFIX, "icon"); //$NON-NLS-1$

    public static String PROGRESS_VIEW_ID = "org.eclipse.ui.views.ProgressView"; //$NON-NLS-1$

    public static final QualifiedName PROPERTY_IN_DIALOG = new QualifiedName(
            IProgressConstants.PROPERTY_PREFIX, "inDialog"); //$NON-NLS-1$
    
    public static final QualifiedName NO_IMMEDIATE_ERROR_PROMPT_PROPERTY = new QualifiedName(
            PROPERTY_PREFIX, "delayErrorPrompt"); //$NON-NLS-1$
}
