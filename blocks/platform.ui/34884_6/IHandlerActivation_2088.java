
package org.eclipse.ui.handlers;

import java.util.Collection;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;

public class HandlerUtil {
	private static void noVariableFound(ExecutionEvent event, String name)
			throws ExecutionException {
		throw new ExecutionException("No " + name //$NON-NLS-1$
				+ " found while executing " + event.getCommand().getId()); //$NON-NLS-1$
	}

	private static void incorrectTypeFound(ExecutionEvent event, String name,
			Class expectedType, Class wrongType) throws ExecutionException {
		throw new ExecutionException("Incorrect type for " //$NON-NLS-1$
				+ name
				+ " found while executing " //$NON-NLS-1$
				+ event.getCommand().getId()
				+ ", expected " + expectedType.getName() //$NON-NLS-1$
				+ " found " + wrongType.getName()); //$NON-NLS-1$
	}

	public static Object getVariable(ExecutionEvent event, String name) {
		if (event.getApplicationContext() instanceof IEvaluationContext) {
			Object var = ((IEvaluationContext) event.getApplicationContext())
					.getVariable(name);
			return var == IEvaluationContext.UNDEFINED_VARIABLE ? null : var;
		}
		return null;
	}

	public static Object getVariableChecked(ExecutionEvent event, String name)
			throws ExecutionException {
		Object o = getVariable(event, name);
		if (o == null) {
			noVariableFound(event, name);
		}
		return o;
	}

	public static Object getVariable(Object context, String name) {
		if (context instanceof IEvaluationContext) {
			Object var = ((IEvaluationContext) context).getVariable(name);
			return var == IEvaluationContext.UNDEFINED_VARIABLE ? null : var;
		}
		return null;
	}

	public static Collection getActiveContexts(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_CONTEXT_NAME);
		if (o instanceof Collection) {
			return (Collection) o;
		}
		return null;
	}

	public static Collection getActiveContextsChecked(ExecutionEvent event)
			throws ExecutionException {
		Object o = getVariableChecked(event, ISources.ACTIVE_CONTEXT_NAME);
		if (!(o instanceof Collection)) {
			incorrectTypeFound(event, ISources.ACTIVE_CONTEXT_NAME,
					Collection.class, o.getClass());
		}
		return (Collection) o;
	}

	public static Shell getActiveShell(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_SHELL_NAME);
		if (o instanceof Shell) {
			return (Shell) o;
		}
		return null;
	}

	public static Shell getActiveShellChecked(ExecutionEvent event)
			throws ExecutionException {
		Object o = getVariableChecked(event, ISources.ACTIVE_SHELL_NAME);
		if (!(o instanceof Shell)) {
			incorrectTypeFound(event, ISources.ACTIVE_SHELL_NAME, Shell.class,
					o.getClass());
		}
		return (Shell) o;
	}

	public static IWorkbenchWindow getActiveWorkbenchWindow(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_WORKBENCH_WINDOW_NAME);
		if (o instanceof IWorkbenchWindow) {
			return (IWorkbenchWindow) o;
		}
		return null;
	}

	public static IWorkbenchWindow getActiveWorkbenchWindowChecked(
			ExecutionEvent event) throws ExecutionException {
		Object o = getVariableChecked(event,
				ISources.ACTIVE_WORKBENCH_WINDOW_NAME);
		if (!(o instanceof IWorkbenchWindow)) {
			incorrectTypeFound(event, ISources.ACTIVE_WORKBENCH_WINDOW_NAME,
					IWorkbenchWindow.class, o.getClass());
		}
		return (IWorkbenchWindow) o;
	}

	public static IEditorPart getActiveEditor(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_EDITOR_NAME);
		if (o instanceof IEditorPart) {
			return (IEditorPart) o;
		}
		return null;
	}

	public static IEditorPart getActiveEditorChecked(ExecutionEvent event)
			throws ExecutionException {
		Object o = getVariableChecked(event, ISources.ACTIVE_EDITOR_NAME);
		if (!(o instanceof IEditorPart)) {
			incorrectTypeFound(event, ISources.ACTIVE_EDITOR_NAME,
					IEditorPart.class, o.getClass());
		}
		return (IEditorPart) o;
	}

	public static String getActiveEditorId(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_EDITOR_ID_NAME);
		if (o instanceof String) {
			return (String) o;
		}
		return null;
	}

	public static String getActiveEditorIdChecked(ExecutionEvent event)
			throws ExecutionException {
		Object o = getVariableChecked(event, ISources.ACTIVE_EDITOR_ID_NAME);
		if (!(o instanceof String)) {
			incorrectTypeFound(event, ISources.ACTIVE_EDITOR_ID_NAME,
					String.class, o.getClass());
		}
		return (String) o;
	}

	public static IEditorInput getActiveEditorInput(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_EDITOR_INPUT_NAME);
		if (o instanceof IEditorInput) {
			return (IEditorInput) o;
		}
		return null;
	}

	public static IEditorInput getActiveEditorInputChecked(ExecutionEvent event)
			throws ExecutionException {
		Object o = getVariableChecked(event, ISources.ACTIVE_EDITOR_INPUT_NAME);
		if (!(o instanceof IEditorInput)) {
			incorrectTypeFound(event, ISources.ACTIVE_EDITOR_INPUT_NAME, IEditorInput.class,
					o.getClass());
		}
		return (IEditorInput) o;
	}

	public static IWorkbenchPart getActivePart(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_PART_NAME);
		if (o instanceof IWorkbenchPart) {
			return (IWorkbenchPart) o;
		}
		return null;
	}

	public static IWorkbenchPart getActivePartChecked(ExecutionEvent event)
			throws ExecutionException {
		Object o = getVariableChecked(event, ISources.ACTIVE_PART_NAME);
		if (!(o instanceof IWorkbenchPart)) {
			incorrectTypeFound(event, ISources.ACTIVE_PART_NAME,
					IWorkbenchPart.class, o.getClass());
		}
		return (IWorkbenchPart) o;
	}

	public static String getActivePartId(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_PART_ID_NAME);
		if (o instanceof String) {
			return (String) o;
		}
		return null;
	}

	public static String getActivePartIdChecked(ExecutionEvent event)
			throws ExecutionException {
		Object o = getVariableChecked(event, ISources.ACTIVE_PART_ID_NAME);
		if (!(o instanceof String)) {
			incorrectTypeFound(event, ISources.ACTIVE_PART_ID_NAME,
					String.class, o.getClass());
		}
		return (String) o;
	}

	public static IWorkbenchSite getActiveSite(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_SITE_NAME);
		if (o instanceof IWorkbenchSite) {
			return (IWorkbenchSite) o;
		}
		return null;
	}

	public static IWorkbenchSite getActiveSiteChecked(ExecutionEvent event)
			throws ExecutionException {
		Object o = getVariableChecked(event, ISources.ACTIVE_SITE_NAME);
		if (!(o instanceof IWorkbenchSite)) {
			incorrectTypeFound(event, ISources.ACTIVE_SITE_NAME,
					IWorkbenchSite.class, o.getClass());
		}
		return (IWorkbenchSite) o;
	}

	public static ISelection getCurrentSelection(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (o instanceof ISelection) {
			return (ISelection) o;
		}
		return null;
	}

	public static ISelection getCurrentSelectionChecked(ExecutionEvent event)
			throws ExecutionException {
		Object o = getVariableChecked(event,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (!(o instanceof ISelection)) {
			incorrectTypeFound(event, ISources.ACTIVE_CURRENT_SELECTION_NAME,
					ISelection.class, o.getClass());
		}
		return (ISelection) o;
	}

	public static Collection getActiveMenus(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_MENU_NAME);
		if (o instanceof Collection) {
			return (Collection) o;
		}
		return null;
	}

	public static Collection getActiveMenusChecked(ExecutionEvent event)
			throws ExecutionException {
		Object o = getVariableChecked(event, ISources.ACTIVE_MENU_NAME);
		if (!(o instanceof Collection)) {
			incorrectTypeFound(event, ISources.ACTIVE_MENU_NAME,
					Collection.class, o.getClass());
		}
		return (Collection) o;
	}

	public static ISelection getActiveMenuSelection(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_MENU_SELECTION_NAME);
		if (o instanceof ISelection) {
			return (ISelection) o;
		}
		return null;
	}

	public static ISelection getActiveMenuSelectionChecked(ExecutionEvent event)
			throws ExecutionException {
		Object o = getVariableChecked(event,
				ISources.ACTIVE_MENU_SELECTION_NAME);
		if (!(o instanceof ISelection)) {
			incorrectTypeFound(event, ISources.ACTIVE_MENU_SELECTION_NAME,
					ISelection.class, o.getClass());
		}
		return (ISelection) o;
	}

	public static ISelection getActiveMenuEditorInput(ExecutionEvent event) {
		Object o = getVariable(event, ISources.ACTIVE_MENU_EDITOR_INPUT_NAME);
		if (o instanceof ISelection) {
			return (ISelection) o;
		}
		return null;
	}

	public static ISelection getActiveMenuEditorInputChecked(
			ExecutionEvent event) throws ExecutionException {
		Object o = getVariableChecked(event,
				ISources.ACTIVE_MENU_EDITOR_INPUT_NAME);
		if (!(o instanceof ISelection)) {
			incorrectTypeFound(event, ISources.ACTIVE_MENU_EDITOR_INPUT_NAME,
					ISelection.class, o.getClass());
		}
		return (ISelection) o;
	}

	public static ISelection getShowInSelection(ExecutionEvent event) {
		Object o = getVariable(event, ISources.SHOW_IN_SELECTION);
		if (o instanceof ISelection) {
			return (ISelection) o;
		}
		return null;
	}

	public static ISelection getShowInSelectionChecked(ExecutionEvent event)
			throws ExecutionException {
		Object o = getVariableChecked(event, ISources.SHOW_IN_SELECTION);
		if (!(o instanceof ISelection)) {
			incorrectTypeFound(event, ISources.SHOW_IN_SELECTION,
					ISelection.class, o.getClass());
		}
		return (ISelection) o;
	}

	public static Object getShowInInput(ExecutionEvent event) {
		Object var = getVariable(event, ISources.SHOW_IN_INPUT);
		return var;
	}

	public static Object getShowInInputChecked(ExecutionEvent event)
			throws ExecutionException {
		Object var = getVariableChecked(event, ISources.SHOW_IN_INPUT);
		return var;
	}

	public static boolean toggleCommandState(Command command) throws ExecutionException {
		State state = command.getState(RegistryToggleState.STATE_ID);
		if(state == null)
			throw new ExecutionException("The command does not have a toggle state"); //$NON-NLS-1$
		 if(!(state.getValue() instanceof Boolean))
			throw new ExecutionException("The command's toggle state doesn't contain a boolean value"); //$NON-NLS-1$
			 
		boolean oldValue = ((Boolean) state.getValue()).booleanValue();
		state.setValue(new Boolean(!oldValue));
		return oldValue;
	}
	
	public static boolean matchesRadioState(ExecutionEvent event)
			throws ExecutionException {

		String parameter = event.getParameter(RadioState.PARAMETER_ID);
		if (parameter == null)
			throw new ExecutionException(
					"The event does not have the radio state parameter"); //$NON-NLS-1$

		Command command = event.getCommand();
		State state = command.getState(RadioState.STATE_ID);
		if (state == null)
			throw new ExecutionException(
					"The command does not have a radio state"); //$NON-NLS-1$
		if (!(state.getValue() instanceof String))
			throw new ExecutionException(
					"The command's radio state doesn't contain a String value"); //$NON-NLS-1$

		return parameter.equals(state.getValue());
	}

	public static void updateRadioState(Command command, String newState)
			throws ExecutionException {

		State state = command.getState(RadioState.STATE_ID);
		if (state == null)
			throw new ExecutionException(
					"The command does not have a radio state"); //$NON-NLS-1$
		state.setValue(newState);
	}

}
