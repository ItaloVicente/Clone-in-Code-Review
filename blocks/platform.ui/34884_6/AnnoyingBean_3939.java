
package org.eclipse.core.tests.internal.databinding;

import org.eclipse.core.internal.databinding.observable.Queue;

import junit.framework.TestCase;

public class QueueTest extends TestCase {

	private Queue queue;

	@Override
	protected void setUp() throws Exception {
		this.queue = new Queue();
	}
	
	public void testIsEmpty() {
		assertTrue(queue.isEmpty());
		queue.enqueue("foo");
		assertFalse(queue.isEmpty());
		queue.enqueue("bar");
		assertFalse(queue.isEmpty());
		queue.dequeue();
		assertFalse(queue.isEmpty());
		queue.dequeue();
		assertTrue(queue.isEmpty());
	}
	
	public void testEnqueueAndDequeue() {
		try {
			queue.dequeue();
			fail("expected IllegalStateException");
		} catch(IllegalStateException ex) {
		}
		queue.enqueue("foo");
		assertEquals("foo", queue.dequeue());
		try {
			queue.dequeue();
			fail("expected IllegalStateException");
		} catch(IllegalStateException ex) {
		}
		queue.enqueue("foo");
		queue.enqueue("bar");
		queue.dequeue();
		queue.enqueue("bas");
		queue.enqueue("moo");
		assertEquals("bar", queue.dequeue());
		assertEquals("bas", queue.dequeue());
		assertEquals("moo", queue.dequeue());
		assertTrue(queue.isEmpty());
	}
	
}
