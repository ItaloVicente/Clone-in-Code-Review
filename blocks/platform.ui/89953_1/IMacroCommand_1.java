package org.eclipse.e4.core.macros;

public interface EMacroContext {

	boolean isRecording();

	boolean isPlayingBack();

	void addCommand(IMacroCommand macroCommand);

	void toggleMacroRecord();

	void playbackLastMacro(IMacroPlaybackContext macroPlaybackContext);

	void addMacroListener(IMacroListener listener);

	void removeMacroListener(IMacroListener listener);

}
