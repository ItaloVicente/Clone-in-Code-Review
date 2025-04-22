
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;

public class ReceiveCommand {
	public static enum Type {
		CREATE

		UPDATE

		UPDATE_NONFASTFORWARD

		DELETE;
	}

	public static enum Result {
		NOT_ATTEMPTED

		REJECTED_NOCREATE

		REJECTED_NODELETE

		REJECTED_NONFASTFORWARD

		REJECTED_CURRENT_BRANCH

		REJECTED_MISSING_OBJECT

		REJECTED_OTHER_REASON

		LOCK_FAILURE

		OK;
	}

	public static List<ReceiveCommand> filter(Iterable<ReceiveCommand> in
			Result want) {
		List<ReceiveCommand> r;
		if (in instanceof Collection)
			r = new ArrayList<>(((Collection<?>) in).size());
		else
			r = new ArrayList<>();
		for (ReceiveCommand cmd : in) {
			if (cmd.getResult() == want)
				r.add(cmd);
		}
		return r;
	}

	public static List<ReceiveCommand> filter(List<ReceiveCommand> commands
			Result want) {
		return filter((Iterable<ReceiveCommand>) commands
	}

	public static void abort(Iterable<ReceiveCommand> commands) {
		for (ReceiveCommand c : commands) {
			if (c.getResult() == NOT_ATTEMPTED) {
				c.setResult(REJECTED_OTHER_REASON
						JGitText.get().transactionAborted);
			}
		}
	}

	public static boolean isTransactionAborted(ReceiveCommand cmd) {
		return cmd.getResult() == REJECTED_OTHER_REASON
				&& cmd.getMessage().equals(JGitText.get().transactionAborted);
	}

	public static ReceiveCommand link(@NonNull ObjectId oldId
			@NonNull String newTarget
		return new ReceiveCommand(oldId
	}

	public static ReceiveCommand link(@Nullable String oldTarget
			@NonNull String newTarget
		return new ReceiveCommand(oldTarget
	}

	public static ReceiveCommand unlink(@NonNull String oldTarget
			@NonNull ObjectId newId
		return new ReceiveCommand(oldTarget
	}

	private final ObjectId oldId;

	private final String oldSymref;

	private final ObjectId newId;

	private final String newSymref;

	private final String name;

	private Type type;

	private boolean typeIsCorrect;

	private Ref ref;

	private Result status = Result.NOT_ATTEMPTED;

	private String message;

	private boolean customRefLog;

	private String refLogMessage;

	private boolean refLogIncludeResult;

	private Boolean forceRefLog;

	public ReceiveCommand(final ObjectId oldId
			final String name) {
		if (oldId == null) {
			throw new IllegalArgumentException(
					JGitText.get().oldIdMustNotBeNull);
		}
		if (newId == null) {
			throw new IllegalArgumentException(
					JGitText.get().newIdMustNotBeNull);
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					JGitText.get().nameMustNotBeNullOrEmpty);
		}
		this.oldId = oldId;
		this.oldSymref = null;
		this.newId = newId;
		this.newSymref = null;
		this.name = name;

		type = Type.UPDATE;
		if (ObjectId.zeroId().equals(oldId)) {
			type = Type.CREATE;
		}
		if (ObjectId.zeroId().equals(newId)) {
			type = Type.DELETE;
		}
	}

	public ReceiveCommand(final ObjectId oldId
			final String name
		if (oldId == null) {
			throw new IllegalArgumentException(
					JGitText.get().oldIdMustNotBeNull);
		}
		if (newId == null) {
			throw new IllegalArgumentException(
					JGitText.get().newIdMustNotBeNull);
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					JGitText.get().nameMustNotBeNullOrEmpty);
		}
		this.oldId = oldId;
		this.oldSymref = null;
		this.newId = newId;
		this.newSymref = null;
		this.name = name;
		switch (type) {
		case CREATE:
			if (!ObjectId.zeroId().equals(oldId)) {
				throw new IllegalArgumentException(
						JGitText.get().createRequiresZeroOldId);
			}
			break;
		case DELETE:
			if (!ObjectId.zeroId().equals(newId)) {
				throw new IllegalArgumentException(
						JGitText.get().deleteRequiresZeroNewId);
			}
			break;
		case UPDATE:
		case UPDATE_NONFASTFORWARD:
			if (ObjectId.zeroId().equals(newId)
					|| ObjectId.zeroId().equals(oldId)) {
				throw new IllegalArgumentException(
						JGitText.get().updateRequiresOldIdAndNewId);
			}
			break;
		default:
			throw new IllegalStateException(
					JGitText.get().enumValueNotSupported0);
		}
		this.type = type;
	}

	private ReceiveCommand(ObjectId oldId
		if (oldId == null) {
			throw new IllegalArgumentException(
					JGitText.get().oldIdMustNotBeNull);
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					JGitText.get().nameMustNotBeNullOrEmpty);
		}
		this.oldId = oldId;
		this.oldSymref = null;
		this.newId = ObjectId.zeroId();
		this.newSymref = newSymref;
		this.name = name;
		if (AnyObjectId.equals(ObjectId.zeroId()
			type = Type.CREATE;
		} else if (newSymref != null) {
			type = Type.UPDATE;
		} else {
			type = Type.DELETE;
		}
		typeIsCorrect = true;
	}

	private ReceiveCommand(String oldSymref
		if (newId == null) {
			throw new IllegalArgumentException(
					JGitText.get().newIdMustNotBeNull);
		}
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					JGitText.get().nameMustNotBeNullOrEmpty);
		}
		this.oldId = ObjectId.zeroId();
		this.oldSymref = oldSymref;
		this.newId = newId;
		this.newSymref = null;
		this.name = name;
		if (oldSymref == null) {
			type = Type.CREATE;
		} else if (!AnyObjectId.equals(ObjectId.zeroId()
			type = Type.UPDATE;
		} else {
			type = Type.DELETE;
		}
		typeIsCorrect = true;
	}

	private ReceiveCommand(@Nullable String oldTarget
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					JGitText.get().nameMustNotBeNullOrEmpty);
		}
		this.oldId = ObjectId.zeroId();
		this.oldSymref = oldTarget;
		this.newId = ObjectId.zeroId();
		this.newSymref = newTarget;
		this.name = name;
		if (oldTarget == null) {
			if (newTarget == null) {
				throw new IllegalArgumentException(
						JGitText.get().bothRefTargetsMustNotBeNull);
			}
			type = Type.CREATE;
		} else if (newTarget != null) {
			type = Type.UPDATE;
		} else {
			type = Type.DELETE;
		}
		typeIsCorrect = true;
	}

	public ObjectId getOldId() {
		return oldId;
	}

	@Nullable
	public String getOldSymref() {
		return oldSymref;
	}

	public ObjectId getNewId() {
		return newId;
	}

	@Nullable
	public String getNewSymref() {
		return newSymref;
	}

	public String getRefName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public Ref getRef() {
		return ref;
	}

	public Result getResult() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public void setRefLogMessage(String msg
		customRefLog = true;
		if (msg == null && !appendStatus) {
			disableRefLog();
		} else if (msg == null && appendStatus) {
			refLogIncludeResult = true;
		} else {
			refLogMessage = msg;
			refLogIncludeResult = appendStatus;
		}
	}

	public void disableRefLog() {
		customRefLog = true;
		refLogMessage = null;
		refLogIncludeResult = false;
	}

	public void setForceRefLog(boolean force) {
		forceRefLog = Boolean.valueOf(force);
	}

	public boolean hasCustomRefLog() {
		return customRefLog;
	}

	public boolean isRefLogDisabled() {
		return refLogMessage == null;
	}

	@Nullable
	public String getRefLogMessage() {
		return refLogMessage;
	}

	public boolean isRefLogIncludingResult() {
		return refLogIncludeResult;
	}

	@Nullable
	public Boolean isForceRefLog() {
		return forceRefLog;
	}

	public void setResult(Result s) {
		setResult(s
	}

	public void setResult(Result s
		status = s;
		message = m;
	}

	public void updateType(RevWalk walk) throws IOException {
		if (typeIsCorrect)
			return;
		if (type == Type.UPDATE && !AnyObjectId.equals(oldId
			RevObject o = walk.parseAny(oldId);
			RevObject n = walk.parseAny(newId);
			if (!(o instanceof RevCommit)
					|| !(n instanceof RevCommit)
					|| !walk.isMergedInto((RevCommit) o
				setType(Type.UPDATE_NONFASTFORWARD);
		}
		typeIsCorrect = true;
	}

	public void execute(BaseReceivePack rp) {
		try {
			String expTarget = getOldSymref();
			boolean detach = getNewSymref() != null
					|| (type == Type.DELETE && expTarget != null);
			RefUpdate ru = rp.getRepository().updateRef(getRefName()
			if (expTarget != null) {
				if (!ru.getRef().isSymbolic() || !ru.getRef().getTarget()
						.getName().equals(expTarget)) {
					setResult(Result.LOCK_FAILURE);
					return;
				}
			}

			ru.setRefLogIdent(rp.getRefLogIdent());
			ru.setRefLogMessage(refLogMessage
			switch (getType()) {
			case DELETE:
				if (!ObjectId.zeroId().equals(getOldId())) {
					ru.setExpectedOldObjectId(getOldId());
				}
				ru.setForceUpdate(true);
				setResult(ru.delete(rp.getRevWalk()));
				break;

			case CREATE:
			case UPDATE:
			case UPDATE_NONFASTFORWARD:
				ru.setForceUpdate(rp.isAllowNonFastForwards());
				ru.setExpectedOldObjectId(getOldId());
				ru.setRefLogMessage("push"
				if (getNewSymref() != null) {
					setResult(ru.link(getNewSymref()));
				} else {
					ru.setNewObjectId(getNewId());
					setResult(ru.update(rp.getRevWalk()));
				}
				break;
			}
		} catch (IOException err) {
			reject(err);
		}
	}

	void setRef(Ref r) {
		ref = r;
	}

	void setType(Type t) {
		type = t;
	}

	void setTypeFastForwardUpdate() {
		type = Type.UPDATE;
		typeIsCorrect = true;
	}

	public void setResult(RefUpdate.Result r) {
		switch (r) {
		case NOT_ATTEMPTED:
			setResult(Result.NOT_ATTEMPTED);
			break;

		case LOCK_FAILURE:
		case IO_FAILURE:
			setResult(Result.LOCK_FAILURE);
			break;

		case NO_CHANGE:
		case NEW:
		case FORCED:
		case FAST_FORWARD:
			setResult(Result.OK);
			break;

		case REJECTED:
			setResult(Result.REJECTED_NONFASTFORWARD);
			break;

		case REJECTED_CURRENT_BRANCH:
			setResult(Result.REJECTED_CURRENT_BRANCH);
			break;

		case REJECTED_MISSING_OBJECT:
			setResult(Result.REJECTED_MISSING_OBJECT);
			break;

		case REJECTED_OTHER_REASON:
			setResult(Result.REJECTED_OTHER_REASON);
			break;

		default:
			setResult(Result.REJECTED_OTHER_REASON
			break;
		}
	}

	void reject(IOException err) {
		setResult(Result.REJECTED_OTHER_REASON
				JGitText.get().lockError
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return getType().name() + ": " + getOldId().name() + " "
				+ getNewId().name() + " " + getRefName();
	}
}
