package org.silence.framework.holder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class XmlDocumentHolder implements DocumentHolder {
    /**
     * 由于可能配置多个配置文件所以定义一个Map类型的成员变量用配置文件的路径关联他们的Document对象
     * Map的实际类型定义成了HashMap
     */
    private Map<String, Document> documents = new HashMap<String, Document>();

    /**
     * 根据xml文件的路径得到dom4j里面的Document对象
     *
     * @param filePath
     * @return
     */
    @Override
    public Document getDocument(String filePath) {
        /**
         * 通过xml文件的路径获取出Map里保存的Document对象
         */
        Document doc = this.documents.get(filePath);
        /**
         * 如果根据xml文件的路径从Map中取出的Document对象为空，则调用本类里面定义的
         * readDocument方法获得该路径所对应文件的Document对象后，在将路径和Document
         * 对象这样一对信息保存到Map中去
         */
        if (doc == null) {
            //使用SAXReader来读取xml文件
            this.documents.put(filePath, this.readDocument(filePath));
        }
        /**
         * 返回Map中该xml文档路径所对应的Document对象
         */
        return this.documents.get(filePath);
    }

    /**
     * 根据文件的路径读取出Document对象，该方法是准备被下面的getDocument方法调用的
     * 所以定义成了private
     *
     * @param filePath
     * @return
     */
    private Document readDocument(String filePath) {
        File xmlFile = new File(filePath);
        if (xmlFile.exists()) {
            try {
                /**
                 * new一个带dtd验证的SaxReader对象
                 */
                SAXReader reader = new SAXReader(true);
//                SAXReader reader = new SAXReader();
                /**
                 * 设置用来验证的dtd的输入源
                 */
                XmlEntityResolver resolver = new XmlEntityResolver();
                reader.setEntityResolver(resolver);
                /**
                 * 根据xml的路径读取出Document对象
                 */
                Document document = reader.read(xmlFile.getAbsolutePath());
                return document;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
