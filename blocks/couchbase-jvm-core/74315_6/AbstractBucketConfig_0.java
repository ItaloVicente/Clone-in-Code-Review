            Map<ServiceType, Integer> ports = nodesExt.get(i).ports();
            Map<ServiceType, Integer> sslPorts = nodesExt.get(i).sslPorts();

            if (!bucketCapabilities.contains(BucketCapabilities.COUCHAPI)) {
                ports.remove(ServiceType.VIEW);
                sslPorts.remove(ServiceType.VIEW);
            }

            converted.add(new DefaultNodeInfo(hostname, ports, sslPorts));
