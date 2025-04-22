
package org.eclipse.jgit.internal.storage.dfs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class DfsBlockCacheTest {

	@SuppressWarnings("boxing")
	private static void assertThatIsSameBlock(DfsBlock actual
			DfsBlock expected) {
		assertThat(actual
		assertThat(actual.pack
		assertThat(actual.start
		assertThat(actual.end
		assertThat(actual
	}

	private static DfsBlock randomBlock() {
		DfsPackKey key = new DfsPackKey();
		long pos = 50;
		byte[] buf = new byte[100];
		ThreadLocalRandom.current().nextBytes(buf);

		return new DfsBlock(key
	}

	private static DfsPackDescription randomPackDescription() {
		DfsRepositoryDescription repoDesc = new DfsRepositoryDescription(
				UUID.randomUUID().toString());
		return new DfsPackDescription(repoDesc
	}

	@SuppressWarnings("unchecked")
	private static DfsRepository randomRepository() throws Exception {
		InMemoryRepository repository = new InMemoryRepository(
				new DfsRepositoryDescription(UUID.randomUUID().toString()));
		TestRepository git = new TestRepository<>(repository);
		git.blob(UUID.randomUUID().toString());
		RevCommit commit1 = git.commit().message("0").create();
		git.blob(UUID.randomUUID().toString());
		RevCommit commit2 = git.commit().message("1").parent(commit1).create();
		git.update("master"

		return repository;
	}

	private DfsBlockCacheConfig config;

	private DfsBlockCache cut;

	@SuppressWarnings("boxing")
	@Test
	public void cachDoesNotExceedConfiguredLimit() throws Exception {
		DfsBlockCacheConfig config1 = new DfsBlockCacheConfig();
		config1.setBlockLimit(1000);
		config1.setBlockSize(256);
		DfsBlockCache.reconfigure(config1);
		DfsBlockCache cut1 = DfsBlockCache.getInstance();

		long bytesLoaded = -10;
		while (bytesLoaded < config1.getBlockLimit()) {
			DfsRepository repository = randomRepository();
			DfsPackFile[] packFiles = repository.getObjectDatabase().getPacks();
			DfsBlock block = cut1.getOrLoad(packFiles[0]
					(DfsReader) repository.newObjectReader());
			bytesLoaded += block.size();

			assertThat(cut1.getCurrentSize()
					lessThanOrEqualTo(config1.getBlockLimit()));
		}
	}

	@Test
	public void getExistingBlock() {
		DfsBlock block = randomBlock();
		cut.put(block);

		assertThatIsSameBlock(cut.get(block.pack
	}

	@Test
	public void getNotExistingPackKey() {
		DfsBlock block = randomBlock();
		cut.put(block);

		assertThat(cut.get(new DfsPackKey()
	}

	@Test
	public void getNotExistingPosition() {
		DfsBlock block = randomBlock();
		cut.put(block);

		assertThat(cut.get(block.pack
				nullValue());
		assertThat(cut.get(block.pack
				nullValue());
	}

	@Test
	public void getOrCreateExisting() {
		DfsPackDescription packDesc = randomPackDescription();
		DfsPackKey key = new DfsPackKey();
		DfsPackFile packFile1 = cut.getOrCreate(packDesc
		DfsPackFile packFile2 = cut.getOrCreate(packDesc

		assertThat(packFile2
	}

	@Test
	public void getOrCreateExistingWithoutPackKey() {
		DfsPackDescription packDesc = randomPackDescription();
		DfsPackFile packFile1 = cut.getOrCreate(packDesc
		DfsPackFile packFile2 = cut.getOrCreate(packDesc

		assertThat(packFile2
	}

	@SuppressWarnings("boxing")
	@Test
	public void getOrCreateNotExisting() {
		DfsPackDescription packDesc = randomPackDescription();
		DfsPackKey key = new DfsPackKey();
		DfsPackFile packFile = cut.getOrCreate(packDesc

		assertThat(packFile
		assertThat(packFile.getCachedSize()
		assertThat(packFile.getPackDescription()
		assertThat(packFile.length
		assertThat(packFile.key
	}

	@Test
	public void getOrCreateNotExistingPackKey() {
		DfsPackDescription packDesc = randomPackDescription();
		DfsPackFile packFile1 = cut.getOrCreate(packDesc
		DfsPackFile packFile2 = cut.getOrCreate(packDesc

		assertThat(packFile2
		assertThat(packFile2.getPackDescription()
				is(packFile1.getPackDescription()));
	}

	@Test
	public void getOrLoadDifferentPositionsExpectsSameBlock() throws Exception {
		DfsRepository repository = randomRepository();
		DfsPackFile[] packFiles = repository.getObjectDatabase().getPacks();
		DfsPackFile packFile = packFiles[0];
		DfsBlock block1 = cut.getOrLoad(packFile
				(DfsReader) repository.newObjectReader());
		DfsBlock block2 = cut.getOrLoad(packFile
				(DfsReader) repository.newObjectReader());

		assertThat(block2
	}

	@SuppressWarnings("boxing")
	@Test
	public void getOrLoadExistingPosition() throws Exception {
		DfsRepository repository = randomRepository();
		DfsPackFile[] packFiles = repository.getObjectDatabase().getPacks();
		DfsPackFile packFile = packFiles[0];
		DfsBlock block = cut.getOrLoad(packFile
				(DfsReader) repository.newObjectReader());

		assertThat(block
		assertThat(block.pack
		assertThat(block.start
		assertThat(block.end
		assertThat((long) block.size()

		byte[] copy = new byte[20];
		int length = block.copy(0
		assertThat(length
	}

	@Test
	public void getOrLoadExistingPositionTwiceExpectsSameBlock()
			throws Exception {
		DfsRepository repository = randomRepository();
		DfsPackFile[] packFiles = repository.getObjectDatabase().getPacks();
		DfsPackFile packFile = packFiles[0];
		DfsBlock block1 = cut.getOrLoad(packFile
				(DfsReader) repository.newObjectReader());
		DfsBlock block2 = cut.getOrLoad(packFile
				(DfsReader) repository.newObjectReader());

		assertThat(block2
	}

	@SuppressWarnings("boxing")
	@Test
	public void metrics() {
		DfsBlockCacheConfig config1 = new DfsBlockCacheConfig();
		config1.setBlockLimit(1000);
		config1.setBlockSize(256);
		DfsBlockCache.reconfigure(config1);
		DfsBlockCache cut1 = DfsBlockCache.getInstance();

		DfsBlock block = randomBlock();
		cut1.put(block);

		cut1.get(block.pack
		cut1.get(block.pack
		cut1.get(block.pack
		cut1.get(new DfsPackKey()
		cut1.get(new DfsPackKey()
		cut1.get(new DfsPackKey()

		assertThat(cut1.getHitCount()
		assertThat(cut1.getMissCount()
		assertThat(cut1.getHitRatio()
		assertThat(cut1.getCurrentSize()
		assertThat(cut1.getFillPercentage()
				is(cut1.getCurrentSize() * 100 / config1.getBlockLimit()));
		assertThat(cut1.getTotalRequestCount()

		long bytesLoaded = config1.getBlockLimit() * -1;
		while (bytesLoaded < cut1.getBlockSize()) {
			DfsBlock block1 = randomBlock();
			cut1.put(block1);
			bytesLoaded += block1.size();
		}

		assertThat(cut1.getEvictions()
	}

	@SuppressWarnings("boxing")
	@Test
	public void metricsWithRepository() throws Exception {
		DfsRepository repository = randomRepository();
		DfsBlockCacheConfig config1 = new DfsBlockCacheConfig();
		config1.setBlockLimit(1000);
		config1.setBlockSize(256);
		DfsBlockCache.reconfigure(config1);
		DfsBlockCache cut1 = DfsBlockCache.getInstance();
		DfsPackFile[] packFiles = repository.getObjectDatabase().getPacks();

		cut1.getOrLoad(packFiles[0]
				(DfsReader) repository.newObjectReader());
		cut1.getOrLoad(packFiles[0]
				(DfsReader) repository.newObjectReader());
		cut1.getOrLoad(packFiles[0]
				(DfsReader) repository.newObjectReader());

		assertThat(cut1.getHitCount()
		assertThat(cut1.getMissCount()
		assertThat(cut1.getHitRatio()
		assertThat(cut1.getCurrentSize()
		assertThat(cut1.getFillPercentage()
				is(cut1.getCurrentSize() * 100 / config1.getBlockLimit()));
		assertThat(cut1.getTotalRequestCount()

		long bytesLoaded = config1.getBlockLimit() * -1;
		while (bytesLoaded < cut1.getBlockSize()) {
			DfsRepository repository1 = randomRepository();
			DfsPackFile[] packFiles1 = repository.getObjectDatabase()
					.getPacks();

			cut1.getOrLoad(packFiles1[0]
					(DfsReader) repository1.newObjectReader());
			bytesLoaded += packFiles1[0].length;
		}

		assertThat(cut1.getEvictions()
	}

	@Test
	public void putBlockIsAvailable() {
		DfsBlock block = randomBlock();
		cut.put(block);

		assertThatIsSameBlock(cut.get(block.pack
	}

	@Test
	public void putByParameterIsAvailable() {
		DfsBlock block = randomBlock();
		cut.put(block.pack

		assertThatIsSameBlock(cut.get(block.pack
	}

	@SuppressWarnings("boxing")
	@Test
	public void removePacksReleasesMemory() throws Exception {
		DfsRepository repository = randomRepository();
		assertThat(cut.getCurrentSize()

		DfsPackFile[] packFiles = repository.getObjectDatabase().getPacks();
		for (DfsPackFile packFile : packFiles) {
			packFile.close();
		}

		assertThat(cut.getCurrentSize()
	}

	@Before
	public void setup() throws Exception {
		config = new DfsBlockCacheConfig();
		DfsBlockCache.reconfigure(config);
		cut = DfsBlockCache.getInstance();
	}

	@SuppressWarnings("boxing")
	@Test
	public void shouldCopyThroughCache() {
		assertThat(cut.shouldCopyThroughCache(
				(long) (config.getBlockLimit() * config.getStreamRatio() - 1))
				is(true));
		assertThat(cut.shouldCopyThroughCache(
				(long) (config.getBlockLimit() * config.getStreamRatio()))
				is(true));
		assertThat(cut.shouldCopyThroughCache(
				(long) (config.getBlockLimit() * config.getStreamRatio() + 1))
				is(false));
	}
}
