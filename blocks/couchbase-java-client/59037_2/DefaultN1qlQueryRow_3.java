    private final AsyncN1qlQueryRow asyncRow;

    public DefaultN1qlQueryRow(AsyncN1qlQueryRow asyncRow) {
        this.asyncRow = asyncRow;
    }

    @Override
    public byte[] byteValue() {
        return asyncRow.byteValue();
