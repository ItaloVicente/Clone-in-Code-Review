                                    Object next = iterator.next();
                                    if (next == null) {
                                        if (element == next) {
                                            return true;
                                        }
                                    } else if (next.equals(element)) {
