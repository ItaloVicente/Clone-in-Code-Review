		Collections.sort(all
                    int result = Long.signum(a.runningTimeNanos - b.runningTimeNanos);
                    if (result == 0) {
                        result = a.algorithm.name.compareTo(b.algorithm.name);
                    }
                    return result;
                });
