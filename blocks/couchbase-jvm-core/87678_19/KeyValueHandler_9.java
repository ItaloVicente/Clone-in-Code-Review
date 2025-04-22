        if (msg.getFramingExtrasLength() > 0) {
            long duration = parseServerDurationFromFrame(msg.getFramingExtras());
            ((BinaryResponse) response).serverDuration(duration);
            if (request.span() != null && duration > 0) {
                request.span().setTag("peer.latency_micros", duration);
            }
        }

