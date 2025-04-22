
package org.eclipse.jgit.niofs.internal.op.model;

public class RevertCommitContent implements CommitContent {

    private final String refTree;

    public RevertCommitContent(final String refTree) {
        this.refTree = refTree;
    }

    public String getRefTree() {
        return refTree;
    }
}
