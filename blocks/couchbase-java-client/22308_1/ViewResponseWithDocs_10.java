      if(getViewType().equals(ViewType.MAPREDUCE)) {
        s.append(((ViewRowNoDocs)r).getKey());
        s.append(" : ");
        s.append(((ViewRowNoDocs)r).getValue());
      } else if(getViewType().equals(ViewType.SPATIAL)) {
        s.append(((ViewRowNoDocsSpatial)r).getBbox());
        s.append(" : ");
        s.append(((ViewRowNoDocsSpatial)r).getGeometry());
      }
