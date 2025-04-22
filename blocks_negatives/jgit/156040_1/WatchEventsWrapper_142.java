        if (!Objects.equals(nodeId, that.nodeId)) {
            return false;
        }
        if (!Objects.equals(events, that.events)) {
            return false;
        }
        if (!Objects.equals(watchable, that.watchable)) {
            return false;
        }
        return Objects.equals(fsName, that.fsName);
    }
