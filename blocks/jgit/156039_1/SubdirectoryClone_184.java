
package org.eclipse.jgit.niofs.internal.op.commands;

import java.io.IOException;
import java.util.Spliterator;

import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.revwalk.RevCommit;

import static java.util.stream.StreamSupport.stream;

public class Squash {

    private final String branch;
    private final GitImpl git;
    private String squashedCommitMessage;
    private String startCommitString;

    public Squash(final GitImpl git
                  final String branch
                  final String startCommitString
                  final String squashedCommitMessage) {
        this.git = git;
        this.squashedCommitMessage = squashedCommitMessage;
        this.branch = branch;
        this.startCommitString = startCommitString;
    }

    public void execute() {
        final Repository repo = this.git.getRepository();

        final RevCommit latestCommit = git.getLastCommit(branch);
        final RevCommit startCommit = checkIfCommitIsPresentAtBranch(this.git
                                                                     this.branch
                                                                     this.startCommitString);

        RevCommit parent = startCommit;
        if (startCommit.getParentCount() > 0) {
            parent = startCommit.getParent(0);
        }

        final CommitBuilder commitBuilder = new CommitBuilder();
        commitBuilder.setParentId(parent);
        commitBuilder.setTreeId(latestCommit.getTree().getId());
        commitBuilder.setMessage(squashedCommitMessage);
        commitBuilder.setAuthor(startCommit.getAuthorIdent());
        commitBuilder.setCommitter(startCommit.getAuthorIdent());

        try (final ObjectInserter odi = repo.newObjectInserter()) {
            final RevCommit squashedCommit = git.resolveRevCommit(odi.insert(commitBuilder));
            git.refUpdate(branch
                          squashedCommit);
        } catch (ConcurrentRefUpdateException | IOException e) {
            throw new GitException("Error on executing squash."
                                   e);
        }
    }

    private RevCommit checkIfCommitIsPresentAtBranch(final GitImpl git
                                                     final String branch
                                                     final String startCommitString) {

        try {
            final ObjectId id = git.getRef(branch).getObjectId();
            final Spliterator<RevCommit> log = git._log().add(id).call().spliterator();
            return stream(log
                          false)
                    .filter((elem) -> elem.getName().equals(startCommitString))
                    .findFirst().orElseThrow(() -> new GitException("Commit is not present at branch " + branch));
        } catch (GitAPIException | MissingObjectException | IncorrectObjectTypeException e) {
            throw new GitException("A problem occurred when trying to get commit list"
                                   e);
        }
    }
}

