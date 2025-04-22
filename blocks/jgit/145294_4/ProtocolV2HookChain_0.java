package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ProtocolV2HookChainTest {

	@Test
	public void testDefaultIfEmpty() {
		ProtocolV2Hook[] noHooks = {};
		ProtocolV2Hook newChain = ProtocolV2HookChain
				.newChain(Arrays.asList(noHooks));
		assertEquals(newChain
	}

	@Test
	public void testFlattenChainIfOnlyOne() {
		FakeProtocolV2Hook hook1 = new FakeProtocolV2Hook();
		ProtocolV2Hook newChain = ProtocolV2HookChain
				.newChain(Arrays.asList(ProtocolV2Hook.DEFAULT
		assertEquals(newChain
	}

	@Test
	public void testMultipleHooks() throws ServiceMayNotContinueException {
		FakeProtocolV2Hook hook1 = new FakeProtocolV2Hook();
		FakeProtocolV2Hook hook2 = new FakeProtocolV2Hook();

		ProtocolV2Hook chained = ProtocolV2HookChain
				.newChain(Arrays.asList(hook1
		chained.onLsRefs(LsRefsV2Request.builder().build());

		assertTrue(hook1.wasInvoked());
		assertTrue(hook2.wasInvoked());
	}

	private static final class FakeProtocolV2Hook implements ProtocolV2Hook {
		boolean invoked;

		@Override
		public void onLsRefs(LsRefsV2Request req)
				throws ServiceMayNotContinueException {
			invoked = true;
		}

		@Override
		public void onCapabilities(CapabilitiesV2Request req)
				throws ServiceMayNotContinueException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void onFetch(FetchV2Request req)
				throws ServiceMayNotContinueException {
			throw new UnsupportedOperationException();
		}

		public boolean wasInvoked() {
			return invoked;
		}
	}
}
