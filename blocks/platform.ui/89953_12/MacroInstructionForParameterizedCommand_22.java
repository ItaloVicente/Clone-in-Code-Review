package org.eclipse.e4.ui.macros.internal.keybindings;

import javax.inject.Inject;
import org.eclipse.e4.core.macros.EMacroService;
import org.eclipse.e4.core.macros.IMacroStateListener;
import org.eclipse.e4.ui.bindings.keys.KeyBindingDispatcher;

public class KeyBindingDispatcherInterceptorInstaller implements IMacroStateListener {

	@Inject
	private KeyBindingDispatcher fDispatcher;

	private KeyBindingDispatcherInterceptor fInterceptor;

	@Override
	public void macroStateChanged(EMacroService macroService) {
		if (macroService.isRecording() || macroService.isPlayingBack()) {
			if (fInterceptor == null) {
				fInterceptor = new KeyBindingDispatcherInterceptor(macroService, fDispatcher);
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
