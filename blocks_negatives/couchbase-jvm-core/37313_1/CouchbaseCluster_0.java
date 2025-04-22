            requestHandler.addNode(((AddNodeRequest) request).hostname()).subscribe(new Observer<LifecycleState>() {
                @Override
                public void onCompleted() {
                    request.observable().onCompleted();
                }

                @Override
                public void onError(Throwable e) {
                    request.observable().onError(e);
                }

                @Override
                public void onNext(LifecycleState lifecycleState) {
                    request.observable().onNext(new AddNodeResponse(ResponseStatus.SUCCESS, ((AddNodeRequest) request).hostname()));
                }
            });
