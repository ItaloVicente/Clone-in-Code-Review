           for(Map.Entry<String, Object> entry : rawViews.toMap().entrySet()) {
               String viewName = entry.getKey();
               JsonObject viewContent = (JsonObject) entry.getValue();
               String map = viewContent.getString("map");
               String reduce = viewContent.getString("reduce");
               views.add(DefaultView.create(viewName, map, reduce));
