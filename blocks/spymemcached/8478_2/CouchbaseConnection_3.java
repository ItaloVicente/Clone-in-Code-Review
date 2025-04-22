			List<CouchbaseNode> newNodes = createConnections(newServers);

			List<CouchbaseNode> mergedNodes = new ArrayList<CouchbaseNode>();
			mergedNodes.addAll(stayNodes);
			mergedNodes.addAll(newNodes);

			nodes = mergedNodes;

			nodesToShutdown.addAll(oddNodes);
		} catch (IOException e) {
			getLogger().error("Connection reconfiguration failed", e);
		} finally {
			reconfiguring = false;
		}
