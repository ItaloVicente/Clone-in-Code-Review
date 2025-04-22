package org.eclipse.e4.ui.macros.internal.keybindings;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.macros.EMacroContext;
import org.eclipse.e4.core.macros.IMacroContextListener;
import org.eclipse.e4.ui.bindings.keys.KeyBindingDispatcher;
import org.eclipse.e4.ui.macros.EAcceptedCommands;

public class KeyBindingDispatcherInterceptorInstaller implements IMacroContextListener {

	@Inject
	private KeyBindingDispatcher fDispatcher;

	@Inject
	private EAcceptedCommands fAcceptedCommands;

	@Inject
	private IEclipseContext fEclipseContext;

	private KeyBindingDispatcherInterceptor fInterceptor;

	@Override
	public void onMacroStateChanged(EMacroContext macroContext) {
		if (macroContext.isRecording() || macroContext.isPlayingBack()) {
			if (fInterceptor == null) {
				fInterceptor = new KeyBindingDispatcherInterceptor(macroContext, fAcceptedCommands, fEclipseContext);
				fDispatcher.addInterceptor(fInterceptor);
			}
		} else {
			if (fInterceptor != null) {
				fDispatcher.removeInterceptor(fInterceptor);
				fInterceptor = null;
			}
		}
	}
}
