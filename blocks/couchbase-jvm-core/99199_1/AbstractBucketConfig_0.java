
            if (nodeInfo == null) {
                ports.remove(ServiceType.BINARY);
                sslPorts.remove(ServiceType.BINARY);
            }

            converted.add(new DefaultNodeInfo(hostname, ports, sslPorts));
