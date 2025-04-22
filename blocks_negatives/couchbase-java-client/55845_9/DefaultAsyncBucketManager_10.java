    private static Func1<AsyncN1qlQueryRow, IndexInfo> ROW_VALUE_TO_INDEXINFO =
            new Func1<AsyncN1qlQueryRow, IndexInfo>() {
                @Override
                public IndexInfo call(AsyncN1qlQueryRow asyncN1qlQueryRow) {
                    return new IndexInfo(asyncN1qlQueryRow.value());
                }
            };

