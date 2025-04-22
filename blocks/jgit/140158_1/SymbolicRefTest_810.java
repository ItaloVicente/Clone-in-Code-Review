
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.SubmoduleConfig.FetchRecurseSubmodulesMode;
import org.junit.Test;

public class SubmoduleConfigTest {
	@Test
	public void fetchRecurseMatch() throws Exception {
		assertTrue(FetchRecurseSubmodulesMode.YES.matchConfigValue("yes"));
		assertTrue(FetchRecurseSubmodulesMode.YES.matchConfigValue("YES"));
		assertTrue(FetchRecurseSubmodulesMode.YES.matchConfigValue("true"));
		assertTrue(FetchRecurseSubmodulesMode.YES.matchConfigValue("TRUE"));

		assertTrue(FetchRecurseSubmodulesMode.ON_DEMAND
				.matchConfigValue("on-demand"));
		assertTrue(FetchRecurseSubmodulesMode.ON_DEMAND
				.matchConfigValue("ON-DEMAND"));
		assertTrue(FetchRecurseSubmodulesMode.ON_DEMAND
				.matchConfigValue("on_demand"));
		assertTrue(FetchRecurseSubmodulesMode.ON_DEMAND
				.matchConfigValue("ON_DEMAND"));

		assertTrue(FetchRecurseSubmodulesMode.NO.matchConfigValue("no"));
		assertTrue(FetchRecurseSubmodulesMode.NO.matchConfigValue("NO"));
		assertTrue(FetchRecurseSubmodulesMode.NO.matchConfigValue("false"));
		assertTrue(FetchRecurseSubmodulesMode.NO.matchConfigValue("FALSE"));
	}

	@Test
	public void fetchRecurseNoMatch() throws Exception {
		assertFalse(FetchRecurseSubmodulesMode.YES.matchConfigValue("Y"));
		assertFalse(FetchRecurseSubmodulesMode.NO.matchConfigValue("N"));
		assertFalse(FetchRecurseSubmodulesMode.ON_DEMAND
				.matchConfigValue("ONDEMAND"));
		assertFalse(FetchRecurseSubmodulesMode.YES.matchConfigValue(""));
		assertFalse(FetchRecurseSubmodulesMode.YES.matchConfigValue(null));
	}

	@Test
	public void fetchRecurseToConfigValue() {
		assertEquals("on-demand"
				FetchRecurseSubmodulesMode.ON_DEMAND.toConfigValue());
		assertEquals("true"
		assertEquals("false"
	}
}
