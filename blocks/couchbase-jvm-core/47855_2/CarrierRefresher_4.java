                return raw.replace("$HOST", response.hostname().getHostName());
            }
        })
        .doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable ex) {
                LOGGER.debug("Could not fetch config from bucket \"" + bucketName + "\" against \""
                    + hostname + "\".", ex);
            }
        });
