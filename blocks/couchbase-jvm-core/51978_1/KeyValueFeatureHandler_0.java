        userAgent = environment.userAgent();
        boolean tcpNodelay = environment.tcpNodelayEnabled();

        features = new ArrayList<ServerFeatures>();
        features.add(ServerFeatures.MUTATION_SEQNO);
        features.add(tcpNodelay ? ServerFeatures.TCPNODELAY : ServerFeatures.TCPDELAY);

