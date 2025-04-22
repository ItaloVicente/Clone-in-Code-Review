
        clusterNodeHandler = new ClusterNodeHandler(environment, configProvider.configs());
        disruptor.handleEventsWith(clusterNodeHandler);
        disruptor.start();

        ringBuffer = disruptor.getRingBuffer();
