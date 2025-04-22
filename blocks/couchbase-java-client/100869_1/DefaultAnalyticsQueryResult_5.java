        if (this.handle != null && this.status.equalsIgnoreCase("running")) {
            this.allRows = this.handle.rows()
                    .map(new Func1<AsyncAnalyticsQueryRow, AnalyticsQueryRow>() {
                        @Override
                        public AnalyticsQueryRow call(AsyncAnalyticsQueryRow row) {
                            return new DefaultAnalyticsQueryRow(row);
                        }
                    })
                    .toList().toBlocking().single();
        }
