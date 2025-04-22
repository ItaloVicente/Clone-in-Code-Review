
package org.eclipse.jgit.niofs.internal.op.commands;

import java.io.IOException;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.revwalk.RevCommit;

public class GetLastCommit {

    private final Git git;
    private final Ref ref;

    public GetLastCommit(final Git git
                         final String branchName) {
        this(git
             git.getRef(branchName));
    }

    public GetLastCommit(final Git git
                         final Ref ref) {
        this.git = git;
        this.ref = ref;
    }

    public RevCommit execute() throws IOException {
        if (ref == null) {
            return null;
        }
        return git.resolveRevCommit(ref.getTarget().getObjectId());
    }
}
