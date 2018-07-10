package org.silence.framework;

import org.dom4j.Document;

public interface DocumentHolder {
    /**
     * 根据文件的路径得到dom4j里面的Document对象
     * @param filePath
     * @return
     */
    public Document getDocument(String filePath);

}
