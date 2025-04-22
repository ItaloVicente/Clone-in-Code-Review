
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandler2;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.commands.IObjectWithState;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.State;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IActionDelegateWithEvent;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.BundleUtility;

public final class ActionDelegateHandlerProxy implements ISelectionListener,
		ISelectionChangedListener, INullSelectionListener, IHandler2,
		IObjectWithState, IPartListener2 {

	private CommandLegacyActionWrapper action;

	private String actionId;

	private ParameterizedCommand command;

	private ISelection currentSelection;

	private IActionDelegate delegate;

	private IEditorActionDelegate editorDelegate = null;
	private IViewActionDelegate viewDelegate = null;
	private IObjectActionDelegate objectDelegate = null;
	private IWorkbenchWindowActionDelegate windowDelegate = null;
	
	private IWorkbenchPart currentPart = null;

	private String delegateAttributeName;

	private IConfigurationElement element;

	private final Expression enabledWhenExpression;

	private transient ListenerList listenerList = null;

	private final String style;

	private final String viewId;

	private final IWorkbenchWindow window;

	public ActionDelegateHandlerProxy(final IConfigurationElement element,
			final String delegateAttributeName, final String actionId,
			final ParameterizedCommand command, final IWorkbenchWindow window,
			final String style, final Expression enabledWhenExpression,
			final String viewId) {
		if (element == null) {
			throw new NullPointerException(
					"The configuration element backing a handler proxy cannot be null"); //$NON-NLS-1$
		}

		if (delegateAttributeName == null) {
			throw new NullPointerException(
					"The attribute containing the action delegate must be known"); //$NON-NLS-1$
		}

		if (window == null) {
			throw new NullPointerException(
					"The workbench window for a delegate must not be null"); //$NON-NLS-1$
		}

		this.element = element;
		this.enabledWhenExpression = enabledWhenExpression;
		this.delegateAttributeName = delegateAttributeName;
		this.window = window;
		this.command = command;
		this.actionId = actionId;
		this.style = style;
		this.viewId = viewId;
	}

	@Override
	public final void addHandlerListener(final IHandlerListener handlerListener) {
		if (listenerList == null) {
			listenerList = new ListenerList(ListenerList.IDENTITY);
		}

		listenerList.add(handlerListener);
	}

	@Override
	public void addState(String id, State state) {

	}

	
	@Override
	public final void dispose() {
		disposeDelegate();
	}

	private void disposeDelegate() {
		final IActionDelegate actDel = getDelegate();
		if (actDel instanceof IWorkbenchWindowActionDelegate) {
			final IWorkbenchWindowActionDelegate workbenchWindowDelegate = (IWorkbenchWindowActionDelegate) actDel;
			workbenchWindowDelegate.dispose();
		} else if (actDel instanceof IActionDelegate2) {
			final IActionDelegate2 delegate2 = (IActionDelegate2) actDel;
			delegate2.dispose();
		}
		delegate = null;
		editorDelegate = null;
		objectDelegate = null;
		viewDelegate = null;
		windowDelegate = null;
		currentSelection = null;
	}

	@Override
	public final Object execute(final ExecutionEvent event) {
		final IAction action = getAction();
		if (loadDelegate() && (action != null)) {
			final Object trigger = event.getTrigger();

			final Object applicationContext = event.getApplicationContext();
			if (applicationContext instanceof IEvaluationContext) {
				final IEvaluationContext context = (IEvaluationContext) applicationContext;
				updateDelegate(action, context);
			}

			if ((action.getStyle() == IAction.AS_CHECK_BOX)
					|| (action.getStyle() == IAction.AS_RADIO_BUTTON)) {
				action.setChecked(!action.isChecked());
			}

			if ((delegate instanceof IActionDelegate2)
					&& (trigger instanceof Event)) {
				final IActionDelegate2 delegate2 = (IActionDelegate2) delegate;
				final Event triggeringEvent = (Event) trigger;
				delegate2.runWithEvent(action, triggeringEvent);
			} else if ((delegate instanceof IActionDelegateWithEvent)
					&& (trigger instanceof Event)) {
				final IActionDelegateWithEvent delegateWithEvent = (IActionDelegateWithEvent) delegate;
				final Event triggeringEvent = (Event) trigger;
				delegateWithEvent.runWithEvent(action, triggeringEvent);
			} else {
				delegate.run(action);
			}
		}

		return null;
	}

	private void updateDelegate(final IAction action,
			final IEvaluationContext context) {
		if (action == null) {
			return;
		}
		if (delegate == null) {
			if (!BundleUtility.isActive(element.getContributor().getName())) {
				return;
			}
		}

		if (editorDelegate != null) {
			final Object activeEditor = context
					.getVariable(ISources.ACTIVE_EDITOR_NAME);
			if (activeEditor != IEvaluationContext.UNDEFINED_VARIABLE) {
				editorDelegate.setActiveEditor(action,
						(IEditorPart) activeEditor);
			}
			updateActivePart(activeEditor==IEvaluationContext.UNDEFINED_VARIABLE
					?null:(IWorkbenchPart)activeEditor);
		} else if (objectDelegate != null) {
			final Object activePart = context
					.getVariable(ISources.ACTIVE_PART_NAME);
			if (activePart != IEvaluationContext.UNDEFINED_VARIABLE) {
				objectDelegate.setActivePart(action,
						(IWorkbenchPart) activePart);
			}
			updateActivePart(activePart==IEvaluationContext.UNDEFINED_VARIABLE
					?null:(IWorkbenchPart) activePart);
		}

		final Object selectionObject = getCurrentSelection(context);
		if (selectionObject instanceof ISelection) {
			currentSelection = (ISelection) selectionObject;
		} else {
			currentSelection = null;
		}
		if (delegate != null) {
			delegate.selectionChanged(action, currentSelection);
		}
	}

	private void updateActivePart(IWorkbenchPart activePart) {
		if (currentPart == activePart) {
			return;
		}
		if (currentPart != null) {
			currentPart.getSite().getPage().removePartListener(this);
		}
		if (activePart != null) {
			activePart.getSite().getPage().addPartListener(this);
		} else {
			selectionChanged(StructuredSelection.EMPTY);
			disposeDelegate();
			if (action!=null) {
				action.setEnabled(true);
			}
		}
		currentPart = activePart;
	}

	private Object getCurrentSelection(final IEvaluationContext context) {
		Object obj = context
				.getVariable(ISources.ACTIVE_MENU_EDITOR_INPUT_NAME);
		if (obj == null || obj == IEvaluationContext.UNDEFINED_VARIABLE) {
			obj = context.getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
			if (obj == null || obj == IEvaluationContext.UNDEFINED_VARIABLE) {
				obj = context
						.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
			}
		}
		return obj;
	}

	public final CommandLegacyActionWrapper getAction() {
		if (action == null) {
			action = new CommandLegacyActionWrapper(actionId, command, style,
					window);
			action.addPropertyChangeListener(new IPropertyChangeListener() {
				@Override
				public final void propertyChange(final PropertyChangeEvent event) {
				}
			});
		}
		return action;
	}

	public final IActionDelegate getDelegate() {
		return delegate;
	}

	@Override
	public State getState(String stateId) {
		return null;
	}

	@Override
	public String[] getStateIds() {
		return null;
	}

	public final void handleStateChange(final State state, final Object oldValue) {
	}

	private final boolean initDelegate() {
		final IWorkbenchPage page = window.getActivePage();
		final IWorkbenchPart activePart;
		final IEditorPart activeEditor;
		if (page == null) {
			activePart = null;
			activeEditor = null;
		} else {
			activePart = page.getActivePart();
			activeEditor = page.getActiveEditor();
		}
		final IActionDelegate delegate = getDelegate();
		final IAction action = getAction();

		if ((viewId != null) && (page != null) && (viewDelegate != null)) {
			final IViewPart viewPart = page.findView(viewId);
			if (viewPart == null) {
				return false;
			}
		}

		final ISafeRunnable runnable = new ISafeRunnable() {
			@Override
			public final void handleException(final Throwable exception) {
			}

			@Override
			public final void run() {
				if (delegate instanceof IActionDelegate2) {
					final IActionDelegate2 delegate2 = (IActionDelegate2) delegate;
					delegate2.init(action);
				}

				if ((objectDelegate != null) && (activePart != null)) {
					objectDelegate.setActivePart(action, activePart);
					updateActivePart(activePart);
				} else if (editorDelegate != null) {
					editorDelegate.setActiveEditor(action, activeEditor);
					updateActivePart(activeEditor);
				} else if ((viewId != null) && (page != null)
						&& (viewDelegate != null)) {
					final IViewPart viewPart = page.findView(viewId);
					viewDelegate.init(viewPart);
				} else if (windowDelegate != null) {
					windowDelegate.init(window);
				}
			}
		};
		SafeRunner.run(runnable);
		return true;
	}
	
	@Override
	public void setEnabled(Object evaluationContext) {
		if (!(evaluationContext instanceof IEvaluationContext)) {
			return;
		}

		IEvaluationContext context = (IEvaluationContext) evaluationContext;
		final CommandLegacyActionWrapper action = getAction();
		if (enabledWhenExpression != null) {
			try {
				final EvaluationResult result = enabledWhenExpression
						.evaluate(context);
				if (action != null) {
					action.setEnabled(result != EvaluationResult.FALSE);
				}
			} catch (final CoreException e) {
				final StringBuffer message = new StringBuffer(
						"An exception occurred while evaluating the enabledWhen expression for "); //$NON-NLS-1$
				if (delegate != null) {
					message.append(delegate);
				} else {
					message.append(element.getAttribute(delegateAttributeName));
				}
				message.append("' could not be loaded"); //$NON-NLS-1$
				final IStatus status = new Status(IStatus.WARNING,
						WorkbenchPlugin.PI_WORKBENCH, 0, e.getMessage(), e);
				WorkbenchPlugin.log(message.toString(), status);
				return;
			}
		}
		updateDelegate(action, context);
	}

	@Override
	public final boolean isEnabled() {
		return (action == null) || action.isEnabledDisregardingCommand();
	}

	@Override
	public final boolean isHandled() {
		return true;
	}

	private final boolean isSafeToLoadDelegate() {
		return false;
	}

	public final boolean loadDelegate() {
		if (delegate == null) {
			if (viewId != null) {
				final IWorkbenchPage activePage = window.getActivePage();
				if (activePage != null) {
					final IViewPart part = activePage.findView(viewId);
					if (part == null) {
						return false;
					}
				} else {
					return false;
				}
			}

			try {
				delegate = (IActionDelegate) element
						.createExecutableExtension(delegateAttributeName);
				String name = element.getDeclaringExtension()
						.getExtensionPointUniqueIdentifier();
				if ("org.eclipse.ui.actionSets".equals(name) //$NON-NLS-1$
						&& delegate instanceof IWorkbenchWindowActionDelegate) {
					windowDelegate = (IWorkbenchWindowActionDelegate) delegate;
				} else if ("org.eclipse.ui.editorActions".equals(name) //$NON-NLS-1$
						&& delegate instanceof IEditorActionDelegate) {
					editorDelegate = (IEditorActionDelegate) delegate;
				} else if ("org.eclipse.ui.viewActions".equals(name) //$NON-NLS-1$
						&& delegate instanceof IViewActionDelegate) {
					viewDelegate = (IViewActionDelegate) delegate;
				} else if ("org.eclipse.ui.popupMenus".equals(name)) { //$NON-NLS-1$
					IConfigurationElement parent = (IConfigurationElement) element
							.getParent();
					if ("objectContribution".equals(parent.getName()) //$NON-NLS-1$
							&& delegate instanceof IObjectActionDelegate) {
						objectDelegate = (IObjectActionDelegate) delegate;
					} else if (viewId == null
							&& delegate instanceof IEditorActionDelegate) {
						editorDelegate = (IEditorActionDelegate) delegate;
					} else if (viewId != null
							&& delegate instanceof IViewActionDelegate) {
						viewDelegate = (IViewActionDelegate) delegate;
					}
				}
				if (initDelegate()) {
					return true;
				}

				delegate = null;
				objectDelegate = null;
				viewDelegate = null;
				editorDelegate = null;
				windowDelegate = null;
				return false;

			} catch (final ClassCastException e) {
				final String message = "The proxied delegate was the wrong class"; //$NON-NLS-1$
				final IStatus status = new Status(IStatus.ERROR,
						WorkbenchPlugin.PI_WORKBENCH, 0, message, e);
				WorkbenchPlugin.log(message, status);
				return false;

			} catch (final CoreException e) {
				final String message = "The proxied delegate for '" //$NON-NLS-1$
						+ element.getAttribute(delegateAttributeName)
						+ "' could not be loaded"; //$NON-NLS-1$
				IStatus status = new Status(IStatus.ERROR,
						WorkbenchPlugin.PI_WORKBENCH, 0, message, e);
				WorkbenchPlugin.log(message, status);
				return false;
			}
		}

		return true;
	}

	private final void refreshEnablement() {
		final IActionDelegate delegate = getDelegate();
		final IAction action = getAction();
		if ((delegate != null) && (action != null)) {
			delegate.selectionChanged(action, currentSelection);
		}
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		if (listenerList != null) {
			listenerList.remove(handlerListener);

			if (listenerList.isEmpty()) {
				listenerList = null;
			}
		}
	}

	@Override
	public void removeState(String stateId) {

	}

	private final void selectionChanged(final ISelection selection) {
		currentSelection = selection;
		if (currentSelection == null) {
			currentSelection = StructuredSelection.EMPTY;
		}


		final IActionDelegate delegate = getDelegate();
		if (delegate == null && isSafeToLoadDelegate()) {
			loadDelegate();
		}
		refreshEnablement();
	}

	@Override
	public final void selectionChanged(final IWorkbenchPart part,
			final ISelection selection) {
		selectionChanged(selection);

	}

	@Override
	public final void selectionChanged(final SelectionChangedEvent event) {
		final ISelection selection = event.getSelection();
		selectionChanged(selection);
	}

	@Override
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("ActionDelegateHandlerProxy("); //$NON-NLS-1$
		buffer.append(getDelegate());
		if (element != null) {
			buffer.append(',');
			try {
				final String className = element
						.getAttribute(delegateAttributeName);
				buffer.append(className);
			} catch (InvalidRegistryObjectException e) {
				buffer.append(actionId);
			}
		}
		buffer.append(')');
		return buffer.toString();
	}

	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		if (currentPart != null && partRef.getPart(false) == currentPart) {
			updateActivePart(null);
		}
	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
	}
}
