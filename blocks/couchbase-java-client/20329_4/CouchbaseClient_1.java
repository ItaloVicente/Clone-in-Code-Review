      setOp.set(false, new OperationStatus(false, "Set get "
              + "execution exception "));
    }
    if (!setStatus) {
      return setOp;
    }
    try {
      observePoll(key, 0x0L, req, rep);
      setOp.set(true, setOp.getStatus());
    } catch (ObservedException e) {
      setOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedTimeoutException e) {
      setOp.set(false, new OperationStatus(false, e.getMessage()));
    } catch (ObservedModifiedException e) {
      setOp.set(false, new OperationStatus(false, e.getMessage()));
