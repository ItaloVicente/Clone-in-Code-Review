
package org.eclipse.ui.internal.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISourceProvider;
import org.eclipse.ui.ISourceProviderListener;
import org.eclipse.ui.ISources;

public abstract class ExpressionAuthority implements ISourceProviderListener {

	private final IEvaluationContext context;

	private IEvaluationContext currentState = null;

	private final Collection providers = new ArrayList();

	protected ExpressionAuthority() {
		context = new EvaluationContext(null, this);
		context.setAllowPluginActivation(true);
		context.addVariable("org.eclipse.core.runtime.Platform", Platform.class); //$NON-NLS-1$
	}

	public final void addSourceProvider(final ISourceProvider provider) {
		provider.addSourceProviderListener(this);
		providers.add(provider);

		final Map currentState = provider.getCurrentState();
		final Iterator variableItr = currentState.entrySet().iterator();
		while (variableItr.hasNext()) {
			final Map.Entry entry = (Map.Entry) variableItr.next();
			final String variableName = (String) entry.getKey();
			final Object variableValue = entry.getValue();

			if ((variableName != null)
					&& (!ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME
							.equals(variableName))) {
				changeVariable(variableName, variableValue);
			}
		}
		sourceChanged(0, currentState);
	}

	public void dispose() {
		final Iterator providerItr = providers.iterator();
		while (providerItr.hasNext()) {
			final ISourceProvider provider = (ISourceProvider) providerItr
					.next();
			provider.removeSourceProviderListener(this);
		}

		providers.clear();
	}

	protected final boolean evaluate(final Collection collection) {
		final Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			final IEvaluationResultCache cache = (IEvaluationResultCache) iterator
					.next();
			if (evaluate(cache)) {
				return true;
			}
		}

		return false;
	}

	protected final boolean evaluate(final IEvaluationResultCache expression) {
		final IEvaluationContext contextWithDefaultVariable = getCurrentState();
		return expression.evaluate(contextWithDefaultVariable);
	}

	public final IEvaluationContext getCurrentState() {
		if (currentState == null) {
			final Object defaultVariable = context
					.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
			final IEvaluationContext contextWithDefaultVariable;
			if (defaultVariable instanceof IStructuredSelection) {
				final IStructuredSelection selection = (IStructuredSelection) defaultVariable;
				contextWithDefaultVariable = new EvaluationContext(context,
						selection.toList());
			} else if ((defaultVariable instanceof ISelection)
					&& (!((ISelection) defaultVariable).isEmpty())) {
				contextWithDefaultVariable = new EvaluationContext(context,
						Collections.singleton(defaultVariable));
			} else {
				contextWithDefaultVariable = new EvaluationContext(context,
						Collections.EMPTY_LIST);
			}
			currentState = contextWithDefaultVariable;
		}

		return currentState;
	}

	protected final Object getVariable(final String name) {
		return context.getVariable(name);
	}

	public final void removeSourceProvider(final ISourceProvider provider) {
		provider.removeSourceProviderListener(this);
		providers.remove(provider);

		final Map currentState = provider.getCurrentState();
		final Iterator variableItr = currentState.entrySet().iterator();
		while (variableItr.hasNext()) {
			final Map.Entry entry = (Map.Entry) variableItr.next();
			final String variableName = (String) entry.getKey();
			changeVariable(variableName, null);
		}
	}

	protected final void changeVariable(final String name, final Object value) {
		if (value == null) {
			context.removeVariable(name);
		} else {
			context.addVariable(name, value);
		}
	}

	protected abstract void sourceChanged(final int sourcePriority);

	protected void sourceChanged(final String[] sourceNames) {
	}

	@Override
	public final void sourceChanged(final int sourcePriority,
			final Map sourceValuesByName) {
		if (sourceValuesByName
				.containsKey(ISources.ACTIVE_CURRENT_SELECTION_NAME)) {
			currentState = null;
		}

		final Iterator entryItr = sourceValuesByName.entrySet().iterator();
		while (entryItr.hasNext()) {
			final Map.Entry entry = (Map.Entry) entryItr.next();
			final String sourceName = (String) entry.getKey();
			final Object sourceValue = entry.getValue();
			updateEvaluationContext(sourceName, sourceValue);
		}
		sourceChanged(sourcePriority, (String[]) sourceValuesByName.keySet()
				.toArray(new String[0]));
	}

	@Override
	public final void sourceChanged(final int sourcePriority,
			final String sourceName, final Object sourceValue) {
		if (ISources.ACTIVE_CURRENT_SELECTION_NAME.equals(sourceName)) {
			currentState = null;
		}

		updateEvaluationContext(sourceName, sourceValue);
		sourceChanged(sourcePriority, new String[] { sourceName });
	}

	private void sourceChanged(int sourcePriority, String[] sourceNames) {
		sourceChanged(sourcePriority);
		sourceChanged(sourceNames);
	}

	protected final void updateCurrentState() {
		final Iterator providerItr = providers.iterator();
		while (providerItr.hasNext()) {
			final ISourceProvider provider = (ISourceProvider) providerItr
					.next();
			final Map currentState = provider.getCurrentState();
			final Iterator variableItr = currentState.entrySet().iterator();
			while (variableItr.hasNext()) {
				final Map.Entry entry = (Map.Entry) variableItr.next();
				final String variableName = (String) entry.getKey();
				final Object variableValue = entry.getValue();
				if ((variableName != null)
						&& (!ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME
								.equals(variableName))) {
					changeVariable(variableName, variableValue);
				}
			}
		}
	}

	protected void updateEvaluationContext(final String name, final Object value) {
		if (name != null) {
			changeVariable(name, value);
		}
	}
}
