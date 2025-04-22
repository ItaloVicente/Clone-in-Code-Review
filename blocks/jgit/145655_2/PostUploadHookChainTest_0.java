package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.eclipse.jgit.storage.pack.PackStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PostUploadHookChainTest {

	@Test
	public void testDefaultIfEmpty() {
		PostUploadHook[] noHooks = {};
		PostUploadHook newChain = PostUploadHookChain
				.newChain(Arrays.asList(noHooks));
		assertEquals(newChain
	}

	@Test
	public void testFlattenChainIfOnlyOne() {
		FakePostUploadHook hook1 = new FakePostUploadHook();
		PostUploadHook newChain = PostUploadHookChain
				.newChain(Arrays.asList(PostUploadHook.NULL
		assertEquals(newChain
	}

	@Test
	public void testMultipleHooks() {
		FakePostUploadHook hook1 = new FakePostUploadHook();
		FakePostUploadHook hook2 = new FakePostUploadHook();

		PostUploadHook chained = PostUploadHookChain
				.newChain(Arrays.asList(hook1
		chained.onPostUpload(null);

		assertTrue(hook1.wasInvoked());
		assertTrue(hook2.wasInvoked());
	}

	private static final class FakePostUploadHook implements PostUploadHook {
		boolean invoked;

		public boolean wasInvoked() {
			return invoked;
		}

		@Override
		public void onPostUpload(PackStatistics stats) {
			invoked = true;
		}
	}
}
