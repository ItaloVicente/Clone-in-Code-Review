package org.eclipse.egit.ui.internal.components;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.op.ListRemoteOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.dialogs.CancelableFuture;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;

public abstract class AsynchronousListOperation<T>
		extends CancelableFuture<Collection<T>> {

	private final Repository repository;

	private final String uriText;

	private final String jobTitle;

	private ListRemoteOperation listOp;

	public AsynchronousListOperation(Repository repository, String uriText,
			String jobTitle) {
		this.repository = repository;
		this.uriText = uriText;
		this.jobTitle = jobTitle;
	}

	@Override
	protected String getJobTitle() {
		return MessageFormat.format(jobTitle, uriText);
	}

	@Override
	protected void prepareRun() throws InvocationTargetException {
		try {
			listOp = new ListRemoteOperation(repository, new URIish(uriText),
					Activator.getDefault().getPreferenceStore()
							.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT));
		} catch (URISyntaxException e) {
			throw new InvocationTargetException(e);
		}
	}

	@Override
	protected void run(IProgressMonitor monitor)
			throws InterruptedException, InvocationTargetException {
		listOp.run(monitor);
		set(convert(listOp.getRemoteRefs()));
	}

	protected abstract Collection<T> convert(Collection<Ref> refs);

	@Override
	protected void done() {
		listOp = null;
	}

}
