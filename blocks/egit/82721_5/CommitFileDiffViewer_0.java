package org.eclipse.egit.ui.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.BooleanSupplier;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.ActiveShellExpression;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;

public final class ActionUtils {

	private ActionUtils() {
	}

	public static IAction createGlobalAction(ActionFactory factory,
			final Runnable action) {
		final String text = factory
				.create(PlatformUI.getWorkbench().getActiveWorkbenchWindow())
				.getText();
		Action result = new Action() {

			@Override
			public String getText() {
				return text;
			}

			@Override
			public void run() {
				action.run();
			}
		};
		result.setActionDefinitionId(factory.getCommandId());
		result.setId(factory.getId());
		return result;
	}

	public static IAction createGlobalAction(ActionFactory factory,
			final Runnable action, final BooleanSupplier enabled) {
		final String text = factory
				.create(PlatformUI.getWorkbench().getActiveWorkbenchWindow())
				.getText();
		Action result = new Action() {

			@Override
			public String getText() {
				return text;
			}

			@Override
			public void run() {
				action.run();
			}

			@Override
			public boolean isEnabled() {
				return enabled.getAsBoolean();
			}
		};
		result.setActionDefinitionId(factory.getCommandId());
		result.setId(factory.getId());
		return result;
	}

	public static void setGlobalActions(Control control,
			Collection<IAction> actions) {
		setGlobalActions(control, actions, CommonUtils
				.getService(PlatformUI.getWorkbench(), IHandlerService.class));
	}

	public static void setGlobalActions(Control control, IAction... actions) {
		setGlobalActions(control, Arrays.asList(actions), CommonUtils
				.getService(PlatformUI.getWorkbench(), IHandlerService.class));
	}

	public static void setGlobalActions(Control control,
			Collection<IAction> actions, IHandlerService service) {
		Collection<IHandlerActivation> handlerActivations = new HashSet<>();
		control.addDisposeListener((event) -> {
			service.deactivateHandlers(handlerActivations);
			handlerActivations.clear();
		});
		final ActiveShellExpression expression = new ActiveShellExpression(
				control.getShell());
		control.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				service.deactivateHandlers(handlerActivations);
				handlerActivations.clear();
			}

			@Override
			public void focusGained(FocusEvent e) {
				for (final IAction action : actions) {
					handlerActivations.add(service.activateHandler(
							action.getActionDefinitionId(),
							new ActionHandler(action), expression, true));
				}
			}
		});
	}

	public static void setGlobalActions(Control control,
			IHandlerService service, IAction... actions) {
		setGlobalActions(control, Arrays.asList(actions), service);
	}
}
