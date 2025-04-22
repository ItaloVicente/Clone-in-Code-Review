        return finalStatus.map(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String status) {
                return "success".equalsIgnoreCase(status) || "completed".equalsIgnoreCase(status);
            }
        });
    }

    @Override
    public Observable<String> status() {
        return finalStatus;
