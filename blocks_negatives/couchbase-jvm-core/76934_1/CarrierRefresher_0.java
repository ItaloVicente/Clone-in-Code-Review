            if (refreshSequence == null) {
                refreshSequence = pollSequence.flatMap(new Func1<Long, Observable<String>>() {
                    @Override
                    public Observable<String> call(Long aLong) {
                        return refreshAgainstNode(bucketName, nodeInfo.hostname());
                    }
                });
