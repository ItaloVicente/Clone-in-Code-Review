
package org.eclipse.jgit.transport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PublisherStream {
	private static abstract class Node {
		protected abstract PublisherPush get();

		protected abstract Node next(long time
				throws InterruptedException;

		protected abstract Node next();

		protected abstract void decrement() throws PublisherException;
	}

	private static class DataNode extends Node {
		private final CountDownLatch nextSet = new CountDownLatch(1);

		private final PublisherPush data;

		private volatile Node next;

		private final AtomicInteger refCount = new AtomicInteger();

		private DataNode(PublisherPush p
			data = p;
			refCount.set(count);
		}

		@Override
		protected PublisherPush get() {
			return data;
		}

		@Override
		protected Node next(long time
				throws InterruptedException {
			if (next != null)
				return next;
			nextSet.await(time
			return next;
		}

		@Override
		protected Node next() {
			return next;
		}

		protected void setNext(Node n) {
			if (n == null)
				throw new NullPointerException();
			next = n;
			nextSet.countDown();
		}

		@Override
		protected void decrement() throws PublisherException {
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

	private static class ForwardingNode extends Node {
		private final Node delegate;

		private ForwardingNode(Node delegate) {
			this.delegate = delegate;
		}

		@Override
		protected PublisherPush get() {
			return null;
		}

		@Override
		protected Node next(long time
				throws InterruptedException {
			return delegate;
		}

		@Override
		protected Node next() {
			return delegate;
		}

		@Override
		protected void decrement() throws PublisherException {
		}
	}

	private static class LinkNode extends DataNode {
		private final Node dataNode;

		private Node nextNode;

		public LinkNode(Node data) {
			super(data.get()
			dataNode = data;
		}

		@Override
		protected PublisherPush get() {
			return dataNode.get();
		}

		@Override
		protected void setNext(Node n) {
			nextNode = n;
		}

		@Override
		protected Node next(long time
				throws InterruptedException {
			return nextNode;
		}

		@Override
		protected Node next() {
			return nextNode;
		}

		@Override
		protected void decrement() throws PublisherException {
			dataNode.decrement();
		}

		@Override
		public int hashCode() {
			return dataNode.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return dataNode.equals(obj);
		}
	}

	public abstract static class Window {
		private Node current;

		private final int markCapacity;

		private final List<Node> marked;

		private Window(final Node n
			current = new Node() {
				@Override
				protected PublisherPush get() {
					return null;
				}

				@Override
				protected Node next(long time
						throws InterruptedException {
					return n.next(time
				}

				@Override
				protected Node next() {
					return n.next();
				}

				@Override
				protected void decrement() throws PublisherException {
				}
			};
			markCapacity = capacity;
			marked = new ArrayList<Node>(capacity + 1);
		}

		PublisherPush next(long time
				throws InterruptedException
			Node n;
			do {
				n = current.next(time
				if (n == null)
				if (!marked.contains(current))
					current.decrement();
				current = n;
			} while (n.get() == null);
			return n.get();
		}

		public void mark() throws PublisherException {
			marked.add(current);
			if (marked.size() > markCapacity) {
				Node n = marked.remove(0);
				n.decrement();
			}
		}

		public boolean rollback(String pushId) throws PublisherException {
			if (marked.size() == 0)
				return false;

			boolean matched = false;
			LinkNode prevNode = null;
			Node lastNode = current;
			for (Iterator<Node> it = marked.iterator(); it.hasNext(); ) {
				Node dataNode = it.next();
				if (matched) {
					it.remove();
					if (dataNode != lastNode) {
						LinkNode newNode = new LinkNode(dataNode);
						prevNode.setNext(newNode);
						prevNode = newNode;
					}
				} else if (dataNode.get().getPushId() == pushId
						|| pushId == null) {
					if (dataNode == lastNode)
					prevNode = new LinkNode(dataNode);
					current = prevNode;
					matched = true;
				}
			}
			if (prevNode != null)
			return matched;
		}

		public void prepend(PublisherPush item) {
			DataNode newNode = new DataNode(item
			newNode.setNext(current);
			current = new ForwardingNode(newNode);
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

	private DataNode tail = new DataNode(null

	private int windowCount;

	public synchronized void add(PublisherPush item) throws PublisherException {
		boolean hasWindows;
		synchronized (this) {
			hasWindows = windowCount != 0;
			if (hasWindows) {
				DataNode prev = tail;
				tail = new DataNode(item
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
