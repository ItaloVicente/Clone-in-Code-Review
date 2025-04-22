        if (color1 == null && color2 == null) {
            return new RGB(0, 0, 0);
        } else if (color1 != null && color2 == null) {
            return ColorUtil.getColorValue(color1);
        } else if (color1 == null && color2 != null) {
            return ColorUtil.getColorValue(color2);
        } else {
            RGB rgb1 = ColorUtil.getColorValue(color1);
            RGB rgb2 = ColorUtil.getColorValue(color2);
            return ColorUtil.blend(rgb1, rgb2);
        }
    }
