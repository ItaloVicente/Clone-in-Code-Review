package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.zip.Deflater;

import org.eclipse.jgit.api.GarbageCollectCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.junit.Test;

public class PackFileSnapshotTest extends RepositoryTestCase {

	private static ObjectId unknownID = ObjectId
			.fromString("1234567890123456789012345678901234567890");

	@Test
	public void testSamePackDifferentCompressionDetectChecksumChanged()
			throws Exception {
		Git git = Git.wrap(db);
		File f = writeTrashFile("file"
		for (int i = 0; i < 10; i++) {
			appendRandomLine(f);
			git.add().addFilepattern("file").call();
			git.commit().setMessage("message" + i).call();
		}

		FileBasedConfig c = db.getConfig();
		c.setInt(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTOPACKLIMIT
		c.save();
		Collection<PackFile> packs = gc(Deflater.NO_COMPRESSION);
		assertEquals("expected 1 packfile after gc"
		PackFile p1 = packs.iterator().next();
		PackFileSnapshot snapshot = p1.getFileSnapshot();

		packs = gc(Deflater.BEST_COMPRESSION);
		assertEquals("expected 1 packfile after gc"
		PackFile p2 = packs.iterator().next();
		File pf = p2.getPackFile();

		assertTrue("expected snapshot to detect modified pack"
				snapshot.isModified(pf));
		assertTrue("expected checksum changed"
	}

	private void appendRandomLine(File f
			throws IOException {
		try (Writer w = Files.newBufferedWriter(f.toPath()
				StandardOpenOption.APPEND)) {
			appendRandomLine(w
		}
	}

	private void appendRandomLine(File f) throws IOException {
		appendRandomLine(f
	}

	private void appendRandomLine(Writer w
			throws IOException {
		for (int i = 0; i < len; i++) {
			w.append((char) (c1 + r.nextInt(1 + c2 - c1)));
		}
	}

	private ObjectId createTestRepo(int testDataSeed
			throws IOException
			NoHeadException
			ConcurrentRefUpdateException
			AbortedByHookException {
		Random r = new Random(testDataSeed);
		Git git = Git.wrap(db);
		File f = writeTrashFile("file"
		appendRandomLine(f
		git.add().addFilepattern("file").call();
		git.commit().setMessage("message1").call();
		appendRandomLine(f
		git.add().addFilepattern("file").call();
		return git.commit().setMessage("message2").call().getId();
	}

	@Test
	public void testDetectModificationAlthoughSameSizeAndModificationtime()
			throws Exception {
		int testDataSeed = 1;
		int testDataLength = 100;
		FileBasedConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT
		config.save();

		createTestRepo(testDataSeed

		PackFile pf = repackAndCheck(5
		Path packFilePath = pf.getPackFile().toPath();
		AnyObjectId chk1 = pf.getPackChecksum();
		String name = pf.getPackName();
		Long length = Long.valueOf(pf.getPackFile().length());
		long m1 = packFilePath.toFile().lastModified();

		fsTick(packFilePath.toFile());

		AnyObjectId chk2 = repackAndCheck(6
				.getPackChecksum();
		long m2 = packFilePath.toFile().lastModified();
		assumeFalse(m2 == m1);

		AnyObjectId chk3 = repackAndCheck(7
				.getPackChecksum();
		long m3 = packFilePath.toFile().lastModified();

		db.getObjectDatabase().has(unknownID);
		assertEquals(chk3
				.getPackChecksum());
		assumeTrue(m3 == m2);
	}

	@Test
	public void testDetectModificationAlthoughSameSizeAndModificationtimeAndFileKey()
			throws Exception {
		int testDataSeed = 1;
		int testDataLength = 100;
		FileBasedConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_TRUSTFOLDERSTAT
		config.save();

		createTestRepo(testDataSeed

		PackFile pf = repackAndCheck(5
		Path packFilePath = pf.getPackFile().toPath();
		Path packFileBasePath = packFilePath.resolveSibling(
				packFilePath.getFileName().toString().replaceAll(".pack"
		AnyObjectId chk1 = pf.getPackChecksum();
		String name = pf.getPackName();
		Long length = Long.valueOf(pf.getPackFile().length());
		copyPack(packFileBasePath

		AnyObjectId chk2 = repackAndCheck(6
				.getPackChecksum();
		copyPack(packFileBasePath

		AnyObjectId chk3 = repackAndCheck(7
				.getPackChecksum();
		long m3 = packFilePath.toFile().lastModified();
		db.getObjectDatabase().has(unknownID);
		assertEquals(chk3
				.getPackChecksum());

		fsTick(packFilePath.toFile());

		copyPack(packFileBasePath
		long m2 = packFilePath.toFile().lastModified();
		assumeFalse(m3 == m2);

		db.getObjectDatabase().has(unknownID);
		assertEquals(chk2
				.getPackChecksum());

		copyPack(packFileBasePath
		long m1 = packFilePath.toFile().lastModified();
		assumeTrue(m2 == m1);
		db.getObjectDatabase().has(unknownID);
		assertEquals(chk1
				.getPackChecksum());
	}

	private Path copyFile(Path src
		if (Files.exists(dst)) {
			dst.toFile().setWritable(true);
			try (OutputStream dstOut = Files.newOutputStream(dst)) {
				Files.copy(src
				return dst;
			}
		} else {
			return Files.copy(src
		}
	}

	private Path copyPack(Path base
			throws IOException {
		copyFile(Paths.get(base + ".idx" + srcSuffix)
				Paths.get(base + ".idx" + dstSuffix));
		copyFile(Paths.get(base + ".bitmap" + srcSuffix)
				Paths.get(base + ".bitmap" + dstSuffix));
		return copyFile(Paths.get(base + ".pack" + srcSuffix)
				Paths.get(base + ".pack" + dstSuffix));
	}

	private PackFile repackAndCheck(int compressionLevel
			Long oldLength
			throws IOException
		PackFile p = getSinglePack(gc(compressionLevel));
		File pf = p.getPackFile();
		assumeTrue(oldLength == null || pf.length() == oldLength.longValue());
		assumeTrue(oldChkSum == null || !p.getPackChecksum().equals(oldChkSum));
		assertTrue(oldName == null || p.getPackName().equals(oldName));
		return p;
	}

	private PackFile getSinglePack(Collection<PackFile> packs) {
		Iterator<PackFile> pIt = packs.iterator();
		PackFile p = pIt.next();
		assertFalse(pIt.hasNext());
		return p;
	}

	private Collection<PackFile> gc(int compressionLevel)
			throws IOException
		GC gc = new GC(db);
		PackConfig pc = new PackConfig(db.getConfig());
		pc.setCompressionLevel(compressionLevel);

		pc.setSinglePack(true);

		pc.setDeltaSearchWindowSize(
				GarbageCollectCommand.DEFAULT_GC_AGGRESSIVE_WINDOW);
		pc.setMaxDeltaDepth(GarbageCollectCommand.DEFAULT_GC_AGGRESSIVE_DEPTH);
		pc.setReuseObjects(false);

		gc.setPackConfig(pc);
		gc.setExpireAgeMillis(0);
		gc.setPackExpireAgeMillis(0);
		return gc.gc();
	}

}
