            Map<AlternateAddressType, AlternateAddress> aa = null;
            for (NodeInfo ni : nodeInfos) {
                if (ni.hostname().equals(hostname)) {
                    aa = ni.alternateAddresses();
                }
            }
            converted.add(new DefaultNodeInfo(hostname, ports, sslPorts, aa));
