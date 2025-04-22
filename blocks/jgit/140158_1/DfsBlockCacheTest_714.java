
package org.eclipse.jgit.internal.storage.dfs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.eclipse.jgit.internal.JGitText;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DfsBlockCacheConfigTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void blockSizeNotPowerOfTwoExpectsException() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage(is(JGitText.get().blockSizeNotPowerOf2));

		new DfsBlockCacheConfig().setBlockSize(1000);
	}

	@Test
	@SuppressWarnings("boxing")
	public void negativeBlockSizeIsConvertedToDefault() {
		DfsBlockCacheConfig config = new DfsBlockCacheConfig();
		config.setBlockSize(-1);

		assertThat(config.getBlockSize()
	}

	@Test
	@SuppressWarnings("boxing")
	public void tooSmallBlockSizeIsConvertedToDefault() {
		DfsBlockCacheConfig config = new DfsBlockCacheConfig();
		config.setBlockSize(10);

		assertThat(config.getBlockSize()
	}

	@Test
	@SuppressWarnings("boxing")
	public void validBlockSize() {
		DfsBlockCacheConfig config = new DfsBlockCacheConfig();
		config.setBlockSize(65536);

		assertThat(config.getBlockSize()
	}
}
