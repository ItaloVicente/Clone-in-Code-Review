package org.eclipse.egit.core.internal.rebase;

import java.io.File;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.events.RefsChangedEvent;
import org.eclipse.jgit.events.RefsChangedListener;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.RebaseTodoFile;
import org.eclipse.jgit.lib.RebaseTodoLine;
import org.eclipse.jgit.lib.RebaseTodoLine.Action;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;

public class RebaseInteractivePlan implements RefsChangedListener {

	public static interface RebaseInteractivePlanChangeListener {
		public void planElementsOrderChanged(
				RebaseInteractivePlan rebaseInteractivePlan, PlanElement element,
				int oldIndex, int newIndex);

		public void planElementTypeChanged(
				RebaseInteractivePlan rebaseInteractivePlan, PlanElement element,
				ElementAction oldType, ElementAction newType);

		public void planWasUpdatedFromRepository(RebaseInteractivePlan plan);
	}

	private ArrayList<RebaseInteractivePlanChangeListener> planChangeListeners = new ArrayList<RebaseInteractivePlanChangeListener>();

	private List<PlanElement> todoList;

	private List<PlanElement> doneList;

	private JoinedList<List<PlanElement>, PlanElement> planList;

	private ReversedList<JoinedList<List<PlanElement>, PlanElement>, PlanElement> reversedPlanList;

	private final Repository repository;

	private ListenerHandle myRefsChangedHandle;

	private static final Map<File, RebaseInteractivePlan> planRegistry = new HashMap<File, RebaseInteractivePlan>();

	private boolean reversed = false;

	private static final String REBASE_TODO = "rebase-merge/git-rebase-todo"; //$NON-NLS-1$

	private static final String REBASE_DONE = "rebase-merge/done"; //$NON-NLS-1$

	public static RebaseInteractivePlan getPlan(Repository repo) {
		RebaseInteractivePlan plan = planRegistry.get(repo.getDirectory());
		if (plan == null) {
			plan = new RebaseInteractivePlan(repo, false);
			planRegistry.put(repo.getDirectory(), plan);
		}
		return plan;
	}

	private RebaseInteractivePlan(Repository repo, boolean reversed) {
		this.repository = repo;
		setReversed(reversed);
		reparsePlan();
		setupRepositoryRefChangedListener();
	}

	private void setupRepositoryRefChangedListener() {
		myRefsChangedHandle = Repository.getGlobalListenerList()
				.addRefsChangedListener(this);
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

	public boolean isReversed() {
		return reversed;
	}

	public void dispose() {
		myRefsChangedHandle.remove();
		planRegistry.remove(this.repository.getDirectory());
		planList.clear();
		planChangeListeners.clear();
	}

	public List<PlanElement> getList() {
		if (reversed)
			return reversedPlanList;
		return planList;
	}

	public void onRefsChanged(RefsChangedEvent event) {
		Repository repo = event.getRepository();
		if (this.repository == repo)
			reparsePlan();
		return;
	}

	public Repository getRepository() {
		return repository;
	}

	public boolean addRebaseInteractivePlanChangeListener(
			RebaseInteractivePlanChangeListener listener) {
		if (planChangeListeners.contains(listener))
			return false;
		return planChangeListeners.add(listener);
	}

	public boolean removeRebaseInteractivePlanChangeListener(
			RebaseInteractivePlanChangeListener listener) {
		return planChangeListeners.remove(listener);
	}

	private void notifyPlanElementsOrderChange(PlanElement element, int oldIndex,
			int newIndex) {
		persist();
		for (RebaseInteractivePlanChangeListener listener : planChangeListeners) {
			listener.planElementsOrderChanged(this, element, oldIndex, newIndex);
		}
	}

	private void notifyPlanElementActionChange(PlanElement element,
			ElementAction oldType, ElementAction newType) {
		persist();
		for (RebaseInteractivePlanChangeListener listener : planChangeListeners) {
			listener.planElementTypeChanged(this, element, oldType, newType);
		}
	}

	private void notifyPlanWasUpdatedFromRepository() {
		for (RebaseInteractivePlanChangeListener listener : planChangeListeners) {
			listener.planWasUpdatedFromRepository(this);
		}
	}

	private void reparsePlan() {
		doneList = parseDone();
		todoList = parseTodo();
		planList = JoinedList.wrap(doneList, todoList);
		reversedPlanList = ReversedList.wrap(planList);
		notifyPlanWasUpdatedFromRepository();
	}

	private List<PlanElement> parseTodo() {
		List<RebaseTodoLine> rebaseTodoLines;
		try {
			rebaseTodoLines = repository.readRebaseTodo(REBASE_TODO, true);
		} catch (IOException e) {
			rebaseTodoLines = new LinkedList<RebaseTodoLine>();
		}
		List<PlanElement> todoElements = createElementList(rebaseTodoLines);
		return todoElements;
	}

	private List<PlanElement> parseDone() {
		List<RebaseTodoLine> rebaseDoneLines;
		try {
			rebaseDoneLines = repository.readRebaseTodo(REBASE_DONE, false);
		} catch (IOException e) {
			rebaseDoneLines = new LinkedList<RebaseTodoLine>();
		}
		List<PlanElement> doneElements = createElementList(rebaseDoneLines);
		return doneElements;
	}

	private List<PlanElement> createElementList(List<RebaseTodoLine> rebaseTodoLines) {
		List<PlanElement> planElements = new LinkedList<PlanElement>();
		for (RebaseTodoLine todoLine : rebaseTodoLines) {
			PlanElement element = createElement(todoLine);
			planElements.add(element);
		}
		return planElements;
	}

	private PlanElement createElement(RebaseTodoLine todoLine) {
		PlanElement element = new PlanElement(todoLine);
		return element;
	}

	public boolean hasRebaseBeenStartedYet() {
		return isRebasingInteractive() && doneList.size() > 0;
	}

	public boolean isRebasingInteractive() {
		return repository.getRepositoryState() == RepositoryState.REBASING_INTERACTIVE;
	}

	private static boolean moveUp(final int index, final List<?> list) {
		if (index < 0 || index >= list.size() - 1)
			return false;
		Collections.swap(list, index, index + 1);
		return true;
	}

	private static boolean moveDown(final int index, final List<?> list) {
		if (index <= 0 || index >= list.size())
			return false;
		Collections.swap(list, index, index - 1);
		return true;
	}

	public void moveTodoEntryDown(PlanElement element) {
		List<PlanElement> list = todoList;
		if (reversed)
			list = ReversedList.wrap(todoList);
		int oldIndex = list.indexOf(element);
		moveDown(oldIndex, list);
		int newIndex = list.indexOf(element);
		if (oldIndex != newIndex)
			notifyPlanElementsOrderChange(element, oldIndex, newIndex);
	}

	public void moveTodoEntryUp(PlanElement element) {
		List<PlanElement> list = todoList;
		if (reversed)
			list = ReversedList.wrap(todoList);
		int oldIndex = list.indexOf(element);
		moveUp(oldIndex, list);
		int newIndex = list.indexOf(element);
		if (oldIndex != newIndex)
			notifyPlanElementsOrderChange(element, oldIndex, newIndex);
	}

	private static void move(int sourceIndex, int targetIndex, final List<PlanElement> list) {
		if (sourceIndex == targetIndex)
			return;
		if (sourceIndex < targetIndex) {
			Collections.rotate(list.subList(sourceIndex, targetIndex + 1), -1);
		} else {
			Collections.rotate(list.subList(targetIndex, sourceIndex + 1), 1);
		}
	}

	public void moveTodoEntry(PlanElement sourceElement, PlanElement targetElement,
			boolean before) {
		if (sourceElement == targetElement)
			return;
		Assert.isNotNull(sourceElement);
		Assert.isNotNull(targetElement);
		if (ElementType.TODO != sourceElement.getElementType())
			throw new IllegalArgumentException();

		List<PlanElement> list = todoList;
		if (reversed) {
			list = ReversedList.wrap(todoList);
		}

		int initialSourceIndex = list.indexOf(sourceElement);
		int targetIndex = list.indexOf(targetElement);

		if (targetIndex == -1 || initialSourceIndex == -1)
			return;
		if (targetIndex == initialSourceIndex)
			return;

		if (targetIndex > initialSourceIndex && before)
			targetIndex--;
		if (targetIndex < initialSourceIndex && !before)
			targetIndex++;

		move(initialSourceIndex, targetIndex, list);
		int newIndex = list.indexOf(sourceElement);
		if (initialSourceIndex != newIndex)
			notifyPlanElementsOrderChange(sourceElement, initialSourceIndex,
					newIndex);
	}

	public boolean persist() {
		if (!isRebasingInteractive())
			return false;
		List<RebaseTodoLine> todoLines = new LinkedList<RebaseTodoLine>();
		for (PlanElement element : planList.getSecondList()) {
			todoLines.add(element.getRebaseTodoLine());
		}
		try {
			repository.writeRebaseTodoFile(REBASE_TODO, todoLines, false);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	public void parse() throws IOException {
		if (!isRebasingInteractive())
			return;
		reparsePlan();
	}

	public class PlanElement {
		private final RebaseTodoLine line;

		private PlanElement(RebaseTodoLine line) {
			if (line == null)
				throw new IllegalArgumentException();
			this.line = line;
		}

		public ElementType getElementType() {
			if (todoList.indexOf(this) != -1)
				return ElementType.TODO;
			int indexInDone = doneList.indexOf(this);
			if (indexInDone != -1) {
				if (indexInDone == doneList.size() - 1
						&& isRebasingInteractive())
					return ElementType.DONE_CURRENT;
				return ElementType.DONE;
			}
			throw new IllegalStateException();
		}

		private RebaseTodoLine getRebaseTodoLine() {
			return line;
		}

		public AbbreviatedObjectId getCommit() {
			return line.getCommit();
		}

		public String getShortMessage() {
			return line.getShortMessage();
		}

		public void setPlanElementAction(ElementAction planElementAction) {
			if (isComment()) {
				if (planElementAction == null)
					return;
				throw new IllegalArgumentException();
			}
			ElementAction oldType = this.getPlanElementAction();
			if (oldType == planElementAction)
				return;
			switch (planElementAction) {
			case DELETE:
				line.setAction(Action.COMMENT);
				break;
			case EDIT:
				line.setAction(Action.EDIT);
				break;
			case FIXUP:
				line.setAction(Action.FIXUP);
				break;
			case PICK:
				line.setAction(Action.PICK);
				break;
			case REWORD:
				line.setAction(Action.REWORD);
				break;
			case SQUASH:
				line.setAction(Action.SQUASH);
				break;
			default:
				throw new IllegalArgumentException();
			}
			notifyPlanElementActionChange(this, oldType, planElementAction);
		}

		public ElementAction getPlanElementAction() {
			if (isDelete())
				return ElementAction.DELETE;
			if (isComment())
				return null;
			switch (line.getAction()) {
			case EDIT:
				return ElementAction.EDIT;
			case FIXUP:
				return ElementAction.FIXUP;
			case PICK:
				return ElementAction.PICK;
			case SQUASH:
				return ElementAction.SQUASH;
			case REWORD:
				return ElementAction.REWORD;
			default:
				throw new IllegalStateException();
			}

		}

		public boolean isComment() {
			return (Action.COMMENT.equals(line.getAction()) && null == line
					.getCommit());
		}

		public boolean isDelete() {
			return (Action.COMMENT.equals(line.getAction()) && null != line
					.getCommit());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PlanElement other = (PlanElement) obj;
			if (other.line.getCommit() == null) {
				if (this.line.getCommit() == null)
					return true;
				return false;
			}
			if (!other.line.getCommit().equals(this.line.getCommit()))
				return false;
			if (!other.getPlanElementAction().equals(
					this.getPlanElementAction()))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}

	public enum ElementAction {
		DELETE,
		EDIT,
		PICK,
		SQUASH,
		FIXUP,
		REWORD;
	}

	public static enum ElementType {
		DONE,
		TODO,
		DONE_CURRENT;
	}

	public static class ReversedList<L extends List<T>, T> extends
			AbstractList<T> {
		private final L wrapped;

		private ReversedList(L wrapped) {
			super();
			this.wrapped = wrapped;
		}

		public static <L extends List<T>, T> ReversedList<L, T> wrap(L list) {
			return new ReversedList<L, T>(list);
		}

		public L getWrapped() {
			return wrapped;
		}

		private int reversedIndex(int index) {
			return size() - index;
		}

		@Override
		public T get(int index) {
			return wrapped.get(reversedIndex(index) - 1);
		}

		@Override
		public int size() {
			return wrapped.size();
		}

		@Override
		public void add(int index, T element) {
			wrapped.add(reversedIndex(index), element);
			modCount++;
		}

		@Override
		public T set(int index, T element) {
			return wrapped.set(reversedIndex(index) - 1, element);
		}

		@Override
		public T remove(int index) {
			T removed = wrapped.remove(reversedIndex(index));
			modCount++;
			return removed;
		}
	}

	public static class JoinedList<L extends List<T>, T> extends
			AbstractList<T> {

		private final L firstList, secondList;

		public L getFirstList() {
			return firstList;
		}

		public L getSecondList() {
			return secondList;
		}

		private JoinedList(L first, L second) {
			super();
			Assert.isNotNull(first);
			Assert.isNotNull(second);
			this.firstList = first;
			this.secondList = second;
		}

		public static <L extends List<T>, T> JoinedList<L, T> wrap(L first,
				L second) {
			return new JoinedList<L, T>(first, second);
		}

		private static class RelativeIndex<T> {
			private final int relativeIndex;

			private final List<T> list;

			public final int getRelativeIndex() {
				return relativeIndex;
			}

			public final List<T> getList() {
				return list;
			}

			RelativeIndex(int relativeIndex, List<T> list) {
				super();
				this.relativeIndex = relativeIndex;
				this.list = list;
			}
		}

		private RelativeIndex<T> mapAbsolutIndex(int index) {
			if (index < firstList.size())
				return new RelativeIndex<T>(index, firstList);
			return new RelativeIndex<T>(index - firstList.size(), secondList);
		}

		@Override
		public void add(int index, T element) {
			RelativeIndex<T> rel = mapAbsolutIndex(index);
			rel.getList().add(rel.getRelativeIndex(), element);
			modCount++;
		}

		public T get(int index) {
			RelativeIndex<T> rel = mapAbsolutIndex(index);
			return rel.getList().get(rel.getRelativeIndex());
		}

		public T remove(int index) {
			RelativeIndex<T> rel = mapAbsolutIndex(index);
			modCount++;
			return rel.getList().remove(rel.getRelativeIndex());
		}

		public T set(int index, T element) {
			RelativeIndex<T> rel = mapAbsolutIndex(index);
			return rel.getList().set(rel.getRelativeIndex(), element);
		}

		public int size() {
			return firstList.size() + secondList.size();
		}
	}
}
