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
import org.eclipse.e4.core.macros.IMacroInstruction;
import org.eclipse.e4.core.macros.IMacroPlaybackContext;

public class SavedJSMacro implements IMacro {

	private final File fFile;

	public SavedJSMacro(File file) {
		this.fFile = file;
	}

	@SuppressWarnings({ "rawtypes" })
	public static void runMacroInstruction(IMacroPlaybackContext macroPlaybackContext, String macroInstructionId,
			Object macroInstructionParameters) throws Exception {
		Map<String, String> stringMap = new HashMap<>();
		Map m = (Map) macroInstructionParameters;
		Set<Map.Entry> entrySet = m.entrySet();
		for (Map.Entry entry : entrySet) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			stringMap.put(key.toString(), value.toString());
		}
		IMacroInstruction macroInstruction = macroPlaybackContext.createMacroInstruction(macroInstructionId, stringMap);
		macroInstruction.execute(macroPlaybackContext);
	}

	@Override
	public void playback(IMacroPlaybackContext macroPlaybackContext) throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("nashorn"); //$NON-NLS-1$
		SimpleScriptContext context = new SimpleScriptContext();
		context.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
		Bindings engineScope = context.getBindings(ScriptContext.ENGINE_SCOPE);

		engineScope.put("__macroPlaybackContext", macroPlaybackContext); //$NON-NLS-1$

		engine.eval("" + //$NON-NLS-1$
				"__macro = Java.type('org.eclipse.e4.core.macros.internal.SavedJSMacro');\n" //$NON-NLS-1$
				+ "function runMacroInstruction(macroMacroInstructionId, macroInstructionParameters){" //$NON-NLS-1$
				+ "    __macro.runMacroInstruction(__macroPlaybackContext, macroMacroInstructionId, macroInstructionParameters);" //$NON-NLS-1$
				+ "}" //$NON-NLS-1$
				+ "", context); //$NON-NLS-1$

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fFile), "UTF-8"))) { //$NON-NLS-1$
			engine.eval(reader, context);

			engine.eval("runMacro();", context); //$NON-NLS-1$
		}
	}
}
