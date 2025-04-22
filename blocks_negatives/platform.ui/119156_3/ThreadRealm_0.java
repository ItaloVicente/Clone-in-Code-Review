                synchronized (queue) {
                    if (queue.isEmpty()) {
                        queue.wait();
                    } else {
                        runnable = queue.getFirst();
                    }
