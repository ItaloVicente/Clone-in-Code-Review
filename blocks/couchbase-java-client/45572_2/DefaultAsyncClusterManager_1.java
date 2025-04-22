            .doOnNext(new Action1<ClusterConfigResponse>() {
                @Override
                public void call(ClusterConfigResponse response) {
                    if (!response.status().isSuccess()) {
                        if (response.config().contains("Unauthorized")) {
                            throw new InvalidPasswordException();
                        } else {
                            throw new CouchbaseException(response.status() + ": " + response.config());
                        }
                    }
                }
            })
