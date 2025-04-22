package org.eclipse.jgit.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.util.List;

public class PerformanceLogContextTest {

	@Test
	public void testAddEvent() {
		PerformanceLogRecord record = new PerformanceLogRecord("record"
		PerformanceLogContext.getInstance().addEvent(record);

		List<PerformanceLogRecord> eventRecords = PerformanceLogContext
				.getInstance().getEventRecords();
		assertTrue(eventRecords.contains(record));
		assertEquals(1
	}

	@Test
	public void testCleanEvents() {
		PerformanceLogRecord record1 = new PerformanceLogRecord("record1"
		PerformanceLogContext.getInstance().addEvent(record1);

		PerformanceLogRecord record2 = new PerformanceLogRecord("record2"
		PerformanceLogContext.getInstance().addEvent(record2);

		PerformanceLogContext.getInstance().cleanEvents();
		List<PerformanceLogRecord> eventRecords = PerformanceLogContext
				.getInstance().getEventRecords();
		assertEquals(0
	}

	@Test
	public void testAddEventsTwoThreads() {
		TestRunnable thread1 = new TestRunnable("record1"
		TestRunnable thread2 = new TestRunnable("record2"

		new Thread(thread1).start();
		new Thread(thread2).start();
	}

	private static class TestRunnable implements Runnable {
		private String name;

		private long duration;

		public TestRunnable(String name
			this.name = name;
			this.duration = duration;
		}

		@Override
		public void run() {
			PerformanceLogRecord record = new PerformanceLogRecord(name
					duration);
			PerformanceLogContext.getInstance().addEvent(record);
			assertEquals(1
					.getEventRecords().size());
		}
	}
}
