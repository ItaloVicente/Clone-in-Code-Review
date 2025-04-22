
package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.internal.storage.dfs.DfsReader;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.internal.storage.pack.PackOutputStream;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.transport.TriggerRefreshPackListException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class LocalCachedPackTest {
    InMemoryRepository db;

    @Before
    public void setUp() {
        db = new InMemoryRepository(new DfsRepositoryDescription("test"));
    }

    @Test
    public void testTriggerRefreshPackListExceptionThrownWhenStaleFileHandle() throws Exception {
        PackFile mockedPackFile = new PackFile(new File(
                "/path/to/repo.git/objects/pack"));
        Pack mockedPack = Mockito.mock(Pack.class);
        when(mockedPack.getPackFile()).thenReturn(mockedPackFile);
        doThrow(new IOException("Stale file handle"))
            .when(mockedPack)
            .copyPackAsIs(any()

        try (DfsReader ctx = db.getObjectDatabase().newReader();
             PackWriter pw = new PackWriter(ctx);
             ByteArrayOutputStream os = new ByteArrayOutputStream();
             PackOutputStream out = new PackOutputStream(
                     NullProgressMonitor.INSTANCE

            LocalCachedPack localCachedPack =
                    new LocalCachedPack(Collections.singletonList(mockedPack));
            assertThrows(
                TriggerRefreshPackListException.class
                () -> localCachedPack.copyAsIs(out
            );
        }
    }

    @Test
    public void testIOExceptionThrownWhenAnyIOException() throws Exception {
        Pack mockedPack = Mockito.mock(Pack.class);
        doThrow(new IOException("Any IO Exception"))
            .when(mockedPack)
            .copyPackAsIs(any()

        try (DfsReader ctx = db.getObjectDatabase().newReader();
             PackWriter pw = new PackWriter(ctx);
             ByteArrayOutputStream os = new ByteArrayOutputStream();
             PackOutputStream out = new PackOutputStream(
                     NullProgressMonitor.INSTANCE

            LocalCachedPack localCachedPack =
                    new LocalCachedPack(Collections.singletonList(mockedPack));
            assertThrows(
                IOException.class
                () -> localCachedPack.copyAsIs(out
            );
        }

    }

    @Test
    public void testNoExceptionsAreThrown() throws Exception {
        Pack mockedPack = Mockito.mock(Pack.class);
        doNothing()
            .when(mockedPack)
            .copyPackAsIs(any()

        try (DfsReader ctx = db.getObjectDatabase().newReader();
             PackWriter pw = new PackWriter(ctx);
             ByteArrayOutputStream os = new ByteArrayOutputStream();
             PackOutputStream out = new PackOutputStream(
                     NullProgressMonitor.INSTANCE

            LocalCachedPack localCachedPack =
                    new LocalCachedPack(Collections.singletonList(mockedPack));
            localCachedPack.copyAsIs(out
        }
    }
}
