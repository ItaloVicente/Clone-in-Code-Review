    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ObserveResponse{");
        sb.append("observeStatus=").append(observeStatus);
        sb.append(", master=").append(master);
        sb.append(", cas=").append(cas);
        sb.append('}');
        return sb.toString();
    }
