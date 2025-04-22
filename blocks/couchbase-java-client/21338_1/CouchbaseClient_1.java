      List<View> views = asyncGetViews(designDocumentName).get();
      if(views == null) {
        throw new InvalidViewException("Could not load views for design doc \""
          + designDocumentName + "\"");
      }
      return views;
