
package org.eclipse.ui.internal.contexts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.core.commands.util.Tracing;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.ISources;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.internal.misc.Policy;
import org.eclipse.ui.internal.services.ExpressionAuthority;

public final class ContextAuthority extends ExpressionAuthority {


	private static final int ACTIVATIONS_TO_RECOMPUTE_SIZE = 4;

	private static final boolean DEBUG = Policy.DEBUG_CONTEXTS;

	private static final boolean DEBUG_PERFORMANCE = Policy.DEBUG_CONTEXTS_PERFORMANCE;

	private static final String DISPOSE_LISTENER = "org.eclipse.ui.internal.contexts.ContextAuthority"; //$NON-NLS-1$

	private static final String TRACING_COMPONENT = "CONTEXTS"; //$NON-NLS-1$

	private final Set[] activationsBySourcePriority = new Set[33];

	private final Map contextActivationsByContextId = new HashMap();

	private final ContextManager contextManager;

	private final IContextService contextService;

	private final Map registeredWindows = new WeakHashMap();

	ContextAuthority(final ContextManager contextManager,
			final IContextService contextService) {
		if (contextManager == null) {
			throw new NullPointerException(
					"The context authority needs a context manager"); //$NON-NLS-1$
		}
		if (contextService == null) {
			throw new NullPointerException(
					"The context authority needs an evaluation context"); //$NON-NLS-1$
		}

		this.contextManager = contextManager;
		this.contextService = contextService;
	}
	
	final void activateContext(final IContextActivation activation) {
		final String contextId = activation.getContextId();
		final Object value = contextActivationsByContextId.get(contextId);
		if (value instanceof Collection) {
			final Collection contextActivations = (Collection) value;
			if (!contextActivations.contains(activation)) {
				contextActivations.add(activation);
				updateContext(contextId, containsActive(contextActivations));
			}
		} else if (value instanceof IContextActivation) {
			if (value != activation) {
				final Collection contextActivations = new ArrayList(2);
				contextActivations.add(value);
				contextActivations.add(activation);
				contextActivationsByContextId
						.put(contextId, contextActivations);
				updateContext(contextId, containsActive(contextActivations));
			}
		} else {
			contextActivationsByContextId.put(contextId, activation);
			updateContext(contextId, evaluate(activation));
		}

		final int sourcePriority = activation.getSourcePriority();
		for (int i = 1; i <= 32; i++) {
			if ((sourcePriority & (1 << i)) != 0) {
				Set activations = activationsBySourcePriority[i];
				if (activations == null) {
					activations = new HashSet(1);
					activationsBySourcePriority[i] = activations;
				}
				activations.add(activation);
			}
		}
	}

	private final void checkWindowType(final Shell newShell,
			final Shell oldShell) {
		Collection oldActivations = (Collection) registeredWindows
				.get(oldShell);
		if (oldActivations == null) {
			oldActivations = (Collection) registeredWindows.get(null);
			if (oldActivations != null) {
				final Iterator oldActivationItr = oldActivations.iterator();
				while (oldActivationItr.hasNext()) {
					final IContextActivation activation = (IContextActivation) oldActivationItr
							.next();
					deactivateContext(activation);
				}
			}
		}

		if ((newShell != null) && (!newShell.isDisposed())) {
			final Collection newActivations;

			if ((newShell.getParent() != null)
					&& (registeredWindows.get(newShell) == null)) {
				newActivations = new ArrayList();
				final Expression expression = new ActiveShellExpression(
						newShell);
				final IContextActivation dialogWindowActivation = new ContextActivation(
						IContextService.CONTEXT_ID_DIALOG_AND_WINDOW,
						expression, contextService);
				activateContext(dialogWindowActivation);
				newActivations.add(dialogWindowActivation);
				final IContextActivation dialogActivation = new ContextActivation(
						IContextService.CONTEXT_ID_DIALOG, expression,
						contextService);
				activateContext(dialogActivation);
				newActivations.add(dialogActivation);
				registeredWindows.put(null, newActivations);

				newShell.addDisposeListener(new DisposeListener() {

					@Override
					public void widgetDisposed(DisposeEvent e) {
						registeredWindows.remove(null);
						if (!newShell.isDisposed()) {
							newShell.removeDisposeListener(this);
						}

						final Iterator newActivationItr = newActivations
								.iterator();
						while (newActivationItr.hasNext()) {
							deactivateContext((IContextActivation) newActivationItr
									.next());
						}
					}
				});

			} else {
				newActivations = null;

			}
		}
	}

	private final boolean containsActive(final Collection activations) {
		final Iterator activationItr = activations.iterator();
		while (activationItr.hasNext()) {
			final IContextActivation activation = (IContextActivation) activationItr
					.next();
			if (evaluate(activation)) {
				return true;
			}
		}

		return false;
	}

	final void deactivateContext(final IContextActivation activation) {
		final String contextId = activation.getContextId();
		final Object value = contextActivationsByContextId.get(contextId);
		if (value instanceof Collection) {
			final Collection contextActivations = (Collection) value;
			if (contextActivations.contains(activation)) {
				contextActivations.remove(activation);
				if (contextActivations.isEmpty()) {
					contextActivationsByContextId.remove(contextId);
					updateContext(contextId, false);

				} else if (contextActivations.size() == 1) {
					final IContextActivation remainingActivation = (IContextActivation) contextActivations
							.iterator().next();
					contextActivationsByContextId.put(contextId,
							remainingActivation);
					updateContext(contextId, evaluate(remainingActivation));

				} else {
					updateContext(contextId, containsActive(contextActivations));
				}
			}
		} else if (value instanceof IContextActivation) {
			if (value == activation) {
				contextActivationsByContextId.remove(contextId);
				updateContext(contextId, false);
			}
		}

		final int sourcePriority = activation.getSourcePriority();
		for (int i = 1; i <= 32; i++) {
			if ((sourcePriority & (1 << i)) != 0) {
				final Set activations = activationsBySourcePriority[i];
				if (activations == null) {
					continue;
				}
				activations.remove(activation);
				if (activations.isEmpty()) {
					activationsBySourcePriority[i] = null;
				}
			}
		}
	}

	final Shell getActiveShell() {
		return (Shell) getVariable(ISources.ACTIVE_SHELL_NAME);
	}

	public final int getShellType(final Shell shell) {
		if (shell == null) {
			return IContextService.TYPE_NONE;
		}

		final Collection activations = (Collection) registeredWindows
				.get(shell);
		if (activations != null) {
			if (activations.isEmpty()) {
				return IContextService.TYPE_NONE;
			}

			final Iterator activationItr = activations.iterator();
			while (activationItr.hasNext()) {
				final IContextActivation activation = (IContextActivation) activationItr
						.next();
				final String contextId = activation.getContextId();
				if (contextId == IContextService.CONTEXT_ID_DIALOG) {
					return IContextService.TYPE_DIALOG;
				} else if (contextId == IContextService.CONTEXT_ID_WINDOW) {
					return IContextService.TYPE_WINDOW;
				}
			}

			Assert
					.isTrue(
							false,
							"A registered shell should have at least one submission matching TYPE_WINDOW or TYPE_DIALOG"); //$NON-NLS-1$
			return IContextService.TYPE_NONE; // not reachable

		} else if (shell.getParent() != null) {
			return IContextService.TYPE_DIALOG;

		} else {
			return IContextService.TYPE_NONE;
		}
	}

	public final boolean registerShell(final Shell shell, final int type) {
		if (shell == null) {
			throw new NullPointerException("The shell was null"); //$NON-NLS-1$
		}

		if (DEBUG) {
			final StringBuffer buffer = new StringBuffer("register shell '"); //$NON-NLS-1$
			buffer.append(shell);
			buffer.append("' as "); //$NON-NLS-1$
			switch (type) {
			case IContextService.TYPE_DIALOG:
				buffer.append("dialog"); //$NON-NLS-1$
				break;
			case IContextService.TYPE_WINDOW:
				buffer.append("window"); //$NON-NLS-1$
				break;
			case IContextService.TYPE_NONE:
				buffer.append("none"); //$NON-NLS-1$
				break;
			default:
				buffer.append("unknown"); //$NON-NLS-1$
				break;
			}
			Tracing.printTrace(TRACING_COMPONENT, buffer.toString());
		}

		final List activations = new ArrayList();
		Expression expression;
		IContextActivation dialogWindowActivation;
		switch (type) {
		case IContextService.TYPE_DIALOG:
			expression = new ActiveShellExpression(shell);
			dialogWindowActivation = new ContextActivation(
					IContextService.CONTEXT_ID_DIALOG_AND_WINDOW, expression,
					contextService);
			activateContext(dialogWindowActivation);
			activations.add(dialogWindowActivation);
			final IContextActivation dialogActivation = new ContextActivation(
					IContextService.CONTEXT_ID_DIALOG, expression,
					contextService);
			activateContext(dialogActivation);
			activations.add(dialogActivation);
			break;
		case IContextService.TYPE_NONE:
			break;
		case IContextService.TYPE_WINDOW:
			expression = new ActiveShellExpression(shell);
			dialogWindowActivation = new ContextActivation(
					IContextService.CONTEXT_ID_DIALOG_AND_WINDOW, expression,
					contextService);
			activateContext(dialogWindowActivation);
			activations.add(dialogWindowActivation);
			final IContextActivation windowActivation = new ContextActivation(
					IContextService.CONTEXT_ID_WINDOW, expression,
					contextService);
			activateContext(windowActivation);
			activations.add(windowActivation);
			break;
		default:
			throw new IllegalArgumentException("The type is not recognized: " //$NON-NLS-1$
					+ type);
		}

		boolean returnValue = false;
		final Collection previousActivations = (Collection) registeredWindows
				.get(shell);
		if (previousActivations != null) {
			returnValue = true;
			final Iterator previousActivationItr = previousActivations
					.iterator();
			while (previousActivationItr.hasNext()) {
				final IContextActivation activation = (IContextActivation) previousActivationItr
						.next();
				deactivateContext(activation);
			}
		}

		registeredWindows.put(shell, activations);

		final DisposeListener shellDisposeListener = new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				registeredWindows.remove(shell);
				if (!shell.isDisposed()) {
					shell.removeDisposeListener(this);
				}

				final Iterator activationItr = activations.iterator();
				while (activationItr.hasNext()) {
					deactivateContext((IContextActivation) activationItr.next());
				}
			}
		};

		shell.addDisposeListener(shellDisposeListener);
		shell.setData(DISPOSE_LISTENER, shellDisposeListener);

		return returnValue;
	}

	@Override
	protected final void sourceChanged(final int sourcePriority) {
		long startTime = 0L;
		if (DEBUG_PERFORMANCE) {
			startTime = System.currentTimeMillis();
		}

		final Set activationsToRecompute = new HashSet(
				ACTIVATIONS_TO_RECOMPUTE_SIZE);
		for (int i = 1; i <= 32; i++) {
			if ((sourcePriority & (1 << i)) != 0) {
				final Collection activations = activationsBySourcePriority[i];
				if (activations != null) {
					final Iterator activationItr = activations.iterator();
					while (activationItr.hasNext()) {
						activationsToRecompute.add(activationItr.next());
					}
				}
			}
		}

		final Collection changedContextIds = new ArrayList(
				activationsToRecompute.size());
		final Iterator activationItr = activationsToRecompute.iterator();
		while (activationItr.hasNext()) {
			final IContextActivation activation = (IContextActivation) activationItr
					.next();
			final boolean currentActive = evaluate(activation);
			activation.clearResult();
			final boolean newActive = evaluate(activation);
			if (newActive != currentActive) {
				changedContextIds.add(activation.getContextId());
			}
		}

		try {
			contextManager.deferUpdates(true);
			final Iterator changedContextIdItr = changedContextIds.iterator();
			while (changedContextIdItr.hasNext()) {
				final String contextId = (String) changedContextIdItr.next();
				final Object value = contextActivationsByContextId
						.get(contextId);
				if (value instanceof IContextActivation) {
					final IContextActivation activation = (IContextActivation) value;
					updateContext(contextId, evaluate(activation));
				} else if (value instanceof Collection) {
					updateContext(contextId, containsActive((Collection) value));
				} else {
					updateContext(contextId, false);
				}
			}
		} finally {
			contextManager.deferUpdates(false);
		}

		if (DEBUG_PERFORMANCE) {
			final long elapsedTime = System.currentTimeMillis() - startTime;
			final int size = activationsToRecompute.size();
			if (size > 0) {
				Tracing.printTrace(TRACING_COMPONENT, size
						+ " activations recomputed in " + elapsedTime + "ms"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	public final boolean unregisterShell(final Shell shell) {
		if (shell == null) {
			return false;
		}

		if (!shell.isDisposed()) {
			final DisposeListener oldListener = (DisposeListener) shell
					.getData(DISPOSE_LISTENER);
			if (oldListener != null) {
				shell.removeDisposeListener(oldListener);
			}
		}

		Collection previousActivations = (Collection) registeredWindows
				.get(shell);
		if (previousActivations != null) {
			registeredWindows.remove(shell);

			final Iterator previousActivationItr = previousActivations
					.iterator();
			while (previousActivationItr.hasNext()) {
				final IContextActivation activation = (IContextActivation) previousActivationItr
						.next();
				deactivateContext(activation);
			}
			return true;
		}

		return false;
	}

	private final void updateContext(final String contextId,
			final boolean active) {
		if (active) {
			contextManager.addActiveContext(contextId);
		} else {
			contextManager.removeActiveContext(contextId);
		}
	}

	@Override
	protected final void updateEvaluationContext(final String name,
			final Object value) {
		if ((name != null)
				&& (!ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME.equals(name))) {
			if (ISources.ACTIVE_SHELL_NAME.equals(name)) {
				checkWindowType((Shell) value,
						(Shell) getVariable(ISources.ACTIVE_SHELL_NAME));
			}

			changeVariable(name, value);
		}
	}

	final void updateShellKludge() {
		updateCurrentState();
		sourceChanged(ISources.ACTIVE_SHELL);
	}
}
