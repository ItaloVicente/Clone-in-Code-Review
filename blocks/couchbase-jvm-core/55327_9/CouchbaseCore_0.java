        } else if (request instanceof GetNodesRequest) {
            requestHandler
                    .getNodes((GetNodesRequest) request)
                    .map(new Func1<Node, GetNodesResponse>() {
                        @Override
                        public GetNodesResponse call(Node node) {
                            return new GetNodesResponse(ResponseStatus.SUCCESS, node.hostname());
                        }
                    })
                    .subscribe(request.observable());
