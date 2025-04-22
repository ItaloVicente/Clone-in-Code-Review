            if (currentRequest != null) {
                Long st = sentRequestTimings.poll();
                if (st != null) {
                    currentOpTime = System.nanoTime() - st;
                } else {
                    currentOpTime = -1;
                }
            }

