        while(true) {
            for (int i = 0; i < 1024; i++) {
                GetRequest request = new GetRequest(key + i, bucket);
                try {
                    cluster.<GetResponse>send(request).toBlockingObservable().single().content().toString(CharsetUtil.UTF_8);
                } catch(Exception ex) {
                    System.out.println(ex);
                }
            }
        }
