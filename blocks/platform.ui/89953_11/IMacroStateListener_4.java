package org.eclipse.e4.core.macros;

import java.util.Map;

public interface IMacroPlaybackContext {

	IMacroInstruction createMacroInstruction(String macroInstructionId, Map<String, String> stringMap) throws Exception;

}
