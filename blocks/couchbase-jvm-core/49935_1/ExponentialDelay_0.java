
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExponentialDelay{");
        sb.append("growBy ").append(growBy);
        sb.append(" " + unit());
        sb.append("; lower=").append(lower);
        sb.append(", upper=").append(upper);
        sb.append('}');
        return sb.toString();
    }
