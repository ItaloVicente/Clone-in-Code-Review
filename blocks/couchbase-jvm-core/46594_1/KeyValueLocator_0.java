            if (request instanceof ObserveRequest) {
                request.observable().onError(new ReplicaNotConfiguredException("Replica number "
                        + ((ObserveRequest) request).replica() + " not available for bucket " + config.name()));
                return null;
            }

