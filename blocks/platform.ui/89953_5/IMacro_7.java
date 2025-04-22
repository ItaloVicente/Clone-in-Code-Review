package org.eclipse.e4.core.macros.internal;

import java.util.Map;
import org.eclipse.e4.core.macros.IMacroCommandCreator;
import org.eclipse.e4.core.macros.IMacroPlaybackContext;


	void playback(IMacroPlaybackContext macroPlaybackContext) throws Exception;

	void setCommandIdToCreator(Map<String, IMacroCommandCreator> commandIdToCreator);

}
