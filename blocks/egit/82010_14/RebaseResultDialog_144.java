package org.eclipse.egit.ui.internal.rebase;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan;
import org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan.ElementAction;
import org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan.PlanElement;
import org.eclipse.egit.core.internal.rebase.RebaseInteractivePlan.RebaseInteractivePlanChangeListener;

public class RebasePlanIndexer {
	private RebaseInteractivePlan plan;

	private List<PlanElement> filteredPlan;

	private RebaseInteractivePlanChangeListener listener;

	private int lastFoundElementPosition;

	public RebasePlanIndexer(RebaseInteractivePlan plan) {
		this.plan = plan;
		this.filteredPlan = new ArrayList<>();

		listener = new RebasePlanChangeListener();
		plan.addRebaseInteractivePlanChangeListener(listener);

		createIndex();
	}

	private void createIndex() {
		lastFoundElementPosition = 0;

		filteredPlan.clear();
		for (PlanElement element : plan.getList()) {
			if (!element.isComment())
				filteredPlan.add(element);
		}
	}

	public int indexOf(Object element) {
		if (filteredPlan.isEmpty())
			return -1;

		if (filteredPlan.get(lastFoundElementPosition).equals(element))
			return lastFoundElementPosition;

		int upIndex = mapToCircularIndex(lastFoundElementPosition + 1);
		if (filteredPlan.get(upIndex).equals(element)) {
			lastFoundElementPosition = upIndex;
			return lastFoundElementPosition;
		}

		int downIndex = mapToCircularIndex(lastFoundElementPosition - 1);
		if (filteredPlan.get(downIndex).equals(element)) {
			lastFoundElementPosition = downIndex;
			return lastFoundElementPosition;
		}

		int index = mapToCircularIndex(upIndex + 1);
		while (index != downIndex) {
			if (filteredPlan.get(index).equals(element)) {
				lastFoundElementPosition = index;
				return lastFoundElementPosition;
			}
			index = mapToCircularIndex(index + 1);
		}

		lastFoundElementPosition = 0;
		return -1;
	}

	private int mapToCircularIndex(int index) {
		int size = filteredPlan.size();

		if (index < 0)
			return size + index;

		if (index >= size)
			return index - size;

		return index;
	}

	public void dispose() {
		plan.removeRebaseInteractivePlanChangeListener(listener);
	}

	private class RebasePlanChangeListener implements RebaseInteractivePlanChangeListener {
		@Override
		public void planElementTypeChanged(
				RebaseInteractivePlan rebaseInteractivePlan,
				PlanElement element, ElementAction oldType,
				ElementAction newType) {
		}

		@Override
		public void planElementsOrderChanged(
				RebaseInteractivePlan rebaseInteractivePlan,
				PlanElement element, int oldIndex, int newIndex) {
			createIndex();
		}

		@Override
		public void planWasUpdatedFromRepository(RebaseInteractivePlan newPlan) {
			createIndex();
		}
	}
}
