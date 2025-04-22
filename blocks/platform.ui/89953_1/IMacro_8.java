package org.eclipse.e4.core.macros.internal;

import java.util.Map;
import org.eclipse.e4.core.macros.IMacroCreator;
import org.eclipse.e4.core.macros.IMacroPlaybackContext;


	void playback(IMacroPlaybackContext macroPlaybackContext) throws Exception;

	void setCommandIdToCreator(Map<String, IMacroCreator> commandIdToCreator);

}
