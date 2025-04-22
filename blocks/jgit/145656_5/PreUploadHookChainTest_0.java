package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PreUploadHookChainTest {

	@Test
	public void testDefaultIfEmpty() {
		PreUploadHook[] noHooks = {};
		PreUploadHook newChain = PreUploadHookChain
				.newChain(Arrays.asList(noHooks));
		assertEquals(newChain
	}

	@Test
	public void testFlattenChainIfOnlyOne() {
		FakePreUploadHook hook1 = new FakePreUploadHook();
		PreUploadHook newChain = PreUploadHookChain
				.newChain(Arrays.asList(PreUploadHook.NULL
		assertEquals(newChain
	}

	@Test
	public void testMultipleHooks() throws ServiceMayNotContinueException {
		FakePreUploadHook hook1 = new FakePreUploadHook();
		FakePreUploadHook hook2 = new FakePreUploadHook();

		PreUploadHook chained = PreUploadHookChain
				.newChain(Arrays.asList(hook1
		chained.onBeginNegotiateRound(null

		assertTrue(hook1.wasInvoked());
		assertTrue(hook2.wasInvoked());
	}

	private static final class FakePreUploadHook implements PreUploadHook {
		boolean invoked;

		@Override
		public void onBeginNegotiateRound(UploadPack up
				Collection<? extends ObjectId> wants
				throws ServiceMayNotContinueException {
			invoked = true;
		}

		@Override
		public void onEndNegotiateRound(UploadPack up
				Collection<? extends ObjectId> wants
				int cntNotFound
				throws ServiceMayNotContinueException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void onSendPack(UploadPack up
				Collection<? extends ObjectId> wants
				Collection<? extends ObjectId> haves)
				throws ServiceMayNotContinueException {
			throw new UnsupportedOperationException();
		}

		public boolean wasInvoked() {
			return invoked;
		}
	}
}
