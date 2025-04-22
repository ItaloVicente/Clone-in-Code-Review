package org.eclipse.ui.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.commands.contexts.Context;
import org.eclipse.e4.ui.services.EContextService;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.INestableKeyBindingService;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.LegacyHandlerSubmissionExpression;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.actions.CommandAction;
import org.eclipse.ui.internal.handlers.CommandLegacyActionWrapper;

public final class KeyBindingService implements INestableKeyBindingService {
    private boolean disposed;

	private final Map<IWorkbenchSite, IKeyBindingService> nestedServices = new HashMap<IWorkbenchSite, IKeyBindingService>();

	private Set<String> enabledContextIds = Collections.EMPTY_SET;


    private IWorkbenchPartSite workbenchPartSite;

	private KeyBindingService parent;

	private IKeyBindingService activeService;

	private Map<IAction, IHandlerActivation> actionToProxy = new HashMap<IAction, IHandlerActivation>();

    public KeyBindingService(IWorkbenchPartSite workbenchPartSite) {
        this(workbenchPartSite, null);
    }

    KeyBindingService(IWorkbenchPartSite workbenchPartSite,
            KeyBindingService parent) {
        this.workbenchPartSite = workbenchPartSite;
		this.parent = parent;
    }

    @Override
	public boolean activateKeyBindingService(IWorkbenchSite nestedSite) {
        if (disposed) {
			return false;
		}

		if (nestedSite == null) {
			if (activeService == null) {
				return false;
			}
			deactivateNestedService();
			return true;
		}

		final IKeyBindingService service = nestedServices.get(nestedSite);

		if (service == activeService) {
			return false;
		}

		deactivateNestedService();
		if (service != null) {
			activateNestedService(service);
		}
        return true;
    }

	private void activateNestedService(IKeyBindingService service) {
		boolean active = false;
		boolean haveParent = (parent != null);
		if (haveParent) {
			active = (parent.activeService == this);
			if (active) {
				parent.deactivateNestedService();
			}
		}

		activeService = service;

		if (service == null) {
			return;
		}

        if (haveParent) {
			if (active) {
				parent.activateNestedService(this);
			}

		} else if (activeService instanceof KeyBindingService) {

			EContextService cs = ((KeyBindingService) activeService).workbenchPartSite
					.getService(EContextService.class);
			for (String id : ((KeyBindingService) activeService).enabledContextIds) {
				cs.activateContext(id);
			}
			IHandlerService hs = ((KeyBindingService) activeService).workbenchPartSite
					.getService(IHandlerService.class);
			Iterator<Entry<IAction, IHandlerActivation>> i = ((KeyBindingService) activeService).actionToProxy
					.entrySet().iterator();
			while (i.hasNext()) {
				Entry<IAction, IHandlerActivation> entry = i.next();
				hs.activateHandler(entry.getValue());
			}
		}
	}

	private void deactivateNestedService() {
		if (disposed) {
			return;
		}

		if (activeService == null) {
			return;
		}

		boolean active = false;
		if (parent != null) {
			if (parent.activeService == this) {
				active = true;
				parent.deactivateNestedService();
			}

		} else if (activeService instanceof KeyBindingService) {

			EContextService cs = ((KeyBindingService) activeService).workbenchPartSite
					.getService(EContextService.class);
			for (String id : ((KeyBindingService) activeService).enabledContextIds) {
				cs.deactivateContext(id);
			}
			IHandlerService hs = ((KeyBindingService) activeService).workbenchPartSite
					.getService(IHandlerService.class);
			hs.deactivateHandlers(((KeyBindingService) activeService).actionToProxy.values());
		}

		activeService = null;

		if (active) {
			parent.activateNestedService(this);
		}
	}

	public void dispose() {
		if (!disposed) {
			disposed = true;
			deactivateNestedService();
			EContextService cs = workbenchPartSite
					.getService(EContextService.class);
			for (String id : enabledContextIds) {
				cs.deactivateContext(id);
			}
			enabledContextIds.clear();
			IHandlerService hs = workbenchPartSite
					.getService(IHandlerService.class);
			hs.deactivateHandlers(actionToProxy.values());
			actionToProxy.clear();
		}

	}


	@Override
	public IKeyBindingService getKeyBindingService(IWorkbenchSite nestedSite) {
		if (disposed) {
			return null;
		}

		if (nestedSite == null) {
			return null;
		}

		IKeyBindingService service = nestedServices.get(nestedSite);
		if (service == null) {
			if (nestedSite instanceof IWorkbenchPartSite) {
				service = new KeyBindingService((IWorkbenchPartSite) nestedSite, this);
			} else {
				service = new KeyBindingService(null, this);
			}

			nestedServices.put(nestedSite, service);
		}

		return service;
	}

    @Override
	public String[] getScopes() {
        if (disposed) {
			return null;
		}

		final Set<String> activeScopes = new HashSet<String>();
        activeScopes.addAll(enabledContextIds);
		if (activeService instanceof KeyBindingService) {
			activeScopes.addAll(((KeyBindingService) activeService).enabledContextIds);
		}

		return activeScopes.toArray(new String[activeScopes.size()]);
    }

    @Override
	public void registerAction(IAction action) {
        if (disposed) {
			return;
		}
        
        if (action instanceof CommandLegacyActionWrapper) {
			WorkbenchPlugin
					.log("Cannot register a CommandLegacyActionWrapper back into the system"); //$NON-NLS-1$
			return;
        }
        
        if (action instanceof CommandAction) {
			return;
        }

        unregisterAction(action);

		IWorkbenchPartSite partSite = workbenchPartSite;
		if (parent != null) {
			KeyBindingService currentParent = parent;
			while (currentParent != null) {
				partSite = currentParent.workbenchPartSite;
				currentParent = currentParent.parent;
			}
		}

		String commandId = action.getActionDefinitionId();
		if (commandId != null) {
			for (IAction registeredAction : actionToProxy.keySet()) {
				if (commandId.equals(registeredAction.getActionDefinitionId())) {
					unregisterAction(registeredAction);
					break;
				}
			}

			IHandlerService hs = workbenchPartSite
					.getService(IHandlerService.class);
			actionToProxy.put(action, hs.activateHandler(commandId, new ActionHandler(action),
					new LegacyHandlerSubmissionExpression(null, partSite.getShell(), partSite)));

		}
    }

	@Override
	public boolean removeKeyBindingService(IWorkbenchSite nestedSite) {
		if (disposed) {
			return false;
		}

		final IKeyBindingService service = nestedServices.remove(nestedSite);
		if (service == null) {
			return false;
		}

		if (service.equals(activeService)) {
			deactivateNestedService();
		}

		return true;
	}

    @Override
	public void setScopes(String[] scopes) {
        if (disposed) {
			return;
		}
		Set<String> oldContextIds = enabledContextIds;
		enabledContextIds = new HashSet<String>(Arrays.asList(scopes));
		EContextService cs = workbenchPartSite.getService(EContextService.class);
		addParents(cs, scopes);
		
		for (String id : oldContextIds) {
			if (!enabledContextIds.contains(id)) {
				cs.deactivateContext(id);
			}
        }
		for (String id : enabledContextIds) {
			if (!oldContextIds.contains(id)) {
				cs.activateContext(id);
			}
		}
    }

	private void addParents(EContextService cs, String[] scopes) {
		for (String id : scopes) {
			try {
				Context current = cs.getContext(id);
				String parentId = current.getParentId();
				while (parentId != null && !enabledContextIds.contains(parentId)) {
					enabledContextIds.add(parentId);
					current = cs.getContext(parentId);
					parentId = current.getParentId();
				}
			} catch (NotDefinedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void unregisterAction(IAction action) {
		if (disposed) {
			return;
		}

		if (action instanceof CommandLegacyActionWrapper) {
			WorkbenchPlugin.log("Cannot unregister a CommandLegacyActionWrapper out of the system"); //$NON-NLS-1$
			return;
		}

		IHandlerActivation activation = actionToProxy.remove(action);
		if (activation == null) {
			return;
		}
		IHandlerService hs = workbenchPartSite.getService(IHandlerService.class);
		hs.deactivateHandler(activation);
	}
}
