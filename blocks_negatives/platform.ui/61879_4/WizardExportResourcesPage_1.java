                    ArrayList results = new ArrayList();
                    for (int i = 0; i < members.length; i++) {
                        if ((members[i].getType() & resourceType) > 0) {
                            results.add(members[i]);
                        }
                    }
                    return results.toArray();
                }
                if (o instanceof ArrayList) {
                    return ((ArrayList) o).toArray();
                }
                return new Object[0];
            }
        };
