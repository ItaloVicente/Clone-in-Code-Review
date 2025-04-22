
      @Override
      public void set(Boolean o, OperationStatus s) {
        super.set(o, s);
        notifyListeners();
      }

