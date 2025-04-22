    }
    if (!replaceStatus) {
      return replaceOp;
    }
    try {
      observePoll(key, replaceOp.getCas(), req, rep, false);
      replaceOp.set(true, replaceOp.getStatus());
    } catch (ObservedException e) {
      replaceOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedTimeoutException e) {
      replaceOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedModifiedException e) {
      replaceOp.set(false, new OperationStatus(false, e.getMessage()));
    }
    return replaceOp;
