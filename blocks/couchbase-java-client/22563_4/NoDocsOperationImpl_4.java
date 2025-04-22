            if(elem.has("bbox")) {
              String bbox = elem.getString("bbox");
              String geometry = elem.getString("geometry");
              rows.add(new SpatialViewRowNoDocs(id, bbox, geometry, value));
            } else {
              String key = elem.getString("key");
              rows.add(new ViewRowNoDocs(id, key, value));
            }
