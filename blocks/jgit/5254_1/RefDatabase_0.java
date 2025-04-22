
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;

public class BatchRefUpdate {
	private final RefDatabase refdb;

	private final List<ReceiveCommand> commands = new ArrayList<ReceiveCommand>();

	private boolean classifiedUpdates;

	private boolean force;

	private PersonIdent refLogIdent;

	private String refLogMessage;

	private boolean refLogIncludeResult;

	protected BatchRefUpdate(RefDatabase refdb) {
		this.refdb = refdb;
	}

	public boolean isForceUpdate() {
		return force;
	}

	public BatchRefUpdate setForceUpdate(final boolean b) {
		force = b;
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
		classifiedUpdates = false;
		return this;
	}

	public BatchRefUpdate addCommand(ReceiveCommand... cmd) {
		commands.addAll(Arrays.asList(cmd));
		classifiedUpdates = false;
		return this;
	}

	public BatchRefUpdate addCommand(Collection<ReceiveCommand> cmd) {
		commands.addAll(cmd);
		classifiedUpdates = false;
		return this;
	}

	public BatchRefUpdate updateCommandTypes(RevWalk walk) throws IOException {
		if (!classifiedUpdates) {
			for (ReceiveCommand cmd : commands)
				cmd.updateType(walk);
			classifiedUpdates = true;
		}
		return this;
	}

	public BatchRefUpdate execute(RevWalk walk
			throws IOException {
		List<ReceiveCommand> toApply = ReceiveCommand.filter(commands
				ReceiveCommand.Result.NOT_ATTEMPTED);
		if (toApply.isEmpty())
			return this;

		update.beginTask(JGitText.get().updatingReferences
		for (ReceiveCommand cmd : toApply) {
			update.update(1);
			RefUpdate ru = newUpdate(cmd);
			switch (cmd.getType()) {
			case DELETE:
				cmd.setResult(ru.delete(walk));
				break;

			case CREATE:
			case UPDATE:
			case UPDATE_NONFASTFORWARD:
				cmd.setResult(ru.update(walk));
				break;
			}
		}
		update.endTask();
		return this;
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
			ru.setForceUpdate(isForceUpdate());
			ru.setExpectedOldObjectId(cmd.getOldId());
			ru.setNewObjectId(cmd.getNewId());
			return ru;
		}
	}
}
