package org.eclipse.e4.core.macros;

import java.util.Map;

public interface IMacroInstructionFactory {

	IMacroInstruction create(Map<String, String> stringMap) throws Exception;

}
