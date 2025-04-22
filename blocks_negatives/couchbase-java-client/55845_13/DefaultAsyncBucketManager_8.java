                .filter(new Func1<IndexInfo, Boolean>() {
                    @Override
                    public Boolean call(IndexInfo indexInfo) {
                        return indexInfo.state().equals("pending");
                    }
                })
                .map(new Func1<IndexInfo, String>() {
                    @Override
                    public String call(IndexInfo indexInfo) {
                        return indexInfo.name();
