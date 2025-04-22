              view = new View(dn, ddn, viewname,
                map, reduce, ViewType.MAPREDUCE);
              break;
            }
          }
        } else if(viewType.equals(ViewType.SPATIAL) && base.has("spatial")) {
          JSONObject views = base.getJSONObject("spatial");
          Iterator<?> itr = views.keys();
          while (itr.hasNext()) {
            String curView = (String) itr.next();
            if (curView.equals(viewname)) {
              view = new View(dn, ddn, viewname,
                true, false, ViewType.SPATIAL);
