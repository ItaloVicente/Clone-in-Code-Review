    } while (loop++ < obsPollMax);

    long timeTried = obsPollMax * obsPollInt;
    TimeUnit tu = TimeUnit.MILLISECONDS;
    throw new ObservedTimeoutException("Observe Timeout - Polled"
            + " Unsuccessfully for at least " + tu.toSeconds(timeTried)
            + " seconds.");
