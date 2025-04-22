        ConnectionString connectionString;
            connectionString = ConnectionString.create(nodes.get(0));
        } else {
            connectionString = ConnectionString.fromHostnames(nodes);
        }
        return from(environment, connectionString);
