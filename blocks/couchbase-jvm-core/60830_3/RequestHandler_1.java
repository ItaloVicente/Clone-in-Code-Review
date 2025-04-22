
            try {
                checkFeaturesForRequest(request, bucketConfig);
            } catch (ServiceNotAvailableException e) {
                request.observable().onError(e);
                return;
