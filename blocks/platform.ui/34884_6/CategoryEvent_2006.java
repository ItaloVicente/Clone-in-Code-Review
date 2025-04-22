package org.eclipse.ui.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.commands.IHandlerAttributes;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.actions.RetargetAction;

@Deprecated
@SuppressWarnings("all")
public final class ActionHandler extends AbstractHandler {

    private final static String ATTRIBUTE_CHECKED = "checked"; //$NON-NLS-1$

    private final static String ATTRIBUTE_ENABLED = "enabled"; //$NON-NLS-1$

    private final static String ATTRIBUTE_HANDLED = IHandlerAttributes.ATTRIBUTE_HANDLED;

    private final static String ATTRIBUTE_ID = "id"; //$NON-NLS-1$

    private final static String ATTRIBUTE_STYLE = "style"; //$NON-NLS-1$

    private final IAction action;

    private Map attributeValuesByName;

    private IPropertyChangeListener propertyChangeListener;

	@Deprecated
    public ActionHandler(IAction action) {
        if (action == null) {
			throw new NullPointerException();
		}

        this.action = action;
    }

	@Override
	@Deprecated
    public void addHandlerListener(IHandlerListener handlerListener) {
        if (!hasListeners()) {
            attachListener();
        }

        super.addHandlerListener(handlerListener);
    }

    private final void attachListener() {
        if (propertyChangeListener == null) {
            attributeValuesByName = getAttributeValuesByNameFromAction();

            propertyChangeListener = new IPropertyChangeListener() {
                @Override
				public void propertyChange(
                        PropertyChangeEvent propertyChangeEvent) {
                    String property = propertyChangeEvent.getProperty();
                    if (IAction.ENABLED.equals(property)
                            || IAction.CHECKED.equals(property)
                            || IHandlerAttributes.ATTRIBUTE_HANDLED
                                    .equals(property)) {

                        Map previousAttributeValuesByName = attributeValuesByName;
                        attributeValuesByName = getAttributeValuesByNameFromAction();
                        if (!attributeValuesByName
                                .equals(previousAttributeValuesByName)) {
							fireHandlerChanged(new HandlerEvent(
                                    ActionHandler.this, true,
                                    previousAttributeValuesByName));
						}
                    }
                }
            };
        }

        this.action.addPropertyChangeListener(propertyChangeListener);
    }

    private final void detachListener() {
        this.action.removePropertyChangeListener(propertyChangeListener);
        propertyChangeListener = null;
        attributeValuesByName = null;
    }

	@Override
	@Deprecated
    public void dispose() {
        if (hasListeners()) {
            action.removePropertyChangeListener(propertyChangeListener);
        }
    }

   
	@Override
	@Deprecated
    public Object execute(Map parameterValuesByName) throws ExecutionException {
        if ((action.getStyle() == IAction.AS_CHECK_BOX)
                || (action.getStyle() == IAction.AS_RADIO_BUTTON)) {
			action.setChecked(!action.isChecked());
		}
        try {
            action.runWithEvent(new Event());
        } catch (Exception e) {
            throw new ExecutionException(
                    "While executing the action, an exception occurred", e); //$NON-NLS-1$
        }
        return null;
    }

	@Deprecated
    public IAction getAction() {
        return action;
    }

	@Override
	@Deprecated
    public Map getAttributeValuesByName() {
        if (attributeValuesByName == null) {
            return getAttributeValuesByNameFromAction();
        }

        return attributeValuesByName;
    }

    private Map getAttributeValuesByNameFromAction() {
        Map map = new HashMap();
        map.put(ATTRIBUTE_CHECKED, action.isChecked() ? Boolean.TRUE
                : Boolean.FALSE);
        map.put(ATTRIBUTE_ENABLED, action.isEnabled() ? Boolean.TRUE
                : Boolean.FALSE);
        boolean handled = true;
        if (action instanceof RetargetAction) {
            RetargetAction retargetAction = (RetargetAction) action;
            handled = retargetAction.getActionHandler() != null;
        }
        map.put(ATTRIBUTE_HANDLED, handled ? Boolean.TRUE : Boolean.FALSE);
        map.put(ATTRIBUTE_ID, action.getId());
        map.put(ATTRIBUTE_STYLE, new Integer(action.getStyle()));
        return Collections.unmodifiableMap(map);
    }

	@Override
	@Deprecated
    public void removeHandlerListener(IHandlerListener handlerListener) {
        super.removeHandlerListener(handlerListener);

        if (!hasListeners()) {
            detachListener();
        }
    }
	
	@Override
	@Deprecated
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();

		buffer.append("ActionHandler(action="); //$NON-NLS-1$
		buffer.append(action);
		buffer.append(')');

		return buffer.toString();
	}
}
