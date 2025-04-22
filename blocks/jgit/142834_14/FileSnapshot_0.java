package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.Collection;
import java.util.Random;
import java.util.zip.Deflater;

import org.eclipse.jgit.api.GarbageCollectCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.junit.Test;

public class PackFileSnapshotTest extends RepositoryTestCase {

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

	private void appendRandomLine(File f) throws IOException {
		try (Writer w = Files.newBufferedWriter(f.toPath()
				StandardOpenOption.APPEND)) {
			w.append(randomLine(20));
		}
	}

	private String randomLine(int len) {
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			int rnd = a + (int) (random.nextFloat() * (z - a + 1));
			buffer.append((char) rnd);
		}
		buffer.append('\n');
		return buffer.toString();
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
