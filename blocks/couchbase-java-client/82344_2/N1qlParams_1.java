    public N1qlParams readonly(boolean readonly) {
        this.readonly = readonly;
        return this;
    }

    public N1qlParams scanCap(int scanCap) {
        this.scanCap = scanCap;
        return this;
    }

    public N1qlParams pipelineBatch(int pipelineBatch) {
        this.pipelineBatch = pipelineBatch;
        return this;
    }

    public N1qlParams pipelineCap(int pipelineCap) {
        this.pipelineCap = pipelineCap;
        return this;
    }

