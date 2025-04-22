package org.eclipse.e4.ui.macros;

import java.util.Map;

public interface EAcceptedCommands {

	boolean isCommandAccepted(String commandId);

	boolean isCommandRecorded(String commandId);

	void setCommandAccepted(String commandId, boolean acceptCommand, boolean recordActivation);

	Map<String, Boolean> getCommandsAccepted();

}
