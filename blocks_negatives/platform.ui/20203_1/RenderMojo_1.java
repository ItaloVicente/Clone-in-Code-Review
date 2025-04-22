
        
        int targetWidth = nativeWidth;
        int targetHeight = nativeHeight;

        if(!highres) {
            log.info(Thread.currentThread().getName() + " "
                    + " Rasterizing (Scaling Native): " + icon.nameBase
                    + ".png at " + nativeWidth + "x" + nativeHeight);
        } else {
            log.info(Thread.currentThread().getName() + " "
                    + " Rasterizing (Scaling Half): " + icon.nameBase
                    + ".png at " + doubleWidth + "x" + doubleWidth);
            
            targetWidth = doubleWidth;
            targetHeight = doubleHeight;
        }
