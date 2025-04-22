
package org.eclipse.jgit.internal.storage.file;

import org.apache.log4j.*;
import org.eclipse.jgit.junit.MockSystemReader;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.storage.file.WindowCacheConfig;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class GcPackfileChecksumMismatchTest extends GcTestCase {

    static class ReadThread extends Thread {
        final ObjectDirectory objectsDirectory;
        final ObjectId objectId;
        private final long initialDelay;
        boolean failed = false;
        Logger log = Logger.getLogger(ReadThread.class);

        ReadThread(ObjectDirectory objectDirectory
            this.objectsDirectory = objectDirectory;
            this.objectId = objectId;
            this.initialDelay = initialDelay;
        }

        @Override
        public void run() {
            try {
                log.info("Starting thread " + currentThread().getName());
                ObjectLoader objLoader = objectsDirectory.newReader().open(objectId);
                failed = objLoader.getSize() <= 0;
                log.info("Ending thread " + currentThread().getName() + " : " + (failed ? "FAILED" : "SUCCESS"));
            } catch (Exception e) {
                log.error(currentThread().getName() + "Failed"
                failed = true;
            }
        }

        public boolean isFailed() {
            return failed;
        }
    }

    @BeforeClass
    public static void setupWindowCache() {
        WindowCacheConfig cfg = new WindowCacheConfig();
        cfg.setPackedGitLimit(4096);
        cfg.setPackedGitWindowSize(4096);
        WindowCache.reconfigure(cfg);
    }

    @Test
    public void testPack2Commits() throws Exception {
        BranchBuilder bb = tr.branch("refs/heads/master");
        List<RevCommit> commits = new ArrayList<>();

        commits.add(bb.commit().add("A"
        configureGc(gc
        gc.gc();

        for (int i = 0; i < 1024; i++) {
            commits.add(bb.commit().add("A" + i
        }

        ObjectId objId = commits.get(commits.size()-1).getId();

        configureGc(gc
        gc.gc();

        FileRepository openRepo = (FileRepository) FileRepositoryBuilder.create(repo.getDirectory());
        ObjectDirectory objectsDirectory = openRepo.getObjectDatabase();
        try (ObjectReader reader = objectsDirectory.newReader()) {
            reader.open(commits.get(0).getId());
        }

        configureGc(gc
        gc.gc();

        Thread.sleep(5000L);

        int currentReads = 3;
        List<ReadThread> readThreads = new ArrayList<>();
        for (int i = 0; i < currentReads; i++) {
            ReadThread e = new ReadThread(objectsDirectory
            readThreads.add(e);
            e.start();
        }

        for (ReadThread t : readThreads) {
            t.join();
            Assert.assertFalse(t.isFailed());
        }
    }

    private PackConfig configureGc(GC myGc
        PackConfig pconfig = new PackConfig(repo);
        if (aggressive) {
            pconfig.setDeltaSearchWindowSize(250);
            pconfig.setMaxDeltaDepth(250);
            pconfig.setReuseObjects(false);
            pconfig.setCompressionLevel(9);
        } else {
            pconfig.setMaxDeltaDepth(1);
            pconfig.setCompressionLevel(0);
        }
        myGc.setPackConfig(pconfig);
        return pconfig;
    }
}
