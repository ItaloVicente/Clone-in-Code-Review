
    public static GeoBoundingBoxQuery geoBoundingBox(double topLeftLon, double topLeftLat,
        double bottomRightLon, double bottomRightLat) {
        return new GeoBoundingBoxQuery(topLeftLon, topLeftLat, bottomRightLon, bottomRightLat);
    }

    public static GeoDistanceQuery geoDistance(double locationLon, double locationLat, String distance) {
        return new GeoDistanceQuery(locationLon, locationLat, distance);
    }
