            new Func1<AsyncN1qlQueryRow, IndexInfo>() {
                @Override
                public IndexInfo call(AsyncN1qlQueryRow asyncN1qlQueryRow) {
                    return new IndexInfo(asyncN1qlQueryRow.value());
                }
            };
