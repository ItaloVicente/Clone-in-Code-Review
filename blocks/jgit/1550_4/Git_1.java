package org.eclipse.jgit.api;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.Transport;

public class FetchCommand extends GitCommand<FetchResult> {

	private String remote = Constants.DEFAULT_REMOTE_NAME;

	private List<RefSpec> refSpecs;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	private boolean checkFetchedObjects;

	private boolean removeDeletedRefs;

	private int timeout;


	protected FetchCommand(Repository repo) {
		super(repo);
		refSpecs = new ArrayList<RefSpec>(3);
	}

	public FetchResult call() throws JGitInternalException
			InvalidRemoteException {
		checkCallable();

		try {
			Transport transport = Transport.open(repo
			transport.setCheckFetchedObjects(checkFetchedObjects);
			transport.setRemoveDeletedRefs(removeDeletedRefs);
			transport.setTimeout(timeout);

			try {
				FetchResult result = transport.fetch(monitor
				return result;

			} catch (TransportException e) {
				throw new JGitInternalException(
						JGitText.get().exceptionCaughtDuringExecutionOfFetchCommand
						e);
			} finally {
				transport.close();
			}
		} catch (URISyntaxException e) {
			throw new InvalidRemoteException(MessageFormat.format(
					JGitText.get().invalidRemote
		} catch (NotSupportedException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfFetchCommand
					e);
		}

	}

	public FetchCommand setRemote(String remote) {
		checkCallable();
		this.remote = remote;
		return this;
	}

	public String getRemote() {
		return remote;
	}

	public FetchCommand setTimeout(int timeout) {
		checkCallable();
		this.timeout = timeout;
		return this;
	}

	public int getTimeout() {
		return timeout;
	}

	public boolean isCheckFetchedObjects() {
		return checkFetchedObjects;
	}

	public FetchCommand setCheckFetchedObjects(boolean checkFetchedObjects) {
		checkCallable();
		this.checkFetchedObjects = checkFetchedObjects;
		return this;
	}

	public boolean isRemoveDeletedRefs() {
		return removeDeletedRefs;
	}

	public FetchCommand setRemoveDeletedRefs(boolean removeDeletedRefs) {
		checkCallable();
		this.removeDeletedRefs = removeDeletedRefs;
		return this;
	}

	public ProgressMonitor getProgressMonitor() {
		return monitor;
	}

	public FetchCommand setProgressMonitor(ProgressMonitor monitor) {
		checkCallable();
		this.monitor = monitor;
		return this;
	}

	public List<RefSpec> getRefSpecs() {
		return refSpecs;
	}

	public FetchCommand setRefSpecs(RefSpec... specs) {
		checkCallable();
		this.refSpecs.clear();
		for (RefSpec spec : specs)
			refSpecs.add(spec);
		return this;
	}

	public FetchCommand setRefSpecs(List<RefSpec> specs) {
		checkCallable();
		this.refSpecs.clear();
		this.refSpecs.addAll(specs);
		return this;
	}

}
