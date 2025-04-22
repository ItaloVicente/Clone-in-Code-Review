
package org.eclipse.jgit.util;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.After;
import org.junit.Test;

public class BucketQueueTest {
	private QueueTester t0 = new QueueTester(0);

	private QueueTester t1 = new QueueTester(1);

	private QueueTester t2 = new QueueTester(2);

	private QueueTester t3 = new QueueTester(3);

	private QueueTester t4 = new QueueTester(4);

	private QueueTester t5 = new QueueTester(5);

	private QueueTester t6 = new QueueTester(6);

	Comparator<QueueTester> comparator = new Comparator<QueueTester>() {
		public int compare(QueueTester o1
			return o1.i - o2.i;
		}
	};

	Comparator<QueueTester> reverseComparator = new Comparator<QueueTester>() {
		public int compare(QueueTester o1
			return o2.i - o1.i;
		}
	};

	BucketQueue<QueueTester> q = new BucketQueue<QueueTester>(comparator);

	private static class QueueTester extends Object {
		public int i;

		private static int KEY = 0;

		public int key = 0;

		public QueueTester(int i) {
			this.i = i;
			key = KEY++;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof QueueTester)
				return ((QueueTester) o).key == key;
			else
				return false;
		}

		@Override
		public String toString() {
			return "(" + Integer.toString(i) + "
		}
	}

	@Test
	public void testEmpty() throws Exception {
		assertNull(q.peek());
	}

	@After
	public void clearQueue() {
		q = new BucketQueue<QueueTester>(comparator);
	}

	@Test
	public void testInsertInOrder() {
		q.add(t0);
		q.add(t1);
		q.add(t2);
		q.add(t3);

		assertEquals(t0
	}

	@Test
	public void testInsertBackwards() {
		q.add(t6);
		q.add(t5);
		q.add(t4);
		q.peek();
		q.add(t3);
		q.add(t2);
		q.add(t1);
		q.peek();

		assertEquals(t1
	}

	@Test
	public void testOutOfOrder() {
		q.add(t2);
		q.add(t3);
		q.add(t0);
		q.add(t1);

		assertEquals(t0
	}

	@Test
	public void testTieInOrder() {
		QueueTester a = new QueueTester(0);
		QueueTester b = new QueueTester(0);
		QueueTester c = new QueueTester(0);

		q.add(a);
		q.add(b);
		q.add(c);

		assertEquals(a
	}

	@Test
	public void testBucketSize() throws Exception {
		final QueueTester a = new QueueTester(0);

		q.add(a);
		q.add(a);
		q.add(a);
		q.add(a);
		q.add(a);
		q.add(a);
		q.add(a);
		q.add(a);
		q.add(a);
		q.add(a);

		assertEquals(0
		assertEquals(10

		q.peek();

		assertEquals(1

		q.add(a);
		q.add(a);
		q.add(a);
		q.add(a);
		q.peek();

		assertEquals(2
		assertEquals(14

		q.add(a);
		q.peek();

		assertEquals(3
		assertEquals(15

		q.add(a);
		q.peek();

		assertEquals(1
		assertEquals(16

		q.pop();
		assertEquals(15

		q.add(a);
		q.pop();
		q.pop();
		q.pop();
		assertEquals(13
	}

	@Test
	public void testReverseComparator() {
		BucketQueue<QueueTester> qq = new BucketQueue<QueueTester>(
				reverseComparator);

		qq.add(t2);
		qq.add(t4);
		qq.add(t5);
		assertEquals(t5
		qq.add(t3);
		assertEquals(t5
	}

	@Test
	public void testDirectAdd() {
		q.add(t5);
		q.add(t6);
		q.peek();
		q.add(t3);
	}

	@Test
	public void testAlternate() {
		QueueTester a = new QueueTester(0);
		QueueTester b = new QueueTester(0);
		QueueTester c = new QueueTester(0);

		q.add(a);
		q.peek();
		q.add(b);
		q.peek();
		q.add(c);
		q.peek();

		assertEquals(a
	}

	@Test
	public void testBug() {

		BucketQueue<QueueTester> qq = new BucketQueue<QueueTester>(
				reverseComparator);

		QueueTester a100 = new QueueTester(100);
		QueueTester a98 = new QueueTester(98);
		QueueTester a99 = new QueueTester(99);
		QueueTester a89 = new QueueTester(89);
		QueueTester a97 = new QueueTester(97);
		QueueTester a96 = new QueueTester(96);
		QueueTester a91 = new QueueTester(91);
		QueueTester a95 = new QueueTester(95);
		QueueTester a94 = new QueueTester(94);
		QueueTester a92 = new QueueTester(92);
		QueueTester a93 = new QueueTester(93);
		QueueTester a90 = new QueueTester(90);
		QueueTester a77 = new QueueTester(77);
		QueueTester a79 = new QueueTester(79);
		QueueTester a88 = new QueueTester(88);
		QueueTester a84 = new QueueTester(84);
		QueueTester a87 = new QueueTester(87);
		QueueTester a86 = new QueueTester(86);
		QueueTester a85 = new QueueTester(85);
		QueueTester a83 = new QueueTester(83);
		QueueTester a80 = new QueueTester(80);
		QueueTester a78 = new QueueTester(78);
		QueueTester a82 = new QueueTester(82);
		QueueTester a81 = new QueueTester(81);
		QueueTester a76 = new QueueTester(76);
		QueueTester a73 = new QueueTester(73);
		QueueTester a74 = new QueueTester(74);
		QueueTester a75 = new QueueTester(75);

		assertEquals(a100
		qq.add(a98);
		assertEquals(a99
		qq.peek();
		assertEquals(a98
		assertEquals(a97
		assertEquals(a96
		assertEquals(a95
		assertEquals(a94
		assertEquals(a93
		qq.peek();
		assertEquals(a92
		assertEquals(a91
		assertEquals(a90
		assertEquals(a89
		assertEquals(a88
		assertEquals(a87
		assertEquals(a86
		assertEquals(a85
		assertEquals(a84
		assertEquals(a83
		assertEquals(a82
		assertEquals(a81
		assertEquals(a80
		assertEquals(a79
		assertEquals(a78
		assertEquals(a77
		assertEquals(a76
		assertEquals(a75
		assertEquals(a74
		assertEquals(a73
		assertEquals(null
	}
}
