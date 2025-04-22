
package org.eclipse.jgit.transport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PublisherStream {
	private static class Node {
		private final CountDownLatch nextSet = new CountDownLatch(1);

		private final PublisherPush data;

		private volatile Node next;

		private final AtomicInteger refCount = new AtomicInteger();

		private Node(PublisherPush p
			data = p;
			refCount.set(count);
		}

		private PublisherPush get() {
			return data;
		}

		private Node next(long time
				throws InterruptedException {
			if (next != null)
				return next;
			nextSet.await(time
			return next;
		}

		private Node next() {
			return next;
		}

		private void setNext(Node n) {
			if (n == null)
				throw new NullPointerException();
			next = n;
			nextSet.countDown();
		}

		private void decrement() throws PublisherException {
			if (refCount.decrementAndGet() == 0) {
				if (data != null)
					data.close();
			}
		}

		@Override
		public String toString() {
			return "Node[" + data + "
		}
	}

	public abstract static class Window {
		private Node current;

		private Node start;

		private Node first;

		private final int markCapacity;

		private final List<Node> marked;

		private Window(Node n
			current = start = first = n;
			markCapacity = capacity;
			marked = new ArrayList<Node>(capacity + 1);
		}

		PublisherPush next(long time
				throws InterruptedException
			Node next;
			if (current == first && current != start) {
				first = null;
				return current.get();
			}
			next = current.next(time
			if (next == null)
				return null;
			if (!marked.contains(current))
				current.decrement();
			current = next;
			if (current == start) {
				next = current.next(time
				if (next != null) {
					current = next;
					start = null;
				} else
					return null;
			}
			return current.get();
		}

		public void mark() throws PublisherException {
			marked.add(current);
			if (marked.size() > markCapacity) {
				Node n = marked.remove(0);
				n.decrement();
			}
		}

		public boolean rollback(String pushId) throws PublisherException {
			boolean matched = false;
			Node n;
			for (Iterator<Node> it = marked.iterator(); it.hasNext(); ) {
				n = it.next();
				if (matched)
					it.remove();
				else if (n.get().getPushId() == pushId) {
					current = n;
					matched = true;
				}
			}
			return matched;
		}

		public void prepend(PublisherPush item) {
			Node newNode = new Node(item
			newNode.setNext(current);
			current = first = newNode;
		}

		protected void delete(Node last) throws PublisherException {
			Node prev = null;
			for (Node n : marked) {
				prev = n;
				n.decrement();
			}
			if (prev == current)
				current = current.next();

			while (current != null) {
				current.decrement();
				if (current == last)
					break;
				current = current.next();
			}
			current = null;
		}

		abstract public void release() throws PublisherException;
	}

	private Node tail = new Node(null

	private int windowCount;

	public synchronized void add(PublisherPush item) throws PublisherException {
		boolean hasWindows;
		synchronized (this) {
			hasWindows = windowCount != 0;
			if (hasWindows) {
				Node prev = tail;
				tail = new Node(item
				prev.setNext(tail);
			}
		}
		if (!hasWindows)
			item.close();
	}

	public synchronized Window newWindow(int markCapacity) {
		windowCount++;
		return new Window(tail
			@Override
			public void release() throws PublisherException {
				Node last;
				synchronized (PublisherStream.this) {
					windowCount--;
					last = tail;
				}
				delete(last);
			}
		};
	}
}
