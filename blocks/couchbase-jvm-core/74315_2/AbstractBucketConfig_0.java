            Map<ServiceType, Integer> ports = nodesExt.get(i).ports();
            Map<ServiceType, Integer> sslPorts = nodesExt.get(i).sslPorts();

            if (!nodeInfos.get(i).services().containsKey(ServiceType.VIEW)) {
                ports.remove(ServiceType.VIEW);
            }
            if (!nodeInfos.get(i).sslServices().containsKey(ServiceType.VIEW)) {
                sslPorts.remove(ServiceType.VIEW);
            }

            converted.add(new DefaultNodeInfo(hostname, ports, sslPorts));
