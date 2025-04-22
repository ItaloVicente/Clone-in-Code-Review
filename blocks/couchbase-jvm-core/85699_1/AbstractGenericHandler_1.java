            String opaque = "";
            if (request instanceof BinaryRequest) {
                opaque = ", opaque " + ((BinaryRequest) request).opaque();
            }
            long start = request.creationTime();
            long latency = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - start);
