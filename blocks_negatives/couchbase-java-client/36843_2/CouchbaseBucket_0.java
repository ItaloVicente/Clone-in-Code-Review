          Converter converter = converters.get(target);
          D document = (D) converter.newDocument();
          Object content = response.status() == ResponseStatus.OK
            ? converter.decode(response.content()) : null;
          document.id(id);
          document.content(content);
          document.cas(response.cas());
          document.expiry(0);
          return document;
