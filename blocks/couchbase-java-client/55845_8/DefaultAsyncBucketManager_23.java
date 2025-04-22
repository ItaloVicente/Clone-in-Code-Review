        }).map(new Func1<GetSearchIndexResponse, Boolean>() {
            @Override
            public Boolean call(GetSearchIndexResponse response) {
                return response.status().isSuccess();
            }
        });
