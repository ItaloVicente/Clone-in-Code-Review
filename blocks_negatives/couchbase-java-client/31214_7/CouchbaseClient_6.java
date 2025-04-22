      casr = CASResponse.EXISTS;
    }
    if (casr != CASResponse.OK) {
      return casr;
    }

    if(req == PersistTo.ZERO && rep == ReplicateTo.ZERO) {
      return casr;
    }

    try {
      observePoll(key, casOp.getCas(), req, rep, false);
    } catch (ObservedException e) {
      casr = CASResponse.OBSERVE_ERROR_IN_ARGS;
    } catch (ObservedTimeoutException e) {
      casr = CASResponse.OBSERVE_TIMEOUT;
    } catch (ObservedModifiedException e) {
      casr = CASResponse.OBSERVE_MODIFIED;
