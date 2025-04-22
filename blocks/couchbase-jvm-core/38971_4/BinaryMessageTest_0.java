        while(true) {
            for(int i=0; i < 64; i++) {
                try {
                    cluster().<GetResponse>send(new GetRequest("foo" + i, bucket()))
                        .timeout(5, TimeUnit.SECONDS)
                        .toBlocking().single();
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
