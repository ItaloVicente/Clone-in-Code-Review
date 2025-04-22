package org.eclipse.e4.ui.macros.internal.actions;

import org.eclipse.e4.core.macros.EMacroService;
import org.eclipse.e4.core.macros.IMacroInstruction;
import org.eclipse.e4.core.macros.IMacroInstructionsListener;
import org.eclipse.e4.core.macros.IMacroStateListener;
import org.eclipse.e4.ui.macros.internal.UserNotifications;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

public class KeepMacroUIUpdated implements IMacroStateListener {

	private static final class MacroInstructionsListener implements IMacroInstructionsListener {
		@Override
		public void beforeMacroInstructionAdded(IMacroInstruction macroInstruction) {

		}

		@Override
		public void afterMacroInstructionAdded(IMacroInstruction macroInstruction) {
			UserNotifications.setMessage(Messages.KeepMacroUIUpdated_RecordedInMacro + macroInstruction);
		}
	}

	boolean wasRecording = false;

	boolean wasPlayingBack = false;

	IMacroInstructionsListener fMacroInstructionsListener;

	@Override
	public void macroStateChanged(EMacroService macroService) {
		ICommandService commandService = PlatformUI.getWorkbench().getService(ICommandService.class);
		commandService.refreshElements(ToggleMacroRecordAction.COMMAND_ID, null);

		if (macroService.isRecording() != wasRecording) {
			if (!wasRecording) {
				UserNotifications.setMessage(Messages.KeepMacroUIUpdated_StartMacroRecord);
			} else {
				UserNotifications.setMessage(null);
			}
			wasRecording = macroService.isRecording();
		}
		if (macroService.isPlayingBack() != wasPlayingBack) {
			if (!wasPlayingBack) {
				UserNotifications.setMessage(Messages.KeepMacroUIUpdated_StartMacroPlayback);
			} else {
				UserNotifications.setMessage(null);
			}
			wasPlayingBack = macroService.isPlayingBack();
		}

		if (macroService.isRecording()) {
			if (fMacroInstructionsListener == null) {
				fMacroInstructionsListener = new MacroInstructionsListener();
				macroService.addMacroInstructionsListener(fMacroInstructionsListener);
			}
		} else {
			if (fMacroInstructionsListener != null) {
				macroService.removeMacroInstructionsListener(fMacroInstructionsListener);
				fMacroInstructionsListener = null;
			}
		}
	}
}
