            requestHandler
                .addNode(((AddNodeRequest) request).hostname())
                .map(new Func1<LifecycleState, AddNodeResponse>() {
                    @Override
                    public AddNodeResponse call(LifecycleState state) {
                        return new AddNodeResponse(ResponseStatus.SUCCESS, ((AddNodeRequest) request).hostname());
                    }
                })
                .subscribe(request.observable());
