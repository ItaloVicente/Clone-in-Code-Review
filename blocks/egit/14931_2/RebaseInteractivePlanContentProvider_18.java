package org.eclipse.egit.ui.internal.rebase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.api.RebaseCommand.Action;
import org.eclipse.jgit.api.RebaseCommand.Step;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.util.RawParseUtils;

public class RebaseInteractivePlan {


	class PlanEntry {

		public static final String DELETE_TOKEN = "delete"; //$NON-NLS-1$

		private final Step step;

		public PlanEntry(Step step) {
			Assert.isNotNull(step);
			this.step = step;
		}

		public boolean isDelete() {
			return (getAction() == null);
		}

		public PlanEntry markDelete() {
			setAction(null);
			return this;
		}

		public Action getAction() {
			return step.getAction();
		}

		public void setAction(Action action) {
			step.setAction(action);
		}

		public AbbreviatedObjectId getCommitID() {
			return step.getCommit();
		}

		public String getShortMessage() {
			return RawParseUtils.decode(step.getShortMessage());
		}

		Step getStep() {
			return this.step;
		}
	}

	private final List<PlanEntry> todo = new LinkedList<PlanEntry>();

	private final List<Step> done = new LinkedList<Step>();

	private List<Step> initialSteps = null;

	public RebaseInteractivePlan() {
	}

	public void moveEntry(int index, int distance) {
		move(index, index + distance);
	}

	public void moveUp(final int index) {
		if (index < 0 || index >= todo.size() - 1)
			return;
		if (todo.get(index).isDelete())
			return;
		Collections.swap(todo, index, index + 1);
	}

	public void moveDown(final int index) {
		if (index <= 0 || index >= todo.size())
			return;
		if (todo.get(index).isDelete())
			return;
		Collections.swap(todo, index, index - 1);
	}

	public void moveDown(PlanEntry entry) {
		moveDown(todo.indexOf(entry));
	}

	public void moveUp(PlanEntry entry) {
		moveUp(todo.indexOf(entry));
	}

	public void move(int sourceIndex, int targetIndex) {
		if (sourceIndex < 0 || sourceIndex >= todo.size())
			throw new IndexOutOfBoundsException();
		if (targetIndex < 0 || targetIndex >= todo.size())
			throw new IndexOutOfBoundsException();
		if (sourceIndex == targetIndex)
			return;
		if (todo.get(sourceIndex).isDelete())
			return;
		if (sourceIndex < targetIndex) {
			Collections.rotate(todo.subList(sourceIndex, targetIndex + 1), -1);
		} else {
			Collections.rotate(todo.subList(targetIndex, sourceIndex + 1), 1);
		}
	}

	public void moveEntry(PlanEntry source, int targetIndex) {
		if (source == null)
			return;
		if (source.isDelete())
			return;
		int sourceIndex = this.todo.indexOf(source);
		if (sourceIndex == -1)
			throw new NoSuchElementException();
		move(sourceIndex, targetIndex);
	}

	public void moveEntry(PlanEntry source, PlanEntry target) {
		if (source == null || target == null || source == target)
			return;
		if (source.isDelete())
			return;
		moveEntry(source, this.todo.indexOf(target));
	}

	private void add(Step step) {
		if (step == null)
			return;
		PlanEntry entry = new PlanEntry(step);
		todo.add(entry);
	}

	private void addAll(Iterable<Step> steps) {
		if (steps == null)
			return;
		for (Step step : steps) {
			add(step);
		}
	}

	public void insert(PlanEntry entry, int index) {
		if (todo.contains(entry)) {
			move(todo.indexOf(entry), index);
			return;
		}
		todo.add(index, entry);
	}

	public static void markAll(List<PlanEntry> entries,
			RebaseCommand.Action action) {
		for (PlanEntry entry : entries) {
			mark(entry, action);
		}
	}

	public static void mark(PlanEntry entry, RebaseCommand.Action action) {
		entry.setAction(action);
	}

	public void clear() {
		todo.clear();
	}

	private List<Step> createStepsList() {
		List<Step> steps = new ArrayList<Step>(todo.size());
		for (PlanEntry entry : todo) {
			if (entry.isDelete())
				continue;
			steps.add(entry.getStep());
		}
		return steps;
	}

	boolean persist(Repository repo) {
		if (repo.getRepositoryState() != RepositoryState.REBASING_INTERACTIVE)
			return false;
		try {
			Git.wrap(repo).rebase().writeSteps(createStepsList());
		} catch (WrongRepositoryStateException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;

	}

	boolean parse(Repository repo) {
		todo.clear();
		done.clear();
		if (repo.getRepositoryState() != RepositoryState.REBASING_INTERACTIVE)
			return false;

		List<Step> steps = readTodo(repo);
		if (steps == null)
			return false;

		if (initialSteps == null) {
			initialSteps = new LinkedList<Step>();
			deepCopy(steps, initialSteps);
		}
		addAll(steps);

		List<Step> tmp = readDone(repo);
		if (tmp == null)
			return true;
		done.addAll(tmp);


		return true;
	}


	private static void deepCopy(List<Step> source, List<Step> dest) {
		dest.clear();
		for (Step step : source) {
			Step clone = Step.create(step.getCommit(), step.getShortMessage(),
					step.getAction());
			dest.add(clone);
		}
	}

	private static List<Step> readTodo(Repository repo) {
		try {
			return Git.wrap(repo).rebase().readSteps();
		} catch (WrongRepositoryStateException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	private List<Step> readDone(Repository repo) {
		return new LinkedList<RebaseCommand.Step>();
	}

	public List<Step> getDoneSteps() {
		return done;
	}

	public List<PlanEntry> getTodo() {
		return todo;
	}
}
