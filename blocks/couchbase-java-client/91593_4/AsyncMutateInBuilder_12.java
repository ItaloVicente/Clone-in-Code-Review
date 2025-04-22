                if (encodeScope != null) {
                    encodeScope.close();
                    if (encodeScope.span() instanceof ThresholdLogSpan) {
                        encodeScope.span().setBaggageItem(ThresholdLogReporter.KEY_ENCODE_MICROS,
                            Long.toString(((ThresholdLogSpan) encodeScope.span()).durationMicros())
                        );
