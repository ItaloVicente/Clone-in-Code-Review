
package org.eclipse.jgit.transport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PublisherStream<T extends PublisherStream.RefCounted> {
	public static interface RefCounted {
		void setReferences(int number);

		void decrement();
	}

	private static class Node<D extends RefCounted> {
		private final CountDownLatch condition = new CountDownLatch(1);

		private final D data;

		private volatile Node<D> next;

		private Node(D p) {
			data = p;
		}

		private D get() {
			return data;
		}

		private Node<D> next(long time
				throws InterruptedException {
			if (next != null)
				return next;
			condition.await(time
			return next;
		}

		private Node<D> next() {
			return next;
		}

		private void setNext(Node<D> n) {
			if (n == null)
				throw new NullPointerException();
			next = n;
			condition.countDown();
		}
	}

	public abstract static class Window<D extends RefCounted> {
		private Node<D> current;

		private Node<D> first;

		private final int markCapacity;

		private final List<Node<D>> marked;

		private Window(Node<D> start
			current = first = start;
			markCapacity = capacity;
			marked = new ArrayList<Node<D>>();
		}

		D next(long time
			if (current == first) {
				D data = current.get();
				first = null;
				return data;
			}
			Node<D> next = current.next(time
			if (next == null)
				return null;
			if (!marked.contains(current)) {
				D data = current.get();
				if (data != null)
					data.decrement();
			}
			current = next;
			return current.get();
		}

		public boolean hasNext() {
			return (current.next() != null);
		}

		public D peek() {
			return (hasNext()) ? current.get() : null;
		}

		public void mark() {
			marked.add(current);
			if (marked.size() > markCapacity) {
				Node<D> n = marked.remove(0);
				RefCounted rc = n.get();
				if (rc != null)
					rc.decrement();
			}
		}

		public boolean rollback(D item) {
			boolean matched = false;
			Node<D> n;
			for (Iterator<Node<D>> it = marked.iterator(); it.hasNext(); ) {
				n = it.next();
				if (matched)
					it.remove();
				else if (n.get() == item) {
					current = n;
					matched = true;
				}
			}
			return matched;
		}

		public void prepend(D item) {
			item.setReferences(1);
			Node<D> newNode = new Node<D>(item);
			newNode.setNext(current);
			current = first = newNode;
		}

		private void delete() {
			RefCounted rc = null;
			for (Node<D> n : marked) {
				rc = n.get();
				if (rc != null)
					rc.decrement();
			}
			if (rc != current.get()) {
				rc = current.get();
				if (rc != null)
					rc.decrement();
			}
			while (hasNext()) {
				current = current.next();
				rc = current.get();
				if (rc != null)
					rc.decrement();
			}
			current = null;
		}

		abstract public void release();
	}

	private volatile Node<T> tail = new Node<T>(null);

	private volatile int windowCount;

	public synchronized void add(T item) {
		Node<T> prev = tail;
		tail = new Node<T>(item);
		prev.setNext(tail);
		item.setReferences(windowCount);
	}

	public synchronized Window<T> newIterator(int markCapacity) {
		windowCount++;
		return new Window<T>(tail
			@Override
			public void release() {
				deleteIterator(this);
			}
		};
	}

	public synchronized void deleteIterator(Window<T> window) {
		windowCount--;
		window.delete();
	}
}
