
package org.eclipse.jgit.util;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentLinkedList<T> {
	private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

	public interface ConcurrentIterator<D> {
		D next(long time
				throws InterruptedException

		boolean hasNext();

		D peek();

		void markBefore();

		void reset();

		Iterator<D> copy();
	}

	private class ConcurrentNodeIterator implements ConcurrentIterator<T> {
		private Node current;

		private Node marked;

		private Node lastRead;

		private ConcurrentNodeIterator(Node start) {
			current = start;
		}

		public boolean hasNext() {
			Lock readLock = rwLock.readLock();
			readLock.lock();
			try {
				return current.hasNext();
			} finally {
				readLock.unlock();
			}
		}

		public T next(long time
				throws InterruptedException
			Lock readLock = rwLock.readLock();
			readLock.lock();
			try {
				Node next = current.next(time
				if (next == null)
					return null;
				lastRead = current;
				current = next;
				return current.data();
			} finally {
				readLock.unlock();
			}
		}

		public T peek() {
			Lock readLock = rwLock.readLock();
			readLock.lock();
			try {
				if (!current.hasNext())
					return null;
				Node ret = current.nextNonblocking();
				return ret.data();
			} finally {
				readLock.unlock();
			}
		}

		public void markBefore() {
			marked = lastRead;
		}

		public void reset() {
			current = marked;
		}

		public Iterator<T> copy() {
			return new NodeIterator(current
		}
	}

	private class NodeIterator implements Iterator<T> {
		Node current;

		Node prev;

		Node end;

		private NodeIterator(Node start
			current = start;
			end = stop;
		}

		public boolean hasNext() {
			Lock readLock = rwLock.readLock();
			readLock.lock();
			try {
				return current.hasNext() && current != end;
			} finally {
				readLock.unlock();
			}
		}

		public T next() {
			Lock readLock = rwLock.readLock();
			readLock.lock();
			try {
				if (!current.hasNext()) {
					return null;
				}
				prev = current;
				current = current.nextNonblocking();
				return current.data();
			} finally {
				readLock.unlock();
			}
		}

		public void remove() {
			if (prev == null || current.hasWaiters()) {
				return;
			}
			Lock writeLock = rwLock.writeLock();
			writeLock.lock();
			try {
				Node next = current.nextNonblocking();
				if (next == null) {
					tail = prev;
				}
				current = prev;
				current.setNext(next);
				prev = null;
			} finally {
				writeLock.unlock();
			}
		}
	}

	private class Node {
		private final T data;

		private volatile Node next;

		Node(T data) {
			this.data = data;
		}

		T data() {
			return data;
		}

		private void setNext(Node next) {
			this.next = next;
			if (tailCondition != null) {
				tailCondition.signalAll();
			}
		}

		Node next(long time
				throws InterruptedException
			if (next != null)
				return next;

			Lock readLock = rwLock.readLock();
			Lock writeLock = rwLock.writeLock();
			readLock.unlock();
			writeLock.lock();
			try {
				if (next != null)
					return next;
				while (next == null)
					if (!tailCondition.await(time
						throw new TimeoutException();
				return next;
			} finally {
				readLock.lock();
				writeLock.unlock();
			}
		}

		Node nextNonblocking() {
			return next;
		}

		boolean hasNext() {
			return (next != null);
		}

		boolean hasWaiters() {
			return (this == tail);
		}
	}

	private Node head;

	private Node tail;

	private final Condition tailCondition;

	public ConcurrentLinkedList() {
		head = new Node(null);
		tail = head;
		tailCondition = rwLock.writeLock().newCondition();
	}

	public void put(T item) {
		Lock writeLock = rwLock.writeLock();
		writeLock.lock();
		try {
			Node n = new Node(item);
			tail.setNext(n);
			tail = n;
		} finally {
			writeLock.unlock();
		}
	}

	public ConcurrentIterator<T> getTailIterator() {
		return createIterator(tail);
	}

	public ConcurrentIterator<T> getHeadIterator() {
		return createIterator(head);
	}

	private ConcurrentIterator<T> createIterator(final Node start) {
		Lock createLock = rwLock.readLock();
		createLock.lock();
		try {
			return new ConcurrentNodeIterator(start);
		} finally {
			createLock.unlock();
		}
	}

	public Iterator<T> getWriteIterator() {
		Lock createLock = rwLock.readLock();
		createLock.lock();
		try {
			return new NodeIterator(head
		} finally {
			createLock.unlock();
		}
	}
}
