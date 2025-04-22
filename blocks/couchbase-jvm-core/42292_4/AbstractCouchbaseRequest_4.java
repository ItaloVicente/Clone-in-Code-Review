
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + "{");
        sb.append("observable=").append(observable);
        sb.append(", bucket='").append(bucket).append('\'');
        sb.append('}');
        return sb.toString();
    }
