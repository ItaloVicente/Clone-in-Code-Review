    private RingBufferDiagnostics diagnostics;

    public BackpressureException() {}

    public BackpressureException(RingBufferDiagnostics diagnostics) {
        this.diagnostics = diagnostics;
    }

    @InterfaceAudience.Public
    @InterfaceStability.Experimental
    public RingBufferDiagnostics diagostics() {
        return diagnostics;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Backpressure, ringbuffer contains ");
        sb.append(diagnostics == null ? "unavailable" : diagnostics.toString());
        return sb.toString();
    }
