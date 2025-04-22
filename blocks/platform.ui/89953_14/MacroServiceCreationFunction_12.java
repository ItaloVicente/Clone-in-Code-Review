package org.eclipse.e4.core.macros.internal;

import java.util.Map;
import org.eclipse.e4.core.macros.IMacroInstruction;
import org.eclipse.e4.core.macros.IMacroInstructionFactory;
import org.eclipse.e4.core.macros.IMacroPlaybackContext;

public class MacroPlaybackContextImpl implements IMacroPlaybackContext {

	private Map<String, IMacroInstructionFactory> fMacroInstructionIdToFactory;

	public MacroPlaybackContextImpl(Map<String, IMacroInstructionFactory> macroInstructionIdToFactory) {
		this.fMacroInstructionIdToFactory = macroInstructionIdToFactory;
	}

	@Override
	public IMacroInstruction createMacroInstruction(String macroInstructionId, Map<String, String> stringMap)
			throws Exception {
		IMacroInstructionFactory macroFactory = fMacroInstructionIdToFactory.get(macroInstructionId);
		if (macroFactory == null) {
			throw new RuntimeException(
					"Unable to find IMacroInstructionFactory for macro instruction: " + macroInstructionId); //$NON-NLS-1$
		}
		return macroFactory.create(stringMap);
	}

}
