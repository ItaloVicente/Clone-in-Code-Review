package org.eclipse.jface.examples.databinding.contentprovider.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.set.ObservableSet;
import org.eclipse.swt.widgets.Display;

public class AsynchronousTestSet extends ObservableSet {

	private static Random randomNumberGenerator = new Random();

	private Display display;

	private boolean stale = false;

	private static final int AVERAGE_DELTA = 4;

	private static final int AVERAGE_BUSY_TIME = 1000;

	private static List allSets = new ArrayList();

	public AsynchronousTestSet() {
		super(new HashSet(), Object.class);
		display = Display.getCurrent();
		if (display == null) {
			throw new IllegalStateException(
					"This object can only be created in the UI thread"); //$NON-NLS-1$
		}
		recompute();
	}

	@Override
	protected void firstListenerAdded() {
		super.firstListenerAdded();
		allSets.add(this);
	}

	@Override
	protected void lastListenerRemoved() {
		allSets.remove(this);
		super.lastListenerRemoved();
	}

	public static void recomputeAll() {
		for (Iterator iter = allSets.iterator(); iter.hasNext();) {
			AsynchronousTestSet next = (AsynchronousTestSet) iter.next();

			next.recompute();
		}
	}

	public void remove(Collection toRemove) {
		HashSet removed = new HashSet();
		removed.addAll(toRemove);
		removed.retainAll(wrappedSet);

		wrappedSet.removeAll(removed);
		fireSetChange(Diffs.createSetDiff(Collections.EMPTY_SET, removed));
	}

	@Override
	public boolean isStale() {
		return stale;
	}

	public void recompute() {
		if (!isStale()) {
			setStale(true);
			final int sleepTime = (int) (randomNumberGenerator.nextDouble() * (AVERAGE_BUSY_TIME * 2));
			Thread newThread = new Thread(new Runnable() {
				@Override
				public void run() {

					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
					}

					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							final HashSet toAdd = new HashSet();
							final HashSet toRemove = new HashSet();

							int delta = (randomNumberGenerator
									.nextInt(AVERAGE_DELTA * 4) - AVERAGE_DELTA * 2);
							int extraAdds = randomNumberGenerator
									.nextInt(AVERAGE_DELTA);
							int addCount = delta + extraAdds;
							int removeCount = -delta + extraAdds;

							if (addCount > 0) {
								for (int i = 0; i < addCount; i++) {
									toAdd.add(new Integer(randomNumberGenerator
											.nextInt(20)));
								}
							}

							if (removeCount > 0) {
								Iterator oldElements = wrappedSet.iterator();
								for (int i = 0; i < removeCount
										&& oldElements.hasNext(); i++) {
									toRemove.add(oldElements.next());
								}
							}

							toAdd.removeAll(wrappedSet);
							wrappedSet.addAll(toAdd);
							wrappedSet.removeAll(toRemove);

							setStale(false);
							fireSetChange(Diffs.createSetDiff(toAdd, toRemove));
						}
					});
				}
			});

			newThread.start();
		}
	}
}
