package org.eclipse.e4.ui.internal.workbench.lifecycle;

import java.util.List;

public final class StateMachine<STATE_ENUM extends Enum<?>, MANAGED_TYPE> {

	private STATE_ENUM currentState;

	private final StateMachineDefinition<STATE_ENUM, MANAGED_TYPE> machineDefinition;

	private final MANAGED_TYPE managed;

	StateMachine(StateMachineDefinition<STATE_ENUM, MANAGED_TYPE> machineDefinition,
			MANAGED_TYPE managed) {
		this.machineDefinition = machineDefinition;
		this.managed = managed;
		this.currentState = machineDefinition.getStartState();
	}

	public void transitionTo(STATE_ENUM targetState) {
		if (targetState == currentState) {
			return;
		}
		final List<List<STATE_ENUM>> foundPaths = machineDefinition.findPaths(currentState,
				targetState);

		if (foundPaths.size() == 0) {
			throw new IllegalStateException(
					"Cannot transition from " + currentState + " to " + targetState); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (foundPaths.size() > 1) {
			throw new IllegalStateException(
					"Found " + foundPaths.size() + " shortest paths from " + currentState + " to " + targetState); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		final List<STATE_ENUM> path = foundPaths.get(0);
		for (STATE_ENUM s : path) {
			final STATE_ENUM oldState = currentState;
			currentState = s;
			machineDefinition.notifyListeners(managed, currentState, oldState);
		}
	}

	public boolean inState(STATE_ENUM testState) {
		return currentState == testState;
	}

}
