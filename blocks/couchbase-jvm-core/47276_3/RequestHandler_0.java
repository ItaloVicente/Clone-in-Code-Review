                try {
                    checkFeaturesForRequest(request, config.bucketConfig(request.bucket()));
                } catch (UnsupportedOperationException e) {
                    request.observable().onError(e);
                    return;
                }
