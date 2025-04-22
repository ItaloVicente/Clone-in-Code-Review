
package org.eclipse.ui.internal.services;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;
import org.eclipse.ui.internal.ActionSetsEvent;
import org.eclipse.ui.internal.menus.IActionSetsListener;
import org.eclipse.ui.internal.registry.IActionSetDescriptor;
import org.eclipse.ui.internal.util.Util;

public final class ActionSetSourceProvider extends AbstractSourceProvider
		implements IActionSetsListener {

	private static final String[] PROVIDED_SOURCE_NAMES = new String[] { ISources.ACTIVE_ACTION_SETS_NAME };

	private IActionSetDescriptor[] activeActionSets;

	public ActionSetSourceProvider() {
		super();
	}

	@Override
	public final void actionSetsChanged(final ActionSetsEvent event) {
		final IActionSetDescriptor[] newActionSets = event.getNewActionSets();
		if (!Util.equals(newActionSets, activeActionSets)) {
			if (DEBUG) {
				final StringBuffer message = new StringBuffer();
				message.append("Action sets changed to ["); //$NON-NLS-1$
				if (newActionSets != null) {
					for (int i = 0; i < newActionSets.length; i++) {
						message.append(newActionSets[i].getLabel());
						if (i < newActionSets.length - 1) {
							message.append(", "); //$NON-NLS-1$
						}
					}
				}
				message.append(']');
				logDebuggingInfo(message.toString());
			}

			activeActionSets = newActionSets;
			fireSourceChanged(ISources.ACTIVE_ACTION_SETS,
					ISources.ACTIVE_ACTION_SETS_NAME, activeActionSets);

		}
	}

	@Override
	public final void dispose() {
		activeActionSets = null;
	}

	@Override
	public final Map getCurrentState() {
		final Map currentState = new HashMap();
		currentState.put(ISources.ACTIVE_ACTION_SETS_NAME, activeActionSets);
		return currentState;
	}

	@Override
	public final String[] getProvidedSourceNames() {
		return PROVIDED_SOURCE_NAMES;
	}
}
