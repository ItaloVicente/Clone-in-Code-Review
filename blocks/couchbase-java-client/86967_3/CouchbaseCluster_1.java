    public DiagnosticsReport diagnostics() {
        return diagnostics(null);
    }

    @Override
    public DiagnosticsReport diagnostics(String reportId) {
        return Blocking.blockForSingle(
          couchbaseAsyncCluster.diagnostics(reportId),
          environment.managementTimeout(),
          TIMEOUT_UNIT
        );
