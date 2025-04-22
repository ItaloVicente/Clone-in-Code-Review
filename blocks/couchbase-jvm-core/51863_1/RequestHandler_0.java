            if (found != null) {
                if (found.length == 0) {
                    RetryHelper.retryOrCancel(environment, request, responseBuffer);
                }
                for (int i = 0; i < found.length; i++) {
                    try {
                        found[i].send(request);
                    } catch (Exception ex) {
                        request.observable().onError(ex);
                    }
