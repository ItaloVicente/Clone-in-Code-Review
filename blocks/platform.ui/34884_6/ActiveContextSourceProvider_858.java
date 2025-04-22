package org.eclipse.ui.internal.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.ui.LegacyHandlerSubmissionExpression;
import org.eclipse.ui.commands.HandlerSubmission;
import org.eclipse.ui.commands.ICommandManager;
import org.eclipse.ui.commands.IWorkbenchCommandSupport;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.handlers.LegacyHandlerWrapper;

public class WorkbenchCommandSupport implements IWorkbenchCommandSupport {

	private Map activationsBySubmission = null;

	private final CommandManagerLegacyWrapper commandManagerWrapper;

	private final IHandlerService handlerService;

	public WorkbenchCommandSupport(final BindingManager bindingManager,
			final CommandManager commandManager,
			final ContextManager contextManager,
			final IHandlerService handlerService) {
		if (handlerService == null) {
			throw new NullPointerException("The handler service cannot be null"); //$NON-NLS-1$
		}

		this.handlerService = handlerService;

		commandManagerWrapper = CommandManagerFactory.getCommandManagerWrapper(
				bindingManager, commandManager, contextManager);

		org.eclipse.ui.keys.KeyFormatterFactory
				.setDefault(org.eclipse.ui.keys.SWTKeySupport
						.getKeyFormatterForPlatform());
	}

	@Override
	public final void addHandlerSubmission(
			final HandlerSubmission handlerSubmission) {
		final IHandlerActivation activation = handlerService.activateHandler(
				handlerSubmission.getCommandId(), new LegacyHandlerWrapper(
						handlerSubmission.getHandler()),
				new LegacyHandlerSubmissionExpression(handlerSubmission
						.getActivePartId(), handlerSubmission.getActiveShell(),
						handlerSubmission.getActiveWorkbenchPartSite()));
		if (activationsBySubmission == null) {
			activationsBySubmission = new HashMap();
		}
		activationsBySubmission.put(handlerSubmission, activation);
	}

	@Override
	public final void addHandlerSubmissions(final Collection handlerSubmissions) {
		final Iterator submissionItr = handlerSubmissions.iterator();
		while (submissionItr.hasNext()) {
			addHandlerSubmission((HandlerSubmission) submissionItr.next());
		}
	}

	@Override
	public ICommandManager getCommandManager() {
		return commandManagerWrapper;
	}

	@Override
	public final void removeHandlerSubmission(
			final HandlerSubmission handlerSubmission) {
		if (activationsBySubmission == null) {
			return;
		}

		final Object value = activationsBySubmission.remove(handlerSubmission);
		if (value instanceof IHandlerActivation) {
			final IHandlerActivation activation = (IHandlerActivation) value;
			handlerService.deactivateHandler(activation);
		}
	}

	@Override
	public final void removeHandlerSubmissions(
			final Collection handlerSubmissions) {
		final Iterator submissionItr = handlerSubmissions.iterator();
		while (submissionItr.hasNext()) {
			removeHandlerSubmission((HandlerSubmission) submissionItr.next());
		}
	}
}
