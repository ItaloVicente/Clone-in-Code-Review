package org.eclipse.e4.core.macros.internal;

import java.util.Map;
import org.eclipse.e4.core.macros.IMacroCommandFactory;
import org.eclipse.e4.core.macros.IMacroPlaybackContext;


	void playback(IMacroPlaybackContext macroPlaybackContext) throws Exception;

	void setMacroCommandIdToFactory(Map<String, IMacroCommandFactory> macroCommandIdToFactory);

}
