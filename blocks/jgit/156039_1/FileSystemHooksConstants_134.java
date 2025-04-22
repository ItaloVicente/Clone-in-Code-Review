
package org.eclipse.jgit.niofs.internal.hook;

public enum FileSystemHooks {

    ExternalUpdate
    PostCommit
    BranchAccessCheck
    BranchAccessFilter;

    @FunctionalInterface
    public interface FileSystemHook {

        void execute(FileSystemHookExecutionContext context);
    }
}
