            }
        }

        size = list.size();
        IMemento[] results = new IMemento[size];
        for (int x = 0; x < size; x++) {
            results[x] = new XMLMemento(factory, list.get(x));
        }
        return results;
    }

    @Override
