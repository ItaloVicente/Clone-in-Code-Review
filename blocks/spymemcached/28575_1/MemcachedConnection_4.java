            metrics.markMeter(OVERALL_RESPONSE_METRIC);
            if (op.hasErrored()) {
              metrics.markMeter(OVERALL_RESPONSE_FAIL_METRIC);
            } else {
              metrics.markMeter(OVERALL_RESPONSE_SUCC_METRIC);
            }
