package org.eclipse.ui.application;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.internal.handlers.IActionCommandMappingService;

public class ActionBarAdvisor {

    public static final int FILL_PROXY = 0x01;

    public static final int FILL_MENU_BAR = 0x02;

    public static final int FILL_COOL_BAR = 0x04;

    public static final int FILL_STATUS_LINE = 0x08;

    
    private IActionBarConfigurer actionBarConfigurer;
    
    private Map actions = new HashMap();
    
    public ActionBarAdvisor(IActionBarConfigurer configurer) {
        Assert.isNotNull(configurer);
        actionBarConfigurer = configurer;
    }
    
    protected IActionBarConfigurer getActionBarConfigurer() {
        return actionBarConfigurer;
    }

    public void fillActionBars(int flags) {
        if ((flags & FILL_PROXY) == 0) {
            makeActions(actionBarConfigurer.getWindowConfigurer().getWindow());
        }
        if ((flags & FILL_MENU_BAR) != 0) {
            fillMenuBar(actionBarConfigurer.getMenuManager());
        }
        if ((flags & FILL_COOL_BAR) != 0) {
            fillCoolBar(actionBarConfigurer.getCoolBarManager());
        }
        if ((flags & FILL_STATUS_LINE) != 0) {
            fillStatusLine(actionBarConfigurer.getStatusLineManager());
        }
    }
        
    protected void makeActions(IWorkbenchWindow window) {
    }

    protected void register(IAction action) {
    	Assert.isNotNull(action, "Action must not be null"); //$NON-NLS-1$
        String id = action.getId();
        Assert.isNotNull(id, "Action must not have null id"); //$NON-NLS-1$
		if (action instanceof RetargetAction) {
			String definitionId = action.getActionDefinitionId();
			if (definitionId != null) {
				IActionCommandMappingService mapping = getActionBarConfigurer()
						.getWindowConfigurer().getWindow()
						.getService(IActionCommandMappingService.class);
				if (mapping != null) {
					mapping.map(id, definitionId);
				}
			}
		} else {
			getActionBarConfigurer().registerGlobalAction(action);
		}
		actions.put(id, action);
    }
    
    protected IAction getAction(String id) {
        return (IAction) actions.get(id);
    }
    
    protected void fillMenuBar(IMenuManager menuBar) {
    }
    
    protected void fillCoolBar(ICoolBarManager coolBar) {
    }
    
    protected void fillStatusLine(IStatusLineManager statusLine) {
    }    

    public boolean isApplicationMenu(String menuId) {
        return false;
    }

    public void dispose() {
        disposeActions();
    }
    
    protected void disposeActions() {
        for (Iterator i = actions.values().iterator(); i.hasNext();) {
            IAction action = (IAction) i.next();
            disposeAction(action);
        }
        actions.clear();
    }
    
    protected void disposeAction(IAction action) {
        if (action instanceof ActionFactory.IWorkbenchAction) {
            ((ActionFactory.IWorkbenchAction) action).dispose();
        }
    }
	
	public IStatus saveState(IMemento memento) {
		return Status.OK_STATUS;
	}
	
	public IStatus restoreState(IMemento memento) {
		return Status.OK_STATUS;
	}

}
