
package org.eclipse.e4.ui.internal.workbench.lifecycle;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;

public enum PartLifeCycleState {
	UNINITIALIZED,
	RENDERED,
	VISIBLE,
	ACTIVATED,
	BROUGHT_TO_TOP,
	DEACTIVATED,
	HIDDEN,
	UNRENDERED;

	private static final String STATEMACHINE_KEY = PartLifeCycleState.class.getName();

	public static final StateMachine<PartLifeCycleState, MPart> getStateMachine(MPart part) {
		Object data = part.getTransientData().get(STATEMACHINE_KEY);
		if (data == null) {
			installStateMachine(part);
			data = part.getTransientData().get(STATEMACHINE_KEY);
		}
		@SuppressWarnings("unchecked")
		final StateMachine<PartLifeCycleState, MPart> stateMachine = (StateMachine<PartLifeCycleState, MPart>) data;
		return stateMachine;
	}

	public static final void transitionPartState(MPart part, PartLifeCycleState state) {
		getStateMachine(part).transitionTo(state);
	}

	public static final StateMachineDefinition<PartLifeCycleState, MPart> STATE_MACHINE_DEFINITION = StateMachineDefinition
			.<PartLifeCycleState, MPart> newBuilder()
			.startState(UNINITIALIZED).transitionsTo(RENDERED)
			.state(RENDERED).transitionsTo(VISIBLE)
			.state(VISIBLE).transitionsTo(ACTIVATED, BROUGHT_TO_TOP, HIDDEN)
			.state(ACTIVATED).transitionsTo(DEACTIVATED)
			.state(DEACTIVATED).transitionsTo(ACTIVATED, BROUGHT_TO_TOP, HIDDEN)
			.state(BROUGHT_TO_TOP).transitionsTo(ACTIVATED, HIDDEN)
			.state(HIDDEN).transitionsTo(VISIBLE, UNRENDERED)
			.state(UNRENDERED).transitionsTo(RENDERED)
			.build();

	static {
		STATE_MACHINE_DEFINITION.addListener(new DebugStateMachineListener());
		STATE_MACHINE_DEFINITION.addListener(new PartLifeCycleEventDispatcher());
	}

	private static void installStateMachine(final MPart part) {
		if (part.isToBeRendered()) {
			PartLifeCycleState.initStateMachine(part);
		} else {
			PartLifeCycleState.transitionPartState(part, PartLifeCycleState.UNRENDERED);
			part.getTransientData().put(STATEMACHINE_KEY, null);
		}
	}

	static void initStateMachine(MPart part) {
		final StateMachine<PartLifeCycleState, MPart> stateMachine = STATE_MACHINE_DEFINITION
				.newInstance(part);
		part.getTransientData().put(STATEMACHINE_KEY, stateMachine);

	}

	public static void transitionPartStateIf(PartLifeCycleState requiredCurrentState, MPart part,
			PartLifeCycleState newState) {
		final StateMachine<PartLifeCycleState, MPart> stateMachine = getStateMachine(part);
		if (stateMachine.inState(requiredCurrentState)) {
			stateMachine.transitionTo(newState);
		}
	}

}
