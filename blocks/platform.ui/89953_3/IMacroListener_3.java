package org.eclipse.e4.core.macros;

import java.util.Map;

public interface IMacroCreator {

	IMacroCommand create(Map<String, String> stringMap) throws Exception;

}
