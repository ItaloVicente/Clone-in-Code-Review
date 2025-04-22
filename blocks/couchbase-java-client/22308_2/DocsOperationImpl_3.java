            if(getViewType().equals(ViewType.MAPREDUCE)) {
              String key = elem.getString("key");
              String value = elem.getString("value");
              rows.add(new ViewRowWithDocs(id, key, value, null));
            } else {
              String bbox = elem.getString("bbox");
              String geometry = elem.getString("geometry");
              rows.add(new ViewRowWithDocsSpatial(id, bbox, geometry, null));
            }
