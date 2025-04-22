
        try {
            if (icon.disabledPath != null) {
                BufferedImage desaturated16 = desaturator.filter(
                    grayFilter.filter(inputImage, null), null);

                BufferedImage deconstrast = decontrast.filter(desaturated16, null);

                ImageIO.write(deconstrast, "PNG", new File(icon.disabledPath, icon.nameBase));
            }
        } catch (Exception e1) {
            log.error("Failed to render disabled icon: "  +
                               icon.nameBase, e1);
            failedIcons.add(icon);
        }
