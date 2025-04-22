
package org.eclipse.jgit.lib;

import java.io.IOException;

import org.eclipse.jgit.lib.RefUpdate.Result;

public abstract class RefRename {
	protected final RefUpdate source;

	protected final RefUpdate destination;

	private Result result = Result.NOT_ATTEMPTED;

	protected RefRename(RefUpdate src
		source = src;
		destination = dst;

		if (source.getName().startsWith(Constants.R_HEADS)
				&& destination.getName().startsWith(Constants.R_HEADS))
				+ Repository.shortenRefName(destination.getName()));
	}

	public PersonIdent getRefLogIdent() {
		return destination.getRefLogIdent();
	}

	public void setRefLogIdent(PersonIdent pi) {
		destination.setRefLogIdent(pi);
	}

	public String getRefLogMessage() {
		return destination.getRefLogMessage();
	}

	public void setRefLogMessage(String msg) {
		if (msg == null)
			disableRefLog();
		else
			destination.setRefLogMessage(msg
	}

	public void disableRefLog() {
		destination.setRefLogMessage(""
	}

	public Result getResult() {
		return result;
	}

	public Result rename() throws IOException {
		try {
			result = doRename();
			return result;
		} catch (IOException err) {
			result = Result.IO_FAILURE;
			throw err;
		}
	}

	protected abstract Result doRename() throws IOException;

	protected boolean needToUpdateHEAD() throws IOException {
		Ref head = source.getRefDatabase().exactRef(Constants.HEAD);
		if (head != null && head.isSymbolic()) {
			head = head.getTarget();
			return head.getName().equals(source.getName());
		}
		return false;
	}
}
