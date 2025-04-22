    try {
      if (setOp.get()) {
        observePoll(key, setOp.getCas(), req, rep);
      }
    } catch (InterruptedException e) {
      setOp.set(false, setOp.getStatus());
    } catch (ExecutionException e) {
      setOp.set(false, setOp.getStatus());
    } catch (TimeoutException e) {
      setOp.set(false, setOp.getStatus());
    } catch (IllegalArgumentException e) {
      setOp.set(false, setOp.getStatus());
    } catch (RuntimeException e) {
