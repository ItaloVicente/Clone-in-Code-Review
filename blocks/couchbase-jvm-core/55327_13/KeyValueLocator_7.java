        return locateByHostname(request.hostname(), nodes);
    }

    private static Node[] handleStatRequest(StatRequest request, Set<Node> nodes) {
        return locateByHostname(request.hostname(), nodes);
    }

    private static Node[] locateByHostname(final InetAddress hostname, Set<Node> nodes) {
