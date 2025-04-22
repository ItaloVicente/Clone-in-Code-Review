            if(getViewType().equals(ViewType.MAPREDUCE)) {
              String key = elem.getString("key");
              String value = elem.getString("value");
              rows.add(new ViewRowNoDocs(id, key, value));
            } else {
              String bbox = elem.getString("bbox");
              String geometry = elem.getString("geometry");
              rows.add(new ViewRowNoDocsSpatial(id, bbox, geometry));
            }
