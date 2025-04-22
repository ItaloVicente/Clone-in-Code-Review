
package org.eclipse.jgit.niofs.internal.op.commands;

import org.eclipse.jgit.niofs.internal.op.GitImpl;

public class CreateBranch {

    private final GitImpl git;
    private final String source;
    private final String target;

    public CreateBranch(final GitImpl git
                        final String source
                        final String target) {
        this.git = git;
        this.source = source;
        this.target = target;
    }

    public void execute() {
        try {
            git.refUpdate(target
                          git.resolveRevCommit(git.resolveObjectIds(source).get(0)));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
