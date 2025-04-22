    private RingBufferDiagnostics diagnostics;

    public BackpressureException() {}

    public BackpressureException(RingBufferDiagnostics diagnostics) {
        super(makeString(diagnostics));
        this.diagnostics = diagnostics;
    }

    @InterfaceAudience.Public
    @InterfaceStability.Experimental
    public RingBufferDiagnostics diagostics() {
        return diagnostics;
    }

    @Override
    public String toString() {
        return makeString(diagnostics);
    }

    private static String makeString(RingBufferDiagnostics diag) {
        StringBuilder sb = new StringBuilder("Backpressure, ringbuffer contains ");
        sb.append(diag == null ? "unavailable" : diag.toString());
        return sb.toString();
    }
