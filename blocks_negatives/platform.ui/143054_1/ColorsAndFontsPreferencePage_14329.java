                        list.add(fontDefinitions[i]);
                    }
                }
            }
            return list.toArray(new Object[list.size()]);
        }

        private boolean parentIsInSameCategory(ColorDefinition definition) {
            String defaultsTo = definition.getDefaultsTo();
