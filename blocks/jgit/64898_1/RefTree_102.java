
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.encode;
import static org.eclipse.jgit.lib.FileMode.TYPE_GITLINK;
import static org.eclipse.jgit.lib.FileMode.TYPE_SYMLINK;
import static org.eclipse.jgit.lib.Ref.Storage.NETWORK;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;

import java.io.IOException;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceiveCommand.Result;

public class Command {
	public static void abort(Iterable<Command> commands
		if (why == null || why.isEmpty()) {
			why = JGitText.get().transactionAborted;
		}
		for (Command c : commands) {
			if (c.getResult() == NOT_ATTEMPTED) {
				c.setResult(REJECTED_OTHER_REASON
				why = JGitText.get().transactionAborted;
			}
		}
	}

	private final Ref oldRef;
	private final Ref newRef;
	private final ReceiveCommand cmd;
	private Result result;

	public Command(@Nullable Ref oldRef
		this.oldRef = oldRef;
		this.newRef = newRef;
		this.cmd = null;
		this.result = NOT_ATTEMPTED;

		if (oldRef == null && newRef == null) {
			throw new IllegalArgumentException();
		}
		if (newRef != null && !newRef.isPeeled() && !newRef.isSymbolic()) {
			throw new IllegalArgumentException();
		}
		if (oldRef != null && newRef != null
				&& !oldRef.getName().equals(newRef.getName())) {
			throw new IllegalArgumentException();
		}
	}

	public Command(RevWalk rw
			throws MissingObjectException
		this.oldRef = toRef(rw
		this.newRef = toRef(rw
		this.cmd = cmd;
	}

	static Ref toRef(RevWalk rw
			boolean mustExist) throws MissingObjectException
		if (ObjectId.zeroId().equals(id)) {
			return null;
		}

		try {
			RevObject o = rw.parseAny(id);
			if (o instanceof RevTag) {
				RevObject p = rw.peel(o);
				return new ObjectIdRef.PeeledTag(NETWORK
			}
			return new ObjectIdRef.PeeledNonTag(NETWORK
		} catch (MissingObjectException e) {
			if (mustExist) {
				throw e;
			}
			return new ObjectIdRef.Unpeeled(NETWORK
		}
	}

	public String getRefName() {
		if (cmd != null) {
			return cmd.getRefName();
		} else if (newRef != null) {
			return newRef.getName();
		}
		return oldRef.getName();
	}

	public void setResult(Result result) {
		setResult(result
	}

	public void setResult(Result result
		if (cmd != null) {
			cmd.setResult(result
		} else {
			this.result = result;
		}
	}

	public Result getResult() {
		return cmd != null ? cmd.getResult() : result;
	}

	@Nullable
	public String getMessage() {
		return cmd != null ? cmd.getMessage() : null;
	}

	@Nullable
	public Ref getOldRef() {
		return oldRef;
	}

	@Nullable
	public Ref getNewRef() {
		return newRef;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		append(s
		s.append(' ');
		append(s
		s.append(' ').append(getRefName());
		s.append(' ').append(getResult());
		if (getMessage() != null) {
			s.append(' ').append(getMessage());
		}
		return s.toString();
	}

	private static void append(StringBuilder s
		if (r == null) {
			s.append(nullName);
		} else if (r.isSymbolic()) {
			s.append(r.getTarget().getName());
		} else {
			ObjectId id = r.getObjectId();
			if (id != null) {
				s.append(id.name());
			}
		}
	}

	boolean checkRef(@Nullable DirCacheEntry entry) {
		if (entry != null && entry.getRawMode() == 0) {
			entry = null;
		}
		return check(entry
	}

	private static boolean check(@Nullable DirCacheEntry cur
			@Nullable Ref exp) {
		if (cur == null) {
			return exp == null;
		} else if (exp == null) {
			return false;
		}

		if (exp.isSymbolic()) {
			String dst = exp.getTarget().getName();
			return cur.getRawMode() == TYPE_SYMLINK
					&& cur.getObjectId().equals(symref(dst));
		}

		return cur.getRawMode() == TYPE_GITLINK
				&& cur.getObjectId().equals(exp.getObjectId());
	}

	static ObjectId symref(String s) {
		@SuppressWarnings("resource")
		ObjectInserter.Formatter fmt = new ObjectInserter.Formatter();
		return fmt.idFor(OBJ_BLOB
	}
}
