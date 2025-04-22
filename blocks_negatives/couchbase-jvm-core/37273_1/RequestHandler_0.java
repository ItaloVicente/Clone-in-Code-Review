        locator(request).locate(request, nodes, configuration.get()).subscribe(new Observer<Node>() {
            @Override
            public void onCompleted() {
                cleanup();
            }

            @Override
            public void onError(final Throwable e) {
                cleanup();
            }

            @Override
            public void onNext(final Node node) {
                node.send(request);
