package org.eclipse.egit.ui.internal.synchronize.mapping;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelCache;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

public class GitChangeSetDropAdapterAssistant extends CommonDropAdapterAssistant {

	public GitChangeSetDropAdapterAssistant() {
		System.out.println("asdf"); //$NON-NLS-1$
	}

	@Override
	public IStatus validateDrop(Object target, int operation,
			TransferData transferType) {
		if (target instanceof GitModelCache)
			return Status.OK_STATUS;

		return Status.CANCEL_STATUS;
	}

	@Override
	public IStatus handleDrop(CommonDropAdapter aDropAdapter,
			DropTargetEvent aDropTargetEvent, Object aTarget) {
		return null;
	}

	@Override
	public boolean isSupportedType(TransferData aTransferType) {
		return true;
	}

}
