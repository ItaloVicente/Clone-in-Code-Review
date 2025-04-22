
package org.eclipse.ui.navigator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.TransferData;

public interface INavigatorDnDService {

	CommonDragAdapterAssistant[] getCommonDragAssistants();

	void bindDragAssistant(CommonDragAdapterAssistant anAssistant);

	CommonDropAdapterAssistant[] findCommonDropAdapterAssistants(
			Object aDropTarget, TransferData theTransferType);

	CommonDropAdapterAssistant[] findCommonDropAdapterAssistants(
			Object aDropTarget, IStructuredSelection theDragSelection);
}
