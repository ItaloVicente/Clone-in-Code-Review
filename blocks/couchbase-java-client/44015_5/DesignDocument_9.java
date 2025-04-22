            if (view instanceof SpatialView) {
                spatialViews.put(view.name(), view.map());
            } else {
                JsonObject content = JsonObject.empty();
                content.put("map", view.map());
                if (view.hasReduce()) {
                    content.put("reduce", view.reduce());
                }
                views.put(view.name(), content);
