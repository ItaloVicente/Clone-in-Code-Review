

package org.eclipse.jgit.niofs.internal;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.Watchable;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JGitFileSystemWatchServices implements Serializable {

    private final Collection<JGitWatchService> watchServices = new CopyOnWriteArrayList<>();

    public JGitFileSystemWatchServices() {
    }

    public WatchService newWatchService(String fsName) {
        final JGitWatchService ws = new JGitWatchService(fsName
                                                         p -> watchServices.remove(p));
        watchServices.add(ws);
        return ws;
    }

    public synchronized void publishEvents(Path watchable
                                           List<WatchEvent<?>> elist) {
        if (watchServices.isEmpty()) {
            return;
        }

        for (JGitWatchService ws : watchServices) {
            ws.publish(new WatchKey() {

                @Override
                public boolean isValid() {
                    return true;
                }

                @Override
                public List<WatchEvent<?>> pollEvents() {
                    return new CopyOnWriteArrayList<>(elist);
                }

                @Override
                public boolean reset() {
                    return !watchServices.isEmpty();
                }

                @Override
                public void cancel() {
                }

                @Override
                public Watchable watchable() {
                    return watchable;
                }
            });
            synchronized (ws) {
                ws.notifyAll();
            }
        }
    }

    public void close() {
        watchServices.forEach(ws -> ws.closeWithoutNotifyParent());
        watchServices.clear();
    }
}
