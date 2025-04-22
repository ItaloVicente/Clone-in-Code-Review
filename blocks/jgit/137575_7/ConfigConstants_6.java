
package org.eclipse.jgit.diffmergetool;

import org.eclipse.jgit.util.FS.ExecutionResult;

public class ToolException extends Exception {

	ExecutionResult result = null;

	private static final long serialVersionUID = 6618861799028752563L;

	public ToolException() {
		super();
	}

	public ToolException(String message) {
		super(message);
	}

	public ToolException(String message
		super(message);
		this.result = result;
	}

	public ToolException(String message
		super(message
	}

	public ToolException(Throwable cause) {
		super(cause);
	}

	public void setResult(ExecutionResult result) {
		this.result = result;
	}

	public boolean isResult() {
		return result != null;
	}

	public ExecutionResult getResult() {
		return result;
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
