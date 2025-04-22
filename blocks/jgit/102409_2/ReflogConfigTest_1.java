
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.eclipse.jgit.lib.CoreConfig.LogAllRefUpdates;
import org.junit.Test;

public final class CoreConfigTest {
  @Test
	public void logAllRefUpdates() {
		assertFalse(LogAllRefUpdates.FALSE.shouldAutoCreateLog("ANYTHING"));
		assertFalse(LogAllRefUpdates.FALSE.shouldAutoCreateLog("HEAD"));
		assertFalse(LogAllRefUpdates.FALSE.shouldAutoCreateLog("refs/foo"));
		assertFalse(LogAllRefUpdates.FALSE.shouldAutoCreateLog("refs/heads/foo"));
		assertFalse(LogAllRefUpdates.FALSE.shouldAutoCreateLog("refs/tags/foo"));
		assertFalse(LogAllRefUpdates.FALSE.shouldAutoCreateLog("refs/remotes/foo"));
		assertFalse(LogAllRefUpdates.FALSE.shouldAutoCreateLog("refs/notes/foo"));
		assertFalse(LogAllRefUpdates.FALSE.shouldAutoCreateLog("refs/stash"));

		assertFalse(LogAllRefUpdates.TRUE.shouldAutoCreateLog("ANYTHING"));
		assertTrue(LogAllRefUpdates.TRUE.shouldAutoCreateLog("HEAD"));
		assertFalse(LogAllRefUpdates.TRUE.shouldAutoCreateLog("refs/foo"));
		assertTrue(LogAllRefUpdates.TRUE.shouldAutoCreateLog("refs/heads/foo"));
		assertFalse(LogAllRefUpdates.TRUE.shouldAutoCreateLog("refs/tags/foo"));
		assertTrue(LogAllRefUpdates.TRUE.shouldAutoCreateLog("refs/remotes/foo"));
		assertTrue(LogAllRefUpdates.TRUE.shouldAutoCreateLog("refs/notes/foo"));
		assertFalse(LogAllRefUpdates.FALSE.shouldAutoCreateLog("refs/stash"));

		assertTrue(LogAllRefUpdates.ALWAYS.shouldAutoCreateLog("ANYTHING"));
		assertTrue(LogAllRefUpdates.ALWAYS.shouldAutoCreateLog("HEAD"));
		assertTrue(LogAllRefUpdates.ALWAYS.shouldAutoCreateLog("refs/foo"));
		assertTrue(LogAllRefUpdates.ALWAYS.shouldAutoCreateLog("refs/heads/foo"));
		assertTrue(LogAllRefUpdates.ALWAYS.shouldAutoCreateLog("refs/tags/foo"));
		assertTrue(LogAllRefUpdates.ALWAYS.shouldAutoCreateLog("refs/remotes/foo"));
		assertTrue(LogAllRefUpdates.ALWAYS.shouldAutoCreateLog("refs/notes/foo"));
		assertFalse(LogAllRefUpdates.FALSE.shouldAutoCreateLog("refs/stash"));
	}
}
