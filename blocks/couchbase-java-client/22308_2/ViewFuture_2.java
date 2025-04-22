      } else if(viewType.equals(ViewType.SPATIAL)) {
        ViewRowWithDocsSpatial r = (ViewRowWithDocsSpatial)itr.next();
        rows.add(new ViewRowWithDocsSpatial(r.getId(), r.getBbox(),
          r.getGeometry(), docMap.get(r.getId())));
      } else {
        throw new RuntimeException("Unsupported view type: " + viewType);
      }
