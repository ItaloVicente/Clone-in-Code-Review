                    GrayscaleFilter grayFilter = new GrayscaleFilter();

                    HSBAdjustFilter desaturator = new HSBAdjustFilter();
                         desaturator.setSFactor(0.0f);

                    ContrastFilter decontrast = new ContrastFilter();
                         decontrast.setBrightness(2.9f);
                         decontrast.setContrast(0.2f);

