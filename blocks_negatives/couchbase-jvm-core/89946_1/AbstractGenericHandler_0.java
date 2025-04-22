            if (msg.span() instanceof ThresholdLogSpan) {
                msg.span()
                    .setBaggageItem("peer.address", remoteSocket)
                    .setBaggageItem("local.address", localSocket)
                    .setBaggageItem("local.id", localId);
            }
