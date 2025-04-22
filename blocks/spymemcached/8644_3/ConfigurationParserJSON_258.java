        Node node = new Node(status, hostname, ports);
        nodes.add(node);
      }
      return new Bucket(bucketname, config, new URI(streamingUri), nodes);
    } catch (JSONException e) {
      throw new ParseException(e.getMessage(), 0);
    } catch (URISyntaxException e) {
      throw new ParseException(e.getMessage(), 0);
