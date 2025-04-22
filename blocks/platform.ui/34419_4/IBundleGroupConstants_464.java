package org.eclipse.ui.application;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.WorkbenchWindowConfigurer;
import org.eclipse.ui.internal.util.PrefUtil;
import org.eclipse.ui.intro.IIntroManager;

public class WorkbenchWindowAdvisor {

    private IWorkbenchWindowConfigurer windowConfigurer;

    public WorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        Assert.isNotNull(configurer);
        this.windowConfigurer = configurer;
    }

    protected IWorkbenchWindowConfigurer getWindowConfigurer() {
        return windowConfigurer;
    }
    
    public void preWindowOpen() {
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ActionBarAdvisor(configurer);
    }
    
    public void postWindowRestore() throws WorkbenchException {
    }

	protected void cleanUpEditorArea() {
	}

    public void openIntro() {
        
        IWorkbenchConfigurer wbConfig = getWindowConfigurer().getWorkbenchConfigurer();
        final String key = "introOpened"; //$NON-NLS-1$
        Boolean introOpened = (Boolean) wbConfig.getData(key);
        if (introOpened != null && introOpened.booleanValue()) {
			return;
		}

        wbConfig.setData(key, Boolean.TRUE);

        boolean showIntro = PrefUtil.getAPIPreferenceStore().getBoolean(
                IWorkbenchPreferenceConstants.SHOW_INTRO);
        
        IIntroManager introManager = wbConfig.getWorkbench().getIntroManager();
        
        boolean hasIntro = introManager.hasIntro();
        boolean isNewIntroContentAvailable = introManager.isNewContentAvailable();
        
		if (hasIntro && (showIntro || isNewIntroContentAvailable)) {
			PrefUtil.getAPIPreferenceStore().setValue(
					IWorkbenchPreferenceConstants.SHOW_INTRO, false);
			PrefUtil.saveAPIPrefs();
			
            introManager
                    .showIntro(getWindowConfigurer().getWindow(), false);
        }
    }

    public void postWindowCreate() {
    }

    public void postWindowOpen() {
    }

	public boolean preWindowShellClose() {
		return true;
	}

    public void postWindowClose() {
    }

	@Deprecated
    public void createWindowContents(Shell shell) {
        ((WorkbenchWindowConfigurer) getWindowConfigurer()).createDefaultContents(shell);
    }

	@Deprecated
	public Control createEmptyWindowContents(Composite parent) {
        return null;
    }
    
	public boolean isDurableFolder(String perspectiveId, String folderId) {
		return false;
	}

    public void dispose() {
    }
	
	public IStatus saveState(IMemento memento) {
		return Status.OK_STATUS;
	}
	
	public IStatus restoreState(IMemento memento) {
		return Status.OK_STATUS;
	}
}
