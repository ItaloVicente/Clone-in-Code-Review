      ((ReducedCallback) callback).gotData(vr);
      callback.receivedStatus(status);
    } catch (ParseException e) {
      exception = new OperationException(OperationErrorType.GENERAL,
        "Error parsing JSON");
    }
    callback.complete();
  }
