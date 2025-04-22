            DCPConnection newConnection = new DCPConnection(env(), openConnection.connectionName(), openConnection.bucket());
            connection = connections.putIfAbsent(openConnection.connectionName(), newConnection);
            if (connection == null) {
                connection = newConnection;
            }

