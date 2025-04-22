
            Map<String, AlternateAddress> aa = null;
            for (NodeInfo ni : nodeInfos) {
                if (ni.hostname().equals(hostname)) {
                    aa = ni.alternateAddresses();
                }
            }
