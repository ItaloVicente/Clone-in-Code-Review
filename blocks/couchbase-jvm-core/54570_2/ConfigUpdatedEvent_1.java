    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigUpdatedEvent{");
        sb.append("bucketNames=").append(bucketNames);
        sb.append(", clusterNodes=").append(clusterNodes);
        sb.append('}');
        return sb.toString();
    }
