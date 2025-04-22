                } catch (final InterruptedException ex) {
                    if (!running) {
                        return;
                    } else {
                        Thread.currentThread().interrupt();
                    }
