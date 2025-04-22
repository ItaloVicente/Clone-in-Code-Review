            try {
                checkFeaturesForRequest(request);
            } catch (UnsupportedOperationException e) {
                request.observable().onError(e);
                return;
            }

