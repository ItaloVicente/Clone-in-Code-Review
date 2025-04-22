
    /* @todo we need to investigate why the exception occurs, and if there
     *       is a better solution to the problem than just shutting down the
     *       connection. For now just invalidate the BucketMonitor, and the
     *       Client manager will recreate the connection.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        LOGGER.log(Level.INFO, "Exception occurred: ");
        if (monitor != null) {
            monitor.invalidate();
        }
