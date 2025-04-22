        return sb;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CoreEnvironment: {");
        dumpParameters(sb).append('}');
