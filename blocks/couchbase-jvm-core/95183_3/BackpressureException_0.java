    public BackpressureException() {}

    public BackpressureException(RingBufferDiagnostics diagnostics) {
        this.diagnostics = diagnostics;
    }

    public RingBufferDiagnostics getRingBufferDiagnostics() {
        return diagnostics;
    }

    private RingBufferDiagnostics diagnostics;
