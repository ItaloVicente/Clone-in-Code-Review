package org.eclipse.jgit.niofs.internal.op.commands;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.model.CommitContent;
import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
import org.eclipse.jgit.niofs.internal.op.model.CopyCommitContent;
import org.eclipse.jgit.niofs.internal.op.model.DefaultCommitContent;
import org.eclipse.jgit.niofs.internal.op.model.MergeCommitContent;
import org.eclipse.jgit.niofs.internal.op.model.MoveCommitContent;
import org.eclipse.jgit.niofs.internal.op.model.RevertCommitContent;
import org.eclipse.jgit.revwalk.RevCommit;

import static java.util.Collections.reverse;

public class Commit {

	private final Git git;
	private final String branchName;
	private final CommitInfo commitInfo;
	private final boolean amend;
	private final ObjectId originId;
	private final CommitContent content;

	public Commit(final Git git
			final TimeZone timeZone
		this(git
				new DefaultCommitContent(content));
	}

	public Commit(final Git git
			final ObjectId originId
		this.git = git;
		this.branchName = branchName;
		this.commitInfo = commitInfo;
		this.amend = amend;
		this.content = content;
		try {
			if (originId == null) {
				this.originId = git.getLastCommit(branchName);
			} else {
				this.originId = originId;
			}
		} catch (final Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public boolean execute() {
		boolean hadEffecitiveCommit = true;
		final PersonIdent author = buildPersonIdent(git
				commitInfo.getTimeZone()

		try (final ObjectInserter odi = git.getRepository().newObjectInserter()) {
			final ObjectId headId = git.getRepository().resolve(branchName + "^{commit}");

			final Optional<ObjectId> tree;
			if (content instanceof DefaultCommitContent) {
				tree = new CreateDefaultCommitTree(git
			} else if (content instanceof MoveCommitContent) {
				tree = new CreateMoveCommitTree(git
			} else if (content instanceof CopyCommitContent) {
				tree = new CreateCopyCommitTree(git
			} else if (content instanceof RevertCommitContent) {
				tree = new CreateRevertCommitTree(git
			} else {
				tree = Optional.empty();
			}

			if (tree.isPresent()) {
				final CommitBuilder commit = new CommitBuilder();
				commit.setAuthor(author);
				commit.setCommitter(author);
				commit.setEncoding(StandardCharsets.UTF_8);
				commit.setMessage(commitInfo.getMessage());
				if (headId != null) {
					if (content instanceof MergeCommitContent) {
						commit.setParentIds(((MergeCommitContent) content).getParents());
					} else {
						if (amend) {
							final RevCommit previousCommit = git.resolveRevCommit(headId);
							final List<RevCommit> p = Arrays.asList(previousCommit.getParents());
							reverse(p);
							commit.setParentIds(p);
						} else {
							commit.setParentId(headId);
						}
					}
				}
				commit.setTreeId(tree.get());

				final ObjectId commitId = odi.insert(commit);
				odi.flush();

				git.refUpdate(branchName
			} else {
				hadEffecitiveCommit = false;
			}
		} catch (final Throwable t) {
			t.printStackTrace();
			throw new RuntimeException(t);
		}
		return hadEffecitiveCommit;
	}

	private PersonIdent buildPersonIdent(final Git git
			final Date when) {
		final TimeZone tz = timeZone == null ? TimeZone.getDefault() : timeZone;
		final String email = _email == null ? "" : _email;

		if (name != null) {
			if (when != null) {
				return new PersonIdent(name
			} else {
				return new PersonIdent(name
			}
		}
		return new PersonIdent("system"
	}
}
