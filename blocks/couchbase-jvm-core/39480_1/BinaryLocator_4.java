            if (request instanceof ReplicaGetRequest) {
                request.observable().onError(new ReplicaNotConfiguredException("Replica number "
                    + ((ReplicaGetRequest) request).replica() + " not configured for bucket " + config.name()));
            } else if (request instanceof ObserveRequest) {
                request.observable().onError(new ReplicaNotConfiguredException("Replica number "
                    + ((ObserveRequest) request).replica() + " not configured for bucket " + config.name()));
            }

