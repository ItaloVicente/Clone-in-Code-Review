			for (File e : ls) {
                            if (e.isDirectory())
                                silent = recursiveDelete(e
                            else if (!e.delete()) {
                                if (!silent)
                                    reportDeleteFailure(failOnError
                                silent = !failOnError;
                            }
                }
