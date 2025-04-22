package org.eclipse.e4.core.macros;

public interface IMacroInstructionsListener {

	void beforeMacroInstructionAdded(IMacroInstruction macroInstruction) throws CancelMacroRecordingException;

	void afterMacroInstructionAdded(IMacroInstruction macroInstruction);
}
