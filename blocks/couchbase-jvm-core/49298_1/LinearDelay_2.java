
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LinearDelay{");
        sb.append("unit=").append(unit());
        sb.append(", growBy=").append(growBy);
        sb.append(", lower=").append(lower);
        sb.append(", upper=").append(upper);
        sb.append('}');
        return sb.toString();
    }
