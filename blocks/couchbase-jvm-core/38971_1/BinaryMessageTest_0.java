
        while(true) {
            for(int i=0; i < 64; i++) {
               cluster().<GetResponse>send(new GetRequest("foo" + i, bucket())).toBlocking().single();
            }
        }

