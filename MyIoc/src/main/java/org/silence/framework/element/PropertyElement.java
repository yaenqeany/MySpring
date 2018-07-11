package org.silence.framework.element;

public class PropertyElement {
    /**
     * 用来保存property元素的name属性值
     */
    private String name;
    /**
     * 用来保存Property元素下的ref或者是value子元素
     */
    private LeafElement leafElement;
    /**
     * 取出property元素的name属性值的方法
     * @return
     */
    public String getName() {
        return name;
    }
    /**
     * 设置property元素的name属性值的方法
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * 取出property元素下面的子元素的方法，返回子元素的接口类型
     * @return
     */
    public LeafElement getLeafElement() {
        return leafElement;
    }
    /**
     * 设置property元素下面的子元素的方法
     * @param nodeElement
     */
    public void setLeafElement(LeafElement leafElement) {
        this.leafElement = leafElement;
    }
    /**
     * 构造方法将property元素的name值和下面的子元素保存到成员变量中
     * @param name
     * @param leafElement
     */
    public PropertyElement(String name, LeafElement leafElement) {
        this.name = name;
        this.leafElement = leafElement;
    }

}
