package org.eclipse.ui.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.core.commands.IHandlerAttributes;
import org.eclipse.core.commands.IHandlerListener;

@Deprecated
@SuppressWarnings("all")
public abstract class AbstractHandler extends
        org.eclipse.core.commands.AbstractHandler implements IHandler {

    private List handlerListeners;

	@Override
	@Deprecated
    public void addHandlerListener(
            org.eclipse.ui.commands.IHandlerListener handlerListener) {
        if (handlerListener == null) {
			throw new NullPointerException();
		}
        if (handlerListeners == null) {
			handlerListeners = new ArrayList();
		}
        if (!handlerListeners.contains(handlerListener)) {
			handlerListeners.add(handlerListener);
		}
    }

	@Override
	@Deprecated
    public void dispose() {
    }

	@Override
	@Deprecated
    public Object execute(final ExecutionEvent event) throws ExecutionException {
        try {
            return execute(event.getParameters());
        } catch (final org.eclipse.ui.commands.ExecutionException e) {
            throw new ExecutionException(e.getMessage(), e.getCause());
        }
    }

	@Override
	@Deprecated
    protected void fireHandlerChanged(HandlerEvent handlerEvent) {
        super.fireHandlerChanged(handlerEvent);
        
        if (handlerListeners != null) {
            final boolean attributesChanged = handlerEvent.isEnabledChanged()
                    || handlerEvent.isHandledChanged();
            final Map previousAttributes;
            if (attributesChanged) {
                previousAttributes = new HashMap();
                previousAttributes.putAll(getAttributeValuesByName());
                if (handlerEvent.isEnabledChanged()) {
                	Boolean disabled = !isEnabled() ? Boolean.TRUE: Boolean.FALSE;
                    previousAttributes
                            .put("enabled", disabled); //$NON-NLS-1$
                }
                if (handlerEvent.isHandledChanged()) {
                	Boolean notHandled = !isHandled() ? Boolean.TRUE: Boolean.FALSE;
                    previousAttributes.put(
                            IHandlerAttributes.ATTRIBUTE_HANDLED, notHandled);
                }
            } else {
                previousAttributes = null;
            }
            final org.eclipse.ui.commands.HandlerEvent legacyEvent = new org.eclipse.ui.commands.HandlerEvent(
                    this, attributesChanged, previousAttributes);

            for (int i = 0; i < handlerListeners.size(); i++) {
                ((org.eclipse.ui.commands.IHandlerListener) handlerListeners
                        .get(i)).handlerChanged(legacyEvent);
            }
        }
    }

	@Deprecated
    protected void fireHandlerChanged(
            final org.eclipse.ui.commands.HandlerEvent handlerEvent) {
        if (handlerEvent == null) {
			throw new NullPointerException();
		}

        if (handlerListeners != null) {
            for (int i = 0; i < handlerListeners.size(); i++) {
				((org.eclipse.ui.commands.IHandlerListener) handlerListeners
                        .get(i)).handlerChanged(handlerEvent);
			}
        }

        if (super.hasListeners()) {
            final boolean enabledChanged;
            final boolean handledChanged;
            if (handlerEvent.haveAttributeValuesByNameChanged()) {
                Map previousAttributes = handlerEvent
                        .getPreviousAttributeValuesByName();

                Object attribute = previousAttributes.get("enabled"); //$NON-NLS-1$
                if (attribute instanceof Boolean) {
                    enabledChanged = ((Boolean) attribute).booleanValue();
                } else {
                    enabledChanged = false;
                }

                attribute = previousAttributes
                        .get(IHandlerAttributes.ATTRIBUTE_HANDLED);
                if (attribute instanceof Boolean) {
                    handledChanged = ((Boolean) attribute).booleanValue();
                } else {
                    handledChanged = false;
                }
            } else {
                enabledChanged = false;
                handledChanged = true;
            }
            final HandlerEvent newEvent = new HandlerEvent(this,
                    enabledChanged, handledChanged);
            super.fireHandlerChanged(newEvent);
        }
    }

	@Override
	@Deprecated
    public Map getAttributeValuesByName() {
        return Collections.EMPTY_MAP;
    }
    
	@Override
	@Deprecated
    protected final boolean hasListeners() {
        return super.hasListeners() || handlerListeners != null;
    }

	@Override
	@Deprecated
    public boolean isEnabled() {
        final Object handled = getAttributeValuesByName().get("enabled"); //$NON-NLS-1$
        if (handled instanceof Boolean) {
            return ((Boolean) handled).booleanValue();
        }

        return false;
    }

	@Override
	@Deprecated
    public boolean isHandled() {
        final Object handled = getAttributeValuesByName().get(
                IHandlerAttributes.ATTRIBUTE_HANDLED);
        if (handled instanceof Boolean) {
            return ((Boolean) handled).booleanValue();
        }

        return false;
    }

	@Override
	@Deprecated
    public void removeHandlerListener(
            org.eclipse.ui.commands.IHandlerListener handlerListener) {
        if (handlerListener == null) {
			throw new NullPointerException();
		}
        if (handlerListeners == null) {
            return;
        }
        
        if (handlerListeners != null) {
			handlerListeners.remove(handlerListener);
		}
        if (handlerListeners.isEmpty()) {
            handlerListeners = null;
        }
    }
}
