package org.eclipse.ui.actions;

import org.eclipse.core.commands.IHandlerAttributes;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.internal.PartSite;

public class RetargetAction extends PartEventAction implements
        ActionFactory.IWorkbenchAction {

    private HelpListener localHelpListener;

    private boolean enableAccelerator = true;

    private IAction handler;

    private IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {
        @Override
		public void propertyChange(PropertyChangeEvent event) {
            RetargetAction.this.propagateChange(event);
        }
    };

    public RetargetAction(String actionID, String text) {
        this(actionID, text, IAction.AS_UNSPECIFIED);
    }

    public RetargetAction(String actionID, String text, int style) {
        super(text, style);
        setId(actionID);
        setEnabled(false);
        super.setHelpListener(new HelpListener() {
            @Override
			public void helpRequested(HelpEvent e) {
                HelpListener listener = null;
                if (handler != null) {
                    listener = handler.getHelpListener();
                    if (listener == null) {
                        listener = localHelpListener;
					}
                }
                if (listener != null) {
                    listener.helpRequested(e);
				}
            }
        });
    }

    @Override
	public void dispose() {
        if (handler != null) {
            handler.removePropertyChangeListener(propertyChangeListener);
            handler = null;
        }
        IWorkbenchPart part = getActivePart();
        if (part != null) {
            IWorkbenchPartSite site = part.getSite();
            SubActionBars bars = (SubActionBars) ((PartSite) site).getActionBars();
            bars.removePropertyChangeListener(propertyChangeListener);
        }
    }

    public void enableAccelerator(boolean b) {
        enableAccelerator = b;
    }

    @Override
	public int getAccelerator() {
        if (enableAccelerator) {
			return super.getAccelerator();
		}
        return 0;
    }

    @Override
	public void partActivated(IWorkbenchPart part) {
        super.partActivated(part);
        IWorkbenchPartSite site = part.getSite();
        SubActionBars bars = (SubActionBars) ((PartSite) site).getActionBars();
        bars.addPropertyChangeListener(propertyChangeListener);
        setActionHandler(bars.getGlobalActionHandler(getId()));
    }

    @Override
	public void partClosed(IWorkbenchPart part) {
        IWorkbenchPart activePart = part.getSite().getPage().getActivePart();
        if (activePart != null) {
            return;
		}
        if (part == getActivePart()) {
			setActionHandler(null);
		}
        super.partClosed(part);
    }

    @Override
	public void partDeactivated(IWorkbenchPart part) {
        super.partDeactivated(part);
        IWorkbenchPartSite site = part.getSite();
        SubActionBars bars = (SubActionBars) ((PartSite) site).getActionBars();
        bars.removePropertyChangeListener(propertyChangeListener);

        IWorkbenchPart activePart = part.getSite().getPage().getActivePart();
        if (activePart != null) {
            return;
		}

        setActionHandler(null);
    }

    protected void propagateChange(PropertyChangeEvent event) {
        if (event.getProperty().equals(IAction.ENABLED)) {
            Boolean bool = (Boolean) event.getNewValue();
            setEnabled(bool.booleanValue());
        } else if (event.getProperty().equals(IAction.CHECKED)) {
            Boolean bool = (Boolean) event.getNewValue();
            setChecked(bool.booleanValue());
        } else if (event.getProperty().equals(SubActionBars.P_ACTION_HANDLERS)) {
            if (event.getSource() instanceof IActionBars) {
                IActionBars bars = (IActionBars) event.getSource();
                setActionHandler(bars.getGlobalActionHandler(getId()));
            }
        }
    }

    @Override
	public void run() {
        if (handler != null) {
			handler.run();
		}
    }

    @Override
	public void runWithEvent(Event event) {
        if (handler != null) {
			handler.runWithEvent(event);
		}
    }

    public IAction getActionHandler() {
        return handler;
    }
    
    @Override
	public final boolean isHandled() {
        return (handler != null);
    }

    protected void setActionHandler(IAction newHandler) {
        if (newHandler == handler) {
			return;
		}

        if (handler != null) {
            handler.removePropertyChangeListener(propertyChangeListener);
            handler = null;
        }

		IAction oldHandler = handler;
        handler = newHandler;
        if (handler == null) {
            setEnabled(false);
            if (getStyle() == AS_CHECK_BOX || getStyle() == AS_RADIO_BUTTON) {
                setChecked(false);
            }
        } else {
            setEnabled(handler.isEnabled());
            if (getStyle() == AS_CHECK_BOX || getStyle() == AS_RADIO_BUTTON) {
                setChecked(handler.isChecked());
            }
            handler.addPropertyChangeListener(propertyChangeListener);
        }
		
        firePropertyChange(IHandlerAttributes.ATTRIBUTE_HANDLED, oldHandler,
                newHandler);
    }

    @Override
	public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (handler != null) {
			handler.setChecked(checked);
		}
    }

    @Override
	public void setHelpListener(HelpListener listener) {
        localHelpListener = listener;
    }

	@Override
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();

		buffer.append("RetargetAction("); //$NON-NLS-1$
		buffer.append(getId());
		buffer.append(')');

		return buffer.toString();
	}
}
