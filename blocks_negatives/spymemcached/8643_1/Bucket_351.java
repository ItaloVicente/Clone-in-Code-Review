    private final String name;
    private final Config configuration;
    private final URI streamingURI;

    private final List<Node> nodes;


    public Bucket(String name, Config configuration, URI streamingURI, List<Node> nodes) {
        this.name = name;
        this.configuration = configuration;
        this.streamingURI = streamingURI;
        this.nodes = nodes;
    }
