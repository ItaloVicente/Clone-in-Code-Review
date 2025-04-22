
package org.eclipse.ui.internal.services;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.ui.ISources;

public final class SourcePriorityNameMapping implements ISources {

	public static final String LEGACY_LEGACY_NAME = "LEGACY"; //$NON-NLS-1$

	public static final int NO_SOURCE_PRIORITY = 0;

	private static final Map sourcePrioritiesByName = new HashMap();

	static {
		addMapping(ACTIVE_ACTION_SETS_NAME, ACTIVE_ACTION_SETS);
		addMapping(ACTIVE_CONTEXT_NAME, ACTIVE_CONTEXT);
		addMapping(ACTIVE_CURRENT_SELECTION_NAME, ACTIVE_CURRENT_SELECTION);
		addMapping(ACTIVE_EDITOR_NAME, ACTIVE_EDITOR);
		addMapping(ACTIVE_EDITOR_INPUT_NAME, ACTIVE_EDITOR);
		addMapping(ACTIVE_EDITOR_ID_NAME, ACTIVE_EDITOR_ID);
		addMapping(ACTIVE_MENU_NAME, ACTIVE_MENU);
		addMapping(ACTIVE_MENU_SELECTION_NAME, ACTIVE_MENU);
		addMapping(ACTIVE_MENU_EDITOR_INPUT_NAME, ACTIVE_MENU);
		addMapping(ACTIVE_FOCUS_CONTROL_ID_NAME, ACTIVE_MENU);
		addMapping(ACTIVE_FOCUS_CONTROL_NAME, ACTIVE_MENU);
		addMapping(ACTIVE_PART_NAME, ACTIVE_PART);
		addMapping(ACTIVE_PART_ID_NAME, ACTIVE_PART_ID);
		addMapping(ACTIVE_SHELL_NAME, ACTIVE_SHELL);
		addMapping(ACTIVE_SITE_NAME, ACTIVE_SITE);
		addMapping(ACTIVE_WORKBENCH_WINDOW_NAME, ACTIVE_WORKBENCH_WINDOW);
		addMapping(ACTIVE_WORKBENCH_WINDOW_SHELL_NAME,
				ACTIVE_WORKBENCH_WINDOW_SHELL);
		addMapping(ACTIVE_WORKBENCH_WINDOW_IS_COOLBAR_VISIBLE_NAME,
				ACTIVE_WORKBENCH_WINDOW_SUBORDINATE);
		addMapping(ACTIVE_WORKBENCH_WINDOW_ACTIVE_PERSPECTIVE_NAME,
				ACTIVE_WORKBENCH_WINDOW_SUBORDINATE);
		addMapping(ACTIVE_WORKBENCH_WINDOW_IS_PERSPECTIVEBAR_VISIBLE_NAME,
				ACTIVE_WORKBENCH_WINDOW_SUBORDINATE);
		addMapping(LEGACY_LEGACY_NAME, LEGACY_LEGACY);
		addMapping("workbench", WORKBENCH); //$NON-NLS-1$
	}

	public static final void addMapping(final String sourceName,
			final int sourcePriority) {
		if (sourceName == null) {
			throw new NullPointerException("The source name cannot be null."); //$NON-NLS-1$
		}

		if (!sourcePrioritiesByName.containsKey(sourceName)) {
			final Integer priority = new Integer(sourcePriority);

			sourcePrioritiesByName.put(sourceName, priority);
		}
	}

	public static final int computeSourcePriority(final Expression expression) {
		int sourcePriority = ISources.WORKBENCH;

		if (expression == null) {
			return sourcePriority;
		}

		final ExpressionInfo info = expression.computeExpressionInfo();

		if (info.hasDefaultVariableAccess()) {
			sourcePriority |= ISources.ACTIVE_CURRENT_SELECTION;
		}

		final String[] sourceNames = info.getAccessedVariableNames();
		for (int i = 0; i < sourceNames.length; i++) {
			final String sourceName = sourceNames[i];
			sourcePriority |= getMapping(sourceName);
		}

		return sourcePriority;
	}

	public static final int getMapping(final String sourceName) {
		final Object object = sourcePrioritiesByName.get(sourceName);
		if (object instanceof Integer) {
			return ((Integer) object).intValue();
		}

		return NO_SOURCE_PRIORITY;
	}

	private SourcePriorityNameMapping() {
	}
}
