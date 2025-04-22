
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QueryMetrics{");
        sb.append(", resultCount=").append(resultCount);
        sb.append(", errorCount=").append(errorCount);
        sb.append(", warningCount=").append(warningCount);
        sb.append(", mutationCount=").append(mutationCount);
        sb.append(", resultSize=").append(resultSize);
        sb.append(", elapsedTime='").append(elapsedTime).append('\'');
        sb.append(", executionTime='").append(executionTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
