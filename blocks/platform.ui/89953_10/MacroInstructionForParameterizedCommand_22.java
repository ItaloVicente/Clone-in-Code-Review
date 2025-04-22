package org.eclipse.e4.ui.macros.internal.keybindings;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.macros.EMacroService;
import org.eclipse.e4.core.macros.IMacroStateListener;
import org.eclipse.e4.ui.bindings.keys.KeyBindingDispatcher;

public class KeyBindingDispatcherInterceptorInstaller implements IMacroStateListener {

	@Inject
	private KeyBindingDispatcher fDispatcher;

	@Inject
	private IEclipseContext fEclipseContext;

	private KeyBindingDispatcherInterceptor fInterceptor;

	@Override
	public void macroStateChanged(EMacroService macroService) {
		if (macroService.isRecording() || macroService.isPlayingBack()) {
			if (fInterceptor == null) {
				fInterceptor = new KeyBindingDispatcherInterceptor(macroService, fEclipseContext);
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
