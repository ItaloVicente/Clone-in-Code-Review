package org.eclipse.e4.core.macros.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import org.eclipse.core.runtime.Assert;
import org.eclipse.e4.core.macros.IMacroCommand;
import org.eclipse.e4.core.macros.IMacroCommandFactory;
import org.eclipse.e4.core.macros.IMacroPlaybackContext;

public class SavedJSMacro implements IMacro {

	private final File fFile;

	private Map<String, IMacroCommandFactory> fCommandIdToFactory;

	public SavedJSMacro(File file) {
		this.fFile = file;
	}

	@SuppressWarnings({ "rawtypes" })
	public static void runCommand(Map<String, IMacroCommandFactory> commandIdToFactory,
			IMacroPlaybackContext macroPlaybackContext, String commandId, Object commandParameters) throws Exception {
		Assert.isNotNull(commandIdToFactory, "Before running, fCommandIdToFactory must be set."); //$NON-NLS-1$
		Map<String, String> stringMap = new HashMap<>();
		Map m = (Map) commandParameters;
		Set<Map.Entry> entrySet = m.entrySet();
		for (Map.Entry entry : entrySet) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			stringMap.put(key.toString(), value.toString());
		}
		IMacroCommandFactory iMacroFactory = commandIdToFactory.get(commandId);
		if (iMacroFactory == null) {
			throw new RuntimeException("Unable to find IMacroCommandFactory for command: " + commandId); //$NON-NLS-1$
		}
		IMacroCommand command = iMacroFactory.create(stringMap);
		command.execute(macroPlaybackContext);
	}

	@Override
	public void setCommandIdToFactory(Map<String, IMacroCommandFactory> commandIdToFactory) {
		this.fCommandIdToFactory = commandIdToFactory;
	}

	@Override
	public void playback(IMacroPlaybackContext macroPlaybackContext) throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("nashorn"); //$NON-NLS-1$
		SimpleScriptContext context = new SimpleScriptContext();
		context.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
		Bindings engineScope = context.getBindings(ScriptContext.ENGINE_SCOPE);

		engineScope.put("__commandIdToFactory", fCommandIdToFactory); //$NON-NLS-1$
		engineScope.put("__macroPlaybackContext", macroPlaybackContext); //$NON-NLS-1$

		engine.eval("" + //$NON-NLS-1$
				"__macro = Java.type('org.eclipse.e4.core.macros.internal.SavedJSMacro');\n" //$NON-NLS-1$
				+ "function runCommand(commandId, commandParameters){" //$NON-NLS-1$
				+ "__macro.runCommand(__commandIdToFactory, __macroPlaybackContext, commandId, commandParameters);" //$NON-NLS-1$
				+ "}" //$NON-NLS-1$
				+ "", context); //$NON-NLS-1$

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fFile), "UTF-8"))) { //$NON-NLS-1$
			engine.eval(reader, context);

			engine.eval("runMacro();", context); //$NON-NLS-1$
		}
	}
}
