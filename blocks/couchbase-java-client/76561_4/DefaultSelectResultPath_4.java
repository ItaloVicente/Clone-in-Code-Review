    @Override
    public SelectPath intersect() {
        element(new IntersectElement(false));
        return new DefaultSelectPath(this);
    }

    @Override
    public SelectPath intersectAll() {
        element(new IntersectElement(true));
        return new DefaultSelectPath(this);
    }

    @Override
    public SelectPath except() {
        element(new ExceptElement(false));
        return new DefaultSelectPath(this);
    }

    @Override
    public SelectPath exceptAll() {
        element(new ExceptElement(true));
        return new DefaultSelectPath(this);
    }

    @Override
    public SelectResultPath union(final SelectResultPath path) {
        element(new UnionElement(false, path));
        return new DefaultSelectResultPath(this);
    }

    @Override
    public SelectResultPath unionAll(final SelectResultPath path) {
        element(new UnionElement(true, path));
        return new DefaultSelectResultPath(this);
    }

    @Override
    public SelectResultPath intersect(final SelectResultPath path) {
        element(new IntersectElement(false, path));
        return new DefaultSelectResultPath(this);
    }

    @Override
    public SelectResultPath intersectAll(final SelectResultPath path) {
        element(new IntersectElement(true, path));
        return new DefaultSelectResultPath(this);
    }

    @Override
    public SelectResultPath except(final SelectResultPath path) {
        element(new ExceptElement(false, path));
        return new DefaultSelectResultPath(this);
    }

    @Override
    public SelectResultPath exceptAll(final SelectResultPath path) {
        element(new ExceptElement(true, path));
        return new DefaultSelectResultPath(this);
    }
