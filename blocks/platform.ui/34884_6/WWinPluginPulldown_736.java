package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.events.HelpEvent;
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

public class WWinPluginAction extends PluginAction implements
        IActionSetContributionItem {
    private HelpListener localHelpListener;

    private IWorkbenchWindow window;

    private String actionSetId;

    private RetargetAction retargetAction;

    private static ArrayList staticActionList = new ArrayList(50);

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
                    .addPropertyChangeListener(new IPropertyChangeListener() {
                        @Override
						public void propertyChange(PropertyChangeEvent event) {
                            if (event.getProperty().equals(IAction.ENABLED)) {
                                Object val = event.getNewValue();
                                if (val instanceof Boolean) {
                                    setEnabled(((Boolean) val).booleanValue());
                                }
                            } else if (event.getProperty().equals(
                                    IAction.CHECKED)) {
                                Object val = event.getNewValue();
                                if (val instanceof Boolean) {
                                    setChecked(((Boolean) val).booleanValue());
                                }
                            } else if (event.getProperty().equals(IAction.TEXT)) {
                                Object val = event.getNewValue();
                                if (val instanceof String) {
                                    setText((String) val);
                                }
                            } else if (event.getProperty().equals(
                                    IAction.TOOL_TIP_TEXT)) {
                                Object val = event.getNewValue();
                                if (val instanceof String) {
                                    setToolTipText((String) val);
                                }
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

        super.setHelpListener(new HelpListener() {
            @Override
			public void helpRequested(HelpEvent e) {
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
            }
        });
    }

    private static void addToActionList(WWinPluginAction action) {
        staticActionList.add(action);
    }

    private static void removeFromActionList(WWinPluginAction action) {
        staticActionList.remove(action);
    }

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
                "Action must implement IWorkbenchWindowActionDelegate"); //$NON-NLS-1$
    }

    @Override
	protected void initDelegate() {
        super.initDelegate();
        ((IWorkbenchWindowActionDelegate) getDelegate()).init(window);
    }

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

    @Override
	public String getActionSetId() {
        return actionSetId;
    }

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

    @Override
	public void setActionSetId(String newActionSetId) {
        actionSetId = newActionSetId;
    }

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

    protected void refreshSelection() {
        ISelection selection = window.getSelectionService().getSelection();
        selectionChanged(selection);
    }
}
