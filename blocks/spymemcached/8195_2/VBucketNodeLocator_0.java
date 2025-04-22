            getLogger().error("The node locator does not have a primary for key %s.  Wanted vbucket %s which should be on server %s.", k, vbucket, server);
            getLogger().error("List of nodes has %s entries:", nodesMap.size());
            Set<String> keySet = nodesMap.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String anode = iterator.next();
                getLogger().error("MemcachedNode for %s is %s", anode, nodesMap.get(anode));
            }
