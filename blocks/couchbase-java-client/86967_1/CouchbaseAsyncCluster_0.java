    public Observable<DiagnosticsReport> diagnostics(String reportId) {
        return core.<DiagnosticsResponse>send(new DiagnosticsRequest(reportId))
          .map(new Func1<DiagnosticsResponse, DiagnosticsReport>() {
              @Override
              public DiagnosticsReport call(DiagnosticsResponse response) {
                  return response.diagnosticsReport();
              }
          });
    }

    @Override
    public Observable<DiagnosticsReport> diagnostics() {
        return diagnostics(null);
