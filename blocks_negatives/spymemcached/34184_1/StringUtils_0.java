    try {
      new Integer(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
