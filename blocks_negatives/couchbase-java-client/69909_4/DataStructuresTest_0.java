        ctx.bucket().mapAdd("dsmap", "1", "1");
        ctx.bucket().mapAdd("dsmapFull", "1", "1");
        ctx.bucket().listPush("dslist", "1");
        ctx.bucket().listPush("dslistFull", "1");
        ctx.bucket().setAdd("dsset", 1);
        ctx.bucket().setAdd("dssetFull", 1);
        ctx.bucket().queueAdd("dsqueue", 1);
        ctx.bucket().queueAdd("dsqueueFull", 1);
