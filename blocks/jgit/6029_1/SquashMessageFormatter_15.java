
package org.eclipse.jgit.lib;

import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;

public class BatchRefUpdate {
	private final RefDatabase refdb;

	private final List<ReceiveCommand> commands;

	private boolean allowNonFastForwards;

	private PersonIdent refLogIdent;

	private String refLogMessage;

	private boolean refLogIncludeResult;

	protected BatchRefUpdate(RefDatabase refdb) {
		this.refdb = refdb;
		this.commands = new ArrayList<ReceiveCommand>();
	}

	public boolean isAllowNonFastForwards() {
		return allowNonFastForwards;
	}

	public BatchRefUpdate setAllowNonFastForwards(boolean allow) {
		allowNonFastForwards = allow;
		return this;
	}

	public PersonIdent getRefLogIdent() {
		return refLogIdent;
	}

	public BatchRefUpdate setRefLogIdent(final PersonIdent pi) {
		refLogIdent = pi;
		return this;
	}

	public String getRefLogMessage() {
		return refLogMessage;
	}

	public boolean isRefLogIncludingResult() {
		return refLogIncludeResult;
	}

	public BatchRefUpdate setRefLogMessage(String msg
		if (msg == null && !appendStatus)
			disableRefLog();
		else if (msg == null && appendStatus) {
			refLogMessage = "";
			refLogIncludeResult = true;
		} else {
			refLogMessage = msg;
			refLogIncludeResult = appendStatus;
		}
		return this;
	}

	public BatchRefUpdate disableRefLog() {
		refLogMessage = null;
		refLogIncludeResult = false;
		return this;
	}

	public boolean isRefLogDisabled() {
		return refLogMessage == null;
	}

	public List<ReceiveCommand> getCommands() {
		return Collections.unmodifiableList(commands);
	}

	public BatchRefUpdate addCommand(ReceiveCommand cmd) {
		commands.add(cmd);
		return this;
	}

	public BatchRefUpdate addCommand(ReceiveCommand... cmd) {
		return addCommand(Arrays.asList(cmd));
	}

	public BatchRefUpdate addCommand(Collection<ReceiveCommand> cmd) {
		commands.addAll(cmd);
		return this;
	}

	public void execute(RevWalk walk
			throws IOException {
		update.beginTask(JGitText.get().updatingReferences
		for (ReceiveCommand cmd : commands) {
			try {
				update.update(1);

				if (cmd.getResult() == NOT_ATTEMPTED) {
					cmd.updateType(walk);
					RefUpdate ru = newUpdate(cmd);
					switch (cmd.getType()) {
					case DELETE:
						cmd.setResult(ru.delete(walk));
						continue;

					case CREATE:
					case UPDATE:
					case UPDATE_NONFASTFORWARD:
						cmd.setResult(ru.update(walk));
						continue;
					}
				}
			} catch (IOException err) {
				cmd.setResult(REJECTED_OTHER_REASON
						JGitText.get().lockError
			}
		}
		update.endTask();
	}

	protected RefUpdate newUpdate(ReceiveCommand cmd) throws IOException {
		RefUpdate ru = refdb.newUpdate(cmd.getRefName()
		if (isRefLogDisabled())
			ru.disableRefLog();
		else {
			ru.setRefLogIdent(refLogIdent);
			ru.setRefLogMessage(refLogMessage
		}
		switch (cmd.getType()) {
		case DELETE:
			if (!ObjectId.zeroId().equals(cmd.getOldId()))
				ru.setExpectedOldObjectId(cmd.getOldId());
			ru.setForceUpdate(true);
			return ru;

		case CREATE:
		case UPDATE:
		case UPDATE_NONFASTFORWARD:
		default:
			ru.setForceUpdate(isAllowNonFastForwards());
			ru.setExpectedOldObjectId(cmd.getOldId());
			ru.setNewObjectId(cmd.getNewId());
			return ru;
		}
	}
}
