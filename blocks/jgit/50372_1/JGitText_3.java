
package org.eclipse.jgit.api.errors;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.TemporaryBuffer.LocalFile;

public class FilterFailedException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	private String filterCommand;

	private String path;

	private LocalFile outBuffer;

	private LocalFile errorBuffer;

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
			LocalFile outBuffer
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

	public LocalFile getOutBuffer() {
		return outBuffer;
	}

	public LocalFile getErrorBuffer() {
		return errorBuffer;
	}

	public int getReturnCode() {
		return rc;
	}

}
