            Map<ServiceType, Integer> ports = nodesExt.get(i).ports();
            Map<ServiceType, Integer> sslPorts = nodesExt.get(i).sslPorts();

            try {
                if (!nodeInfos.get(i).services().containsKey(ServiceType.VIEW)) {
                    ports.remove(ServiceType.VIEW);
                }
                if (!nodeInfos.get(i).sslServices().containsKey(ServiceType.VIEW)) {
                    sslPorts.remove(ServiceType.VIEW);
                }
            } catch (IndexOutOfBoundsException ex) {
            }

            converted.add(new DefaultNodeInfo(hostname, ports, sslPorts));
