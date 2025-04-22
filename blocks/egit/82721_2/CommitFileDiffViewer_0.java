package org.eclipse.egit.ui.internal;

import java.util.function.BooleanSupplier;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

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
}
