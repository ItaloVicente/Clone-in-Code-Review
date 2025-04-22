package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.transport.ReceiveCommand.Result;

public interface ReceiveCommandErrorHandler {
	default void handleNewIdValidationException(ReceiveCommand cmd
			IOException e) {
		cmd.setResult(Result.REJECTED_MISSING_OBJECT
	}

	default void handleOldIdValidationException(ReceiveCommand cmd
			IOException e) {
		cmd.setResult(Result.REJECTED_MISSING_OBJECT
	}

	default void handleFastForwardCheckException(ReceiveCommand cmd
			IOException e) {
		if (e instanceof MissingObjectException) {
			cmd.setResult(Result.REJECTED_MISSING_OBJECT
		} else {
			cmd.setResult(Result.REJECTED_OTHER_REASON);
		}
	}

	default void handleBatchRefUpdateException(List<ReceiveCommand> cmds
			IOException e) {
		for (ReceiveCommand cmd : cmds) {
			if (cmd.getResult() == Result.NOT_ATTEMPTED) {
				cmd.reject(e);
			}
		}
	}
}
