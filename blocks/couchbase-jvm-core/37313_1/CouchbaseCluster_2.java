            requestHandler
                .removeNode(((RemoveNodeRequest) request).hostname())
                .map(new Func1<LifecycleState, RemoveNodeResponse>() {
                    @Override
                    public RemoveNodeResponse call(LifecycleState state) {
                        return new RemoveNodeResponse(ResponseStatus.SUCCESS);
                    }
                })
                .subscribe(request.observable());
