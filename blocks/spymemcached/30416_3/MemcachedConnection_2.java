
        long timeOnWire =
          System.nanoTime() - currentOp.getWriteCompleteTimestamp();
        metrics.updateHistogram(OVERALL_AVG_TIME_ON_WIRE_METRIC,
          (int)(timeOnWire / 1000));
        metrics.markMeter(OVERALL_RESPONSE_METRIC);
