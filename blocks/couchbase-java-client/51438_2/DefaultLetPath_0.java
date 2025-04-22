

    @Override
    public JoinPath join(Expression from) {
        return join(from.toString());
    }

    @Override
    public JoinPath innerJoin(Expression from) {
        return innerJoin(from.toString());
    }

    @Override
    public JoinPath leftJoin(Expression from) {
        return leftJoin(from.toString());
    }

    @Override
    public JoinPath leftOuterJoin(Expression from) {
        return leftOuterJoin(from.toString());
    }

    @Override
    public NestPath nest(Expression from) {
        return nest(from.toString());
    }

    @Override
    public NestPath innerNest(Expression from) {
        return innerNest(from.toString());
    }

    @Override
    public NestPath leftNest(Expression from) {
        return leftNest(from.toString());
    }

    @Override
    public NestPath leftOuterNest(Expression from) {
        return leftOuterNest(from.toString());
    }

    @Override
    public UnnestPath unnest(Expression path) {
        return unnest(path.toString());
    }

    @Override
    public UnnestPath innerUnnest(Expression path) {
        return innerUnnest(path.toString());
    }

    @Override
    public UnnestPath leftUnnest(Expression path) {
        return leftUnnest(path.toString());
    }

    @Override
    public UnnestPath leftOuterUnnest(Expression path) {
        return leftOuterUnnest(path.toString());
    }
