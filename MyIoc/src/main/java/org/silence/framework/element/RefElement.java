package org.silence.framework.element;

public class RefElement implements LeafElement {
    /**
     * 定义一个成员变量用来保存ref元素的value值，也就是开始标签和结束标签之间的值
     */
    private Object value;
    /**
     * 用构造方法将ref元素的value值传给成员变量保存
     * @param value
     */
    public RefElement(Object value) {
        this.value = value;
    }
    /**
     * 重写接口中的getType方法，返回自己的类型，由于该类型是ref的所以可以直接返回ref
     */
    @Override
    public String getType() {
        return "ref";
    }
    /**
     * 重写自接口的getValue方法，返回元素的value值该value已经通过构造方法保存到成员变量
     * 中了，所以直接返回该成员变量就可以了
     */
    @Override
    public Object getValue() {

        return this.value;
    }

}
