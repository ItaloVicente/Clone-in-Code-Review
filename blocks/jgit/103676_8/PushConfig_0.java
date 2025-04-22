
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.transport.PushConfig.PushRecurseSubmodulesMode;
import org.junit.Test;

public class PushConfigTest {
	@Test
	public void pushRecurseSubmoduleMatch() throws Exception {
		assertTrue(PushRecurseSubmodulesMode.CHECK.matchConfigValue("check"));
		assertTrue(PushRecurseSubmodulesMode.CHECK.matchConfigValue("CHECK"));

		assertTrue(PushRecurseSubmodulesMode.ON_DEMAND
				.matchConfigValue("on-demand"));
		assertTrue(PushRecurseSubmodulesMode.ON_DEMAND
				.matchConfigValue("ON-DEMAND"));
		assertTrue(PushRecurseSubmodulesMode.ON_DEMAND
				.matchConfigValue("on_demand"));
		assertTrue(PushRecurseSubmodulesMode.ON_DEMAND
				.matchConfigValue("ON_DEMAND"));

		assertTrue(PushRecurseSubmodulesMode.NO.matchConfigValue("no"));
		assertTrue(PushRecurseSubmodulesMode.NO.matchConfigValue("NO"));
		assertTrue(PushRecurseSubmodulesMode.NO.matchConfigValue("false"));
		assertTrue(PushRecurseSubmodulesMode.NO.matchConfigValue("FALSE"));
	}

	@Test
	public void pushRecurseSubmoduleNoMatch() throws Exception {
		assertFalse(PushRecurseSubmodulesMode.NO.matchConfigValue("N"));
		assertFalse(PushRecurseSubmodulesMode.ON_DEMAND
				.matchConfigValue("ONDEMAND"));
	}

	@Test
	public void pushRecurseSubmoduleToConfigValue() {
		assertEquals("on-demand"
				PushRecurseSubmodulesMode.ON_DEMAND.toConfigValue());
		assertEquals("check"
		assertEquals("false"
	}
}
