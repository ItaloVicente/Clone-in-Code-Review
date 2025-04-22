    PingReport pr;
    if (CouchbaseTestContext.isMockEnabled()) {
      List<ServiceType> services = Arrays.asList(ServiceType.VIEW, ServiceType.BINARY);
      pr = ctx.bucket().ping("myReportId", services);
    } else {
      pr = ctx.bucket().ping("myReportId");
    }
