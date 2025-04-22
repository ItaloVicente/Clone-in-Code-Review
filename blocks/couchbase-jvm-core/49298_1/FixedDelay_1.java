    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FixedDelay{");
        sb.append("unit=").append(unit());
        sb.append(", delay=").append(delay);
        sb.append('}');
        return sb.toString();
    }
