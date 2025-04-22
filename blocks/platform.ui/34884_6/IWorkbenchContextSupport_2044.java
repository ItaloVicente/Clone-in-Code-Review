package org.eclipse.ui.contexts;

import java.util.Collection;

import org.eclipse.core.commands.contexts.Context;
import org.eclipse.core.commands.contexts.IContextManagerListener;
import org.eclipse.core.expressions.Expression;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.services.IServiceWithSources;

public interface IContextService extends IServiceWithSources {

	public static final String CONTEXT_ID_WORKBENCH_MENU = "org.eclipse.ui.contexts.workbenchMenu"; //$NON-NLS-1$

	public static final String CONTEXT_ID_DIALOG = "org.eclipse.ui.contexts.dialog"; //$NON-NLS-1$

	public static final String CONTEXT_ID_DIALOG_AND_WINDOW = "org.eclipse.ui.contexts.dialogAndWindow"; //$NON-NLS-1$

	public static final String CONTEXT_ID_WINDOW = "org.eclipse.ui.contexts.window"; //$NON-NLS-1$

	public static final int TYPE_DIALOG = 0;

	public static final int TYPE_NONE = 1;

	public static final int TYPE_WINDOW = 2;

	public IContextActivation activateContext(String contextId);

	public IContextActivation activateContext(String contextId,
			Expression expression);

	public IContextActivation activateContext(String contextId,
			Expression expression, boolean global);

	@Deprecated
	public IContextActivation activateContext(String contextId,
			Expression expression, int sourcePriorities);

	public void addContextManagerListener(IContextManagerListener listener);

	public void deactivateContext(IContextActivation activation);

	public void deactivateContexts(Collection activations);

	public Collection getActiveContextIds();

	public Context getContext(String contextId);

	public Context[] getDefinedContexts();

	public Collection getDefinedContextIds();

	public int getShellType(Shell shell);

	public void readRegistry();

	public boolean registerShell(Shell shell, int type);

	public void removeContextManagerListener(IContextManagerListener listener);

	public boolean unregisterShell(Shell shell);

	public void deferUpdates(boolean defer);
	
}
