      if(getViewType().equals(ViewType.MAPREDUCE)) {
        s.append(((ViewRowReduced)r).getKey());
        s.append(" : ");
        s.append(((ViewRowReduced)r).getValue());
      } else if(getViewType().equals(ViewType.SPATIAL)) {
        throw new RuntimeException("Spatial views don't support reduce");
      }
