

package org.eclipse.jgit.niofs.internal;

import java.io.Serializable;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.List;
import java.util.Objects;

public class WatchEventsWrapper implements Serializable {

    private final String nodeId;
    private final List<WatchEvent<?>> events;
    private final URI watchable;
    private final String fsName;

    public WatchEventsWrapper(String nodeId
                              String fsName
                              Path watchable
                              List<WatchEvent<?>> events) {

        this.nodeId = nodeId;
        this.fsName = fsName;
        this.events = events;
        this.watchable = watchable != null ? watchable.toUri() : null;
    }

    public String getFsName() {
        return fsName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public List<WatchEvent<?>> getEvents() {
        return events;
    }

    public Path getWatchable() {
        if (watchable == null) {
            return null;
        }
        try {
            return Paths.get(watchable);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WatchEventsWrapper that = (WatchEventsWrapper) o;

        if (!Objects.equals(nodeId
            return false;
        }
        if (!Objects.equals(events
            return false;
        }
        if (!Objects.equals(watchable
            return false;
        }
        return Objects.equals(fsName
    }

    @Override
    public int hashCode() {
        int result = nodeId != null ? nodeId.hashCode() : 0;
        result = 31 * result + (events != null ? events.hashCode() : 0);
        result = 31 * result + (watchable != null ? watchable.hashCode() : 0);
        result = 31 * result + (fsName != null ? fsName.hashCode() : 0);
        return result;
    }
}
