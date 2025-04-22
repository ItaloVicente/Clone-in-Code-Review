package org.eclipse.ui.internal.contexts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.LegacyHandlerSubmissionExpression;
import org.eclipse.ui.contexts.EnabledSubmission;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextManager;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.contexts.IWorkbenchContextSupport;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.keys.IBindingService;

public class WorkbenchContextSupport implements IWorkbenchContextSupport {

	private Map activationsBySubmission = null;

	private IBindingService bindingService;

	private IContextService contextService;

	private ContextManagerLegacyWrapper contextManagerWrapper;

	private final Workbench workbench;

	public WorkbenchContextSupport(final Workbench workbenchToSupport,
			final ContextManager contextManager) {
		workbench = workbenchToSupport;
		contextService = (IContextService) workbench.getService(IContextService.class);
		bindingService = (IBindingService) workbench.getService(IBindingService.class);
		contextManagerWrapper = ContextManagerFactory
				.getContextManagerWrapper(contextManager);
	}

	@Override
	public final void addEnabledSubmission(
			final EnabledSubmission enabledSubmission) {
		final IContextActivation activation = contextService.activateContext(
				enabledSubmission.getContextId(),
				new LegacyHandlerSubmissionExpression(enabledSubmission
						.getActivePartId(), enabledSubmission.getActiveShell(),
						enabledSubmission.getActiveWorkbenchPartSite()));
		if (activationsBySubmission == null) {
			activationsBySubmission = new HashMap();
		}
		activationsBySubmission.put(enabledSubmission, activation);
	}

	@Override
	public final void addEnabledSubmissions(final Collection enabledSubmissions) {
		final Iterator submissionItr = enabledSubmissions.iterator();
		while (submissionItr.hasNext()) {
			addEnabledSubmission((EnabledSubmission) submissionItr.next());
		}
	}

	@Override
	public final IContextManager getContextManager() {
		return contextManagerWrapper;
	}

	@Override
	public final int getShellType(Shell shell) {
		return contextService.getShellType(shell);
	}

	@Override
	public final boolean isKeyFilterEnabled() {
		return bindingService.isKeyFilterEnabled();
	}

	@Override
	public final void openKeyAssistDialog() {
		bindingService.openKeyAssistDialog();
	}

	@Override
	public final boolean registerShell(final Shell shell, final int type) {
		return contextService.registerShell(shell, type);
	}

	@Override
	public final void removeEnabledSubmission(
			final EnabledSubmission enabledSubmission) {
		if (activationsBySubmission == null) {
			return;
		}

		final Object value = activationsBySubmission.remove(enabledSubmission);
		if (value instanceof IContextActivation) {
			final IContextActivation activation = (IContextActivation) value;
			contextService.deactivateContext(activation);
		}
	}

	@Override
	public final void removeEnabledSubmissions(
			final Collection enabledSubmissions) {
		final Iterator submissionItr = enabledSubmissions.iterator();
		while (submissionItr.hasNext()) {
			removeEnabledSubmission((EnabledSubmission) submissionItr.next());
		}
	}

	@Override
	public final void setKeyFilterEnabled(final boolean enabled) {
		bindingService.setKeyFilterEnabled(enabled);
	}

	@Override
	public final boolean unregisterShell(final Shell shell) {
		return contextService.unregisterShell(shell);
	}
}
