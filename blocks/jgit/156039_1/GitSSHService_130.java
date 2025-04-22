
package org.eclipse.jgit.niofs.internal.daemon.ssh;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;

public class GitReceiveCommand extends BaseGitCommand {

    private final ReceivePackFactory<BaseGitCommand> receivePackFactory;

    public GitReceiveCommand(final String command
                             final JGitFileSystemProvider.RepositoryResolverImpl<BaseGitCommand> repositoryResolver
                             final ReceivePackFactory<BaseGitCommand> receivePackFactory
                             final ExecutorService executorService) {
        super(command
              repositoryResolver
              executorService);
        this.receivePackFactory = receivePackFactory;
    }

    @Override
    protected String getCommandName() {
        return "git-receive-pack";
    }

    @Override
    protected void execute(final Repository repository
                           final InputStream in
                           final OutputStream out
                           final OutputStream err) {
        try {
            final ReceivePack rp = receivePackFactory.create(this
            rp.receive(in
            rp.setPostReceiveHook((rp1
                new Git(repository).gc();
            });
        } catch (final Exception ignored) {
        }
    }
}
