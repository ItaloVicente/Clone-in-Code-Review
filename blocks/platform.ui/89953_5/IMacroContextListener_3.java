package org.eclipse.e4.core.macros;

import java.util.Map;

public interface IMacroCommandCreator {

	IMacroCommand create(Map<String, String> stringMap) throws Exception;

}
