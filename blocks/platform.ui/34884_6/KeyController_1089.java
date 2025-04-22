
package org.eclipse.ui.internal.keys.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.commands.contexts.Context;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.services.IServiceLocator;

public class ContextModel extends CommonModel {
	private static final String CONTEXT_ID_ACTION_SETS = "org.eclipse.ui.contexts.actionSet"; //$NON-NLS-1$
	private static final String CONTEXT_ID_INTERNAL = ".internal."; //$NON-NLS-1$

	public static final String PROP_CONTEXTS = "contexts"; //$NON-NLS-1$
	public static final String PROP_CONTEXT_MAP = "contextIdElementMap"; //$NON-NLS-1$
	private List contexts;
	private Map contextIdToFilteredContexts;
	private Map contextIdToElement;
	private IContextService contextService;

	public ContextModel(KeyController kc) {
		super(kc);
	}

	public void init(IServiceLocator locator) {
		contextService = locator
				.getService(IContextService.class);
		contexts = new ArrayList();
		contextIdToFilteredContexts = new HashMap();
		contextIdToElement = new HashMap();

		Context[] definedContexts = contextService.getDefinedContexts();
		for (int i = 0; i < definedContexts.length; i++) {
			ContextElement ce = new ContextElement(controller);
			ce.init(definedContexts[i]);
			ce.setParent(this);
			contexts.add(ce);
			contextIdToElement.put(definedContexts[i].getId(), ce);
		}
	}

	public List getContexts() {
		return contexts;
	}

	public void setContexts(List contexts) {
		List old = this.contexts;
		this.contexts = contexts;
		controller.firePropertyChange(this, PROP_CONTEXTS, old, contexts);
	}

	public Map getContextIdToElement() {
		return contextIdToElement;
	}

	public void setContextIdToElement(Map contextToElement) {
		Map old = this.contextIdToElement;
		this.contextIdToElement = contextToElement;
		controller.firePropertyChange(this, PROP_CONTEXT_MAP, old,
				contextToElement);
	}

	public void filterContexts(boolean actionSets, boolean internal) {
		for (int i = 0; i < contexts.size(); i++) {
			boolean removeContext = false;
			ContextElement contextElement = (ContextElement) contexts.get(i);

			if (actionSets == true
					&& contextElement.getId().equalsIgnoreCase(
							CONTEXT_ID_ACTION_SETS)) {
				removeContext = true;
			} else {
				String parentId;
				try {
					parentId = ((Context) contextElement.getModelObject())
							.getParentId();
					while (parentId != null) {
						if (parentId.equalsIgnoreCase(CONTEXT_ID_ACTION_SETS)) {
							removeContext = true;
						}
						parentId = contextService.getContext(parentId)
								.getParentId();
					}
				} catch (NotDefinedException e) {
				}
			}

			if (internal == true
					&& contextElement.getId().indexOf(CONTEXT_ID_INTERNAL) != -1) {
				removeContext = true;
			}

			if (removeContext) {
				contextIdToFilteredContexts.put(contextElement.getId(),
						contextElement);
				contextIdToElement.remove(contextElement);
			}
		}

		contexts.removeAll(contextIdToFilteredContexts.values());

		Iterator iterator = contextIdToFilteredContexts.keySet().iterator();
		while (iterator.hasNext()) {
			boolean restoreContext = false;
			ContextElement contextElement = (ContextElement) contextIdToFilteredContexts
					.get(iterator.next());

			try {
				if (actionSets == false) {
					if (contextElement.getId().equalsIgnoreCase(CONTEXT_ID_ACTION_SETS)) {
						restoreContext = true;
					} else {
						String parentId = ((Context) contextElement.getModelObject()).getParentId();
						if (parentId != null && parentId.equalsIgnoreCase(CONTEXT_ID_ACTION_SETS)) {
							restoreContext = true;
						}
					}
				}
			} catch (NotDefinedException e) {
			}
			if (internal == false
					&& contextElement.getId().indexOf(CONTEXT_ID_INTERNAL) != -1) {
				restoreContext = true;
			}

			if (restoreContext) {
				contexts.add(contextElement);
				contextIdToElement.put(contextElement.getId(), contextElement);
				iterator.remove();
			}
		}
	}
}
