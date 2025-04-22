
package org.eclipse.jgit.diffmergetool;

import org.eclipse.jgit.util.FS.ExecutionResult;

public class ToolException extends Exception {

	private final ExecutionResult result;

	private final boolean commandExecutionError;

	private static final long serialVersionUID = 6618861799028752563L;

	public ToolException() {
		this(null
	}

	public ToolException(String message) {
		this(message
	}

	public ToolException(String message
			boolean commandExecutionError) {
		super(message);
		this.result = result;
		this.commandExecutionError = commandExecutionError;
	}

	public ToolException(String message
		super(message
		result = null;
		commandExecutionError = false;
	}

	public ToolException(Throwable cause) {
		super(cause);
		result = null;
		commandExecutionError = false;
	}

	public boolean isResult() {
		return result != null;
	}

	public ExecutionResult getResult() {
		return result;
	}

	public boolean isCommandExecutionError() {
		return commandExecutionError;
	}

	public String getResultStderr() {
		try {
			return new String(result.getStderr().toByteArray());
		} catch (Exception e) {
		}
	}

	public String getResultStdout() {
		try {
			return new String(result.getStdout().toByteArray());
		} catch (Exception e) {
		}
	}

}
