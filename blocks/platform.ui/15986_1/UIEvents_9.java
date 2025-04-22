package org.eclipse.e4.ui.internal.workbench.lifecycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class StateMachineDefinition<STATE_ENUM extends Enum<?>, MANAGED_TYPE> {

	private static final class EndPoints<STATE_ENUM extends Enum<?>> {
		private final STATE_ENUM start;
		private final STATE_ENUM end;

		public EndPoints(STATE_ENUM start, STATE_ENUM end) {
			this.start = start;
			this.end = end;
		}

		public boolean isEnd(STATE_ENUM s) {
			return end == s;
		}

		public STATE_ENUM getStart() {
			return start;
		}

		public STATE_ENUM getEnd() {
			return end;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((end == null) ? 0 : end.hashCode());
			result = prime * result + ((start == null) ? 0 : start.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EndPoints<?> other = (EndPoints<?>) obj;
			if (end == null) {
				if (other.end != null)
					return false;
			} else if (!end.equals(other.end))
				return false;
			if (start == null) {
				if (other.start != null)
					return false;
			} else if (!start.equals(other.start))
				return false;
			return true;
		}

	}

	public static final class Builder<STATE_ENUM extends Enum<?>> {

		protected final Map<STATE_ENUM, Set<STATE_ENUM>> transitionsMap = new HashMap<STATE_ENUM, Set<STATE_ENUM>>();
		private STATE_ENUM currentState;
		private STATE_ENUM startState = null;

		protected Builder() {
		}

		public <MANAGED_TYPE> StateMachineDefinition<STATE_ENUM, MANAGED_TYPE> build() {
			if (startState == null) {
				throw new IllegalStateException(
						"State Machine definition requires one startState(STATE_ENUM) call."); //$NON-NLS-1$
			}
			return new StateMachineDefinition<STATE_ENUM, MANAGED_TYPE>(this);
		}

		public Builder<STATE_ENUM> startState(STATE_ENUM state) {
			if (startState != null) {
				throw new IllegalStateException(
						"Start state has already been defined as: " + startState.toString()); //$NON-NLS-1$
			}
			if (state == null) {
				throw new IllegalArgumentException("Argument state cannot be null."); //$NON-NLS-1$
			}
			startState = state;
			return state(state);

		}

		public Builder<STATE_ENUM> state(STATE_ENUM state) {
			if (state == null) {
				throw new IllegalArgumentException("Argument state cannot be null."); //$NON-NLS-1$
			}
			if (!transitionsMap.containsKey(state)) {
				transitionsMap.put(state, new HashSet<STATE_ENUM>());
			}
			this.currentState = state;
			return this;
		}

		public Builder<STATE_ENUM> transitionsTo(STATE_ENUM toState, STATE_ENUM... otherToStates) {
			if (currentState == null) {
				throw new IllegalStateException(
						"Must call state(STATE_ENUM) or startState(STATE_ENUM) before calling transitionsTo()"); //$NON-NLS-1$
			}
			if (toState == null) {
				throw new IllegalArgumentException("State argument cannot be null."); //$NON-NLS-1$
			}
			for (STATE_ENUM otherState : otherToStates) {
				if (otherState == null) {
					throw new IllegalArgumentException("State argument cannot be null."); //$NON-NLS-1$
				}
			}

			if (!transitionsMap.containsKey(currentState)) {
				transitionsMap.put(currentState, new HashSet<STATE_ENUM>());
			}
			final Set<STATE_ENUM> transitions = transitionsMap.get(currentState);
			transitions.add(toState);
			for (STATE_ENUM s : otherToStates) {
				transitions.add(s);
			}

			return this;
		}

	}

	public static <STATE_ENUM extends Enum<?>, MANAGED_TYPE> Builder<STATE_ENUM> newBuilder() {
		return new Builder<STATE_ENUM>();
	}

	private final Map<STATE_ENUM, Set<STATE_ENUM>> transitionsMap;
	private final STATE_ENUM startState;

	private final ConcurrentMap<EndPoints<STATE_ENUM>, List<List<STATE_ENUM>>> calculatedPaths = new ConcurrentHashMap<EndPoints<STATE_ENUM>, List<List<STATE_ENUM>>>();
	private final Set<IStateMachineListener<STATE_ENUM, MANAGED_TYPE>> listeners = new CopyOnWriteArraySet<IStateMachineListener<STATE_ENUM, MANAGED_TYPE>>();

	protected StateMachineDefinition(Builder<STATE_ENUM> builder) {
		if (builder == null) {
			throw new NullPointerException("Builder is null."); //$NON-NLS-1$
		}
		final Map<STATE_ENUM, Set<STATE_ENUM>> safeCopy = new HashMap<STATE_ENUM, Set<STATE_ENUM>>(
				builder.transitionsMap);
		for (Map.Entry<STATE_ENUM, Set<STATE_ENUM>> entry : safeCopy.entrySet()) {
			entry.setValue(Collections.unmodifiableSet(new HashSet<STATE_ENUM>(entry.getValue())));
		}

		transitionsMap = Collections.unmodifiableMap(safeCopy);
		startState = builder.startState;
	}

	public STATE_ENUM getStartState() {
		return startState;
	}

	public StateMachine<STATE_ENUM, MANAGED_TYPE> newInstance(MANAGED_TYPE managed) {
		if (managed == null) {
			throw new NullPointerException();
		}
		return new StateMachine<STATE_ENUM, MANAGED_TYPE>(this, managed);
	}

	List<List<STATE_ENUM>> findPaths(STATE_ENUM start, STATE_ENUM target) {
		final EndPoints<STATE_ENUM> endPoints = new EndPoints<STATE_ENUM>(start, target);
		final List<List<STATE_ENUM>> paths = calculatedPaths.get(endPoints);
		if (paths != null) {
			return paths;
		}

		final List<List<STATE_ENUM>> foundPaths = new ArrayList<List<STATE_ENUM>>();
		findShortestPath(endPoints, new HashSet<STATE_ENUM>(), new ArrayList<STATE_ENUM>(),
				foundPaths);
		calculatedPaths.putIfAbsent(endPoints, foundPaths);

		return foundPaths;
	}

	private void findShortestPath(EndPoints<STATE_ENUM> endPoints, Set<STATE_ENUM> explored,
			List<STATE_ENUM> partialPath, List<List<STATE_ENUM>> foundPaths) {
		final List<STATE_ENUM> transitions = transitionsFrom(endPoints.getStart());
		transitions.removeAll(explored);
		explored.addAll(transitions);

		for (STATE_ENUM s : transitions) {
			if (endPoints.isEnd(s)) {
				final ArrayList<STATE_ENUM> foundPath = new ArrayList<STATE_ENUM>(partialPath);
				foundPath.add(s);
				foundPaths.add(foundPath);
			}
		}
		if (foundPaths.size() > 0) {
			return;
		}
		for (STATE_ENUM s : transitions) {
			if (!endPoints.isEnd(s)) {
				final ArrayList<STATE_ENUM> newPartialPath = new ArrayList<STATE_ENUM>(partialPath);
				newPartialPath.add(s);
				findShortestPath(new EndPoints<STATE_ENUM>(s, endPoints.getEnd()), explored,
						newPartialPath, foundPaths);
			}
		}
	}

	private List<STATE_ENUM> transitionsFrom(STATE_ENUM s) {
		final Set<STATE_ENUM> transitions = transitionsMap.get(s);
		return transitions != null ? new ArrayList<STATE_ENUM>(transitions) : Collections
				.<STATE_ENUM> emptyList();
	}

	public void addListener(IStateMachineListener<STATE_ENUM, MANAGED_TYPE> listener) {
		listeners.add(listener);
	}

	void notifyListeners(MANAGED_TYPE managed, STATE_ENUM newState, STATE_ENUM oldState) {
		for (IStateMachineListener<STATE_ENUM, MANAGED_TYPE> listener : this.listeners) {
			listener.enteringState(managed, newState, oldState);
		}
	}

}
