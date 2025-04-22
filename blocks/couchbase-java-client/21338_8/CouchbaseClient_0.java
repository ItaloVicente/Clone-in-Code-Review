      View view = asyncGetView(designDocumentName, viewName).get();
      if(view == null) {
        throw new InvalidViewException("Could not load view \"" +
          viewName + "\" for design doc \"" + designDocumentName + "\"");
      }
      return view;
