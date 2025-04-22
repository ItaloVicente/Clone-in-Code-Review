package org.eclipse.e4.ui.macros.internal.keybindings;

import javax.inject.Inject;
import org.eclipse.core.commands.CommandManager;
import org.eclipse.e4.core.macros.EMacroService;
import org.eclipse.e4.core.macros.IMacroStateListener;
import org.eclipse.e4.ui.bindings.keys.KeyBindingDispatcher;

public class KeyBindingDispatcherInterceptorInstaller implements IMacroStateListener {

	@Inject
	private KeyBindingDispatcher fDispatcher;

	@Inject
	private CommandManager fCommandManager;

	private KeyBindingDispatcherInterceptor fInterceptor;

	private CommandManagerExecutionListener fCommandManagerExecutionListener;

	@Override
	public void macroStateChanged(EMacroService macroService) {
		if (macroService.isRecording() || macroService.isPlayingBack()) {
			if (fInterceptor == null) {
				fInterceptor = new KeyBindingDispatcherInterceptor(macroService, fDispatcher);
				fDispatcher.addInterceptor(fInterceptor);
			}
			if (fCommandManagerExecutionListener == null) {
				fCommandManagerExecutionListener = new CommandManagerExecutionListener(macroService, fInterceptor);
				fCommandManager.addExecutionListener(fCommandManagerExecutionListener);
			}
		} else {
			if (fInterceptor != null) {
				fDispatcher.removeInterceptor(fInterceptor);
				fInterceptor = null;
			}
			if (fCommandManagerExecutionListener != null) {
				fCommandManager.removeExecutionListener(fCommandManagerExecutionListener);
				fCommandManagerExecutionListener = null;
			}
		}
	}
}
