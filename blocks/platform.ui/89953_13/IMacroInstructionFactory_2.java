package org.eclipse.e4.core.macros;

import java.util.Map;

public interface IMacroInstruction {

	String getId();

	void execute(IMacroPlaybackContext macroPlaybackContext) throws Exception;

	Map<String, String> toMap();

}
