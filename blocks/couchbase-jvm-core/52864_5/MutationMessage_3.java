
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MutationMessage{");
        sb.append("key='").append(key).append('\'');
        sb.append(", content=").append(content);
        sb.append(", expiration=").append(expiration);
        sb.append(", flags=").append(flags);
        sb.append(", lockTime=").append(lockTime);
        sb.append(", cas=").append(cas);
        sb.append('}');
        return sb.toString();
    }
