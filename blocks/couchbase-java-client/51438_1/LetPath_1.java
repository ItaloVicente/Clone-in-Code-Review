    JoinPath join(Expression from);

    JoinPath innerJoin(Expression from);

    JoinPath leftJoin(Expression from);

    JoinPath leftOuterJoin(Expression from);

    NestPath nest(Expression from);

    NestPath innerNest(Expression from);

    NestPath leftNest(Expression from);

    NestPath leftOuterNest(Expression from);

    UnnestPath unnest(Expression path);

    UnnestPath innerUnnest(Expression path);

    UnnestPath leftUnnest(Expression path);

    UnnestPath leftOuterUnnest(Expression path);

