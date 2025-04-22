
package org.eclipse.jgit.niofs.internal.op.commands;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;

public class Fetch {

    private final GitImpl git;
    private final CredentialsProvider credentialsProvider;
    private final Map.Entry<String
    private final Collection<RefSpec> refSpecs;

    public Fetch(final GitImpl git
                 final CredentialsProvider credentialsProvider
                 final Collection<RefSpec> refSpecs) {
        this.git = git;
        this.credentialsProvider = credentialsProvider;
        this.refSpecs = refSpecs;
        this.remote = new AbstractMap.SimpleEntry<>("origin"
    }

    public Fetch(final GitImpl git
                 final CredentialsProvider credentialsProvider
                 final Map.Entry<String
                 final Collection<RefSpec> refSpecs) {
        this.git = git;
        this.credentialsProvider = credentialsProvider;
        this.remote = remote;
        this.refSpecs = refSpecs;
    }

    public void execute() throws InvalidRemoteException {
        try {
            final List<RefSpec> specs = git.updateRemoteConfig(remote
                                                               refSpecs);

            git._fetch()
                    .setCredentialsProvider(credentialsProvider)
                    .setRemote(remote.getKey())
                    .setRefSpecs(specs)
                    .call();
        } catch (final InvalidRemoteException e) {
            throw e;
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
