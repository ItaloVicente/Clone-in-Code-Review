public class FirstConnectedSelectionStrategy implements SelectionStrategy {
    @Override
    public Endpoint select(CouchbaseRequest request, Endpoint[] endpoints) {
        int numEndpoints = endpoints.length;
        if (numEndpoints == 0) {
            return null;
        }
        for (Endpoint endpoint : endpoints) {
            if (endpoint.isState(LifecycleState.CONNECTED)) {
                return endpoint;
            }
        }
        return null;
    }
