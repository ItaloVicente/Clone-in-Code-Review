
package org.eclipse.jgit.api.errors;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.TemporaryBuffer;

public class FilterFailedException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	private String filterCommand;

	private String path;

	private TemporaryBuffer outBuffer;

	private TemporaryBuffer errorBuffer;

	private int rc;

	public FilterFailedException(Exception cause
			String path
		super(MessageFormat.format(JGitText.get().filterExecutionFailed
				filterCommand
		this.filterCommand = filterCommand;
		this.path = path;
		this.outBuffer = outBuffer;
		this.errorBuffer = errorBuffer;
	}

	@SuppressWarnings("boxing")
	public FilterFailedException(int rc
			TemporaryBuffer outBuffer
		super(MessageFormat.format(JGitText.get().filterExecutionFailedRc
				filterCommand
		this.rc = rc;
		this.filterCommand = filterCommand;
		this.path = path;
		this.outBuffer = outBuffer;
		this.errorBuffer = errorBuffer;
	}

	public String getFilterCommand() {
		return filterCommand;
	}

	public String getPath() {
		return path;
	}

	public TemporaryBuffer getOutBuffer() {
		return outBuffer;
	}

	public TemporaryBuffer getErrorBuffer() {
		return errorBuffer;
	}

	public int getReturnCode() {
		return rc;
	}

}
