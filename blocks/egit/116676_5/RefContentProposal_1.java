package org.eclipse.egit.ui.internal.components;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.ui.UIUtils.ExplicitContentProposalAdapter;
import org.eclipse.egit.ui.UIUtils.IContentProposalCandidateProvider;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.dialogs.CancelableFuture;
import org.eclipse.egit.ui.internal.dialogs.NonBlockingWizardDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.progress.WorkbenchJob;

public class AsynchronousRefProposalProvider
		implements IContentProposalCandidateProvider<Ref> {

	private final IWizardContainer container;

	private final Text textField;

	private final Supplier<String> uriProvider;

	private final Function<String, CancelableFuture<Collection<Ref>>> listProvider;

	private ExplicitContentProposalAdapter contentProposer;

	public AsynchronousRefProposalProvider(
			IWizardContainer container, Text textField,
			Supplier<String> uriProvider,
			Function<String, CancelableFuture<Collection<Ref>>> listProvider) {
		this.container = container;
		this.textField = textField;
		this.uriProvider = uriProvider;
		this.listProvider = listProvider;
	}

	public void setContentProposalAdapter(
			ExplicitContentProposalAdapter adapter) {
		contentProposer = adapter;
	}

	@Override
	public Collection<? extends Ref> getCandidates() {
		String uri = uriProvider.get();
		if (uri == null) {
			return null;
		}
		CancelableFuture<Collection<Ref>> list = listProvider.apply(uri);
		try {
			if (!list.isFinished()) {
				IRunnableWithProgress operation = monitor -> {
					monitor.beginTask(MessageFormat.format(
							UIText.FetchGerritChangePage_FetchingRemoteRefsMessage,
							uri), IProgressMonitor.UNKNOWN);
					Collection<Ref> result = list.get();
					if (monitor.isCanceled()) {
						return;
					}
					if (result == null || result.isEmpty()) {
						return;
					}
					Job showProposals = new WorkbenchJob(
							UIText.FetchGerritChangePage_ShowingProposalsJobName) {

						@Override
						public boolean shouldRun() {
							return super.shouldRun() && contentProposer != null;
						}

						@Override
						public IStatus runInUIThread(
								IProgressMonitor uiMonitor) {
							try {
								if (container instanceof NonBlockingWizardDialog) {
									if (textField.isDisposed()
											|| !textField.isVisible()
											|| textField != textField
													.getDisplay()
													.getFocusControl()) {
										return Status.CANCEL_STATUS;
									}
									String uriNow = uriProvider.get();
									if (!uriNow.equals(uri)) {
										return Status.CANCEL_STATUS;
									}
								}
								contentProposer.openProposalPopup();
							} catch (SWTException e) {
								return Status.CANCEL_STATUS;
							} finally {
								uiMonitor.done();
							}
							return Status.OK_STATUS;
						}

					};
					showProposals.schedule();
				};
				if (container instanceof NonBlockingWizardDialog) {
					NonBlockingWizardDialog dialog = (NonBlockingWizardDialog) container;
					dialog.run(operation, () -> list
							.cancel(CancelableFuture.CancelMode.ABANDON));
				} else {
					container.run(true, true, operation);
				}
				return null;
			}
			return list.get();
		} catch (InterruptedException | InvocationTargetException e) {
			return null;
		}
	}

}
