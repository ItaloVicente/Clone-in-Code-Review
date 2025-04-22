
package org.eclipse.jgit.lib;

import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;
import static java.util.stream.Collectors.toCollection;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PushCertificate;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.util.time.ProposedTimestamp;

public class BatchRefUpdate {
	protected static final Duration MAX_WAIT = Duration.ofSeconds(5);

	private final RefDatabase refdb;

	private final List<ReceiveCommand> commands;

	private boolean allowNonFastForwards;

	private PersonIdent refLogIdent;

	private String refLogMessage;

	private boolean refLogIncludeResult;

	private boolean forceRefLog;

	private PushCertificate pushCert;

	private boolean atomic;

	private List<String> pushOptions;

	private List<ProposedTimestamp> timestamps;

	protected BatchRefUpdate(RefDatabase refdb) {
		this.refdb = refdb;
		this.commands = new ArrayList<>();
		this.atomic = refdb.performsAtomicTransactions();
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

	public BatchRefUpdate setRefLogIdent(PersonIdent pi) {
		refLogIdent = pi;
		return this;
	}

	@Nullable
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

	public BatchRefUpdate setForceRefLog(boolean force) {
		forceRefLog = force;
		return this;
	}

	public boolean isRefLogDisabled() {
		return refLogMessage == null;
	}

	protected boolean isForceRefLog() {
		return forceRefLog;
	}

	public BatchRefUpdate setAtomic(boolean atomic) {
		this.atomic = atomic;
		return this;
	}

	public boolean isAtomic() {
		return atomic;
	}

	public void setPushCertificate(PushCertificate cert) {
		pushCert = cert;
	}

	protected PushCertificate getPushCertificate() {
		return pushCert;
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

	@Nullable
	public List<String> getPushOptions() {
		return pushOptions;
	}

	protected void setPushOptions(List<String> options) {
		pushOptions = options;
	}

	public List<ProposedTimestamp> getProposedTimestamps() {
		if (timestamps != null) {
			return Collections.unmodifiableList(timestamps);
		}
		return Collections.emptyList();
	}

	public BatchRefUpdate addProposedTimestamp(ProposedTimestamp ts) {
		if (timestamps == null) {
			timestamps = new ArrayList<>(4);
		}
		timestamps.add(ts);
		return this;
	}

	public void execute(RevWalk walk
			List<String> options) throws IOException {

		if (atomic && !refdb.performsAtomicTransactions()) {
			for (ReceiveCommand c : commands) {
				if (c.getResult() == NOT_ATTEMPTED) {
					c.setResult(REJECTED_OTHER_REASON
							JGitText.get().atomicRefUpdatesNotSupported);
				}
			}
			return;
		}
		if (!blockUntilTimestamps(MAX_WAIT)) {
			return;
		}

		if (options != null) {
			setPushOptions(options);
		}

		monitor.beginTask(JGitText.get().updatingReferences
		List<ReceiveCommand> commands2 = new ArrayList<>(
				commands.size());
		for (ReceiveCommand cmd : commands) {
			try {
				if (cmd.getResult() == NOT_ATTEMPTED) {
					if (isMissing(walk
							|| isMissing(walk
						cmd.setResult(ReceiveCommand.Result.REJECTED_MISSING_OBJECT);
						continue;
					}
					cmd.updateType(walk);
					switch (cmd.getType()) {
					case CREATE:
						commands2.add(cmd);
						break;
					case UPDATE:
					case UPDATE_NONFASTFORWARD:
						commands2.add(cmd);
						break;
					case DELETE:
						RefUpdate rud = newUpdate(cmd);
						monitor.update(1);
						cmd.setResult(rud.delete(walk));
					}
				}
			} catch (IOException err) {
				cmd.setResult(
						REJECTED_OTHER_REASON
						MessageFormat.format(JGitText.get().lockError
								err.getMessage()));
			}
		}
		if (!commands2.isEmpty()) {
			Collection<String> takenNames = refdb.getRefs().stream()
					.map(Ref::getName)
					.collect(toCollection(HashSet::new));
			Collection<String> takenPrefixes = getTakenPrefixes(takenNames);

			for (ReceiveCommand cmd : commands2) {
				try {
					if (cmd.getResult() == NOT_ATTEMPTED) {
						cmd.updateType(walk);
						RefUpdate ru = newUpdate(cmd);
						SWITCH: switch (cmd.getType()) {
						case DELETE:
							break;
						case UPDATE:
						case UPDATE_NONFASTFORWARD:
							RefUpdate ruu = newUpdate(cmd);
							cmd.setResult(ruu.update(walk));
							break;
						case CREATE:
							for (String prefix : getPrefixes(cmd.getRefName())) {
								if (takenNames.contains(prefix)) {
									cmd.setResult(Result.LOCK_FAILURE);
									break SWITCH;
								}
							}
							if (takenPrefixes.contains(cmd.getRefName())) {
								cmd.setResult(Result.LOCK_FAILURE);
								break SWITCH;
							}
							ru.setCheckConflicting(false);
							takenPrefixes.addAll(getPrefixes(cmd.getRefName()));
							takenNames.add(cmd.getRefName());
							cmd.setResult(ru.update(walk));
						}
					}
				} catch (IOException err) {
					cmd.setResult(REJECTED_OTHER_REASON
							JGitText.get().lockError
				} finally {
					monitor.update(1);
				}
			}
		}
		monitor.endTask();
	}

	private static boolean isMissing(RevWalk walk
			throws IOException {
		if (id.equals(ObjectId.zeroId())) {
		}
		try {
			walk.parseAny(id);
			return false;
		} catch (MissingObjectException e) {
			return true;
		}
	}

	protected boolean blockUntilTimestamps(Duration maxWait) {
		if (timestamps == null) {
			return true;
		}
		try {
			ProposedTimestamp.blockUntil(timestamps
			return true;
		} catch (TimeoutException | InterruptedException e) {
			String msg = JGitText.get().timeIsUncertain;
			for (ReceiveCommand c : commands) {
				if (c.getResult() == NOT_ATTEMPTED) {
					c.setResult(REJECTED_OTHER_REASON
				}
			}
			return false;
		}
	}

	public void execute(RevWalk walk
			throws IOException {
		execute(walk
	}

	private static Collection<String> getTakenPrefixes(Collection<String> names) {
		Collection<String> ref = new HashSet<>();
		for (String name : names) {
			addPrefixesTo(name
		}
		return ref;
	}

	protected static Collection<String> getPrefixes(String name) {
		Collection<String> ret = new HashSet<>();
		addPrefixesTo(name
		return ret;
	}

	protected static void addPrefixesTo(String name
		int p1 = name.indexOf('/');
		while (p1 > 0) {
			out.add(name.substring(0
			p1 = name.indexOf('/'
		}
	}

	protected RefUpdate newUpdate(ReceiveCommand cmd) throws IOException {
		RefUpdate ru = refdb.newUpdate(cmd.getRefName()
		if (isRefLogDisabled(cmd)) {
			ru.disableRefLog();
		} else {
			ru.setRefLogIdent(refLogIdent);
			ru.setRefLogMessage(getRefLogMessage(cmd)
			ru.setForceRefLog(isForceRefLog(cmd));
		}
		ru.setPushCertificate(pushCert);
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

	protected boolean isRefLogDisabled(ReceiveCommand cmd) {
		return cmd.hasCustomRefLog() ? cmd.isRefLogDisabled() : isRefLogDisabled();
	}

	protected String getRefLogMessage(ReceiveCommand cmd) {
		return cmd.hasCustomRefLog() ? cmd.getRefLogMessage() : getRefLogMessage();
	}

	protected boolean isRefLogIncludingResult(ReceiveCommand cmd) {
		return cmd.hasCustomRefLog()
				? cmd.isRefLogIncludingResult() : isRefLogIncludingResult();
	}

	protected boolean isForceRefLog(ReceiveCommand cmd) {
		Boolean isForceRefLog = cmd.isForceRefLog();
		return isForceRefLog != null ? isForceRefLog.booleanValue()
				: isForceRefLog();
	}

	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append(getClass().getSimpleName()).append('[');
		if (commands.isEmpty())
			return r.append(']').toString();

		r.append('\n');
		for (ReceiveCommand cmd : commands) {
			r.append(cmd);
			if (cmd.getMessage() != null) {
			}
		}
		return r.append(']').toString();
	}
}
