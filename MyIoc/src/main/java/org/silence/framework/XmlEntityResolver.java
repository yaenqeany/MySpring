package org.silence.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlEntityResolver implements EntityResolver {

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        if ("-//SILENCE//DTD BEAN//CN".equals(publicId) && "https://github.com/yaenqeany/MySpring/blob/master/MyIoc/src/main/java/org/silence/dtd/beans.dtd".equals(systemId)) {
            InputStream stream = new FileInputStream(new File("src/main/java/org/silence/dtd/beans.dtd"));
            return new InputSource(stream);
        }
        return null;
    }

}
