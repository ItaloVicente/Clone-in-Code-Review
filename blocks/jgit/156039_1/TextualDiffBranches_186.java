
package org.eclipse.jgit.niofs.internal.op.commands;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.niofs.internal.op.GitImpl;

public class SyncRemote {

    private final GitImpl git;
    private final Map.Entry<String

    public SyncRemote(final GitImpl git
                      final Map.Entry<String
        this.git = git;
        this.remote = remote;
    }

    public Optional execute() throws InvalidRemoteException {
        try {
            final List<Ref> branches = git._branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
            final Set<String> remoteBranches = new HashSet<>();
            final Set<String> localBranches = new HashSet<>();
            fillBranches(branches


            for (final String localBranch : localBranches) {
                if (localBranch.equals(Constants.HEAD)) {
                    continue;
                }
                if (remoteBranches.contains(localBranch)) {
                    try {
                        git._branchCreate()
                                .setName(localBranch)
                                .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
                                .setStartPoint(remote.getKey() + "/" + localBranch)
                                .setForce(true)
                                .call();
                    } catch (Throwable t) {
                        throw new RuntimeException("Error creating branch [" + localBranch + "].");
                    }
                }
            }

            remoteBranches.removeAll(localBranches);

            for (final String branch : remoteBranches) {
                if (branch.equals(Constants.HEAD)) {
                    continue;
                }
                try {
                    git._branchCreate()
                            .setName(branch)
                            .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
                            .setStartPoint(remote.getKey() + "/" + branch)
                            .setForce(true)
                            .call();
                } catch (Throwable t) {
                    throw new RuntimeException("Error creating branch [" + branch + "].");
                }
            }
            return null;
        } catch (final InvalidRemoteException e) {
            throw e;
        } catch (final RuntimeException re) {
            throw re;
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    void fillBranches(final List<Ref> branches
                      final Collection<String> remoteBranches
                      final Collection<String> localBranches) {
        for (final Ref branch : branches) {
            final String branchFullName = branch.getName();
            final String remotePrefix = "refs/remotes/" + remote.getKey() + "/";
            final String localPrefix = "refs/heads/";

            if (branchFullName.startsWith(remotePrefix)) {
                remoteBranches.add(branchFullName.replaceFirst(remotePrefix
            } else if (branchFullName.startsWith(localPrefix)) {
                localBranches.add(branchFullName.replaceFirst(localPrefix
            } else {
                localBranches.add(branchFullName.substring(branchFullName.lastIndexOf("/") + 1));
            }
        }
    }
}
