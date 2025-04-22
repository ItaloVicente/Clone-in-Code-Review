package org.eclipse.e4.ui.internal.workbench.lifecycle;

public interface IStateMachineListener<S extends Enum<?>, MANAGED_TYPE> {
	
	public void enteringState(MANAGED_TYPE obj, S newState, S oldState);

}
