        if (ctx.rbacEnabled()) {
            executor = new N1qlQueryExecutor(ctx.cluster().core(), ctx.bucketName(), ctx.adminName(), ctx.adminPassword(), false);
        } else {
            executor = new N1qlQueryExecutor(ctx.cluster().core(), ctx.bucketName(), ctx.bucketPassword(), false);
        }


