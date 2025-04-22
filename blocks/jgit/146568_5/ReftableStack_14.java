
package org.eclipse.jgit.internal.storage.reftable;

import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class ReftableReflogReader implements ReflogReader {
    Lock lock;
    Reftable reftable;
    String refname;

    ReftableReflogReader(Lock lock
        this.lock = lock;
        this.reftable = merged;
        this.refname = refname;
    }

    @Override
    public ReflogEntry getLastEntry() throws IOException {
        lock.lock();
        try {
            LogCursor cursor = reftable.seekLog(refname);
            return cursor.next() ? cursor.getReflogEntry() : null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<ReflogEntry> getReverseEntries() throws IOException {
        return getReverseEntries(Integer.MAX_VALUE);
    }

    @Override public ReflogEntry getReverseEntry(int number) throws IOException {
        List<ReflogEntry> entries = getReverseEntries(number + 1);
        if (number >= entries.size()) {
            return null;
        }
        return entries.get(entries.size() - 1);
    }

    @Override public List<ReflogEntry> getReverseEntries(int max) throws IOException {
        lock.lock();
        try {
            LogCursor cursor = reftable.seekLog(refname);

            List<ReflogEntry> result = new ArrayList<>();
            while (cursor.next() && result.size() < max) {
                result.add(cursor.getReflogEntry());
            }

            return result;
        } finally {
            lock.unlock();
        }
    }
}
