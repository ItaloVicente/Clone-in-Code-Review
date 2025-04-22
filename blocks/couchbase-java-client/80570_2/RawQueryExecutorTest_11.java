        AsyncRawQueryExecutor asyncExecutor;
        if (ctx.rbacEnabled()) {
            asyncExecutor = new AsyncRawQueryExecutor(ctx.bucketName(), ctx.adminName(), ctx.adminPassword(), ctx.cluster().core());
        } else {
            asyncExecutor = new AsyncRawQueryExecutor(ctx.bucketName(), ctx.bucketPassword(), ctx.cluster().core());
        }
