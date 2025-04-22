
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.internal.ketch.Proposal.State.ABORTED;
import static org.eclipse.jgit.internal.ketch.Proposal.State.EXECUTED;
import static org.eclipse.jgit.internal.ketch.Proposal.State.NEW;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.OK;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.reftree.Command;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PushCertificate;
import org.eclipse.jgit.transport.ReceiveCommand;

public class Proposal {
	public enum State {
		NEW(false)

		QUEUED(false)

		RUNNING(false)

		EXECUTED(true)

		ABORTED(true);

		private final boolean done;
		private State(boolean done) {
			this.done = done;
		}

		public boolean isDone() {
			return done;
		}
	}

	private final List<Command> commands;
	private PersonIdent author;
	private String message;
	private PushCertificate pushCert;
	private final List<Runnable> listeners = new CopyOnWriteArrayList<>();
	private final AtomicReference<State> state = new AtomicReference<>(NEW);

	public Proposal(List<Command> cmds) {
		this.commands = cmds;
	}

	public Proposal(RevWalk rw
			throws MissingObjectException
		this(asCommandList(rw
	}

	private static List<Command> asCommandList(RevWalk rw
			Collection<ReceiveCommand> cmds)
					throws MissingObjectException
		List<Command> commands = new ArrayList<>(cmds.size());
		for (ReceiveCommand cmd : cmds) {
			commands.add(new Command(rw
		}
		return commands;
	}

	public Collection<Command> getCommands() {
		return commands;
	}

	@Nullable
	public PersonIdent getAuthor() {
		return author;
	}

	public Proposal setAuthor(@Nullable PersonIdent who) {
		author = who;
		return this;
	}

	@Nullable
	public String getMessage() {
		return message;
	}

	public Proposal setMessage(@Nullable String msg) {
		message = msg != null && !msg.isEmpty() ? msg : null;
		return this;
	}

	@Nullable
	public PushCertificate getPushCertificate() {
		return pushCert;
	}

	public Proposal setPushCertificate(@Nullable PushCertificate cert) {
		pushCert = cert;
		return this;
	}

	public void addListener(Runnable callback) {
		boolean runNow = false;
		synchronized (state) {
			if (state.get().isDone()) {
				runNow = true;
			} else {
				listeners.add(callback);
			}
		}
		if (runNow) {
			callback.run();
		}
	}

	void success() {
		for (Command c : commands) {
			if (c.getResult() == NOT_ATTEMPTED) {
				c.setResult(OK);
			}
		}
		notifyState(EXECUTED);
	}

	void abort() {
		abort(JGitText.get().transactionAborted);
	}

	void abort(String msg) {
		for (Command cmd : commands) {
			if (cmd.getResult() == NOT_ATTEMPTED) {
				cmd.setResult(REJECTED_OTHER_REASON
				msg = JGitText.get().transactionAborted;
			}
		}
		notifyState(ABORTED);
	}

	public State getState() {
		return state.get();
	}

	public boolean isDone() {
		return state.get().isDone();
	}

	public void await() throws InterruptedException {
		synchronized (state) {
			while (!state.get().isDone()) {
				state.wait();
			}
		}
	}

	public boolean await(long wait
		synchronized (state) {
			if (state.get().isDone()) {
				return true;
			}
			state.wait(unit.toMillis(wait));
			return state.get().isDone();
		}
	}

	public boolean awaitStateChange(State notIn
			throws InterruptedException {
		synchronized (state) {
			if (state.get() != notIn) {
				return true;
			}
			state.wait(unit.toMillis(wait));
			return state.get() != notIn;
		}
	}

	void notifyState(State s) {
		synchronized (state) {
			state.set(s);
			state.notifyAll();
		}
		if (s.isDone()) {
			for (Runnable callback : listeners) {
				callback.run();
			}
			listeners.clear();
		}
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		if (author != null) {
		}
		if (message != null) {
		}
		for (Command c : commands) {
			append(s
			s.append(' ');
			append(s
			s.append(' ').append(c.getRefName());
			if (c.getResult() != ReceiveCommand.Result.NOT_ATTEMPTED) {
			}
			s.append('\n');
		}
		s.append('}');
		return s.toString();
	}

	private static void append(StringBuilder s
		if (r == null) {
			s.append(n);
		} else if (r.isSymbolic()) {
			s.append(r.getTarget().getName());
		} else {
			ObjectId id = r.getObjectId();
		}
	}
}
