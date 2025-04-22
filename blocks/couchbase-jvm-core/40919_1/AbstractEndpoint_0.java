        Class<? extends Channel> channelClass = NioSocketChannel.class;
        if (environment.ioPool() instanceof EpollEventLoopGroup) {
            channelClass = EpollSocketChannel.class;
        } else if (environment.ioPool() instanceof OioEventLoopGroup) {
            channelClass = OioSocketChannel.class;
        }
