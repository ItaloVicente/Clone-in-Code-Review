
                    String alternate = bucketConfig.useAlternateNetwork();
                    if (alternate != null) {
                        AlternateAddress aa = nodeInfo.alternateAddresses().get(alternate);
                        if (aa == null) {
                            throw new IllegalStateException("Instructed to use alternate address for " +
                                "seed nodes, but not present - this is a bug!");
                        }
                        newSeedHosts.add(aa.hostname());
                    } else {
                        newSeedHosts.add(nodeInfo.hostname());
                    }
