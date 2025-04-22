package org.eclipse.egit.ui.internal.components;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.op.ListRemoteOperation;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.swt.widgets.Shell;

public class RefContentAssistProvider {
	private List<Ref> destinationRefs;
	private List<Ref> sourceRefs;
	private final Shell shell;
	private final Repository repo;
	private URIish uri;

	public RefContentAssistProvider(Repository repo, URIish uri, Shell shell) {
		this.repo = repo;
		this.uri = uri;
		this.shell = shell;
	}

	public List<Ref> getRefsForContentAssist(boolean source, boolean pushMode) {
		if (source) {
			if (sourceRefs != null)
				return sourceRefs;
		} else if (destinationRefs != null)
			return destinationRefs;

		List<Ref> result = new ArrayList<Ref>();
		try {
			boolean local = pushMode == source;
			if (!local) {
				final ListRemoteOperation lop = new ListRemoteOperation(repo,
						uri,
						Activator.getDefault().getPreferenceStore().getInt(
								UIPreferences.REMOTE_CONNECTION_TIMEOUT));

				new ProgressMonitorDialog(shell).run(false, true,
						new IRunnableWithProgress() {

							public void run(IProgressMonitor monitor)
									throws InvocationTargetException,
									InterruptedException {
								monitor
										.beginTask(
												UIText.RefSpecDialog_GettingRemoteRefsMonitorMessage,
												IProgressMonitor.UNKNOWN);
								lop.run(monitor);
								monitor.done();
							}
						});
				for (Ref ref : lop.getRemoteRefs())
					if (ref.getName().startsWith(Constants.R_HEADS)
							|| (!pushMode && ref.getName().startsWith(
									Constants.R_TAGS)))
						result.add(ref);

			} else if (pushMode)
				for (Ref ref : repo.getRefDatabase().getRefs(
						RefDatabase.ALL).values()) {
					if (ref.getName().startsWith(Constants.R_REMOTES))
						continue;
					result.add(ref);
				}
			else
				for (Ref ref : repo.getRefDatabase().getRefs(
						Constants.R_REMOTES).values())
					result.add(ref);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			Activator.handleError(e.getMessage(), e, true);
			return result;
		}
		if (source)
			sourceRefs = result;
		else
			destinationRefs = result;
		return result;
	}

	public Repository getRepository() {
		return repo;
	}

	public URIish getRemoteURI() {
		return uri;
	}
}
