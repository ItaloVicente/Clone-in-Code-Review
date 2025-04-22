    @Override
    public int hashCode() {
        int result = nodeId != null ? nodeId.hashCode() : 0;
        result = 31 * result + (events != null ? events.hashCode() : 0);
        result = 31 * result + (watchable != null ? watchable.hashCode() : 0);
        result = 31 * result + (fsName != null ? fsName.hashCode() : 0);
        return result;
    }
