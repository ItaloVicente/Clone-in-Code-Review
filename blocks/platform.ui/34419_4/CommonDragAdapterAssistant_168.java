package org.eclipse.ui.navigator;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.navigator.NavigatorSafeRunnable;
import org.eclipse.ui.internal.navigator.Policy;
import org.eclipse.ui.internal.navigator.dnd.NavigatorPluginDropAction;
import org.eclipse.ui.part.PluginTransfer;

public final class CommonDragAdapter extends DragSourceAdapter {

	private final INavigatorContentService contentService;

	private final ISelectionProvider provider;

	private CommonDragAdapterAssistant setDataAssistant;
	
	private List<CommonDragAdapterAssistant> assistantsToUse;
	
	public CommonDragAdapter(INavigatorContentService aContentService,
			ISelectionProvider aProvider) {
		super();
		contentService = aContentService;
		provider = aProvider;
		assistantsToUse = new ArrayList<CommonDragAdapterAssistant>();
	}

	public Transfer[] getSupportedDragTransfers() {
		CommonDragAdapterAssistant[] assistants = contentService
				.getDnDService().getCommonDragAssistants();

		Set<Transfer> supportedTypes = new LinkedHashSet<Transfer>();
		supportedTypes.add(PluginTransfer.getInstance());
		supportedTypes.add(LocalSelectionTransfer.getTransfer());
		Transfer[] transferTypes = null;
		for (int i = 0; i < assistants.length; i++) {
			transferTypes = assistants[i].getSupportedTransferTypes();
			for (int j = 0; j < transferTypes.length; j++) {
				if (transferTypes[j] != null) {
					supportedTypes.add(transferTypes[j]);
				}
			}
		}
		
		Transfer[] transfers = supportedTypes
				.toArray(new Transfer[supportedTypes.size()]);
		return transfers;
	}

	@Override
	public void dragStart(final DragSourceEvent event) {
		if (Policy.DEBUG_DND) {
			System.out.println("CommonDragAdapter.dragStart (begin): " + event); //$NON-NLS-1$
		}
		SafeRunner.run(new NavigatorSafeRunnable() {
			@Override
			public void run() throws Exception {
				DragSource dragSource = (DragSource) event.widget;
				if (Policy.DEBUG_DND) {
					System.out.println("CommonDragAdapter.dragStart source: " + dragSource); //$NON-NLS-1$
				}
				Control control = dragSource.getControl();
				if (control == control.getDisplay().getFocusControl()) {
					ISelection selection = provider.getSelection();
					assistantsToUse.clear();

					if (!selection.isEmpty()) {
						LocalSelectionTransfer.getTransfer().setSelection(selection);

						boolean doIt = false;
						INavigatorDnDService dndService = contentService.getDnDService();
						CommonDragAdapterAssistant[] assistants = dndService
								.getCommonDragAssistants();
						if (assistants.length == 0)
							doIt = true;
						for (int i = 0; i < assistants.length; i++) {
							if (Policy.DEBUG_DND) {
								System.out
										.println("CommonDragAdapter.dragStart assistant: " + assistants[i]); //$NON-NLS-1$
							}
							event.doit = true;
							assistants[i].dragStart(event, (IStructuredSelection) selection);
							doIt |= event.doit;
							if (event.doit) {
								if (Policy.DEBUG_DND) {
									System.out
											.println("CommonDragAdapter.dragStart assistant - event.doit == true"); //$NON-NLS-1$
								}
								assistantsToUse.add(assistants[i]);
							}
						}

						event.doit = doIt;
					} else {
						event.doit = false;
					}
				} else {
					event.doit = false;
				}
			}
		});

		if (Policy.DEBUG_DND) {
			System.out.println("CommonDragAdapter.dragStart (end): doit=" + event.doit); //$NON-NLS-1$
		}
	}

	@Override
	public void dragSetData(final DragSourceEvent event) {

		final ISelection selection = LocalSelectionTransfer.getTransfer()
				.getSelection();

		if (Policy.DEBUG_DND) {
			System.out
					.println("CommonDragAdapter.dragSetData: event" + event + " selection=" + selection); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (LocalSelectionTransfer.getTransfer()
				.isSupportedType(event.dataType)) {
			event.data = selection;

			if (Policy.DEBUG_DND) {
				System.out
						.println("CommonDragAdapter.dragSetData set LocalSelectionTransfer: " + event.data); //$NON-NLS-1$
			}
		} else if (PluginTransfer.getInstance().isSupportedType(event.dataType)) {
			event.data = NavigatorPluginDropAction
					.createTransferData(contentService);
			if (Policy.DEBUG_DND) {
				System.out
						.println("CommonDragAdapter.dragSetData set PluginTransfer: " + event.data); //$NON-NLS-1$
			}
		} else if (selection instanceof IStructuredSelection) {
			if (Policy.DEBUG_DND) {
				System.out
						.println("CommonDragAdapter.dragSetData looking for assistants"); //$NON-NLS-1$
			}

			for (int i = 0, len = assistantsToUse.size(); i < len; i++) {
				final CommonDragAdapterAssistant assistant = assistantsToUse.get(i); 
				if (Policy.DEBUG_DND) {
					System.out
							.println("CommonDragAdapter.dragSetData assistant: " + assistant); //$NON-NLS-1$
				}

				Transfer[] supportedTransferTypes = assistant
						.getSupportedTransferTypes();
				final boolean[] getOut = new boolean[1];
				for (int j = 0; j < supportedTransferTypes.length; j++) {
					if (supportedTransferTypes[j].isSupportedType(event.dataType)) {
						SafeRunner.run(new NavigatorSafeRunnable() {
							@Override
							public void run() throws Exception {
								if (Policy.DEBUG_DND) {
									System.out
											.println("CommonDragAdapter.dragSetData supported xfer type"); //$NON-NLS-1$
								}
								if (assistant.setDragData(event, (IStructuredSelection) selection)) {
									if (Policy.DEBUG_DND) {
										System.out
												.println("CommonDragAdapter.dragSetData set data " + event.data); //$NON-NLS-1$
									}
									setDataAssistant = assistant;
									getOut[0] = true;
								}
							}
						});
						if (getOut[0])
							return;
					}
				}
			}

			if (Policy.DEBUG_DND) {
				System.out
						.println("CommonDragAdapter.dragSetData FAILED no assistant handled it"); //$NON-NLS-1$
			}
			event.doit = false;

		} else {
			if (Policy.DEBUG_DND) {
				System.out
						.println("CommonDragAdapter.dragSetData FAILED can't identify transfer type"); //$NON-NLS-1$
			}
			event.doit = false;
		}
	}
	 
	@Override
	public void dragFinished(DragSourceEvent event) {

		if (Policy.DEBUG_DND) {
			System.out.println("CommonDragAdapter.dragFinished(): " + event); //$NON-NLS-1$
		}

		ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();

		if (event.doit && selection instanceof IStructuredSelection && setDataAssistant != null)
			setDataAssistant.dragFinished(event, (IStructuredSelection) selection);
			
		setDataAssistant = null;

		LocalSelectionTransfer.getTransfer().setSelection(null);

	}

}
