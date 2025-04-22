public class HttpLoader extends AbstractLoader{


    public HttpLoader(Cluster cluster, Environment environment, AtomicReference<List<InetAddress>> seedNodes) {
        super(cluster, environment, seedNodes);
    }
