        ctx.bucket().mapAdd("dsmap", "1", "1", MutationOptionBuilder.builder().createDocument(true));
        ctx.bucket().mapAdd("dsmapFull", "1", "1", MutationOptionBuilder.builder().createDocument(true));
        ctx.bucket().listAppend("dslist", "1", MutationOptionBuilder.builder().createDocument(true));
        ctx.bucket().listAppend("dslistFull", "1", MutationOptionBuilder.builder().createDocument(true));
        ctx.bucket().setAdd("dsset", 1, MutationOptionBuilder.builder().createDocument(true));
        ctx.bucket().setAdd("dssetFull", 1, MutationOptionBuilder.builder().createDocument(true));
        ctx.bucket().queuePush("dsqueue", 1, MutationOptionBuilder.builder().createDocument(true));
        ctx.bucket().queuePush("dsqueueFull", 1, MutationOptionBuilder.builder().createDocument(true));
