package org.eclipse.e4.core.macros;

public interface EMacroContext {

	boolean isRecording();

	boolean isPlayingBack();

	void addMacroCommand(IMacroCommand macroCommand);

	void toggleMacroRecord();

	void playbackLastMacro(IMacroPlaybackContext macroPlaybackContext);

	void addMacroContextListener(IMacroContextListener listener);

	void removeMacroContextListener(IMacroContextListener listener);

}
