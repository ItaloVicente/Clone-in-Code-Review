        this.partition = id;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GetRequest{");
        sb.append("key='").append(key).append('\'');
        sb.append(", partition=").append(partition);
        sb.append('}');
        return sb.toString();
