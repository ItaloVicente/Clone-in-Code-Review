
        this.enabledServices = new HashSet<ServiceType>();
        for (NodeInfo info : nodeInfo) {
            this.enabledServices.addAll(info.services().keySet());
            this.enabledServices.addAll(info.sslServices().keySet());
        }
