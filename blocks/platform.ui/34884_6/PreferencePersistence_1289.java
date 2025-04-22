
package org.eclipse.ui.internal.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

public final class MenuSourceProvider extends AbstractSourceProvider {

	private static final String[] PROVIDED_SOURCE_NAMES = new String[] {
			ISources.ACTIVE_MENU_NAME, ISources.ACTIVE_MENU_SELECTION_NAME,
			ISources.ACTIVE_MENU_EDITOR_INPUT_NAME };

	private Set menuIds = new HashSet();

	public final void addShowingMenus(final Set menuIds,
			final ISelection localSelection, final ISelection localEditorInput) {
		this.menuIds.addAll(menuIds);
		if (DEBUG) {
			logDebuggingInfo("Menu ids changed to " + this.menuIds); //$NON-NLS-1$
		}
		Map m = new HashMap();
		m.put(ISources.ACTIVE_MENU_NAME, this.menuIds);
		if (selection != localSelection) {
			selection = localSelection;
			m.put(ISources.ACTIVE_MENU_SELECTION_NAME,
					selection == null ? IEvaluationContext.UNDEFINED_VARIABLE
							: selection);
		}
		if (input != localEditorInput) {
			input = localEditorInput;
			m.put(ISources.ACTIVE_MENU_EDITOR_INPUT_NAME,
					input == null ? IEvaluationContext.UNDEFINED_VARIABLE
							: input);
		}

		fireSourceChanged(ISources.ACTIVE_MENU, m);
	}

	@Override
	public final void dispose() {
		menuIds.clear();
		selection = null;
		input = null;
	}

	@Override
	public final Map getCurrentState() {
		final Map state = new HashMap();
		state.put(ISources.ACTIVE_MENU_NAME, menuIds);
		state.put(ISources.ACTIVE_MENU_SELECTION_NAME,
				selection == null ? IEvaluationContext.UNDEFINED_VARIABLE
						: selection);
		state.put(ISources.ACTIVE_MENU_EDITOR_INPUT_NAME,
				input == null ? IEvaluationContext.UNDEFINED_VARIABLE : input);
		return state;
	}

	@Override
	public final String[] getProvidedSourceNames() {
		return PROVIDED_SOURCE_NAMES;
	}

	public final void removeShowingMenus(final Set menuIds,
			final ISelection localSelection, final ISelection localEditorInput) {
		this.menuIds.removeAll(menuIds);
		if (DEBUG) {
			logDebuggingInfo("Menu ids changed to " + this.menuIds); //$NON-NLS-1$
		}
		Map m = new HashMap();
		m.put(ISources.ACTIVE_MENU_NAME, this.menuIds);
		if (selection != localSelection) {
			selection = localSelection;
			m.put(ISources.ACTIVE_MENU_SELECTION_NAME,
					selection == null ? IEvaluationContext.UNDEFINED_VARIABLE
							: selection);
		}
		if (input != localEditorInput) {
			input = localEditorInput;
			m.put(ISources.ACTIVE_MENU_EDITOR_INPUT_NAME,
					input == null ? IEvaluationContext.UNDEFINED_VARIABLE
							: input);
		}
		fireSourceChanged(ISources.ACTIVE_MENU, m);
	}

	private ISelection selection = null;
	private ISelection input = null;
}
