package org.eclipse.ui.contexts;

import java.util.Collection;

import org.eclipse.swt.widgets.Shell;

@Deprecated
public interface IWorkbenchContextSupport {

	public static final String CONTEXT_ID_DIALOG = IContextService.CONTEXT_ID_DIALOG;

	public static final String CONTEXT_ID_DIALOG_AND_WINDOW = IContextService.CONTEXT_ID_DIALOG_AND_WINDOW;

	public static final String CONTEXT_ID_WINDOW = IContextService.CONTEXT_ID_WINDOW;

	public static final int TYPE_DIALOG = IContextService.TYPE_DIALOG;

	public static final int TYPE_NONE = IContextService.TYPE_NONE;

	public static final int TYPE_WINDOW = IContextService.TYPE_WINDOW;

	void addEnabledSubmission(EnabledSubmission enabledSubmission);

	void addEnabledSubmissions(Collection enabledSubmissions);

	IContextManager getContextManager();

	public int getShellType(final Shell shell);

	public boolean isKeyFilterEnabled();

	public void openKeyAssistDialog();

	public boolean registerShell(final Shell shell, final int type);

	void removeEnabledSubmission(EnabledSubmission enabledSubmission);

	void removeEnabledSubmissions(Collection enabledSubmissions);

	public void setKeyFilterEnabled(final boolean enabled);

	public boolean unregisterShell(final Shell shell);
}
