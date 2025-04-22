package org.eclipse.ui.dialogs;

public interface IOverwriteQuery {
    public static final String CANCEL = "CANCEL"; //$NON-NLS-1$

    public static final String NO = "NO"; //$NON-NLS-1$

    public static final String YES = "YES"; //$NON-NLS-1$

    public static final String ALL = "ALL"; //$NON-NLS-1$

    public static final String NO_ALL = "NOALL"; //$NON-NLS-1$

    String queryOverwrite(String pathString);
}
