
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ParameterizedN1qlQuery{");
        sb.append("statement=").append(statement().toString());
        sb.append(", params=").append(statementParameters().toString());
        sb.append('}');
        return sb.toString();
    }
