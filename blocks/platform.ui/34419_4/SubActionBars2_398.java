package org.eclipse.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.SubMenuManager;
import org.eclipse.jface.action.SubStatusLineManager;
import org.eclipse.jface.action.SubToolBarManager;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.EditorActionBars;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.actions.CommandAction;
import org.eclipse.ui.internal.handlers.CommandLegacyActionWrapper;
import org.eclipse.ui.internal.handlers.IActionCommandMappingService;
import org.eclipse.ui.internal.services.SourcePriorityNameMapping;
import org.eclipse.ui.services.IServiceLocator;

public class SubActionBars extends EventManager implements IActionBars {

	private static final Expression EXPRESSION = new Expression() {
		@Override
		public final EvaluationResult evaluate(final IEvaluationContext context) {
			return EvaluationResult.TRUE;
		}

		@Override
		public final void collectExpressionInfo(final ExpressionInfo info) {
			info
					.addVariableNameAccess(SourcePriorityNameMapping.LEGACY_LEGACY_NAME);
		}
	};

	public static final String P_ACTION_HANDLERS = "org.eclipse.ui.internal.actionHandlers"; //$NON-NLS-1$

	private Map actionHandlers;

	private boolean actionHandlersChanged;

	private Map activationsByActionIdByServiceLocator;

	private boolean active = false;

	private SubMenuManager menuMgr;

	private IActionBars parent;

	private IServiceLocator serviceLocator;

	private SubStatusLineManager statusLineMgr;

	private SubToolBarManager toolBarMgr;

	private Map actionIdByCommandId = new HashMap();

	public SubActionBars(final IActionBars parent) {
		this(parent, null);
	}

	public SubActionBars(final IActionBars parent,
			final IServiceLocator serviceLocator) {
		if (parent == null) {
			throw new NullPointerException("The parent cannot be null"); //$NON-NLS-1$
		}

		this.parent = parent;
		this.serviceLocator = serviceLocator;
	}

	public void activate() {
		activate(true);
	}

	public void activate(boolean forceVisibility) {
		setActive(true);
	}

	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		addListenerObject(listener);
	}

	protected final void basicSetActive(boolean active) {
		this.active = active;
	}

	@Override
	public void clearGlobalActionHandlers() {
		if (actionHandlers != null) {
			actionHandlers.clear();
			actionHandlersChanged = true;
		}

		if (activationsByActionIdByServiceLocator != null) {
			final Iterator activationItr = activationsByActionIdByServiceLocator
					.entrySet().iterator();
			while (activationItr.hasNext()) {
				final Map.Entry value = (Map.Entry) activationItr.next();
				final IServiceLocator locator = (IServiceLocator) value
						.getKey();
				final IHandlerService service = locator
						.getService(IHandlerService.class);
				final Map activationsByActionId = (Map) value.getValue();
				Collection activations = activationsByActionId.values();
				if (service != null) {
					service.deactivateHandlers(activations);
				}

				for (Object activation : activations) {
					((IHandlerActivation) activation).getHandler().dispose();
				}
			}
			activationsByActionIdByServiceLocator.clear();
		}
	}

	protected SubMenuManager createSubMenuManager(IMenuManager parent) {
		return new SubMenuManager(parent);
	}

	protected SubToolBarManager createSubToolBarManager(IToolBarManager parent) {
		return new SubToolBarManager(parent);
	}

	public void deactivate() {
		deactivate(true);
	}

	public void deactivate(boolean forceHide) {
		setActive(false);
	}

	public void dispose() {
		clearGlobalActionHandlers();
		if (menuMgr != null) {
			menuMgr.dispose();
			menuMgr.disposeManager();
		}
		if (statusLineMgr != null) {
			statusLineMgr.disposeManager();
		}
		if (toolBarMgr != null) {
			toolBarMgr.disposeManager();
		}
		clearListeners();
	}

	protected void fireActionHandlersChanged() {
		if (actionHandlersChanged) {
			firePropertyChange(new PropertyChangeEvent(this, P_ACTION_HANDLERS,
					null, null));
			actionHandlersChanged = false;
		}
	}

	protected void firePropertyChange(PropertyChangeEvent event) {
		Object[] listeners = getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			((IPropertyChangeListener) listeners[i]).propertyChange(event);
		}
	}

	protected final boolean getActive() {
		return active;
	}

	@Override
	public IAction getGlobalActionHandler(String actionID) {
		if (actionHandlers == null) {
			return null;
		}
		return (IAction) actionHandlers.get(actionID);
	}

	public Map getGlobalActionHandlers() {
		return actionHandlers;
	}

	@Override
	public IMenuManager getMenuManager() {
		if (menuMgr == null) {
			menuMgr = createSubMenuManager(parent.getMenuManager());
			menuMgr.setVisible(active);
		}
		return menuMgr;
	}

	protected final IActionBars getParent() {
		return parent;
	}

	@Override
	public final IServiceLocator getServiceLocator() {
		if (serviceLocator != null) {
			return serviceLocator;
		}

		return parent.getServiceLocator();
	}

	@Override
	public IStatusLineManager getStatusLineManager() {
		if (statusLineMgr == null) {
			statusLineMgr = new SubStatusLineManager(parent
					.getStatusLineManager());
			statusLineMgr.setVisible(active);
		}
		return statusLineMgr;
	}

	@Override
	public IToolBarManager getToolBarManager() {
		if (toolBarMgr == null) {
			toolBarMgr = createSubToolBarManager(parent.getToolBarManager());
			toolBarMgr.setVisible(active);
		}
		return toolBarMgr;
	}

	protected final boolean isSubMenuManagerCreated() {
		return menuMgr != null;
	}

	protected final boolean isSubStatusLineManagerCreated() {
		return statusLineMgr != null;
	}

	protected final boolean isSubToolBarManagerCreated() {
		return toolBarMgr != null;
	}

	public void partChanged(IWorkbenchPart part) {
	}

	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		removeListenerObject(listener);
	}

	protected void setActive(boolean set) {
		active = set;
		if (menuMgr != null) {
			menuMgr.setVisible(set);
		}

		if (statusLineMgr != null) {
			statusLineMgr.setVisible(set);
		}

		if (toolBarMgr != null) {
			toolBarMgr.setVisible(set);
		}
	}

	@Override
	public void setGlobalActionHandler(String actionID, IAction handler) {
		if (actionID == null) {
			WorkbenchPlugin
					.log("Cannot set the global action handler for a null action id"); //$NON-NLS-1$
			return;
		}
		
		if (handler instanceof CommandLegacyActionWrapper) {
			WorkbenchPlugin
					.log("Cannot feed a CommandLegacyActionWrapper back into the system"); //$NON-NLS-1$
			return;
		}
		
		if (handler instanceof CommandAction) {
			return;
		}
		
		if (handler != null) {
			if (actionHandlers == null) {
				actionHandlers = new HashMap(11);
			}
			actionHandlers.put(actionID, handler);

			if (serviceLocator != null) {
				String commandId = null;
				final IActionCommandMappingService mappingService = serviceLocator
						.getService(IActionCommandMappingService.class);
				if (mappingService != null) {
					commandId = mappingService.getCommandId(actionID);
				}
				if (commandId == null) {
					commandId = handler.getActionDefinitionId();
				}

				final IHandlerService service = serviceLocator
						.getService(IHandlerService.class);
				Map activationsByActionId = null;
				if (activationsByActionIdByServiceLocator == null) {
					activationsByActionIdByServiceLocator = new WeakHashMap();
					activationsByActionId = new HashMap();
					activationsByActionIdByServiceLocator.put(serviceLocator,
							activationsByActionId);
				} else {
					activationsByActionId = (Map) activationsByActionIdByServiceLocator
							.get(serviceLocator);
					if (activationsByActionId == null) {
						activationsByActionId = new HashMap();
						activationsByActionIdByServiceLocator.put(
								serviceLocator, activationsByActionId);
					} else if (activationsByActionId.containsKey(actionID)) {
						final Object value = activationsByActionId
								.remove(actionID);
						if (value instanceof IHandlerActivation) {
							final IHandlerActivation activation = (IHandlerActivation) value;
							actionIdByCommandId.remove(activation.getCommandId());
							if (service != null) {
								service.deactivateHandler(activation);
							}
							activation.getHandler().dispose();
						}
					} else if (commandId != null
							&& actionIdByCommandId.containsKey(commandId)) {
						final Object value = activationsByActionId
								.remove(actionIdByCommandId.remove(commandId));
						if (value instanceof IHandlerActivation) {
							final IHandlerActivation activation = (IHandlerActivation) value;
							if (service != null) {
								service.deactivateHandler(activation);
							}
							activation.getHandler().dispose();
						}
					}
				}

				if (commandId != null) {
					actionIdByCommandId.put(commandId, actionID);
					final IHandler actionHandler = new ActionHandler(handler);
					Expression handlerExpression = EXPRESSION;
					if (this instanceof EditorActionBars) {
						handlerExpression = ((EditorActionBars)this).getHandlerExpression();
					}
					if (service != null) {
						final IHandlerActivation activation = service
								.activateHandler(commandId, actionHandler,
										handlerExpression);
						activationsByActionId.put(actionID, activation);
					}
				}
			}

		} else {
			if (actionHandlers != null) {
				actionHandlers.remove(actionID);
			}

			if (serviceLocator != null) {
				final IHandlerService service = serviceLocator
						.getService(IHandlerService.class);
				if (activationsByActionIdByServiceLocator != null) {
					final Map activationsByActionId = (Map) activationsByActionIdByServiceLocator
							.get(serviceLocator);
					if ((activationsByActionId != null)
							&& (activationsByActionId.containsKey(actionID))) {
						final Object value = activationsByActionId
								.remove(actionID);
						if (value instanceof IHandlerActivation) {
							final IHandlerActivation activation = (IHandlerActivation) value;
							actionIdByCommandId.remove(activation.getCommandId());
							service.deactivateHandler(activation);
							activation.getHandler().dispose();
						}
					}
				}
			}
		}
		actionHandlersChanged = true;
	}

	protected final void setServiceLocator(final IServiceLocator locator) {
		if (locator == null) {
			throw new NullPointerException("The service locator cannot be null"); //$NON-NLS-1$
		}
		this.serviceLocator = locator;
	}

	@Override
	public void updateActionBars() {
		parent.updateActionBars();
		fireActionHandlersChanged();
	}
}
