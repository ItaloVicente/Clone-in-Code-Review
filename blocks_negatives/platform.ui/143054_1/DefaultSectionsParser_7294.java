                    markTable.put(markNumber, me);
                    if (parent == file) {
                        topLevel.add(me);
                    }
                }
            }
        }
        if (lastme != null) {
            lastme.setNumberOfLines(lineno - lastlineno - 1);
        }
        MarkElement[] results = new MarkElement[topLevel.size()];
        results = topLevel.toArray(results);
        return results;
    }
