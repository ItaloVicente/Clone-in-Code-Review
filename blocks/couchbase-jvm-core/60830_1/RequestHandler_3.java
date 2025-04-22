            throw new ServiceNotAvailableException("The View service is not enabled or no node in the cluster "
                + "supports it.");
        } else if (request instanceof QueryRequest && !(environment.queryEnabled()
            || config.serviceEnabled(ServiceType.QUERY))) {
            throw new ServiceNotAvailableException("The Query service is not enabled or no node in the "
                + "cluster supports it.");
