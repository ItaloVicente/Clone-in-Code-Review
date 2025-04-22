        if (!(this.ioPoolShutdownHook instanceof NoOpShutdownHook)) {
            this.nettyShutdownHook = new NettyShutdownHook();
        } else {
            this.nettyShutdownHook = this.ioPoolShutdownHook;
        }

