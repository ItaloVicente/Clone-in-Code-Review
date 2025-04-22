    private final Status status;
    private final String hostname;
    private final Map<Port, String> ports;

    public Node(Status status, String hostname, Map<Port, String> ports) {
        this.status = status;
        this.hostname = hostname;
        this.ports = new EnumMap<Port, String>(ports);
    }
