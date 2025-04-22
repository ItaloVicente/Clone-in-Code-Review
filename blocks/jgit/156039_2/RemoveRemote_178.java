package org.eclipse.jgit.niofs.internal.op.commands;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.eclipse.jgit.internal.ketch.Proposal.State.QUEUED;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.MASTER;
import static org.eclipse.jgit.lib.Ref.Storage.NETWORK;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.ketch.Proposal;
import org.eclipse.jgit.internal.storage.reftree.Command;
import org.eclipse.jgit.internal.storage.reftree.RefTree;
import org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.exceptions.ConcurrentRefUpdateException;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;

public class RefTreeUpdateCommand {

	private final Git git;
	private final String name;
	private final RevCommit commit;

	public RefTreeUpdateCommand(final Git git
		this.git = git;
		this.name = branchName;
		this.commit = commit;
	}

	public void execute() throws java.io.IOException
		update(git.getRepository()
		if (name.equals(MASTER) && !git.isHEADInitialized()) {
			synchronized (git.getRepository()) {
				symRef(git
				git.setHeadAsInitialized();
			}
		}
	}

	private void symRef(final Git git
		commit(git.getRepository()
			Ref old = tree.exactRef(reader
			Ref newx = tree.exactRef(reader
			final Command n;
			if (newx != null) {
				n = new Command(old
			} else {
				n = new Command(old
			}
			return tree.apply(Collections.singleton(n));
		});
	}

	private void update(final Repository _repo
		commit(_repo
			final Ref old = refTree.exactRef(reader
			final List<Command> n = new ArrayList<>(1);
			try (RevWalk rw = new RevWalk(_repo)) {
				n.add(new Command(old
				if (git.isKetchEnabled()) {
					proposeKetch(n
				}
			} catch (final IOException | InterruptedException e) {
				String msg = JGitText.get().transactionAborted;
				for (Command cmd : n) {
					if (cmd.getResult() == NOT_ATTEMPTED) {
						cmd.setResult(REJECTED_OTHER_REASON
					}
				}
				throw new GitException("Error");
			}
			return refTree.apply(n);
		});
	}

	private void proposeKetch(final List<Command> n
		final Proposal proposal = new Proposal(n).setAuthor(_commit.getAuthorIdent()).setMessage("push");
		git.getKetchLeader().queueProposal(proposal);
		if (proposal.isDone()) {
			throw new GitException("Error");
		}
		if (proposal.getState() == QUEUED) {
			waitForQueue(proposal);
		}
		if (!proposal.isDone()) {
			waitForPropose(proposal);
		}
	}

	private void waitForQueue(final Proposal proposal) throws InterruptedException {
		while (!proposal.awaitStateChange(QUEUED
			System.out.println("waiting queue...");
		}
		switch (proposal.getState()) {
		case RUNNING:
		default:
			break;

		case EXECUTED:
			break;

		case ABORTED:
			break;
		}
	}

	private void waitForPropose(final Proposal proposal) throws InterruptedException {
		while (!proposal.await(250
			System.out.println("waiting propose...");
		}
	}

	private static Ref toRef(final RevWalk rw
			throws IOException {
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

	interface BiFunction {

		boolean apply(final ObjectReader reader
	}

	private void commit(final Repository repo
		try (final ObjectReader reader = repo.newObjectReader();
				final ObjectInserter inserter = repo.newObjectInserter();
				final RevWalk rw = new RevWalk(reader)) {

			final RefTreeDatabase refdb = (RefTreeDatabase) repo.getRefDatabase();
			final RefDatabase bootstrap = refdb.getBootstrap();
			final RefUpdate refUpdate = bootstrap.newUpdate(refdb.getTxnCommitted()

			final CommitBuilder cb = new CommitBuilder();
			final Ref ref = bootstrap.exactRef(refdb.getTxnCommitted());
			final RefTree tree;
			if (ref != null && ref.getObjectId() != null) {
				tree = RefTree.read(reader
				cb.setParentId(ref.getObjectId());
				refUpdate.setExpectedOldObjectId(ref.getObjectId());
			} else {
				tree = RefTree.newEmptyTree();
				refUpdate.setExpectedOldObjectId(ObjectId.zeroId());
			}

			if (fun.apply(reader
				final Ref ref2 = bootstrap.exactRef(refdb.getTxnCommitted());
				if (ref2 == null || ref2.getObjectId().equals(ref != null ? ref.getObjectId() : null)) {
					cb.setTreeId(tree.writeTree(inserter));
					if (original != null) {
						cb.setAuthor(original.getAuthorIdent());
						cb.setCommitter(original.getAuthorIdent());
					} else {
						final PersonIdent personIdent = new PersonIdent("user"
						cb.setAuthor(personIdent);
						cb.setCommitter(personIdent);
					}
					refUpdate.setNewObjectId(inserter.insert(cb));
					inserter.flush();
					final RefUpdate.Result result = refUpdate.update(rw);
					switch (result) {
					case NEW:
					case FAST_FORWARD:
						break;
					default:
						throw new RuntimeException(
								repo.getDirectory() + " -> " + result.toString() + " : " + refUpdate.getName());
					}
					final File commited = new File(repo.getDirectory()
					final File accepted = new File(repo.getDirectory()
					Files.copy(commited.toPath()
				}
			}
		}
	}
}
