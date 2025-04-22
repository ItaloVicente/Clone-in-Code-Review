
            if (icon.disabledPath != null) {
                BufferedImage desaturated16 = desaturator.filter(
                        grayFilter.filter(sourceImage, null), null);

                BufferedImage deconstrast = decontrast.filter(desaturated16, null);

                ImageIO.write(deconstrast, "PNG", new File(icon.disabledPath, outputName));
            }
