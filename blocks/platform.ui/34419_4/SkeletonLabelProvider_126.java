
package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.navigator.CommonDragAdapterAssistant;

public final class SkeletonCommonDragAssistant extends
		CommonDragAdapterAssistant {

	public static final SkeletonCommonDragAssistant INSTANCE = new SkeletonCommonDragAssistant();

	private static final Transfer[] NO_TRANSFER_TYPES = new Transfer[0];

	private SkeletonCommonDragAssistant() {
	}

	@Override
	public Transfer[] getSupportedTransferTypes() {
		return NO_TRANSFER_TYPES;
	}

	@Override
	public boolean setDragData(DragSourceEvent anEvent,
			IStructuredSelection aSelection) {
		return false;

	}

}
