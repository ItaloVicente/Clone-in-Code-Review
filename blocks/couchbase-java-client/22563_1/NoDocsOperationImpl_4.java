            if(elem.has("bbox")) {
              String bbox = elem.getString("bbox");
              String geometry = elem.getString("geometry");
              rows.add(new SpatialViewRowNoDocs(id, bbox, geometry));
            } else {
              String key = elem.getString("key");
              String value = elem.getString("value");
              rows.add(new ViewRowNoDocs(id, key, value));
            }
