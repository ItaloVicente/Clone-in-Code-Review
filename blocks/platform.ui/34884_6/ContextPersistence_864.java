
package org.eclipse.ui.internal.contexts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.commands.contexts.Context;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.ui.contexts.ContextManagerEvent;
import org.eclipse.ui.contexts.IContext;
import org.eclipse.ui.contexts.IContextManager;
import org.eclipse.ui.contexts.IContextManagerListener;

public final class ContextManagerLegacyWrapper implements
		org.eclipse.core.commands.contexts.IContextManagerListener,
		IContextManager {

	private class ContextIdDepthComparator implements Comparator {

		@Override
		public final int compare(final Object object1, final Object object2) {
			final String contextId1 = (String) object1;
			final String contextId2 = (String) object2;
			Context context;
			String parentId;

			int depth1 = 0;
			context = contextManager.getContext(contextId1);
			try {
				parentId = context.getParentId();
				while (parentId != null) {
					depth1++;
					context = contextManager.getContext(parentId);
					parentId = context.getParentId();
				}
			} catch (final NotDefinedException e) {
			}

			int depth2 = 0;
			context = contextManager.getContext(contextId2);
			try {
				parentId = context.getParentId();
				while (parentId != null) {
					depth2++;
					context = contextManager.getContext(parentId);
					parentId = context.getParentId();
				}
			} catch (final NotDefinedException e) {
			}

			int compare = depth2 - depth1;
			if (compare == 0) {
				compare = contextId1.compareTo(contextId2);
			}

			return compare;
		}
	}

	private class DepthSortedContextIdSet extends TreeSet {

		private static final long serialVersionUID = 3257291326872892465L;

		private DepthSortedContextIdSet(final Set contextIds) {
			super(new ContextIdDepthComparator());
			addAll(contextIds);
		}
	}

	private final ContextManager contextManager;

	private List contextManagerListeners;

	public ContextManagerLegacyWrapper(ContextManager contextManager) {

		if (contextManager == null) {
			throw new NullPointerException("The context manager cannot be null"); //$NON-NLS-1$
		}

		this.contextManager = contextManager;
		this.contextManager.addContextManagerListener(this);
	}

	@Override
	public void addContextManagerListener(
			IContextManagerListener contextManagerListener) {
		if (contextManagerListener == null) {
			throw new NullPointerException();
		}

		if (contextManagerListeners == null) {
			contextManagerListeners = new ArrayList();
		}

		if (!contextManagerListeners.contains(contextManagerListener)) {
			contextManagerListeners.add(contextManagerListener);
		}
	}

	@Override
	public void contextManagerChanged(
			org.eclipse.core.commands.contexts.ContextManagerEvent contextManagerEvent) {
		final String contextId = contextManagerEvent.getContextId();
		final boolean definedContextsChanged;
		final Set previouslyDefinedContextIds;
		if (contextId == null) {
			definedContextsChanged = false;
			previouslyDefinedContextIds = null;
		} else {
			definedContextsChanged = true;
			previouslyDefinedContextIds = new HashSet();
			previouslyDefinedContextIds.addAll(contextManager
					.getDefinedContextIds());
			if (contextManagerEvent.isContextDefined()) {
				previouslyDefinedContextIds.remove(contextId);
			} else {
				previouslyDefinedContextIds.add(contextId);
			}
		}

		fireContextManagerChanged(new ContextManagerEvent(this,
				definedContextsChanged, contextManagerEvent
						.isActiveContextsChanged(),
				previouslyDefinedContextIds, contextManagerEvent
						.getPreviouslyActiveContextIds()));

	}

	protected void fireContextManagerChanged(
			ContextManagerEvent contextManagerEvent) {
		if (contextManagerEvent == null) {
			throw new NullPointerException();
		}

		if (contextManagerListeners != null) {
			for (int i = 0; i < contextManagerListeners.size(); i++) {
				((IContextManagerListener) contextManagerListeners.get(i))
						.contextManagerChanged(contextManagerEvent);
			}
		}
	}

	@Override
	public IContext getContext(String contextId) {
		return new ContextLegacyWrapper(contextManager.getContext(contextId),
				contextManager);
	}

	@Override
	public SortedSet getDefinedContextIds() {
		return new DepthSortedContextIdSet(contextManager
				.getDefinedContextIds());
	}

	@Override
	public SortedSet getEnabledContextIds() {
		return new DepthSortedContextIdSet(contextManager.getActiveContextIds());
	}

	@Override
	public void removeContextManagerListener(
			IContextManagerListener contextManagerListener) {
		if (contextManagerListener == null) {
			throw new NullPointerException();
		}

		if (contextManagerListeners != null) {
			contextManagerListeners.remove(contextManagerListener);
		}
	}

	public void setEnabledContextIds(Set enabledContextIds) {
		contextManager.setActiveContextIds(enabledContextIds);
	}
}
