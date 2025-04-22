
package org.eclipse.jgit.events;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ChangeRecorder implements WorkingTreeModifiedListener {

	private Set<String> modified = new HashSet<>();

	private Set<String> deleted = new HashSet<>();

	private int eventCount;

	@Override
	public void onWorkingTreeModified(WorkingTreeModifiedEvent event) {
		eventCount++;
		modified.removeAll(event.getDeleted());
		deleted.removeAll(event.getModified());
		modified.addAll(event.getModified());
		deleted.addAll(event.getDeleted());
	}

	private String[] getModified() {
		return modified.toArray(new String[modified.size()]);
	}

	private String[] getDeleted() {
		return deleted.toArray(new String[deleted.size()]);
	}

	private void reset() {
		eventCount = 0;
		modified.clear();
		deleted.clear();
	}

	public void assertNoEvent() {
		assertEquals("Unexpected WorkingTreeModifiedEvent "
	}

	public void assertEvent(String[] expectedModified
			String[] expectedDeleted) {
		String[] actuallyModified = getModified();
		String[] actuallyDeleted = getDeleted();
		Arrays.sort(actuallyModified);
		Arrays.sort(expectedModified);
		Arrays.sort(actuallyDeleted);
		Arrays.sort(expectedDeleted);
		assertArrayEquals("Unexpected modifications reported"
				actuallyModified);
		assertArrayEquals("Unexpected deletions reported"
				actuallyDeleted);
		reset();
	}
}
