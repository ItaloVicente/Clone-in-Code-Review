    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(operation).append('(').append(path).append("){");
        if (status.isSuccess() || !(value instanceof Exception)) {
            sb.append("value=").append(value);
        } else if (status == ResponseStatus.FAILURE) {
            sb.append("fatal=").append(value);
        } else {
            sb.append("error=").append(status);
