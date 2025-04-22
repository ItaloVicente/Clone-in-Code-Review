package org.eclipse.e4.core.macros;

import java.util.Set;

public interface EMacroService {

	boolean isRecording();

	boolean isPlayingBack();

	void addMacroInstruction(IMacroInstruction macroInstruction);

	int PRIORITY_LOW = 0;

	int PRIORITY_HIGH = 10;

	void addMacroInstruction(IMacroInstruction macroInstruction, Object event, int priority);

	void toggleMacroRecord();

	void playbackLastMacro();

	void addMacroStateListener(IMacroStateListener listener);

	void removeMacroStateListener(IMacroStateListener listener);


	@SuppressWarnings("javadoc")
	boolean isCommandWhitelisted(String commandId);

	@SuppressWarnings("javadoc")
	boolean getRecordMacroInstruction(String commandId);

	@SuppressWarnings("javadoc")
	void setCommandWhitelisted(String commandId, boolean whitelistCommand, boolean recordMacroInstruction);

	@SuppressWarnings("javadoc")
	Set<String> getCommandsWhitelisted();

}
