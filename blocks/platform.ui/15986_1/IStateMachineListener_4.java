
package org.eclipse.e4.ui.internal.workbench.lifecycle;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;

public class DebugStateMachineListener implements IStateMachineListener<PartLifeCycleState, MPart> {

	private String lastPartLabel = null;
	private long lastTime = 0l;

	public DebugStateMachineListener() {
	}

	public void enteringState(MPart part, PartLifeCycleState newState, PartLifeCycleState oldState) {
		final boolean partLabelChanged;

		final String partLabel = part.getLabel();
		if (lastPartLabel == null) {
			partLabelChanged = true;
		} else if (System.currentTimeMillis() - lastTime > 100) {
			partLabelChanged = true;
		} else if (!lastPartLabel.equals(partLabel)) {
			partLabelChanged = true;
		} else {
			partLabelChanged = false;
		}
		lastTime = System.currentTimeMillis();
		if (partLabelChanged) {
			System.out.println("StateChange: " + partLabel); //$NON-NLS-1$
			lastPartLabel = partLabel;
		}
		System.out.println("    " + oldState + " -> " + newState); //$NON-NLS-1$ //$NON-NLS-2$

	}

}
