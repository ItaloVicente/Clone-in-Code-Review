        PNGTranscoder transcoder = new PNGTranscoder() {
        	
            protected ImageRenderer createRenderer() {
                ImageRenderer renderer = super.createRenderer();

                RenderingHints renderHints = renderer.getRenderingHints();

                renderHints.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_OFF));

                renderHints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY));

                renderHints.add(new RenderingHints(RenderingHints.KEY_DITHERING,
                    RenderingHints.VALUE_DITHER_DISABLE));

                renderHints.add(new RenderingHints(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC));

                renderHints.add(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION,
                    RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY));

                renderHints.add(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON));

                renderHints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING,
                    RenderingHints.VALUE_COLOR_RENDER_QUALITY));

                renderHints.add(new RenderingHints(RenderingHints.KEY_STROKE_CONTROL,
                    RenderingHints.VALUE_STROKE_PURE));

                renderHints.add(new RenderingHints(RenderingHints.KEY_FRACTIONALMETRICS,
                    RenderingHints.VALUE_FRACTIONALMETRICS_ON));

                renderer.setRenderingHints(renderHints);

                return renderer;
            }
        };
          
        transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(width));
        transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(height));
