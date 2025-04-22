
package org.eclipse.jgit.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BucketQueue<T> {
	private List<List<T>> buckets = new LinkedList<List<T>>();

	private List<T> stage = new ArrayList<T>();

	private Comparator<T> comparator;

	private MinFinder<T> minFinder;

	public BucketQueue(Comparator<T> comparator) {
		this.comparator = comparator;
		this.minFinder = new LinearMinFinder<T>(buckets
	}

	public void clear() {
		buckets.clear();
		stage.clear();
		minFinder.clear();
	}

	public void add(T c) {
		stage.add(c);
	}

	public T pop() {
		consolidate();

		return minFinder.pop();
	}

	public T peek() {
		consolidate();

		return minFinder.peek();
	}

	private void consolidate() {
		if (!stage.isEmpty()) {
			Collections.reverse(stage);
			Collections.sort(stage

			buckets.add(0
			stage = new ArrayList<T>();

			consolidateBuckets();
			minFinder.update(buckets);
		}
	}

	private void consolidateBuckets() {
		if (buckets.size() < 2)
			return;

		List<List<T>> mergeableBuckets = new LinkedList<List<T>>();

		Iterator<List<T>> it = buckets.iterator();

		int mergeSize = it.next().size();

		while (it.hasNext()) {
			List<T> bucket = it.next();
			if (mergeSize < bucket.size() / 2) {
				break;
			}

			mergeableBuckets.add(bucket);
			mergeSize += bucket.size();

			it.remove();
		}

		if (mergeableBuckets.size() > 0) {
			mergeableBuckets.add(0
			buckets.add(0
		}
	}

	private List<T> mergeBuckets(List<List<T>> buckets
		@SuppressWarnings("unchecked")
		ArrayList<T> mergedBucket = new ArrayList(
				Arrays.asList((T[]) new Object[mergeSize]));

		MinFinder<T> mergeMinFinder = new LinearMinFinder<T>(buckets
				comparator

		for (int index = mergeSize - 1; buckets.size() > 0; index--) {
			mergedBucket.set(index
		}

		return mergedBucket;
	}

	int getNumBuckets() {
		return buckets.size();
	}

	public int size() {
		int computedSize = stage.size();
		for (List<T> b : buckets) {
			computedSize += b.size();
		}

		return computedSize;
	}

	public List<Iterator<T>> getIterators() {
		List<Iterator<T>> iterators = new ArrayList<Iterator<T>>(
				buckets.size() + 1);
		iterators.add(stage.iterator());
		for (List<T> b : buckets) {
			iterators.add(b.iterator());
		}

		return iterators;
	}

	@Override
	public String toString() {
		String output = "<<<<<<<<<<\nStage: ";
		for (T rc : stage) {
			output += rc + " ";
		}
		output += "\n\n";
		for (List<T> b : buckets) {
			for (T rc : b) {
				output += rc + " ";
			}
			output += "\n";
		}
		output += ">>>>>>>>>>";

		return output;
	}
}
