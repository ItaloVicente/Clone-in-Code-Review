package org.eclipse.jgit.hooks;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RemoteRefUpdate;

public class PrePushHook extends GitHook<String> {


	private String remoteName;

	private String remoteLocation;

	private String refs;

	protected PrePushHook(Repository repo
		super(repo
	}

	@Override
	protected String getStdinArgs() {
		return refs;
	}

	@Override
	public String call() throws IOException
		if (canRun()) {
			doRun();
		}
	}

	private boolean canRun() {
		return true;
	}

	@Override
	public String getHookName() {
		return NAME;
	}

	@Override
	protected String[] getParameters() {
		if (remoteName == null) {
			remoteName = remoteLocation;
		}
		return new String[] { remoteName
	}

	public void setRemoteName(String name) {
		remoteName = name;
	}

	protected String getRemoteName() {
		return remoteName;
	}

	public void setRemoteLocation(String location) {
		remoteLocation = location;
	}

	public void setRefs(Collection<RemoteRefUpdate> toRefs) {
		StringBuilder b = new StringBuilder();
		for (RemoteRefUpdate u : toRefs) {
			b.append(u.getSrcRef());
			b.append(u.getNewObjectId().getName());
			b.append(u.getRemoteName());
			ObjectId ooid = u.getExpectedOldObjectId();
			b.append((ooid == null) ? ObjectId.zeroId().getName() : ooid
					.getName());
		}
		refs = b.toString();
	}
}
