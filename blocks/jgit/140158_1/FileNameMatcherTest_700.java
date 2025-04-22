package org.eclipse.jgit.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.junit.Test;

public class ConfigChangeEventTest extends RepositoryTestCase {
	@Test
	public void testFileRepository_ChangeEventsOnlyOnSave() throws Exception {
		final ConfigChangedEvent[] events = new ConfigChangedEvent[1];
		db.getListenerList().addConfigChangedListener(
				new ConfigChangedListener() {
					@Override
					public void onConfigChanged(ConfigChangedEvent event) {
						events[0] = event;
					}
				});
		FileBasedConfig config = db.getConfig();
		assertNull(events[0]);

		config.setString("test"
		assertNull(events[0]);
		config.save();
		assertNotNull(events[0]);
		assertEquals(events[0].getRepository()

		events[0] = null;

		config.unset("test"
		assertNull(events[0]);
		config.save();
		assertNotNull(events[0]);
		assertEquals(events[0].getRepository()
	}
}
