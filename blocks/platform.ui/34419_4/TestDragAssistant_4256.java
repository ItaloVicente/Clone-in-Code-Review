
package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.resources.ResourceDropAdapterAssistant;

public class TestCopyDropAssistant extends ResourceDropAdapterAssistant {

	@Override
	public IStatus handleDrop(CommonDropAdapter aDropAdapter,
			DropTargetEvent aDropTargetEvent, Object aTarget) {
		return super.handleDrop(aDropAdapter, aDropTargetEvent, aTarget);
	}

	@Override
	public IStatus validateDrop(Object target, int operation,
			TransferData transferType) {

		DropTargetEvent event = getCurrentEvent();
		Assert.isTrue(event != null);

		getCommonDropAdapter().overrideOperation(DND.DROP_COPY);
		return super.validateDrop(target, operation, transferType);
	}

}
