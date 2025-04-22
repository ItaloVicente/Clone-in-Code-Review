
package org.eclipse.jgit.transport;

import java.util.ArrayDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PublisherStream {
	private static class Data {
		private static final Data EMPTY = new Data(null

		private final PublisherPush push;

		private final AtomicInteger references;

		private Data(PublisherPush push
			this.push = push;
			references = new AtomicInteger(count);
		}

		private void decrement() throws PublisherException {
			if (push != null) {
				int count = references.decrementAndGet();
				if (count == 0)
					push.close();
				else if (count < 0)
					throw new IllegalStateException("too many decrements");
			}
		}

		private boolean isPushId(String pushId) {
			return push != null && push.getPushId().equals(pushId);
		}

		@Override
		public String toString() {
			return "Data[" + push + "
		}
	}

	private static abstract class Node {
		private final Data data;

		private Node(Data data) {
			if (data == null)
				throw new NullPointerException();
			this.data = data;
		}

		protected abstract Node next(long time
				throws InterruptedException;

		@Override
		public String toString() {
			return "Node[" + data + "]";
		}
	}

	private static class BlockingNode extends Node {
		private final CountDownLatch nextBarrier;

		private volatile Node next;

		private BlockingNode(Data data) {
			super(data);
			nextBarrier = new CountDownLatch(1);
		}

		@Override
		protected Node next(long time
				throws InterruptedException {
			nextBarrier.await(time
			return next;
		}

		private void setNext(Node n) {
			if (n == null)
				throw new NullPointerException();
			next = n;
			nextBarrier.countDown();
		}
	}

	private static class LinkingNode extends Node {
		private final Node next;

		private LinkingNode(Data data
			super(data);
			if (next == null)
				throw new NullPointerException();
			this.next = next;
		}

		@Override
		protected Node next(long time
			return next;
		}
	}

	private static class ForwardingNode extends Node {
		private final Node delegate;

		private ForwardingNode(Data data
			super(data);
			this.delegate = delegate;
		}

		@Override
		protected Node next(long time
				throws InterruptedException {
			return delegate.next(time
		}
	}

	public abstract static class Window {
		private Node current;

		private final int markCapacity;

		private final ArrayDeque<Data> marked;

		private Window(final Node n
			current = new ForwardingNode(Data.EMPTY
			markCapacity = capacity;
			marked = new ArrayDeque<Data>(capacity);
		}

		PublisherPush next(long time
				throws InterruptedException
			do {
				Node next = current.next(time
				if (next == null)
				current.data.decrement();
				current = next;
			} while (current.data.push == null);
			return current.data.push;
		}

		public void mark() throws PublisherException {
			if (current.data.push != null) {
				if (marked.size() >= markCapacity) {
					Data data = marked.removeLast();
					data.decrement();
				}
				marked.addFirst(current.data);
				current = new ForwardingNode(Data.EMPTY
			}
		}

		public boolean rollback(String pushId) throws PublisherException {
			if (current.data.isPushId(pushId))
				return true;

			Node last = current;
			for (Data data : marked) {
				if (data.isPushId(pushId)) {
					current = new LinkingNode(Data.EMPTY
					while (!marked.isEmpty()
							&& !marked.getFirst().isPushId(pushId))
						marked.removeFirst();
					return true;
				}
				last = new LinkingNode(data
			}
			return false;
		}

		public void prepend(PublisherPush item) throws PublisherException {
			if (item == null)
				throw new NullPointerException();
			Data data = new Data(item
			Node next = new ForwardingNode(data
			current.data.decrement();
			current = new LinkingNode(Data.EMPTY
		}

		protected void consume(Data last) throws PublisherException {
			boolean found = false;
			for (Data data : marked) {
				found |= data == last;
				data.decrement();
			}
			marked.clear();

			while (!found) {
				Node n = current;
				while (!(found = n.data == last)) {
					if (!(n instanceof ForwardingNode))
						break;
					n = ((ForwardingNode) n).delegate;
				}
				current.data.decrement();
				try {
					current = current.next(0
				} catch (InterruptedException e) {
					throw new PublisherException("unexpected interrupt"
				}
			}
			current = null;
		}

		abstract public void release() throws PublisherException;
	}

	private BlockingNode tail = new BlockingNode(Data.EMPTY);

	private int windowCount;

	public void add(PublisherPush item) throws PublisherException {
		if (item == null)
			throw new NullPointerException();
		boolean hasWindows;
		synchronized (this) {
			hasWindows = windowCount != 0;
			if (hasWindows) {
				BlockingNode old = tail;
				tail = new BlockingNode(new Data(item
				old.setNext(tail);
			}
		}
		if (!hasWindows)
			item.close();
	}

	private synchronized Node deleteWindow() {
		windowCount--;
		return tail;
	}

	public synchronized Window newWindow(int markCapacity) {
		windowCount++;
		return new Window(tail
			private boolean released;

			@Override
			public void release() throws PublisherException {
				if (released)
					throw new IllegalStateException();
				released = true;
				Node last = deleteWindow();
				consume(last.data);
			}
		};
	}
}
