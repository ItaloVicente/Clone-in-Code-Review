
package org.eclipse.jgit.api.errors;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class FilterFailedException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	private String filterCommand;

	private String path;

	private byte[] stdout;

	private String stderr;

	private int rc;

	public FilterFailedException(Exception cause
			String path) {
		super(MessageFormat.format(JGitText.get().filterExecutionFailed
				filterCommand
		this.filterCommand = filterCommand;
		this.path = path;
	}

	@SuppressWarnings("boxing")
	public FilterFailedException(int rc
			byte[] stdout
		super(MessageFormat.format(JGitText.get().filterExecutionFailedRc
				filterCommand
		this.rc = rc;
		this.filterCommand = filterCommand;
		this.path = path;
		this.stdout = stdout;
		this.stderr = stderr;
	}

	public String getFilterCommand() {
		return filterCommand;
	}

	public String getPath() {
		return path;
	}

	public byte[] getOutput() {
		return stdout;
	}

	public String getError() {
		return stderr;
	}

	public int getReturnCode() {
		return rc;
	}

}
