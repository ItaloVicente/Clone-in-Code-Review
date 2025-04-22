/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.actions.LabelRetargetAction;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

/**
 * This class extends regular plugin action with the additional requirement that
 * the delegate has to implement interface
 * {@link org.eclipse.ui.IWorkbenchWindowActionDelegate}. This interface has
 * one additional method (init) whose purpose is to initialize the delegate with
 * the window in which the action is intended to run.
 */
public class WWinPluginAction extends PluginAction implements
        IActionSetContributionItem {
    /**
     * The help listener assigned to this action, or <code>null</code> if none.
     */
    private HelpListener localHelpListener;

    private IWorkbenchWindow window;

    private String actionSetId;

    private RetargetAction retargetAction;

    private static ArrayList staticActionList = new ArrayList(50);

    /**
     * Constructs a new <code>WWinPluginAction</code> object.
     *
     * @param actionElement the configuration element
     * @param window the window to contribute to
     * @param id the identifier
     * @param style the style
     */
    public WWinPluginAction(IConfigurationElement actionElement,
            IWorkbenchWindow window, String id, int style) {
        super(actionElement, id, style);
        this.window = window;

        String retarget = actionElement
                .getAttribute(IWorkbenchRegistryConstants.ATT_RETARGET);
        if (retarget != null && Boolean.valueOf(retarget).booleanValue()) {
            String allowLabelUpdate = actionElement
                    .getAttribute(IWorkbenchRegistryConstants.ATT_ALLOW_LABEL_UPDATE);
            String label = actionElement
                    .getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);

            if (allowLabelUpdate != null && Boolean.valueOf(allowLabelUpdate).booleanValue()) {
				retargetAction = new LabelRetargetAction(id, label, style);
			} else {
				retargetAction = new RetargetAction(id, label, style);
			}
            retargetAction
                    .addPropertyChangeListener(event -> {
					    if (event.getProperty().equals(IAction.ENABLED)) {
					        Object val1 = event.getNewValue();
					        if (val1 instanceof Boolean) {
					            setEnabled(((Boolean) val1).booleanValue());
					        }
					    } else if (event.getProperty().equals(
					            IAction.CHECKED)) {
					        Object val2 = event.getNewValue();
					        if (val2 instanceof Boolean) {
					            setChecked(((Boolean) val2).booleanValue());
					        }
					    } else if (event.getProperty().equals(IAction.TEXT)) {
					        Object val3 = event.getNewValue();
					        if (val3 instanceof String) {
					            setText((String) val3);
					        }
					    } else if (event.getProperty().equals(
					            IAction.TOOL_TIP_TEXT)) {
					        Object val4 = event.getNewValue();
					        if (val4 instanceof String) {
					            setToolTipText((String) val4);
					        }
					    }
					});
            retargetAction.setEnabled(false);
            setEnabled(false);
            window.getPartService().addPartListener(retargetAction);
            IWorkbenchPart activePart = window.getPartService().getActivePart();
            if (activePart != null) {
				retargetAction.partActivated(activePart);
			}
        } else {
            window.getSelectionService().addSelectionListener(this);
            refreshSelection();
        }
        addToActionList(this);

        super.setHelpListener(e -> {
		    HelpListener listener = null;
		    if (retargetAction != null) {
				listener = retargetAction.getHelpListener();
			}
		    if (listener == null) {
		        listener = localHelpListener;
			}
		    if (listener != null) {
		        listener.helpRequested(e);
			}
		});
    }

    /**
     * Adds an item to the action list.
     */
    private static void addToActionList(WWinPluginAction action) {
        staticActionList.add(action);
    }

    /**
     * Removes an item from the action list.
     */
    private static void removeFromActionList(WWinPluginAction action) {
        staticActionList.remove(action);
    }

    /**
     * Creates any actions which belong to an activated plugin.
     */
    public static void refreshActionList() {
        Iterator iter = staticActionList.iterator();
        while (iter.hasNext()) {
            WWinPluginAction action = (WWinPluginAction) iter.next();
            if ((action.getDelegate() == null) && action.isOkToCreateDelegate()) {
                action.createDelegate();
            }
        }
    }

    @Override
	protected IActionDelegate validateDelegate(Object obj)
            throws WorkbenchException {
        if (obj instanceof IWorkbenchWindowActionDelegate) {
			return (IWorkbenchWindowActionDelegate) obj;
		}

        throw new WorkbenchException(
    }

    @Override
	protected void initDelegate() {
        super.initDelegate();
        ((IWorkbenchWindowActionDelegate) getDelegate()).init(window);
    }

    /**
     * Disposes of the action and any resources held.
     */
    @Override
	public void dispose() {
        removeFromActionList(this);
        if (retargetAction != null) {
            window.getPartService().removePartListener(retargetAction);
            retargetAction.dispose();
            retargetAction = null;
        }
        window.getSelectionService().removeSelectionListener(this);
        super.dispose();
    }

    /**
     * Returns the action set id.
     */
    @Override
	public String getActionSetId() {
        return actionSetId;
    }

    /**
     * Returns true if the window has been set.
     * The window may be null after the constructor is called and
     * before the window is stored.  We cannot create the delegate
     * at that time.
     */
    @Override
	public boolean isOkToCreateDelegate() {
        return super.isOkToCreateDelegate() && window != null
                && retargetAction == null;
    }

    @Override
	public void runWithEvent(Event event) {
        if (retargetAction == null) {
            super.runWithEvent(event);
            return;
        }

        if (event != null) {
			retargetAction.runWithEvent(event);
		} else {
			retargetAction.run();
		}
    }

    /**
     * Sets the action set id.
     */
    @Override
	public void setActionSetId(String newActionSetId) {
        actionSetId = newActionSetId;
    }

    /**
     * The <code>WWinPluginAction</code> implementation of this method
     * declared on <code>IAction</code> stores the help listener in
     * a local field. The supplied listener is only used if there is
     * no retarget action.
     */
    @Override
	public void setHelpListener(HelpListener listener) {
        localHelpListener = listener;
    }

    @Override
	public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (retargetAction != null) {
			retargetAction.setChecked(checked);
		}
    }

    /**
     * Refresh the selection for the action.
     */
    protected void refreshSelection() {
        ISelection selection = window.getSelectionService().getSelection();
        selectionChanged(selection);
    }

	@Override
	public String toString() {
				+ ", enabled=" + isEnabled() + //$NON-NLS-1$
				(actionSetId != null ? ", actionSet=" + actionSetId : "") //$NON-NLS-1$ //$NON-NLS-2$
	}
}
