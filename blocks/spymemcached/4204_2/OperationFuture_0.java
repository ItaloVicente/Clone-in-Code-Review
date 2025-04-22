<<<<<<< HEAD
                if(op.isTimedOut()) {
                        throw new ExecutionException(new RuntimeException("Operation timed out."));
=======
                if(op != null && op.isTimedOut()) {
                        throw new ExecutionException(new CheckedOperationTimeoutException("Operation timed out.", op));
>>>>>>> b4f449f... sq Do not write timedout operations part 3
                }
