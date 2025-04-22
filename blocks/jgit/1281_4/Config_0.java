package org.eclipse.jgit.events;

import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.storage.file.FileBasedConfig;

public class ConfigChangeEventTest extends RepositoryTestCase {
	public void testChangeEventsOnlyOnSave() throws Exception {
		final ConfigChangedEvent[] events = new ConfigChangedEvent[1];
		db.getListenerList().addConfigChangedListener(
				new ConfigChangedListener() {
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
