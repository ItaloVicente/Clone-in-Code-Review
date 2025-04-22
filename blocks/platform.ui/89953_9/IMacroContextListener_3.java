package org.eclipse.e4.core.macros;

import java.util.Map;

public interface IMacroCommandFactory {

	IMacroCommand create(Map<String, String> stringMap) throws Exception;

}
