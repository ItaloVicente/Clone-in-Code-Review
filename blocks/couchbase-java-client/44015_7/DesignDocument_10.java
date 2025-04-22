        JsonObject spatialViews = raw.getObject("spatial");
        if (spatialViews != null) {
            for(Map.Entry<String, Object> entry : spatialViews.toMap().entrySet()) {
                String viewName = entry.getKey();
                String map = (String) entry.getValue();
                views.add(SpatialView.create(viewName, map));
            }
        }
