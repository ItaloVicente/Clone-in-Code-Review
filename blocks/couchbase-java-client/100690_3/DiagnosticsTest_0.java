  @Test
  public void shouldAllowFromCluster() {
    Observable<DiagnosticsReport> report = ctx.cluster().async().diagnostics();
    DiagnosticsReport extracted = report.toBlocking().single();
    assertNotNull(extracted);
    assertNotNull(extracted.exportToJson());
  }

