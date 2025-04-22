package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.IWorkingSetEditWizard;
import org.eclipse.ui.dialogs.IWorkingSetNewWizard;
import org.eclipse.ui.dialogs.IWorkingSetSelectionDialog;

public interface IWorkingSetManager {

    public static final String CHANGE_WORKING_SET_ADD = "workingSetAdd"; //$NON-NLS-1$

    public static final String CHANGE_WORKING_SET_REMOVE = "workingSetRemove"; //$NON-NLS-1$

    public static final String CHANGE_WORKING_SET_CONTENT_CHANGE = "workingSetContentChange"; //$NON-NLS-1$

    public static final String CHANGE_WORKING_SET_NAME_CHANGE = "workingSetNameChange"; //$NON-NLS-1$	
    
    public static final String CHANGE_WORKING_SET_LABEL_CHANGE = "workingSetLabelChange"; //$NON-NLS-1$	
    
    public static final String CHANGE_WORKING_SET_UPDATER_INSTALLED = "workingSetUpdaterInstalled"; //$NON-NLS-1$
    
    public static final String CHANGE_WORKING_SET_UPDATER_UNINSTALLED = "workingSetUpdaterUninstalled"; //$NON-NLS-1$

    public void addPropertyChangeListener(IPropertyChangeListener listener);

    public void addRecentWorkingSet(IWorkingSet workingSet);

    public void addWorkingSet(IWorkingSet workingSet);

    public IWorkingSet createWorkingSet(String name, IAdaptable[] elements);
    
	public IWorkingSet createAggregateWorkingSet(String name, String label,
			IWorkingSet[] components);

    public IWorkingSet createWorkingSet(IMemento memento);

    public IWorkingSetEditWizard createWorkingSetEditWizard(
            IWorkingSet workingSet);
    

    public IWorkingSetNewWizard createWorkingSetNewWizard(String[] workingSetIds);
    
    @Deprecated
	public IWorkingSetSelectionDialog createWorkingSetSelectionDialog(
            Shell parent);

    public IWorkingSetSelectionDialog createWorkingSetSelectionDialog(
            Shell parentShell, boolean multi);

    public IWorkingSetSelectionDialog createWorkingSetSelectionDialog(
            Shell parentShell, boolean multi, String[] workingsSetIds);
    
    public IWorkingSet[] getRecentWorkingSets();

    public IWorkingSet getWorkingSet(String name);

    public IWorkingSet[] getWorkingSets();
    
    public IWorkingSet[] getAllWorkingSets();

    public void removePropertyChangeListener(IPropertyChangeListener listener);

    public void removeWorkingSet(IWorkingSet workingSet);
    
    public void dispose();
    
	public void addToWorkingSets(IAdaptable element, IWorkingSet[] workingSets);

	public void setRecentWorkingSetsLength(int length);

	public int getRecentWorkingSetsLength();
}
