
package org.eclipse.ui.internal.navigator.dnd;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;

public class SkeletonCommonDropAssistant extends CommonDropAdapterAssistant {

	public static final SkeletonCommonDropAssistant INSTANCE = new SkeletonCommonDropAssistant();

	private SkeletonCommonDropAssistant() {
	}

	@Override
	public IStatus handleDrop(CommonDropAdapter aDropAdapter,
			DropTargetEvent aDropTargetEvent, Object aTarget) {
		return Status.CANCEL_STATUS;
	}

	@Override
	public IStatus validateDrop(Object target, int operation,
			TransferData transferType) {
		return Status.CANCEL_STATUS;
	}


}
