
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefComparator;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.opt.OptionWithValuesListHandler;
import org.eclipse.jgit.revwalk.RevWalk;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Branch extends TextBuiltin {

	private String otherBranch;
	private boolean createForce;
	private boolean rename;

	@Option(name = "--remote"
	private boolean remote = false;

	@Option(name = "--all"
	private boolean all = false;

	@Option(name = "--contains"
	private String containsCommitish;

	private List<String> delete;

	@Option(name = "--delete"
			"-d" }
	public void delete(List<String> names) {
		if (names.isEmpty()) {
			throw die(CLIText.get().branchNameRequired);
		}
		delete = names;
	}

	private List<String> deleteForce;

	@Option(name = "--delete-force"
			"-D" }
	public void deleteForce(List<String> names) {
		if (names.isEmpty()) {
			throw die(CLIText.get().branchNameRequired);
		}
		deleteForce = names;
	}

	@Option(name = "--create-force"
			"-f" }
	public void createForce(List<String> branchAndStartPoint) {
		createForce = true;
		if (branchAndStartPoint.isEmpty()) {
			throw die(CLIText.get().branchNameRequired);
		}
		if (branchAndStartPoint.size() > 2) {
			throw die(CLIText.get().tooManyRefsGiven);
		}
		if (branchAndStartPoint.size() == 1) {
			branch = branchAndStartPoint.get(0);
		} else {
			branch = branchAndStartPoint.get(0);
			otherBranch = branchAndStartPoint.get(1);
		}
	}

	@Option(name = "--move"
			"-m" }
	public void moveRename(List<String> currentAndNew) {
		rename = true;
		if (currentAndNew.isEmpty()) {
			throw die(CLIText.get().branchNameRequired);
		}
		if (currentAndNew.size() > 2) {
			throw die(CLIText.get().tooManyRefsGiven);
		}
		if (currentAndNew.size() == 1) {
			branch = currentAndNew.get(0);
		} else {
			branch = currentAndNew.get(0);
			otherBranch = currentAndNew.get(1);
		}
	}

	@Option(name = "--verbose"
	private boolean verbose = false;

	@Argument(metaVar = "metaVar_name")
	private String branch;

	private final Map<String

	private RevWalk rw;

	private int maxNameLength;

	@Override
	protected void run() {
		try {
			if (delete != null || deleteForce != null) {
				if (delete != null) {
					delete(delete
				}
				if (deleteForce != null) {
					delete(deleteForce
				}
				return;
			}
			if (rename) {
				String src
				if (otherBranch == null) {
					final Ref head = db.exactRef(Constants.HEAD);
					if (head != null && head.isSymbolic()) {
						src = head.getLeaf().getName();
					} else {
						throw die(CLIText.get().cannotRenameDetachedHEAD);
					}
					dst = branch;
				} else {
					src = branch;
					final Ref old = db.findRef(src);
					if (old == null) {
						throw die(MessageFormat.format(CLIText.get().doesNotExist
					}
					if (!old.getName().startsWith(Constants.R_HEADS)) {
						throw die(MessageFormat.format(CLIText.get().notABranch
					}
					src = old.getName();
					dst = otherBranch;
				}

				if (!dst.startsWith(Constants.R_HEADS)) {
					dst = Constants.R_HEADS + dst;
				}
				if (!Repository.isValidRefName(dst)) {
					throw die(MessageFormat.format(CLIText.get().notAValidRefName
				}

				RefRename r = db.renameRef(src
				if (r.rename() != Result.RENAMED) {
					throw die(MessageFormat.format(CLIText.get().cannotBeRenamed
				}

			} else if (createForce || branch != null) {
				String newHead = branch;
				String startBranch;
				if (createForce) {
					startBranch = otherBranch;
				} else {
					startBranch = Constants.HEAD;
				}
				Ref startRef = db.findRef(startBranch);
				if (startRef != null) {
					startBranch = startRef.getName();
				} else if (startAt != null) {
					startBranch = startAt.name();
				} else {
					throw die(MessageFormat.format(
							CLIText.get().notAValidCommitName
				}
				startBranch = Repository.shortenRefName(startBranch);
				String newRefName = newHead;
				if (!newRefName.startsWith(Constants.R_HEADS)) {
					newRefName = Constants.R_HEADS + newRefName;
				}
				if (!Repository.isValidRefName(newRefName)) {
					throw die(MessageFormat.format(CLIText.get().notAValidRefName
				}
				if (!createForce && db.resolve(newRefName) != null) {
					throw die(MessageFormat.format(CLIText.get().branchAlreadyExists
				}
				RefUpdate updateRef = db.updateRef(newRefName);
				updateRef.setNewObjectId(startAt);
				updateRef.setForceUpdate(createForce);
				updateRef.setRefLogMessage(MessageFormat.format(CLIText.get().branchCreatedFrom
				Result update = updateRef.update();
				if (update == Result.REJECTED) {
					throw die(MessageFormat.format(CLIText.get().couldNotCreateBranch
				}
			} else {
				if (verbose) {
					rw = new RevWalk(db);
				}
				list();
			}
		} catch (IOException | GitAPIException e) {
			throw die(e.getMessage()
		}
	}

	private void list() throws IOException
		Ref head = db.exactRef(Constants.HEAD);
		if (head != null) {
			String current = head.getLeaf().getName();
			try (Git git = new Git(db)) {
				ListBranchCommand command = git.branchList();
				if (all)
					command.setListMode(ListMode.ALL);
				else if (remote)
					command.setListMode(ListMode.REMOTE);

				if (containsCommitish != null)
					command.setContains(containsCommitish);

				List<Ref> refs = command.call();
				for (Ref ref : refs) {
					if (ref.getName().equals(Constants.HEAD))
						addRef("(no branch)"
				}

				addRefs(refs
				addRefs(refs

				try (ObjectReader reader = db.newObjectReader()) {
					for (Entry<String
						final Ref ref = e.getValue();
						printHead(reader
								current.equals(ref.getName())
					}
				}
			}
		}
	}

	private void addRefs(Collection<Ref> refs
		for (Ref ref : RefComparator.sort(refs)) {
			final String name = ref.getName();
			if (name.startsWith(prefix))
				addRef(name.substring(name.indexOf('/'
		}
	}

	private void addRef(String name
		printRefs.put(name
		maxNameLength = Math.max(maxNameLength
	}

	private void printHead(final ObjectReader reader
			final boolean isCurrent
		outw.print(isCurrent ? '*' : ' ');
		outw.print(' ');
		outw.print(ref);
		if (verbose) {
			final int spaces = maxNameLength - ref.length() + 1;
			outw.format("%" + spaces + "s"
			final ObjectId objectId = refObj.getObjectId();
			outw.print(reader.abbreviate(objectId).name());
			outw.print(' ');
			outw.print(rw.parseCommit(objectId).getShortMessage());
		}
		outw.println();
	}

	private void delete(List<String> branches
			throws IOException {
		String current = db.getBranch();
		ObjectId head = db.resolve(Constants.HEAD);
		for (String b : branches) {
			if (b.equals(current)) {
				throw die(MessageFormat.format(CLIText.get().cannotDeleteTheBranchWhichYouAreCurrentlyOn
			}
			RefUpdate update = db.updateRef((remote ? Constants.R_REMOTES
					: Constants.R_HEADS)
					+ b);
			update.setNewObjectId(head);
			update.setForceUpdate(force || remote);
			Result result = update.delete();
			if (result == Result.REJECTED) {
				throw die(MessageFormat.format(CLIText.get().branchIsNotAnAncestorOfYourCurrentHEAD
			} else if (result == Result.NEW)
				throw die(MessageFormat.format(CLIText.get().branchNotFound
			if (remote)
				outw.println(MessageFormat.format(CLIText.get().deletedRemoteBranch
			else if (verbose)
				outw.println(MessageFormat.format(CLIText.get().deletedBranch
		}
	}
}
