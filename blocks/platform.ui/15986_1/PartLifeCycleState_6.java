
package org.eclipse.e4.ui.internal.workbench.lifecycle;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.UIEvents;

public class PartLifeCycleEventDispatcher implements
		IStateMachineListener<PartLifeCycleState, MPart> {

	public void enteringState(MPart part, PartLifeCycleState newState, PartLifeCycleState oldState) {
		switch (newState) {
		case UNINITIALIZED:
			break;
		case ACTIVATED:
			UIEvents.publishEvent(UIEvents.UILifeCycle.ACTIVATE, part);
			break;
		case BROUGHT_TO_TOP:
			UIEvents.publishEvent(UIEvents.UILifeCycle.BRINGTOTOP, part);
			break;
		case UNRENDERED:
			UIEvents.publishEvent(UIEvents.UILifeCycle.UNRENDERED, part);
			break;
		case DEACTIVATED:
			UIEvents.publishEvent(UIEvents.UILifeCycle.DEACTIVATED, part);
			break;
		case HIDDEN:
			UIEvents.publishEvent(UIEvents.UILifeCycle.HIDDEN, part);
			break;
		case RENDERED:
			UIEvents.publishEvent(UIEvents.UILifeCycle.RENDERED, part);
			break;
		case VISIBLE:
			UIEvents.publishEvent(UIEvents.UILifeCycle.VISIBLE, part);
			break;
		}

	}

}
