            if (config.nodes().size() != nodes.size()) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Node list and configuration's partition hosts sizes : {} <> {}, rescheduling",
                            nodes.size(), config.nodes().size());
                }
                RetryHelper.retryOrCancel(env, request, responseBuffer);
                return;
