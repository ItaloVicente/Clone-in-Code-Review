
package org.eclipse.ui.tests.navigator.extension;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.ui.navigator.resources.ResourceDragAdapterAssistant;

public class TestDragAssistant extends ResourceDragAdapterAssistant {

	public static DragSourceEvent _finishedEvent;
	public static IStructuredSelection _finishedSelection;

	public static boolean _dragSetDataCalled;

	public static boolean _doit;

	public static void resetTest() {
		_doit = true;
		_dragSetDataCalled = false;
		_finishedEvent = null;
		_finishedSelection = null;
	}
	
	public void dragStart(DragSourceEvent anEvent,
			IStructuredSelection aSelection) {
		super.dragStart(anEvent, aSelection);
		anEvent.doit = _doit;
	}

	public boolean setDragData(DragSourceEvent anEvent,
			IStructuredSelection aSelection) {
		super.setDragData(anEvent, aSelection);
		_dragSetDataCalled = true;
		return true;
	}

	public void dragFinished(DragSourceEvent anEvent,
			IStructuredSelection aSelection) {

		_finishedEvent = anEvent;
		_finishedSelection = aSelection;
	}

}
