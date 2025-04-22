            throw new ServiceNotAvailableException("The Search service is not enabled or no node in the "
                + "cluster supports it.");
        } else if (request instanceof DCPRequest && !(environment.dcpEnabled()
            || config.serviceEnabled(ServiceType.DCP))) {
            throw new ServiceNotAvailableException("The DCP service is not enabled or no node in the cluster "
                + "supports it.");
