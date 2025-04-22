
    public static GeoBoundingBoxQuery geoBoundingBox(float topLeftLon, float topLeftLat,
        float bottomRightLon, float bottomRightLat) {
        return new GeoBoundingBoxQuery(topLeftLon, topLeftLat, bottomRightLon, bottomRightLat);
    }

    public static GeoDistanceQuery geoDistance(float locationLon, float locationLat, String distance) {
        return new GeoDistanceQuery(locationLon, locationLat, distance);
    }
